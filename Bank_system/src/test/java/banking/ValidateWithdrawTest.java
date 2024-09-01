package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateWithdrawTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final String CD_ID = "98765432";
    public static final double APR = 2.5;
    public static final double AMOUNT = 1000;
    CommandValidator commandValidator;
    Bank account;
    Pass pass;

    @BeforeEach
    void setUp() {
        account = new Bank();
        commandValidator = new CommandValidator(account);
        pass = new Pass(account);
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.create("cd", CD_ID, APR, AMOUNT);
        account.deposit(CHECKING_ID, AMOUNT);
        account.deposit(SAVINGS_ID, AMOUNT);
    }

    @Test
    void withdraw_checking() {
        assertTrue(commandValidator.validate("Withdraw 12345678 400"));
    }

    @Test
    void withdraw_savings() {
        assertTrue(commandValidator.validate("Withdraw 23456789 1000"));
    }

    @Test
    void withdraw_cd() {
        assertFalse(commandValidator.validate("Withdraw 98765432 1000"));
    }

    @Test
    void withdraw_checking_twice() {
        account.withdraw(CHECKING_ID, 300);
        assertTrue(commandValidator.validate("Withdraw 12345678 400"));
    }

    @Test
    void one_withdraw_per_month_savings() {
        account.deposit(SAVINGS_ID, 1000);
        account.withdraw(SAVINGS_ID, 300);
        assertFalse(commandValidator.validate("Withdraw 23456789 200"));
    }

    @Test
    void withdraw_case_insensitive() {
        assertTrue(commandValidator.validate("WithDRAw 12345678 400"));
    }

    @Test
    void withdraw_spelling_typo() {
        assertFalse(commandValidator.validate("Withyou 12345678 500"));
        assertFalse(commandValidator.validate("Wthdraw 23456789 500"));
    }

    @Test
    void non_existed_id() {
        assertFalse(commandValidator.validate("Withdraw 24687513 500"));
    }

    @Test
    void validate_id_length() {
        assertFalse(commandValidator.validate("Withdraw 123456 500.0"));
        assertFalse(commandValidator.validate("Withdraw 123456789 500"));
    }

    @Test
    void validate_id_regex() {
        assertFalse(commandValidator.validate("Withdraw abc1235 400"));
    }

    @Test
    void missing_withdraw_id() {
        assertFalse(commandValidator.validate("Withdraw 500"));
    }

    @Test
    void validate_amount_data_type() {
        assertTrue(commandValidator.validate("Withdraw 12345678 400"));
        assertTrue(commandValidator.validate("Withdraw 23456789 300.5251230"));
    }

    @Test
    void validate_amount_not_number() {
        assertFalse(commandValidator.validate("Withdraw 12345678 abc"));
        assertFalse(commandValidator.validate("Withdraw 12345678 1k"));
    }

    @Test
    void missing_withdraw_amount() {
        assertFalse(commandValidator.validate("Withdraw 12345678"));
        assertFalse(commandValidator.validate("Withdraw 23456789"));
    }

    @Test
    void withdraw_checking_bound() {
        assertTrue(commandValidator.validate("Withdraw 12345678 0"));
        assertTrue(commandValidator.validate("Withdraw 12345678 400"));
    }

    @Test
    void withdraw_negative_amount_from_checking() {
        assertFalse(commandValidator.validate("Withdraw 12345678 -200"));
    }

    @Test
    void withdraw_exceeds_checking_max() {
        assertFalse(commandValidator.validate("Withdraw 12345678 400.50"));
    }

    @Test
    void withdraw_checking_amount_overflow() {
        assertFalse(commandValidator.validate("Withdraw 12345678 99999999999999999999999999999"));
    }

    @Test
    void withdraw_savings_bound() {
        assertTrue(commandValidator.validate("Withdraw 23456789 0"));
        assertTrue(commandValidator.validate("Withdraw 23456789 1000"));
    }

    @Test
    void withdraw_negative_amount_from_savings() {
        assertFalse(commandValidator.validate("Withdraw 23456789 -20"));
    }

    @Test
    void withdraw_exceeds_savings_max() {
        assertFalse(commandValidator.validate("Withdraw 23456789 1000.25"));
    }

    @Test
    void withdraw_saving_amount_overflow() {
        assertFalse(commandValidator.validate("Withdraw 23456789 99999999999999999999999999"));
    }

    @Test
    void savings_withdraw_twice_invalid() {
        assertTrue(commandValidator.validate("Withdraw 23456789 500"));
        account.withdraw(SAVINGS_ID, 500);
        assertFalse(commandValidator.validate("Withdraw 23456789 300"));
    }

    @Test
    void savings_withdraw_twice_valid_after_month() {
        assertTrue(commandValidator.validate("Withdraw 23456789 500"));
        account.withdraw(SAVINGS_ID, 500);
        pass.time(1);
        assertTrue(commandValidator.validate("Withdraw 23456789 300"));
    }

    @Test
    void cd_withdraw_before_twelve_months() {
        pass.time(11);
        assertFalse(commandValidator.validate("Withdraw 98765432 1200"));
    }

    @Test
    void cd_half_withdraw_after_twelve_months() {
        pass.time(12);
        assertFalse(commandValidator.validate("Withdraw 98765432 500"));
    }

    @Test
    void cd_exact_withdraw_after_twelve_months() {
        pass.time(12);
        assertTrue(commandValidator.validate("Withdraw 98765432 1105.055961726336"));
    }


    @Test
    void no_extra_space_between_command() {
        assertFalse(commandValidator.validate("Withdraw      23456789  0"));
    }

    @Test
    void command_takes_correct_arguments() {
        assertFalse(commandValidator.validate("12345678 Withdraw 250"));
        assertFalse(commandValidator.validate("Withdraw 23456789 50 hello world"));
    }
}
