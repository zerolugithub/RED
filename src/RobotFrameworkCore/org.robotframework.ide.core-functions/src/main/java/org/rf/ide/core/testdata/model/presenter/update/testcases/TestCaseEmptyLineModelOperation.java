/*
 * Copyright 2017 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.rf.ide.core.testdata.model.presenter.update.testcases;

import java.util.List;

import org.rf.ide.core.testdata.model.AModelElement;
import org.rf.ide.core.testdata.model.ModelType;
import org.rf.ide.core.testdata.model.presenter.update.IExecutablesStepsHolderElementOperation;
import org.rf.ide.core.testdata.model.table.RobotEmptyRow;
import org.rf.ide.core.testdata.model.table.testcases.TestCase;
import org.rf.ide.core.testdata.text.read.IRobotTokenType;
import org.rf.ide.core.testdata.text.read.recognizer.RobotTokenType;

public class TestCaseEmptyLineModelOperation implements IExecutablesStepsHolderElementOperation<TestCase> {

    @Override
    public boolean isApplicable(final ModelType elementType) {
        return elementType == ModelType.EMPTY_LINE;
    }

    @Override
    public boolean isApplicable(final IRobotTokenType elementType) {
        return elementType == RobotTokenType.EMPTY_CELL;
    }

    @Override
    public AModelElement<TestCase> create(final TestCase testCase, final int index, final String actionName,
            final List<String> args, final String comment) {
        final RobotEmptyRow<TestCase> row = new RobotEmptyRow<>();
        row.setParent(testCase);
        return row;
    }

    @Override
    public AModelElement<?> insert(final TestCase testCase, final int index, final AModelElement<?> modelElement) {
        return testCase.addElement(index, modelElement);
    }

    @Override
    public void update(final AModelElement<?> modelElement, final int index, final String value) {
        // Do nothing
    }

    @Override
    public void update(final AModelElement<?> modelElement, final List<String> newArguments) {
        // Do nothing
    }
}
