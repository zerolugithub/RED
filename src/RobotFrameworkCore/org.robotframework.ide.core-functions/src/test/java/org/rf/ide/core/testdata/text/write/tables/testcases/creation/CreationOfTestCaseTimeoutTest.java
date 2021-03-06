/*
 * Copyright 2016 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.rf.ide.core.testdata.text.write.tables.testcases.creation;

import org.junit.Test;
import org.rf.ide.core.testdata.model.FileFormat;
import org.rf.ide.core.testdata.model.RobotFile;
import org.rf.ide.core.testdata.model.table.LocalSetting;
import org.rf.ide.core.testdata.model.table.TestCaseTable;
import org.rf.ide.core.testdata.model.table.testcases.TestCase;
import org.rf.ide.core.testdata.text.read.recognizer.RobotToken;
import org.rf.ide.core.testdata.text.write.NewRobotFileTestHelper;
import org.rf.ide.core.testdata.text.write.RobotFormatParameterizedTest;

public class CreationOfTestCaseTimeoutTest extends RobotFormatParameterizedTest {

    public CreationOfTestCaseTimeoutTest(final String extension, final FileFormat format) {
        super(extension, format);
    }

    @Test
    public void test_emptyFile_and_thanCreateTestCaseTimeout_withoutKeywordName_andTimeoutDecOnly() throws Exception {
        test_timeoutDecOnly("EmptyTestCaseTimeoutNoTestName", "");
    }

    @Test
    public void test_emptyFile_and_thanCreateTestCaseTimeout_withKeywordName_andTimeoutDecOnly() throws Exception {
        test_timeoutDecOnly("EmptyTestCaseTimeout", "TestCase");
    }

    private void test_timeoutDecOnly(final String fileNameWithoutExt, final String userKeywordName) throws Exception {
        // prepare
        final String filePath = convert(fileNameWithoutExt);
        final RobotFile modelFile = NewRobotFileTestHelper.getModelFileToModify("2.9");

        // test data prepare
        modelFile.includeTestCaseTableSection();
        final TestCaseTable testCaseTable = modelFile.getTestCaseTable();

        final RobotToken keyName = new RobotToken();
        keyName.setText(userKeywordName);
        final TestCase test = new TestCase(keyName);
        testCaseTable.addTest(test);
        test.newTimeout(0);

        // verify
        NewRobotFileTestHelper.assertNewModelTheSameAsInFile(filePath, modelFile);
    }

    @Test
    public void test_emptyFile_and_thanCreateTestCaseTimeout_withoutKeywordName_andTimeoutDec_withComment()
            throws Exception {
        test_timeoutDec_withCommentOnly("EmptyTestCaseTimeoutCommentNoTestName", "");
    }

    @Test
    public void test_emptyFile_and_thanCreateTestCaseTimeout_withKeywordName_andTimeoutDec_withComment()
            throws Exception {
        test_timeoutDec_withCommentOnly("EmptyTestCaseTimeoutComment", "TestCase");
    }

    private void test_timeoutDec_withCommentOnly(final String fileNameWithoutExt, final String userKeywordName)
            throws Exception {
        // prepare
        final String filePath = convert(fileNameWithoutExt);
        final RobotFile modelFile = NewRobotFileTestHelper.getModelFileToModify("2.9");

        // test data prepare
        modelFile.includeTestCaseTableSection();
        final TestCaseTable testCaseTable = modelFile.getTestCaseTable();

        final RobotToken keyName = new RobotToken();
        keyName.setText(userKeywordName);
        final TestCase test = new TestCase(keyName);
        testCaseTable.addTest(test);
        final LocalSetting<TestCase> testTimeout = test.newTimeout(0);

        testTimeout.addCommentPart("cm1");
        testTimeout.addCommentPart("cm2");
        testTimeout.addCommentPart("cm3");

        // verify
        NewRobotFileTestHelper.assertNewModelTheSameAsInFile(filePath, modelFile);
    }

    @Test
    public void test_emptyFile_and_thanCreateTestCaseTimeout_withoutKeywordName_andTimeoutDec_withValue()
            throws Exception {
        test_timeoutDec_withComment_andValue("EmptyTestCaseTimeoutWithValueNoTestName", "");
    }

    @Test
    public void test_emptyFile_and_thanCreateTestCaseTimeout_withKeywordName_andTimeoutDec_withValue()
            throws Exception {
        test_timeoutDec_withComment_andValue("EmptyTestCaseTimeoutWithValue", "TestCase");
    }

    private void test_timeoutDec_withComment_andValue(final String fileNameWithoutExt, final String userKeywordName)
            throws Exception {
        // prepare
        final String filePath = convert(fileNameWithoutExt);
        final RobotFile modelFile = NewRobotFileTestHelper.getModelFileToModify("2.9");

        // test data prepare
        modelFile.includeTestCaseTableSection();
        final TestCaseTable testCaseTable = modelFile.getTestCaseTable();

        final RobotToken keyName = new RobotToken();
        keyName.setText(userKeywordName);
        final TestCase test = new TestCase(keyName);
        testCaseTable.addTest(test);
        final LocalSetting<TestCase> testTimeout = test.newTimeout(0);

        testTimeout.addToken("1 hours");

        // verify
        NewRobotFileTestHelper.assertNewModelTheSameAsInFile(filePath, modelFile);
    }

    @Test
    public void test_emptyFile_and_thanCreateTestCaseTimeout_withoutKeywordName_andTimeoutDec_withValue_andComment()
            throws Exception {
        test_timeoutDec_withComment_andValue_andComment("EmptyTestCaseTimeoutWithValueAndCommentNoTestName", "");
    }

    @Test
    public void test_emptyFile_and_thanCreateTestCaseTimeout_withKeywordName_andTimeoutDec_withValue_andComment()
            throws Exception {
        test_timeoutDec_withComment_andValue_andComment("EmptyTestCaseTimeoutWithValueAndComment", "TestCase");
    }

    private void test_timeoutDec_withComment_andValue_andComment(final String fileNameWithoutExt,
            final String userKeywordName) throws Exception {
        // prepare
        final String filePath = convert(fileNameWithoutExt);
        final RobotFile modelFile = NewRobotFileTestHelper.getModelFileToModify("2.9");

        // test data prepare
        modelFile.includeTestCaseTableSection();
        final TestCaseTable testCaseTable = modelFile.getTestCaseTable();

        final RobotToken keyName = new RobotToken();
        keyName.setText(userKeywordName);
        final TestCase test = new TestCase(keyName);
        testCaseTable.addTest(test);
        final LocalSetting<TestCase> testTimeout = test.newTimeout(0);

        testTimeout.addToken("1 hours");
        testTimeout.addCommentPart("cm1");
        testTimeout.addCommentPart("cm2");
        testTimeout.addCommentPart("cm3");

        // verify
        NewRobotFileTestHelper.assertNewModelTheSameAsInFile(filePath, modelFile);
    }

    @Test
    public void test_emptyFile_and_thanCreateTestCaseTimeout_withoutKeywordName_andTimeoutDec_withValue_and3MsgArgs()
            throws Exception {
        test_timeoutDec_withComment_andValue_and3MsgArgs("TestCaseTimeoutWithValueAnd3MsgArgsNoTestName", "");
    }

    @Test
    public void test_emptyFile_and_thanCreateTestCaseTimeout_withKeywordName_andTimeoutDec_withValue_and3MsgArgs()
            throws Exception {
        test_timeoutDec_withComment_andValue_and3MsgArgs("TestCaseTimeoutWithValueAnd3MsgArgs", "TestCase");
    }

    private void test_timeoutDec_withComment_andValue_and3MsgArgs(final String fileNameWithoutExt,
            final String userKeywordName) throws Exception {
        // prepare
        final String filePath = convert(fileNameWithoutExt);
        final RobotFile modelFile = NewRobotFileTestHelper.getModelFileToModify("2.9");

        // test data prepare
        modelFile.includeTestCaseTableSection();
        final TestCaseTable testCaseTable = modelFile.getTestCaseTable();

        final RobotToken keyName = new RobotToken();
        keyName.setText(userKeywordName);
        final TestCase test = new TestCase(keyName);
        testCaseTable.addTest(test);
        final LocalSetting<TestCase> testTimeout = test.newTimeout(0);

        testTimeout.addToken("1 hours");
        testTimeout.addToken("msg1");
        testTimeout.addToken("msg2");
        testTimeout.addToken("msg3");

        // verify
        NewRobotFileTestHelper.assertNewModelTheSameAsInFile(filePath, modelFile);
    }

    @Test
    public void test_emptyFile_and_thanCreateTestCaseTimeout_withoutKeywordName_andTimeoutDec_withValue_and3MsgArgs_andComment()
            throws Exception {
        test_timeoutDec_withComment_andValue_and3MsgArgs_andComment(
                "TestCaseTimeoutWithValueAnd3MsgArgsAndCommentNoTestName", "");
    }

    @Test
    public void test_emptyFile_and_thanCreateTestCaseTimeout_withKeywordName_andTimeoutDec_withValue_and3MsgArgs_andComment()
            throws Exception {
        test_timeoutDec_withComment_andValue_and3MsgArgs_andComment("TestCaseTimeoutWithValueAnd3MsgArgsAndComment",
                "TestCase");
    }

    private void test_timeoutDec_withComment_andValue_and3MsgArgs_andComment(final String fileNameWithoutExt,
            final String userKeywordName) throws Exception {
        // prepare
        final String filePath = convert(fileNameWithoutExt);
        final RobotFile modelFile = NewRobotFileTestHelper.getModelFileToModify("2.9");

        // test data prepare
        modelFile.includeTestCaseTableSection();
        final TestCaseTable testCaseTable = modelFile.getTestCaseTable();

        final RobotToken keyName = new RobotToken();
        keyName.setText(userKeywordName);
        final TestCase test = new TestCase(keyName);
        testCaseTable.addTest(test);
        final LocalSetting<TestCase> testTimeout = test.newTimeout(0);

        testTimeout.addToken("1 hours");
        testTimeout.addToken("msg1");
        testTimeout.addToken("msg2");
        testTimeout.addToken("msg3");

        testTimeout.addCommentPart("cm1");
        testTimeout.addCommentPart("cm2");
        testTimeout.addCommentPart("cm3");

        // verify
        NewRobotFileTestHelper.assertNewModelTheSameAsInFile(filePath, modelFile);
    }

    private String convert(final String fileName) {
        return "testCases/setting/timeout/new/" + fileName + "." + getExtension();
    }
}
