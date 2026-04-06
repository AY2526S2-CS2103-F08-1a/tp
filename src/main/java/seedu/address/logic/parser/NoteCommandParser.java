package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_APPEND;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Note;

/**
 * Parses input arguments and creates a new NoteCommand object
 */
public class NoteCommandParser implements Parser<NoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NoteCommand and returns an
     * NoteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public NoteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NOTE, PREFIX_NOTE_APPEND);

        Index index = parseIndex(argMultimap);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NOTE, PREFIX_NOTE_APPEND);

        boolean hasReplacePrefix = argMultimap.getValue(PREFIX_NOTE).isPresent();
        boolean hasAppendPrefix = argMultimap.getValue(PREFIX_NOTE_APPEND).isPresent();

        if (hasReplacePrefix == hasAppendPrefix) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
        }

        if (hasReplacePrefix) {
            return new NoteCommand(index, new Note(argMultimap.getValue(PREFIX_NOTE).orElse("")), false);
        }
        return new NoteCommand(index, new Note(argMultimap.getValue(PREFIX_NOTE_APPEND).orElse("")), true);
    }

    private Index parseIndex(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE), pe);
        }
    }
}
