package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_helpWithoutArgs_success() {
        String expectedMessage = CommandRegistry.getUsageMap().values().stream()
                .collect(Collectors.joining("\n\n"));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, true, false);
        assertCommandSuccess(new HelpCommand(""), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpWithSpecificCommand_success() {
        CommandResult expectedCommandResult = new CommandResult(AddCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand("add"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpWithUnknownCommand_success() {
        String expectedMessage = "Unknown command: xyz\n\nType 'help' to see all available commands.";
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false);
        assertCommandSuccess(new HelpCommand("xyz"), model, expectedCommandResult, expectedModel);
    }
}
