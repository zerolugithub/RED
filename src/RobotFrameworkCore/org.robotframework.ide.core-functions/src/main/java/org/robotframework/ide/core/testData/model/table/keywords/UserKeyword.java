package org.robotframework.ide.core.testData.model.table.keywords;

import java.util.LinkedList;
import java.util.List;

import org.robotframework.ide.core.testData.model.table.execution.RobotTableExecutionModel;
import org.robotframework.ide.core.testData.model.table.keywords.settings.Arguments;
import org.robotframework.ide.core.testData.model.table.keywords.settings.Documentation;
import org.robotframework.ide.core.testData.model.table.keywords.settings.Return;
import org.robotframework.ide.core.testData.model.table.keywords.settings.Teardown;
import org.robotframework.ide.core.testData.model.table.keywords.settings.Timeout;


public class UserKeyword {

    private final UserKeywordName keywordName;
    private final List<Documentation> documentation = new LinkedList<>();
    private final List<Arguments> arguments = new LinkedList<>();
    private final List<Return> returned = new LinkedList<>();
    private final List<Teardown> teardowns = new LinkedList<>();
    private final List<Timeout> timeouts = new LinkedList<>();
    private final RobotTableExecutionModel keywordSteps = new RobotTableExecutionModel();


    public UserKeyword(final UserKeywordName keywordName) {
        this.keywordName = keywordName;
    }
}
