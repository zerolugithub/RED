/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.core.testData.model.table.testCases.mapping;

import java.util.List;
import java.util.Stack;

import org.robotframework.ide.core.testData.model.FilePosition;
import org.robotframework.ide.core.testData.model.RobotFileOutput;
import org.robotframework.ide.core.testData.model.table.mapping.ElementsUtility;
import org.robotframework.ide.core.testData.model.table.mapping.IParsingMapper;
import org.robotframework.ide.core.testData.model.table.mapping.ParsingStateHelper;
import org.robotframework.ide.core.testData.model.table.testCases.TestCase;
import org.robotframework.ide.core.testData.model.table.testCases.TestCaseSetup;
import org.robotframework.ide.core.testData.text.read.IRobotTokenType;
import org.robotframework.ide.core.testData.text.read.ParsingState;
import org.robotframework.ide.core.testData.text.read.RobotLine;
import org.robotframework.ide.core.testData.text.read.recognizer.RobotToken;
import org.robotframework.ide.core.testData.text.read.recognizer.RobotTokenType;


public class TestCaseSetupKeywordArgumentMapper implements IParsingMapper {

    private final ElementsUtility utility;
    private final ParsingStateHelper stateHelper;


    public TestCaseSetupKeywordArgumentMapper() {
        this.utility = new ElementsUtility();
        this.stateHelper = new ParsingStateHelper();
    }


    @Override
    public RobotToken map(RobotLine currentLine,
            Stack<ParsingState> processingState,
            RobotFileOutput robotFileOutput, RobotToken rt, FilePosition fp,
            String text) {
        List<IRobotTokenType> types = rt.getTypes();
        types.remove(RobotTokenType.UNKNOWN);
        types.add(0, RobotTokenType.TEST_CASE_SETTING_SETUP_KEYWORD_ARGUMENT);

        rt.setRaw(new StringBuilder(text));
        rt.setText(new StringBuilder(text));
        List<TestCase> testCases = robotFileOutput.getFileModel()
                .getTestCaseTable().getTestCases();
        TestCase testCase = testCases.get(testCases.size() - 1);
        List<TestCaseSetup> setups = testCase.getSetups();
        TestCaseSetup setup = setups.get(setups.size() - 1);
        setup.addArgument(rt);

        processingState
                .push(ParsingState.TEST_CASE_SETTING_SETUP_KEYWORD_ARGUMENT);

        return rt;
    }


    @Override
    public boolean checkIfCanBeMapped(RobotFileOutput robotFileOutput,
            RobotLine currentLine, RobotToken rt, String text,
            Stack<ParsingState> processingState) {
        boolean result = false;
        ParsingState state = stateHelper.getCurrentStatus(processingState);
        if (state == ParsingState.TEST_CASE_SETTING_SETUP) {
            List<TestCase> tests = robotFileOutput.getFileModel()
                    .getTestCaseTable().getTestCases();
            List<TestCaseSetup> setups = tests.get(tests.size() - 1)
                    .getSetups();
            result = utility.checkIfHasAlreadyKeywordName(setups);
        } else if (state == ParsingState.TEST_CASE_SETTING_SETUP_KEYWORD
                || state == ParsingState.TEST_CASE_SETTING_SETUP_KEYWORD_ARGUMENT) {
            result = true;
        }

        return result;
    }

}
