package org.robotframework.ide.eclipse.main.plugin;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.robotframework.ide.core.executor.RobotRuntimeEnvironment;
import org.robotframework.ide.eclipse.main.plugin.project.RobotProjectConfig;
import org.robotframework.ide.eclipse.main.plugin.project.RobotProjectConfig.LibraryType;
import org.robotframework.ide.eclipse.main.plugin.project.RobotProjectConfig.ReferencedLibrary;
import org.robotframework.ide.eclipse.main.plugin.project.RobotProjectConfigReader;
import org.robotframework.ide.eclipse.main.plugin.project.RobotProjectConfigReader.CannotReadProjectConfigurationException;
import org.robotframework.ide.eclipse.main.plugin.project.build.LibspecsFolder;
import org.robotframework.ide.eclipse.main.plugin.project.library.LibrarySpecification;
import org.robotframework.ide.eclipse.main.plugin.project.library.LibrarySpecificationReader;
import org.robotframework.ide.eclipse.main.plugin.project.library.LibrarySpecificationReader.CannotReadlibrarySpecificationException;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

public class RobotProject extends RobotContainer {

    private List<LibrarySpecification> stdLibsSpecs;
    private List<LibrarySpecification> refLibsSpecs;
    private RobotProjectConfig configuration;

    RobotProject(final IProject project) {
        super(null, project);
    }

    public IProject getProject() {
        return (IProject) container;
    }

    public String getVersion() {
        readProjectConfigurationIfNeeded();
        final RobotRuntimeEnvironment env = getRuntimeEnvironment();
        return env == null ? "???" : env.getVersion();
    }

    public List<LibrarySpecification> getStandardLibraries() {
        readProjectConfigurationIfNeeded();
        final RobotRuntimeEnvironment env = getRuntimeEnvironment();
        if (env == null) {
            return newArrayList();
        } else if (stdLibsSpecs != null) {
            return stdLibsSpecs;
        }

        try {
            stdLibsSpecs = newArrayList(Iterables.transform(env.getStandardLibrariesNames(),
                    new Function<String, LibrarySpecification>() {
                        @Override
                        public LibrarySpecification apply(final String libraryName) {
                            final IFile file = LibspecsFolder.get(getProject()).getSpecFile(libraryName);
                            return LibrarySpecificationReader.readSpecification(file);
                        }
                    }));
            return stdLibsSpecs;
        } catch (final CannotReadlibrarySpecificationException e) {
            return newArrayList();
        }
    }

    public boolean hasReferencedLibraries() {
        readProjectConfigurationIfNeeded();
        if (refLibsSpecs != null && !refLibsSpecs.isEmpty()) {
            return true;
        }
        return configuration != null && configuration.hasReferencedLibraries();
    }

    public List<LibrarySpecification> getReferencedLibraries() {
        readProjectConfigurationIfNeeded();
        if (refLibsSpecs != null) {
            return refLibsSpecs;
        } else if (configuration == null) {
            return newArrayList();
        }

        refLibsSpecs = newArrayList(Iterables.filter(
                Iterables.transform(configuration.getLibraries(),
                new Function<ReferencedLibrary, LibrarySpecification>() {
                    @Override
                    public LibrarySpecification apply(final ReferencedLibrary lib) {
                        try {
                            if (lib.provideType() == LibraryType.VIRTUAL) {
                                final IPath path = Path.fromPortableString(lib.getPath());
                                final IResource libspec = getProject().getParent().findMember(path);
                                if (libspec != null && libspec.getType() == IResource.FILE) {
                                    return LibrarySpecificationReader.readSpecification((IFile) libspec);
                                }
                                return null;
                            } else if (lib.provideType() == LibraryType.JAVA) {
                                final IFile file = LibspecsFolder.get(getProject()).getSpecFile(lib.getName());
                                return LibrarySpecificationReader.readSpecification(file);
                            } else {
                                return null;
                            }
                        } catch (final CannotReadlibrarySpecificationException e) {
                            return null;
                        }
                    }
                }), Predicates.<LibrarySpecification> notNull()));
        return refLibsSpecs;
    }

    private synchronized RobotProjectConfig readProjectConfigurationIfNeeded() {
        if (configuration == null) {
            try {
                configuration = new RobotProjectConfigReader().readConfiguration(getProject());
            } catch (final CannotReadProjectConfigurationException e) {
                // oh well...
            }
        }
        return configuration;
    }

    public void clear() {
        configuration = null;
        stdLibsSpecs = null;
        refLibsSpecs = null;
    }

    public RobotRuntimeEnvironment getRuntimeEnvironment() {
        readProjectConfigurationIfNeeded();
        if (configuration == null || configuration.usesPreferences()) {
            return RobotFramework.getDefault().getActiveRobotInstallation();
        }
        return RobotFramework.getDefault().getRobotInstallation(configuration.providePythonLocation());
    }

    public IFile getConfigurationFile() {
        return getProject().getFile(RobotProjectConfig.FILENAME);
    }

    public IFile getFile(final String filename) {
        return getProject().getFile(filename);
    }

    public List<String> getClasspath() {
        readProjectConfigurationIfNeeded();
        if (configuration != null) {
            final Set<String> cp = newHashSet(".");
            for (final ReferencedLibrary lib : configuration.getLibraries()) {
                if (lib.provideType() == LibraryType.JAVA) {
                    cp.add(lib.getPath());
                }
            }
            return newArrayList(cp);
        }
        return newArrayList(".");
    }
}
