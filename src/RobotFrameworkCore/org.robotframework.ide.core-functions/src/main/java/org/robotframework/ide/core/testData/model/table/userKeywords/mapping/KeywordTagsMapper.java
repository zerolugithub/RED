package org.robotframework.ide.core.testData.model.table.userKeywords.mapping;

import java.util.Stack;

import org.robotframework.ide.core.testData.model.FilePosition;
import org.robotframework.ide.core.testData.model.RobotFileOutput;
import org.robotframework.ide.core.testData.model.table.userKeywords.KeywordTags;
import org.robotframework.ide.core.testData.model.table.userKeywords.UserKeyword;
import org.robotframework.ide.core.testData.text.read.ParsingState;
import org.robotframework.ide.core.testData.text.read.RobotLine;
import org.robotframework.ide.core.testData.text.read.recognizer.RobotToken;
import org.robotframework.ide.core.testData.text.read.recognizer.RobotTokenType;


public class KeywordTagsMapper extends AKeywordSettingDeclarationMapper {

    public KeywordTagsMapper() {
        super(RobotTokenType.KEYWORD_SETTING_TAGS);
    }


    @Override
    public RobotToken map(RobotLine currentLine,
            Stack<ParsingState> processingState,
            RobotFileOutput robotFileOutput, RobotToken rt, FilePosition fp,
            String text) {
        rt.setType(RobotTokenType.KEYWORD_SETTING_TAGS);
        rt.setText(new StringBuilder(text));

        UserKeyword keyword = findOrCreateNearestKeyword(currentLine,
                processingState, robotFileOutput, rt, fp);
        KeywordTags tags = new KeywordTags(rt);
        keyword.addTag(tags);

        processingState.push(ParsingState.KEYWORD_SETTING_TAGS);

        return rt;
    }
}
