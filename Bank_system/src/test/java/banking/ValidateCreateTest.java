package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateCreateTest {
    CommandValidator commandValidator;
    Bank account;

    @BeforeEach
    void setUp() {
        account = new Bank();
        commandValidator = new CommandValidator(account);
    }

    @Test
    void create_checking() {
        assertTrue(commandValidator.validate("Create checking 12345678 2.5"));
    }

    @Test
    void create_savings() {
        assertTrue(commandValidator.validate("Create savings 12345678 2.5"));
    }

    @Test
    void create_cd() {
        assertTrue(commandValidator.validate("Create cd 12345678 2.5 1000"));
    }

    @Test
    void create_case_insensitive() {
        assertTrue(commandValidator.validate("cReate checking 12345678 2.5"));
        assertTrue(commandValidator.validate("CrEaTe savings 12345678 2.5"));
        assertTrue(commandValidator.validate("CREATE cd 12345678 2.5 1000"));
    }

    @Test
    void missing_create() {
        assertFalse(commandValidator.validate("checking 12345678 2.5"));
        assertFalse(commandValidator.validate("cd 12345678 2.5 2500"));
    }

    @Test
    void account_type_case_insensitive() {
        assertTrue(commandValidator.validate("Create CheCkING 12345678 2.5"));
        assertTrue(commandValidator.validate("Create saVinGs 12345678 2.5"));
        assertTrue(commandValidator.validate("Create cD 12345678 2.5 1000"));
    }

    @Test
    void create_spelling_typo() {
        assertFalse(commandValidator.validate("Creae checking 12345678 2.5"));
        assertFalse(commandValidator.validate("Cret savings 12345678 2.5"));
        assertFalse(commandValidator.validate("createee cd 12345678 2.5 1000"));
    }

    @Test
    void account_type_spelling_typo() {
        assertFalse(commandValidator.validate("Create Chekingg 12345678 2.5"));
        assertFalse(commandValidator.validate("Create saving 12345678 2.5"));
        assertFalse(commandValidator.validate("Create ccd 12345678 2.5 1000"));
    }

    @Test
    void missing_create_account_type() {
        assertFalse(commandValidator.validate("create 12345678 2.5"));
        assertFalse(commandValidator.validate("create 12345678 2.5 2000"));
    }

    @Test
    void create_existed_id() {
        assertTrue(commandValidator.validate("Create checking 12345678 2.5"));
        account.create("checking", "12345678", 2.5);
        account.create("savings", "98765432", 4.5);
        account.create("cd", "23456789", 3.5, 1500);
        assertFalse(commandValidator.validate("Create checking 12345678 4.5"));
        assertFalse(commandValidator.validate("Create checking 98765432 2.5"));
        assertFalse(commandValidator.validate("Create cd 98765432 4.5 1500"));
    }

    @Test
    void validate_id_length() {
        assertFalse(commandValidator.validate("Create checking 123456 2.5"));
        assertFalse(commandValidator.validate("Create checking 1234567890 2.5"));
    }

    @Test
    void validate_id_regex() {
        assertFalse(commandValidator.validate("Create checking 123456ab 2.5"));
        assertFalse(commandValidator.validate("Create checking 123-4567 2.5"));
        assertFalse(commandValidator.validate("Create checking abcdefgh 2.5"));
    }

    @Test
    void missing_create_id() {
        assertFalse(commandValidator.validate("create checking 2.5"));
        assertFalse(commandValidator.validate("create cd 2.5 2000"));
    }

    @Test
    void validate_apr_bound() {
        assertTrue(commandValidator.validate("Create checking 12345678 0.0"));
        assertTrue(commandValidator.validate("Create savings 23456789 10.0"));
    }

    @Test
    void validate_apr_data_type() {
        assertTrue(commandValidator.validate("Create checking 98765432 5"));
        assertTrue(commandValidator.validate("Create checking 98765432 2.543218"));
    }

    @Test
    void negative_apr() {
        assertFalse(commandValidator.validate("Create checking 12345678 -1.0"));
        assertFalse(commandValidator.validate("Create savings 98765432 -5.0"));
        assertFalse(commandValidator.validate("Create cd 23456789 -1.4 1500"));
    }

    @Test
    void apr_exceeds_max() {
        assertFalse(commandValidator.validate("Create checking 12345678 11.4"));
        assertFalse(commandValidator.validate("Create savings 98765432 25.5"));
        assertFalse(commandValidator.validate("Create cd 23456789 80.25 1500"));
    }

    @Test
    void apr_overflow() {
        assertFalse(commandValidator.validate("Create checking 12345678 99999999999999999"));
        assertFalse(commandValidator.validate("Create savings 98765432 89998989999999897"));
        assertFalse(commandValidator.validate("Create cd 23456789 1000010254100009 1500"));
    }

    @Test
    void apr_not_number() {
        assertFalse(commandValidator.validate("Create checking 12345678 ab"));
        assertFalse(commandValidator.validate("Create savings 98765432 2-5"));
        assertFalse(commandValidator.validate("Create cd 23456789 20k 1500"));
    }

    @Test
    void missing_create_apr() {
        assertFalse(commandValidator.validate("create checking 12345678"));
        assertFalse(commandValidator.validate("create cd 12345678 2000"));
    }

    @Test
    void validate_amount_bound() {
        assertTrue(commandValidator.validate("Create cd 12345678 2.5 1000.0"));
        assertTrue(commandValidator.validate("Create cd 23456789 10.0 10000.0"));
    }

    @Test
    void validate_amount_data_type() {
        assertTrue(commandValidator.validate("Create cd 98765432 2.5 1500"));
        assertTrue(commandValidator.validate("Create cd 98765432 2.5 1500.525984"));
    }

    @Test
    void negative_amount() {
        assertFalse(commandValidator.validate("Create cd 12345678 2.5 -100.0"));
    }

    @Test
    void amount_exceeds_max() {
        assertFalse(commandValidator.validate("Create cd 23456789 2.5 11000.5"));
    }

    @Test
    void amount_overflow() {
        assertFalse(commandValidator.validate("Create cd 23456789 2.5 999999999999999999999999"));
    }

    @Test
    void amount_not_number() {
        assertFalse(commandValidator.validate("Create cd 23456789 2.5 20K"));
    }

    @Test
    void missing_create_cd_amount() {
        assertFalse(commandValidator.validate("Create cd 12345678 2.5"));
    }

    @Test
    void passing_amount_to_checking_and_savings() {
        assertFalse(commandValidator.validate("Create checking 12345678 2.5 1500"));
        assertFalse(commandValidator.validate("Create savings 12345678 2.5 2000"));
    }

    @Test
    void no_extra_space_between_command() {
        assertFalse(commandValidator.validate("Create checking     12345678 2.5"));
    }

    @Test
    void command_takes_correct_arguments() {
        assertFalse(commandValidator.validate("Create 12345678 checking 2.5"));
        assertFalse(commandValidator.validate("hello world"));
        assertFalse(commandValidator.validate("cd Create 98765432 2.8 1800.50"));
    }
}
