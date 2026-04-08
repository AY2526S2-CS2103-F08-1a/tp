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

        ParsedMeasurements measurements = parseMeasurements(argMultimap);
        if (!measurements.hasAnyValue()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE));
        }
        return new MeasureCommand(index,
                measurements.height(), measurements.weight(), measurements.bodyFatPercentage());
    }

    private ParsedMeasurements parseMeasurements(ArgumentMultimap argMultimap) throws ParseException {
        requireNonNull(argMultimap);
        StringJoiner validationErrors = new StringJoiner("\n");

        // Parse each optional prefixed value and collect all validation failures in one pass.
        Optional<Height> height =
                parseOptionalValue(argMultimap, PREFIX_HEIGHT, ParserUtil::parseHeight, validationErrors);
        Optional<Weight> weight =
                parseOptionalValue(argMultimap, PREFIX_WEIGHT, ParserUtil::parseWeight, validationErrors);
        Optional<BodyFatPercentage> bodyFatPercentage =
                parseOptionalValue(argMultimap, PREFIX_BODY_FAT, ParserUtil::parseBodyFatPercentage, validationErrors);

        throwIfAnyValidationErrors(validationErrors);
        return new ParsedMeasurements(height.orElse(null), weight.orElse(null), bodyFatPercentage.orElse(null));
    }

    private void throwIfAnyValidationErrors(StringJoiner validationErrors) throws ParseException {
        requireNonNull(validationErrors);
        if (validationErrors.length() > 0) {
            throw new ParseException(validationErrors.toString());
        }
    }

    /** Parses one optional field and returns Optional.empty when absent or invalid while collecting parse errors. */
    private <T> Optional<T> parseOptionalValue(ArgumentMultimap argMultimap,
                                               Prefix prefix,
                                               FieldParser<T> parser,
                                               StringJoiner validationErrors) {
        requireNonNull(argMultimap);
        requireNonNull(prefix);
        requireNonNull(parser);
        requireNonNull(validationErrors);

        Optional<String> rawInput = argMultimap.getValue(prefix);
        if (rawInput.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(parser.parse(rawInput.get()));
        } catch (ParseException pe) {
            validationErrors.add(pe.getMessage());
            return Optional.empty();
        }
    }

    private Index parseIndex(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE), pe);
        }
    }

    @FunctionalInterface
    private interface FieldParser<T> {
        T parse(String rawInput) throws ParseException;
    }

    private static final class ParsedMeasurements {
        private final Height height;
        private final Weight weight;
        private final BodyFatPercentage bodyFatPercentage;

        private ParsedMeasurements(Height height, Weight weight, BodyFatPercentage bodyFatPercentage) {
            this.height = height;
            this.weight = weight;
            this.bodyFatPercentage = bodyFatPercentage;
        }

        private Height height() {
            return height;
        }

        private Weight weight() {
            return weight;
        }

        private BodyFatPercentage bodyFatPercentage() {
            return bodyFatPercentage;
        }

        private boolean hasAnyValue() {
            return height != null || weight != null || bodyFatPercentage != null;
        }
    }

}
