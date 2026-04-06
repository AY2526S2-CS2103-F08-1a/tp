package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_FAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.Optional;
import java.util.StringJoiner;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MeasureCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.BodyFatPercentage;
import seedu.address.model.person.Height;
import seedu.address.model.person.Weight;

/**
 * Parses input arguments and creates a new MeasureCommand object.
 */
public class MeasureCommandParser implements Parser<MeasureCommand> {

    @Override
    public MeasureCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_BODY_FAT);
        Index index = parseIndex(argMultimap);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_BODY_FAT);

        Optional<String> heightInput = argMultimap.getValue(PREFIX_HEIGHT);
        Optional<String> weightInput = argMultimap.getValue(PREFIX_WEIGHT);
        Optional<String> bodyFatInput = argMultimap.getValue(PREFIX_BODY_FAT);

        if (!hasAnyMeasurementPrefix(heightInput, weightInput, bodyFatInput)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE));
        }

        StringJoiner validationErrors = new StringJoiner("\n");
        Height height = parseHeight(heightInput, validationErrors);
        Weight weight = parseWeight(weightInput, validationErrors);
        BodyFatPercentage bodyFatPercentage = parseBodyFatPercentage(bodyFatInput, validationErrors);

        if (validationErrors.length() > 0) {
            throw new ParseException(validationErrors.toString());
        }

        assert hasAnyParsedMeasurement(height, weight, bodyFatPercentage)
                : "Invariant broken: successful parse must produce at least one measurement.";

        return new MeasureCommand(index, height, weight, bodyFatPercentage);
    }

    private Index parseIndex(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE), pe);
        }
    }

    private boolean hasAnyMeasurementPrefix(Optional<String> heightInput,
                                            Optional<String> weightInput,
                                            Optional<String> bodyFatInput) {
        return heightInput.isPresent() || weightInput.isPresent() || bodyFatInput.isPresent();
    }

    private boolean hasAnyParsedMeasurement(Height height, Weight weight, BodyFatPercentage bodyFatPercentage) {
        return height != null || weight != null || bodyFatPercentage != null;
    }

    private Height parseHeight(Optional<String> heightInput, StringJoiner validationErrors) {
        if (!heightInput.isPresent()) {
            return null;
        }

        try {
            return ParserUtil.parseHeight(heightInput.get());
        } catch (ParseException pe) {
            validationErrors.add(pe.getMessage());
            return null;
        }
    }

    private Weight parseWeight(Optional<String> weightInput, StringJoiner validationErrors) {
        if (!weightInput.isPresent()) {
            return null;
        }

        try {
            return ParserUtil.parseWeight(weightInput.get());
        } catch (ParseException pe) {
            validationErrors.add(pe.getMessage());
            return null;
        }
    }

    private BodyFatPercentage parseBodyFatPercentage(Optional<String> bodyFatInput,
                                                     StringJoiner validationErrors) {
        if (!bodyFatInput.isPresent()) {
            return null;
        }

        try {
            return ParserUtil.parseBodyFatPercentage(bodyFatInput.get());
        } catch (ParseException pe) {
            validationErrors.add(pe.getMessage());
            return null;
        }
    }
}
