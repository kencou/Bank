package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatePassTest {
    Bank account;
    CommandValidator commandValidator;

    @BeforeEach
    void setUp() {
        account = new Bank();
        commandValidator = new CommandValidator(account);
    }

    @Test
    void pass_valid() {
        assertTrue(commandValidator.validate("Pass 1"));
    }

    @Test
    void pass_zero() {
        assertTrue(commandValidator.validate("Pass 0"));
    }

    @Test
    void pass_max() {
        assertTrue(commandValidator.validate("Pass 60"));
    }

    @Test
    void pass_more_than_sixty() {
        assertFalse(commandValidator.validate("Pass 61"));
    }

    @Test
    void pass_negative() {
        assertFalse(commandValidator.validate("Pass -2"));
    }

    @Test
    void pass_overflow() {
        assertFalse(commandValidator.validate("Pass 999999999999999999"));
    }

    @Test
    void pass_decimal() {
        assertFalse(commandValidator.validate("Pass 1.5"));
    }

    @Test
    void pass_non_integer() {
        assertFalse(commandValidator.validate("Pass abc"));
    }

    @Test
    void pass_case_insensitive() {
        assertTrue(commandValidator.validate("pASs 1"));
    }

    @Test
    void pass_spelling_check() {
        assertFalse(commandValidator.validate("P@s$ 1"));
    }

    @Test
    void pass_forget_time() {
        assertFalse(commandValidator.validate("Pass"));
    }

    @Test
    void no_extra_space_between_command() {
        assertFalse(commandValidator.validate("Pass   1"));
    }

    @Test
    void command_takes_correct_arguments() {
        assertFalse(commandValidator.validate("1 Pass"));
        assertFalse(commandValidator.validate("Pass 1 2"));
    }
}
