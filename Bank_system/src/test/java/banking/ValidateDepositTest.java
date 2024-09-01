package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateDepositTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final String CD_ID = "98765432";
    public static final double APR = 2.5;
    public static final double AMOUNT = 1500;
    CommandValidator commandValidator;
    Bank account;

    @BeforeEach
    void setUp() {
        account = new Bank();
        commandValidator = new CommandValidator(account);
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.create("cd", CD_ID, APR, AMOUNT);
    }

    @Test
    void deposit_checking() {
        assertTrue(commandValidator.validate("Deposit 12345678 500"));
    }

    @Test
    void deposit_savings() {
        assertTrue(commandValidator.validate("Deposit 23456789 1500"));
    }

    @Test
    void deposit_cd() {
        assertFalse(commandValidator.validate("Deposit 98765432 1000"));
    }

    @Test
    void deposit_case_insensitive() {
        assertTrue(commandValidator.validate("DepoSIT 12345678 500"));
    }

    @Test
    void deposit_spelling_typo() {
        assertFalse(commandValidator.validate("Depo 12345678 500"));
        assertFalse(commandValidator.validate("Depostand 23456789 500"));
    }

    @Test
    void non_existed_id() {
        assertFalse(commandValidator.validate("Deposit 24687513 500"));
    }

    @Test
    void validate_id_length() {
        assertFalse(commandValidator.validate("Deposit 123456 500.0"));
        assertFalse(commandValidator.validate("Deposit 123456789 500"));
    }

    @Test
    void validate_id_regex() {
        assertFalse(commandValidator.validate("Deposit abc1235 500"));
    }

    @Test
    void missing_deposit_id() {
        assertFalse(commandValidator.validate("Deposit 500"));
    }

    @Test
    void validate_amount_data_type() {
        assertTrue(commandValidator.validate("Deposit 12345678 500"));
        assertTrue(commandValidator.validate("Deposit 23456789 500.5251230"));
    }

    @Test
    void validate_amount_not_number() {
        assertFalse(commandValidator.validate("Deposit 12345678 abc"));
        assertFalse(commandValidator.validate("Deposit 12345678 1k"));
    }

    @Test
    void missing_deposit_amount() {
        assertFalse(commandValidator.validate("Deposit 12345678"));
        assertFalse(commandValidator.validate("Deposit 23456789"));
    }

    @Test
    void deposit_checking_bound() {
        assertTrue(commandValidator.validate("Deposit 12345678 0"));
        assertTrue(commandValidator.validate("Deposit 12345678 1000"));
    }

    @Test
    void deposit_negative_amount_from_checking() {
        assertFalse(commandValidator.validate("Deposit 12345678 -200"));
    }

    @Test
    void deposit_exceeds_checking_max() {
        assertFalse(commandValidator.validate("Deposit 12345678 1000.50"));
    }

    @Test
    void deposit_checking_amount_overflow() {
        assertFalse(commandValidator.validate("Deposit 12345678 99999999999999999999999999999"));
    }

    @Test
    void deposit_savings_bound() {
        assertTrue(commandValidator.validate("Deposit 23456789 0"));
        assertTrue(commandValidator.validate("Deposit 23456789 2500"));
    }

    @Test
    void deposit_negative_amount_from_savings() {
        assertFalse(commandValidator.validate("Deposit 23456789 -20"));
    }

    @Test
    void deposit_exceeds_savings_max() {
        assertFalse(commandValidator.validate("Deposit 23456789 2500.25"));
    }

    @Test
    void deposit_saving_amount_overflow() {
        assertFalse(commandValidator.validate("Deposit 23456789 99999999999999999999999999"));
    }

    @Test
    void no_extra_space_between_command() {
        assertFalse(commandValidator.validate("Deposit      23456789  0"));
    }

    @Test
    void command_takes_correct_arguments() {
        assertFalse(commandValidator.validate("12345678 Deposit 250"));
        assertFalse(commandValidator.validate("Deposit 23456789 50 hello world"));
    }
}
