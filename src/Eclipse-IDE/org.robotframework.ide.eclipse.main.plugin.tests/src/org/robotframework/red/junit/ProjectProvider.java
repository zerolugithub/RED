/*
 * Copyright 2016 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.red.junit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.rf.ide.core.project.RobotProjectConfig;
import org.robotframework.ide.eclipse.main.plugin.project.RedEclipseProjectConfigWriter;
import org.robotframework.ide.eclipse.main.plugin.project.RobotProjectNature;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

/**
 * @author Michal Anglart
 */
public class ProjectProvider implements TestRule {

    private final String projectName;

    private IProject project;

    public ProjectProvider(final Class<?> testClass) {
        this(testClass.getSimpleName());
    }

    public ProjectProvider(final String projectName) {
        this.projectName = projectName;
    }

    public IProject getProject() {
        return project;
    }

    /**
     * Configures the project to have robot nature. Use wisely since this adds builder
     * to the project, so in some situations project building/validation can start.
     *
     * @throws CoreException
     */
    public void addRobotNature() throws CoreException {
        RobotProjectNature.addRobotNature(project, null, () -> true);
    }

    public void removeRobotNature() throws CoreException {
        RobotProjectNature.removeRobotNature(project, null, () -> true);
    }

    public void configure() throws IOException, CoreException {
        configure(new RobotProjectConfig());
    }

    public void configure(final RobotProjectConfig config) throws IOException, CoreException {
        createFile(RobotProjectConfig.FILENAME, "");
        new RedEclipseProjectConfigWriter().writeConfiguration(config, project);
    }

    public void deconfigure() throws CoreException {
        project.findMember(RobotProjectConfig.FILENAME).delete(true, null);
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                try {
                    if (project == null) {
                        project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
                        project.create(null);
                        project.open(null);
                        project.refreshLocal(IResource.DEPTH_INFINITE, null);
                    }
                    base.evaluate();
                } finally {
                    if (project != null && project.exists()) {
                        project.refreshLocal(IResource.DEPTH_INFINITE, null);
                        project.delete(true, null);
                    }
                }
            }
        };
    }

    public IFolder getDir(final String dirPath) {
        return project.getFolder(Path.fromPortableString(dirPath));
    }

    public IFolder createDir(final String dirPath) throws CoreException {
        final IFolder directory = getDir(dirPath);
        directory.create(true, true, null);
        return directory;
    }

    public IFile getFile(final String filePath) {
        return project.getFile(Path.fromPortableString(filePath));
    }

    public IFile createFile(final String filePath, final String... lines) throws IOException, CoreException {
        return createFile(getFile(filePath), lines);
    }

    public IFile createFile(final IFile file, final String... lines) throws IOException, CoreException {
        try (InputStream source = new ByteArrayInputStream(String.join("\n", lines).getBytes(Charsets.UTF_8))) {
            return createFile(file, source);
        }
    }

    public IFile createFile(final String filePath, final InputStream fileSource) throws IOException, CoreException {
        return createFile(getFile(filePath), fileSource);
    }

    public IFile createFile(final IFile file, final InputStream fileSource) throws IOException, CoreException {
        if (file.exists()) {
            file.setContents(fileSource, true, false, null);
        } else {
            file.create(fileSource, true, null);
        }
        project.refreshLocal(IResource.DEPTH_INFINITE, null);
        return file;
    }

    public String getFileContent(final String filePath) throws IOException, CoreException {
        return getFileContent(getFile(filePath));
    }

    public String getFileContent(final IFile file) throws IOException, CoreException {
        try (final InputStream stream = file.getContents()) {
            return CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
        }
    }

}
