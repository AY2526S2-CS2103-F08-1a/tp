package seedu.address.logic.commands;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Central registry of all commands in the application.
 *
 * This is the single place that imports every concrete {@code Command} class.
 * Other classes (e.g., {@code HelpCommand}) interact with commands through
 * this registry only, achieving the decoupling described by the Command Pattern —
 * the invoker does not need to know each specific command type.
 *
 * Adding a new command requires only one change here: no other class needs
 * updating to expose the new command's usage in the help system.
 */
public class CommandRegistry {

    /**
     * Ordered map of command word to usage message.
     * Insertion order determines display order in 'help'.
     */
    private static final Map<String, String> USAGE_MAP;

    static {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE);
        map.put(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE);
        map.put(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE);
        map.put(NoteCommand.COMMAND_WORD, NoteCommand.MESSAGE_USAGE);
        map.put(PlanCommand.COMMAND_WORD, PlanCommand.MESSAGE_USAGE);
        map.put(StatusCommand.COMMAND_WORD, StatusCommand.MESSAGE_USAGE);
        map.put(MeasureCommand.COMMAND_WORD, MeasureCommand.MESSAGE_USAGE);
        map.put(RateCommand.COMMAND_WORD, RateCommand.MESSAGE_USAGE);
        map.put(LogCommand.COMMAND_WORD, LogCommand.MESSAGE_USAGE);
        map.put(LastCommand.COMMAND_WORD, LastCommand.MESSAGE_USAGE);
        map.put(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE);
        map.put(FilterCommand.COMMAND_WORD, FilterCommand.MESSAGE_USAGE);
        map.put(SortCommand.COMMAND_WORD, SortCommand.MESSAGE_USAGE);
        map.put(ViewCommand.COMMAND_WORD, ViewCommand.MESSAGE_USAGE);
        map.put(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE);
        map.put(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_USAGE);
        map.put(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE);
        map.put(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE);
        USAGE_MAP = Collections.unmodifiableMap(map);
    }

    private CommandRegistry() {} // Utility class — not instantiable

    /**
     * Returns an unmodifiable ordered map of command word to usage message.
     */
    public static Map<String, String> getUsageMap() {
        return USAGE_MAP;
    }
}
