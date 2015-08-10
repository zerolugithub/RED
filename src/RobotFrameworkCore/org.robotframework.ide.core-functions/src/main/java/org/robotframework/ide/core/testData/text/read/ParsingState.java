package org.robotframework.ide.core.testData.text.read;

public enum ParsingState {
    /**
     * 
     */
    UNKNOWN,
    /**
     * 
     */
    TRASH,
    /**
     * 
     */
    TABLE_HEADER_COLUMN,
    /**
     * 
     */
    COMMENT,
    /**
     * 
     */
    SETTING_TABLE_HEADER,
    /**
     * 
     */
    SETTING_TABLE_INSIDE,
    /**
     * 
     */
    VARIABLE_TABLE_HEADER,
    /**
     * 
     */
    VARIABLE_TABLE_INSIDE,
    /**
     * 
     */
    TEST_CASE_TABLE_HEADER,
    /**
     * 
     */
    TEST_CASE_TABLE_INSIDE,
    /**
     * 
     */
    KEYWORD_TABLE_HEADER,
    /**
     * 
     */
    KEYWORD_TABLE_INSIDE,
    /**
     * 
     */
    SETTING_LIBRARY_IMPORT,
    /**
     * 
     */
    SETTING_LIBRARY_NAME_OR_PATH,
    /**
     * 
     */
    SETTING_LIBRARY_ARGUMENTS,
    /**
     * 
     */
    SETTING_LIBRARY_IMPORT_ALIAS,
    /**
     * 
     */
    SETTING_LIBRARY_IMPORT_ALIAS_VALUE,
    /**
     * 
     */
    SETTING_VARIABLE_IMPORT,
    /**
     * 
     */
    SETTING_VARIABLE_IMPORT_PATH,
    /**
     * 
     */
    SETTING_VARIABLE_ARGUMENTS,
    /**
     * 
     */
    SETTING_RESOURCE_IMPORT,
    /**
     * 
     */
    SETTING_RESOURCE_IMPORT_PATH,
    /**
     * 
     */
    SETTING_RESOURCE_UNWANTED_ARGUMENTS,
    /**
     * 
     */
    SETTING_DOCUMENTATION,
    /**
     * 
     */
    SETTING_DOCUMENTATION_TEXT,
    /**
     * 
     */
    SETTING_METADATA,
    /**
     * 
     */
    SETTING_METADATA_KEY,
    /**
     * 
     */
    SETTING_METADATA_VALUE,
    /**
     * 
     */
    SETTING_SUITE_SETUP,
    /**
     * 
     */
    SETTING_SUITE_SETUP_KEYWORD,
    /**
     *  
     */
    SETTING_SUITE_SETUP_KEYWORD_ARGUMENT,
    /**
     * 
     */
    SETTING_SUITE_TEARDOWN,
    /**
     * 
     */
    SETTING_SUITE_TEARDOWN_KEYWORD,
    /**
     *  
     */
    SETTING_SUITE_TEARDOWN_KEYWORD_ARGUMENT,
    /**
     * 
     */
    SETTING_FORCE_TAGS,
    /**
     * 
     */
    SETTING_FORCE_TAGS_TAG_NAME,
    /**
     * 
     */
    SETTING_DEFAULT_TAGS,
    /**
     * 
     */
    SETTING_DEFAULT_TAGS_TAG_NAME,
    /**
     * 
     */
    SETTING_TEST_SETUP,
    /**
     * 
     */
    SETTING_TEST_SETUP_KEYWORD,
    /**
     *  
     */
    SETTING_TEST_SETUP_KEYWORD_ARGUMENT,
    /**
     * 
     */
    SETTING_TEST_TEARDOWN,
    /**
     * 
     */
    SETTING_TEST_TEARDOWN_KEYWORD,
    /**
     *  
     */
    SETTING_TEST_TEARDOWN_KEYWORD_ARGUMENT,
    /**
     * 
     */
    SETTING_TEST_TEMPLATE,
    /**
     * 
     */
    SETTING_TEST_TEMPLATE_KEYWORD,
    /**
     * 
     */
    SETTING_TEST_TEMPLATE_KEYWORD_UNWANTED_ARGUMENTS,
    /**
     * 
     */
    SETTING_TEST_TIMEOUT,
    /**
     * 
     */
    SETTING_TEST_TIMEOUT_VALUE,
    /**
     * 
     */
    SETTING_TEST_TIMEOUT_MESSAGE_ARGUMENTS,
    /**
     * 
     */
    SETTING_UNKNOWN,
    /**
     * 
     */
    SETTING_UNKNOWN_TRASH_ELEMENT,
    /**
     * 
     */
    SCALAR_VARIABLE_DECLARATION,
    /**
     * 
     */
    SCALAR_VARIABLE_VALUE,
    /**
     * 
     */
    LIST_VARIABLE_DECLARATION,
    /**
     * 
     */
    LIST_VARIABLE_VALUE,
    /**
     * 
     */
    DICTIONARY_VARIABLE_DECLARATION,
    /**
     * 
     */
    DICTIONARY_VARIABLE_VALUE,
    /**
     * 
     */
    VARIABLE_UNKNOWN,
    /**
     * 
     */
    VARIABLE_UNKNOWN_VALUE,
    /**
     * 
     */
    TEST_CASE_DECLARATION,
    /**
     * 
     */
    TEST_CASE_SETTING_DOCUMENTATION_DECLARATION,
    /**
     * 
     */
    TEST_CASE_SETTING_DOCUMENTATION_TEXT,
    /**
     * 
     */
    TEST_CASE_SETTING_SETUP,
    /**
     * 
     */
    TEST_CASE_SETTING_SETUP_KEYWORD,
    /**
     *  
     */
    TEST_CASE_SETTING_SETUP_KEYWORD_ARGUMENT,
    /**
     * 
     */
    TEST_CASE_SETTING_TAGS,
    /**
     * 
     */
    TEST_CASE_SETTING_TAGS_TAG_NAME,
    /**
     * 
     */
    TEST_CASE_SETTING_TEARDOWN,
    /**
     * 
     */
    TEST_CASE_SETTING_TEARDOWN_KEYWORD,
    /**
     *  
     */
    TEST_CASE_SETTING_TEARDOWN_KEYWORD_ARGUMENT,
    /**
     * 
     */
    TEST_CASE_SETTING_TEST_TEMPLATE,
    /**
     * 
     */
    TEST_CASE_SETTING_TEST_TEMPLATE_KEYWORD,
    /**
     * 
     */
    TEST_CASE_SETTING_TEST_TEMPLATE_KEYWORD_UNWANTED_ARGUMENTS,
    /**
     * 
     */
    TEST_CASE_SETTING_TEST_TIMEOUT,
    /**
     * 
     */
    TEST_CASE_SETTING_TEST_TIMEOUT_VALUE,
    /**
     * 
     */
    TEST_CASE_SETTING_TEST_TIMEOUT_MESSAGE_ARGUMENTS,
    /**
     * 
     */
    KEYWORD_DECLARATION,
    /**
     * 
     */
    KEYWORD_SETTING_DOCUMENTATION_DECLARATION,
    /**
     * 
     */
    KEYWORD_SETTING_DOCUMENTATION_TEXT,
    /**
     * 
     */
    KEYWORD_SETTING_TAGS,
    /**
     * 
     */
    KEYWORD_SETTING_TAGS_TAG_NAME,
    /**
     * 
     */
    KEYWORD_SETTING_ARGUMENTS,
    /**
     * 
     */
    KEYWORD_SETTING_ARGUMENTS_ARGUMENT_VALUE,
    /**
     * 
     */
    KEYWORD_SETTING_RETURN,
    /**
     * 
     */
    KEYWORD_SETTING_RETURN_VALUE,
    /**
     * 
     */
    KEYWORD_SETTING_TEARDOWN,
    /**
     * 
     */
    KEYWORD_SETTING_TEARDOWN_KEYWORD,
    /**
     *  
     */
    KEYWORD_SETTING_TEARDOWN_KEYWORD_ARGUMENT,
    /**
     * 
     */
    KEYWORD_SETTING_TIMEOUT,
    /**
     * 
     */
    KEYWORD_SETTING_TIMEOUT_VALUE,
    /**
     * 
     */
    KEYWORD_SETTING_TIMEOUT_MESSAGE_ARGUMENTS;
}
