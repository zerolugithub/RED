/*
 * Copyright 2018 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.views.documentation.inputs;

import static com.google.common.collect.Lists.newArrayList;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.eclipse.ui.IWorkbenchPage;
import org.rf.ide.core.executor.RobotRuntimeEnvironment;
import org.rf.ide.core.libraries.Documentation;
import org.rf.ide.core.libraries.KeywordSpecification;
import org.rf.ide.core.libraries.LibrarySpecification;
import org.robotframework.ide.eclipse.main.plugin.RedImages;
import org.robotframework.ide.eclipse.main.plugin.model.RobotProject;
import org.robotframework.ide.eclipse.main.plugin.views.documentation.DocumentationsFormatter;
import org.robotframework.ide.eclipse.main.plugin.views.documentation.LibraryUri;

public class KeywordSpecificationInput extends DocumentationViewInput {

    private final RobotProject project;

    private final LibrarySpecification libSpec;

    private final KeywordSpecification kwSpec;

    public KeywordSpecificationInput(final RobotProject project, final LibrarySpecification libSpec,
            final KeywordSpecification kwSpec) {
        this.project = project;
        this.libSpec = libSpec;
        this.kwSpec = kwSpec;
    }

    @Override
    public boolean contains(final Object wrappedInput) {
        return kwSpec == wrappedInput;
    }

    @Override
    public void prepare() {
        // nothing to prepare
    }

    @Override
    public String provideHtml() {
        final RobotRuntimeEnvironment environment = project.getRuntimeEnvironment();
        final String header = createHeader();
        final Documentation doc = kwSpec.createDocumentation(libSpec.getKeywordNames());

        return new DocumentationsFormatter(environment).format(header, doc, this::localKeywordsLinker);
    }

    private String createHeader() {
        final Optional<URI> imgUri = RedImages.getKeywordImageUri();

        final String srcHref = createShowKeywordSrcUri();
        final String srcLabel = libSpec.getName();
        final String docHref = createShowLibDocUri();

        final String source = String.format("%s [%s]", Formatters.formatHyperlink(srcHref, srcLabel),
                Formatters.formatHyperlink(docHref, "Documentation"));

        return Formatters.formatSimpleHeader(imgUri, kwSpec.getName(),
                newArrayList("Source", source),
                newArrayList("Arguments", kwSpec.createArgumentsDescriptor().getDescription()));
    }

    private String createShowKeywordSrcUri() {
        try {
            return LibraryUri.createShowKeywordSourceUri(project.getName(), libSpec.getName(), kwSpec.getName())
                    .toString();
        } catch (final URISyntaxException e) {
            return "#";
        }
    }

    private String createShowLibDocUri() {
        try {
            return LibraryUri.createShowLibraryDocUri(project.getName(), libSpec.getName()).toString();
        } catch (final URISyntaxException e) {
            return "#";
        }
    }

    private String localKeywordsLinker(final String name) {
        try {
            return LibraryUri.createShowKeywordDocUri(project.getName(), libSpec.getName(), name).toString();
        } catch (final URISyntaxException e) {
            return "#";
        }
    }

    @Override
    public void showInput(final IWorkbenchPage page) {
        // TODO : where should we open specification input? should we at all...?
    }
}