/*
 * Copyright 2018 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.project.build.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.rf.ide.core.testdata.model.table.ARobotSectionTable;
import org.rf.ide.core.testdata.model.table.TableHeader;
import org.robotframework.ide.eclipse.main.plugin.model.RobotCasesSection;
import org.robotframework.ide.eclipse.main.plugin.model.RobotSuiteFile;
import org.robotframework.ide.eclipse.main.plugin.project.build.RobotArtifactsValidator.ModelUnitValidator;
import org.robotframework.ide.eclipse.main.plugin.project.build.RobotProblem;
import org.robotframework.ide.eclipse.main.plugin.project.build.ValidationReportingStrategy;
import org.robotframework.ide.eclipse.main.plugin.project.build.causes.SuiteFileProblem;
import org.robotframework.ide.eclipse.main.plugin.project.build.validation.versiondependent.VersionDependentValidators;

public class RobotRpaSuiteFileValidator extends RobotFileValidator {

    public RobotRpaSuiteFileValidator(final ValidationContext context, final IFile file,
            final ValidationReportingStrategy reporter) {
        super(context, file, reporter);
    }

    @Override
    public void validate(final RobotSuiteFile fileModel, final FileValidationContext validationContext)
            throws CoreException {
        reportVersionSpecificProblems(fileModel, validationContext);

        super.validate(fileModel, validationContext);

        validateIfThereAreNoForbiddenSections(fileModel);
    }

    private void reportVersionSpecificProblems(final RobotSuiteFile fileModel,
            final FileValidationContext validationContext) {
        final VersionDependentValidators versionDependentValidators = new VersionDependentValidators(validationContext,
                reporter);
        versionDependentValidators.getTaskSuiteFileValidators(fileModel).forEach(ModelUnitValidator::validate);
    }

    private void validateIfThereAreNoForbiddenSections(final RobotSuiteFile fileModel) {
        fileModel.findSection(RobotCasesSection.class)
                .map(RobotCasesSection::getLinkedElement)
                .map(ARobotSectionTable::getHeaders)
                .map(headers -> headers.get(0))
                .map(TableHeader::getDeclaration)
                .ifPresent(headerToken -> reporter.handleProblem(
                        RobotProblem.causedBy(SuiteFileProblem.RPA_SUITE_FILE_CONTAINS_TESTS), file, headerToken));
    }
}
