package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_FAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MeasureCommand;
import seedu.address.logic.commands.MeasureCommand.MeasureDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MeasureCommand object.
 */
public class MeasureCommandParser implements Parser<MeasureCommand> {

    @Override
    public MeasureCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_BODY_FAT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_BODY_FAT);

        MeasureDescriptor measureDescriptor = new MeasureDescriptor();

        if (argMultimap.getValue(PREFIX_HEIGHT).isPresent()) {
            measureDescriptor.setHeight(ParserUtil.parseHeight(argMultimap.getValue(PREFIX_HEIGHT).get()));
        }
        if (argMultimap.getValue(PREFIX_WEIGHT).isPresent()) {
            measureDescriptor.setWeight(ParserUtil.parseWeight(argMultimap.getValue(PREFIX_WEIGHT).get()));
        }
        if (argMultimap.getValue(PREFIX_BODY_FAT).isPresent()) {
            measureDescriptor.setBodyFatPercentage(
                    ParserUtil.parseBodyFatPercentage(argMultimap.getValue(PREFIX_BODY_FAT).get()));
        }

        if (!measureDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MeasureCommand.MESSAGE_NOT_EDITED);
        }

        return new MeasureCommand(index, measureDescriptor);
    }
}

