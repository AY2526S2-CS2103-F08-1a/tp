package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.commands.CommandTestUtil.BODY_FAT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HEIGHT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_FAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MeasureCommand;
import seedu.address.logic.commands.MeasureCommand.MeasureDescriptor;
import seedu.address.model.person.BodyFatPercentage;
import seedu.address.model.person.Height;
import seedu.address.model.person.Weight;

public class MeasureCommandParserTest {

    private final MeasureCommandParser parser = new MeasureCommandParser();

    /**
     * Parses input missing the required index and verifies failure.
     */
    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, HEIGHT_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeasureCommand.MESSAGE_USAGE));
    }

    /**
     * Parses input with index but without any measurement prefix and verifies failure.
     */
    @Test
    public void parse_noMeasurementPrefix_failure() {
        assertParseFailure(parser, "1", MeasureCommand.MESSAGE_NOT_EDITED);
    }

    /**
     * Parses input with an invalid height value and verifies failure.
     */
    @Test
    public void parse_invalidHeight_failure() {
        assertParseFailure(parser, "1 " + PREFIX_HEIGHT + "49.9", Height.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses input with an invalid weight value and verifies failure.
     */
    @Test
    public void parse_invalidWeight_failure() {
        assertParseFailure(parser, "1 " + PREFIX_WEIGHT + "10.0", Weight.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses input with an invalid body fat percentage value and verifies failure.
     */
    @Test
    public void parse_invalidBodyFat_failure() {
        assertParseFailure(parser, "1 " + PREFIX_BODY_FAT + "90.0", BodyFatPercentage.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses input with duplicate measurement prefixes and verifies failure.
     */
    @Test
    public void parse_duplicatePrefix_failure() {
        assertParseFailure(parser, "1 " + HEIGHT_DESC_AMY + " " + PREFIX_HEIGHT + "170.0",
                getErrorMessageForDuplicatePrefixes(PREFIX_HEIGHT));
    }

    /**
     * Parses input with a single valid measurement prefix and verifies success.
     */
    @Test
    public void parse_validSinglePrefix_success() {
        MeasureDescriptor descriptor = new MeasureDescriptor();
        descriptor.setHeight(new Height("165.5"));
        MeasureCommand expectedCommand = new MeasureCommand(INDEX_FIRST_PERSON, descriptor);

        assertParseSuccess(parser, "1" + HEIGHT_DESC_AMY, expectedCommand);
    }

    /**
     * Parses input with multiple valid measurement prefixes and verifies success.
     */
    @Test
    public void parse_validMultiplePrefixes_success() {
        MeasureDescriptor descriptor = new MeasureDescriptor();
        descriptor.setHeight(new Height("165.5"));
        descriptor.setWeight(new Weight("58.0"));
        descriptor.setBodyFatPercentage(new BodyFatPercentage("22.5"));
        MeasureCommand expectedCommand = new MeasureCommand(INDEX_FIRST_PERSON, descriptor);

        assertParseSuccess(parser, "1" + HEIGHT_DESC_AMY + WEIGHT_DESC_AMY + BODY_FAT_DESC_AMY, expectedCommand);
    }
}

