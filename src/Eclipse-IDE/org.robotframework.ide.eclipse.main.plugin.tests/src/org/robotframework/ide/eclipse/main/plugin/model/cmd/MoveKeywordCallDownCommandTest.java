/*
* Copyright 2016 Nokia Solutions and Networks
* Licensed under the Apache License, Version 2.0,
* see license.txt file for details.
*/
package org.robotframework.ide.eclipse.main.plugin.model.cmd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.List;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.junit.Test;
import org.robotframework.ide.eclipse.main.plugin.mockeclipse.ContextInjector;
import org.robotframework.ide.eclipse.main.plugin.mockmodel.RobotSuiteFileCreator;
import org.robotframework.ide.eclipse.main.plugin.model.RobotCase;
import org.robotframework.ide.eclipse.main.plugin.model.RobotCasesSection;
import org.robotframework.ide.eclipse.main.plugin.model.RobotElement;
import org.robotframework.ide.eclipse.main.plugin.model.RobotKeywordCall;
import org.robotframework.ide.eclipse.main.plugin.model.RobotKeywordDefinition;
import org.robotframework.ide.eclipse.main.plugin.model.RobotKeywordsSection;
import org.robotframework.ide.eclipse.main.plugin.model.RobotModelEvents;
import org.robotframework.ide.eclipse.main.plugin.model.RobotSuiteFile;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.EditorCommand;

public class MoveKeywordCallDownCommandTest {

    @Test
    public void nothingHappens_whenTryingToMoveExecutableWhichIsBottommostInCases() {
        final List<RobotCase> cases = createTestCasesWithoutSettings();
        final RobotCase lastCase = cases.get(2);
        final RobotKeywordCall call = lastCase.getChildren().get(1);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(call));
        command.execute();
        assertThat(lastCase.getChildren()).extracting(RobotElement::getName).containsExactly("Log1", "Log2");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(lastCase.getChildren()).extracting(RobotElement::getName).containsExactly("Log1", "Log2");

        verifyZeroInteractions(eventBroker);
    }

    @Test
    public void nothingHappens_whenTryingToMoveExecutableWhichIsBottommostInKeywords() {
        final List<RobotKeywordDefinition> keywords = createKeywordsWithoutSettings();
        final RobotKeywordDefinition lastKeyword = keywords.get(2);
        final RobotKeywordCall call = lastKeyword.getChildren().get(1);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(call));
        command.execute();
        assertThat(lastKeyword.getChildren()).extracting(RobotElement::getName).containsExactly("Log1", "Log2");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(lastKeyword.getChildren()).extracting(RobotElement::getName).containsExactly("Log1", "Log2");

        verifyZeroInteractions(eventBroker);
    }

    @Test
    public void rowIsProperlyMovedDown_whenMovingExecutableWhichHasExecutableAfterInsideCase() {
        final List<RobotCase> cases = createTestCasesWithoutSettings();
        final RobotCase firstCase = cases.get(0);
        final RobotKeywordCall call = firstCase.getChildren().get(1);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(call));
        command.execute();
        assertThat(firstCase.getChildren()).extracting(RobotElement::getName).containsExactly("Log1", "Log3", "Log2");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(firstCase.getChildren()).extracting(RobotElement::getName).containsExactly("Log1", "Log2", "Log3");

        verify(eventBroker, times(2)).send(RobotModelEvents.ROBOT_KEYWORD_CALL_MOVED, firstCase);
        verifyNoMoreInteractions(eventBroker);
    }

    @Test
    public void rowIsProperlyMovedDown_whenMovingExecutableWhichHasExecutableAfterInsideKeyword() {
        final List<RobotKeywordDefinition> keywords = createKeywordsWithoutSettings();
        final RobotKeywordDefinition firstKeyword = keywords.get(0);
        final RobotKeywordCall call = firstKeyword.getChildren().get(1);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(call));
        command.execute();
        assertThat(firstKeyword.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Log1", "Log3", "Log2");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(firstKeyword.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Log1", "Log2", "Log3");

        verify(eventBroker, times(2)).send(RobotModelEvents.ROBOT_KEYWORD_CALL_MOVED, firstKeyword);
        verifyNoMoreInteractions(eventBroker);
    }

    @Test
    public void rowIsProperlyMovedDown_whenMovingSettingWhichHasExecutableAfterInsideCase() {
        final List<RobotCase> cases = createTestCasesWithSettings();
        final RobotCase firstCase = cases.get(0);
        final RobotKeywordCall call = firstCase.getChildren().get(1);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(call));
        command.execute();
        assertThat(firstCase.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Log1", "Documentation", "Log2", "Setup", "Log3");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(firstCase.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Documentation", "Log1", "Log2", "Setup", "Log3");

        verify(eventBroker, times(2)).send(RobotModelEvents.ROBOT_KEYWORD_CALL_MOVED, firstCase);
        verifyNoMoreInteractions(eventBroker);
    }

    @Test
    public void rowIsProperlyMovedDown_whenMovingSettingWhichHasSettingAfterInsideCase() {
        final List<RobotCase> cases = createTestCasesWithSettings();
        final RobotCase firstCase = cases.get(0);
        final RobotKeywordCall call = firstCase.getChildren().get(0);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(call));
        command.execute();
        assertThat(firstCase.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Documentation", "Tags", "Log1", "Log2", "Setup", "Log3");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(firstCase.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Documentation", "Log1", "Log2", "Setup", "Log3");

        verify(eventBroker, times(2)).send(RobotModelEvents.ROBOT_KEYWORD_CALL_MOVED, firstCase);
        verifyNoMoreInteractions(eventBroker);
    }

    @Test
    public void rowIsProperlyMovedDown_whenMovingSettingWhichHasExecutableAfterInsideKeywords() {
        final List<RobotKeywordDefinition> keywords = createKeywordsWithSettings();
        final RobotKeywordDefinition firstKeyword = keywords.get(0);
        final RobotKeywordCall call = firstKeyword.getChildren().get(1);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(call));
        command.execute();
        assertThat(firstKeyword.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Log1", "Documentation", "Log2", "Setup", "Log3");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(firstKeyword.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Documentation", "Log1", "Log2", "Setup", "Log3");

        verify(eventBroker, times(2)).send(RobotModelEvents.ROBOT_KEYWORD_CALL_MOVED, firstKeyword);
        verifyNoMoreInteractions(eventBroker);
    }

    @Test
    public void rowIsProperlyMovedDown_whenMovingExecutableWhichHasSettingAfterInsideCase() {
        final List<RobotCase> cases = createTestCasesWithSettings();
        final RobotCase firstCase = cases.get(0);
        final RobotKeywordCall call = firstCase.getChildren().get(3);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(call));
        command.execute();
        assertThat(firstCase.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Documentation", "Log1", "Setup", "Log2", "Log3");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(firstCase.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Documentation", "Log1", "Log2", "Setup", "Log3");

        verify(eventBroker, times(2)).send(RobotModelEvents.ROBOT_KEYWORD_CALL_MOVED, firstCase);
        verifyNoMoreInteractions(eventBroker);
    }

    @Test
    public void rowIsProperlyMovedDown_whenMovingExecutableWhichHasExecutableAfterInsideKeywords() {
        final List<RobotKeywordDefinition> keywords = createKeywordsWithSettings();
        final RobotKeywordDefinition firstKeyword = keywords.get(0);
        final RobotKeywordCall call = firstKeyword.getChildren().get(2);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(call));
        command.execute();
        assertThat(firstKeyword.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Documentation", "Log2", "Log1", "Setup", "Log3");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(firstKeyword.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Documentation", "Log1", "Log2", "Setup", "Log3");

        verify(eventBroker, times(2)).send(RobotModelEvents.ROBOT_KEYWORD_CALL_MOVED, firstKeyword);
        verifyNoMoreInteractions(eventBroker);
    }

    @Test
    public void rowIsProperlyMovedDown_whenMovingSettingWhichHasSettingAfterInsideKeywords() {
        final List<RobotKeywordDefinition> keywords = createKeywordsWithSettings();
        final RobotKeywordDefinition firstKeyword = keywords.get(0);
        final RobotKeywordCall call = firstKeyword.getChildren().get(0);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(call));
        command.execute();
        assertThat(firstKeyword.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Documentation", "Tags", "Log1", "Log2", "Setup", "Log3");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(firstKeyword.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Documentation", "Log1", "Log2", "Setup", "Log3");

        verify(eventBroker, times(2)).send(RobotModelEvents.ROBOT_KEYWORD_CALL_MOVED, firstKeyword);
        verifyNoMoreInteractions(eventBroker);
    }

    @Test
    public void rowIsNotMovedToNextCase_whenItIsBottommostInCase() {
        final List<RobotCase> cases = createTestCasesWithoutSettings();
        final RobotCase sndCase = cases.get(1);
        final RobotCase trdCase = cases.get(2);
        final RobotKeywordCall callToMove = sndCase.getChildren().get(0);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(callToMove));
        command.execute();

        assertThat(sndCase.getChildren()).extracting(RobotElement::getName).containsExactly("Log");
        assertThat(trdCase.getChildren()).extracting(RobotElement::getName).containsExactly("Log1", "Log2");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(sndCase.getChildren()).extracting(RobotElement::getName).containsExactly("Log");
        assertThat(trdCase.getChildren()).extracting(RobotElement::getName).containsExactly("Log1", "Log2");

        verifyZeroInteractions(eventBroker);
    }

    @Test
    public void rowIsNotMovedToNextKeyword_whenItIsBottommostInKeyword() {
        final List<RobotKeywordDefinition> keywords = createKeywordsWithoutSettings();
        final RobotKeywordDefinition sndKeyword = keywords.get(1);
        final RobotKeywordDefinition trdKeyword = keywords.get(2);
        final RobotKeywordCall callToMove = sndKeyword.getChildren().get(0);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(callToMove));
        command.execute();

        assertThat(sndKeyword.getChildren()).extracting(RobotElement::getName).containsExactly("Log");
        assertThat(trdKeyword.getChildren()).extracting(RobotElement::getName).containsExactly("Log1", "Log2");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(sndKeyword.getChildren()).extracting(RobotElement::getName).containsExactly("Log");
        assertThat(trdKeyword.getChildren()).extracting(RobotElement::getName).containsExactly("Log1", "Log2");

        verifyZeroInteractions(eventBroker);
    }

    @Test
    public void rowIsNotMovedToNextCaseWithSettings_whenItIsBottomostInCase() {
        final List<RobotCase> cases = createTestCasesWithSettings();
        final RobotCase fstCase = cases.get(0);
        final RobotCase sndCase = cases.get(1);
        final RobotKeywordCall callToMove = fstCase.getChildren().get(5);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(callToMove));
        command.execute();
        assertThat(fstCase.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Documentation", "Log1", "Log2", "Setup", "Log3");
        assertThat(sndCase.getChildren()).extracting(RobotElement::getName).containsExactly("Setup", "Log");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(fstCase.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Documentation", "Log1", "Log2", "Setup", "Log3");
        assertThat(sndCase.getChildren()).extracting(RobotElement::getName).containsExactly("Setup", "Log");

        verifyZeroInteractions(eventBroker);
    }

    @Test
    public void rowIsNotMovedToNextKeywordWithSettings_whenItIsBottomostInKeyword() {
        final List<RobotKeywordDefinition> cases = createKeywordsWithSettings();
        final RobotKeywordDefinition fstKeyword = cases.get(0);
        final RobotKeywordDefinition sndKeyword = cases.get(1);
        final RobotKeywordCall callToMove = fstKeyword.getChildren().get(5);

        final IEventBroker eventBroker = mock(IEventBroker.class);
        final MoveKeywordCallDownCommand command = ContextInjector.prepareContext()
                .inWhich(eventBroker)
                .isInjectedInto(new MoveKeywordCallDownCommand(callToMove));
        command.execute();
        assertThat(fstKeyword.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Documentation", "Log1", "Log2", "Setup", "Log3");
        assertThat(sndKeyword.getChildren()).extracting(RobotElement::getName).containsExactly("Teardown", "Log");

        for (final EditorCommand undo : command.getUndoCommands()) {
            undo.execute();
        }
        assertThat(fstKeyword.getChildren()).extracting(RobotElement::getName)
                .containsExactly("Tags", "Documentation", "Log1", "Log2", "Setup", "Log3");
        assertThat(sndKeyword.getChildren()).extracting(RobotElement::getName).containsExactly("Teardown", "Log");

        verifyZeroInteractions(eventBroker);
    }

    private static List<RobotCase> createTestCasesWithSettings() {
        final RobotSuiteFile model = new RobotSuiteFileCreator().appendLine("*** Test Cases ***")
                .appendLine("case 1")
                .appendLine("  [Tags]  a  b")
                .appendLine("  [Documentation]  doc")
                .appendLine("  Log1  10")
                .appendLine("  Log2  10")
                .appendLine("  [Setup]  Log  9")
                .appendLine("  Log3  10")
                .appendLine("case 2")
                .appendLine("  [Setup]  Log  xxx")
                .appendLine("  Log  20")
                .appendLine("case 3")
                .appendLine("  Log1  30")
                .appendLine("  Log2  30")
                .build();
        final RobotCasesSection section = model.findSection(RobotCasesSection.class).get();
        return section.getChildren();
    }

    private static List<RobotCase> createTestCasesWithoutSettings() {
        final RobotSuiteFile model = new RobotSuiteFileCreator().appendLine("*** Test Cases ***")
                .appendLine("case 1")
                .appendLine("  Log1  10")
                .appendLine("  Log2  10")
                .appendLine("  Log3  10")
                .appendLine("case 2")
                .appendLine("  Log  20")
                .appendLine("case 3")
                .appendLine("  Log1  30")
                .appendLine("  Log2  30")
                .build();
        final RobotCasesSection section = model.findSection(RobotCasesSection.class).get();
        return section.getChildren();
    }

    private static List<RobotKeywordDefinition> createKeywordsWithSettings() {
        final RobotSuiteFile model = new RobotSuiteFileCreator().appendLine("*** Keywords ***")
                .appendLine("keyword 1")
                .appendLine("  [Tags]  a  b")
                .appendLine("  [Documentation]  doc")
                .appendLine("  Log1  10")
                .appendLine("  Log2  10")
                .appendLine("  [Setup]  Log  9")
                .appendLine("  Log3  10")
                .appendLine("keyword 2")
                .appendLine("  [Teardown]  Log  xxx")
                .appendLine("  Log  20")
                .appendLine("keyword 3")
                .appendLine("  Log1  30")
                .appendLine("  Log2  30")
                .build();
        final RobotKeywordsSection section = model.findSection(RobotKeywordsSection.class).get();
        return section.getChildren();
    }

    private static List<RobotKeywordDefinition> createKeywordsWithoutSettings() {
        final RobotSuiteFile model = new RobotSuiteFileCreator().appendLine("*** Keywords ***")
                .appendLine("keyword 1")
                .appendLine("  Log1  10")
                .appendLine("  Log2  10")
                .appendLine("  Log3  10")
                .appendLine("keyword 2")
                .appendLine("  Log  20")
                .appendLine("keyword 3")
                .appendLine("  Log1  30")
                .appendLine("  Log2  30")
                .build();
        final RobotKeywordsSection section = model.findSection(RobotKeywordsSection.class).get();
        return section.getChildren();
    }
}
