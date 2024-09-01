package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateTransferTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final String CD_ID = "98765432";
    public static final double APR = 2.5;
    public static final double AMOUNT = 1000;
    CommandValidator commandValidator;
    Bank account;

    @BeforeEach
    void setUp() {
        account = new Bank();
        commandValidator = new CommandValidator(account);
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.create("cd", CD_ID, APR, AMOUNT);
        account.deposit(CHECKING_ID, AMOUNT);
        account.deposit(SAVINGS_ID, AMOUNT);
    }

    @Test
    void transfer_checking_to_savings() {
        assertTrue(commandValidator.validate("Transfer 12345678 23456789 400"));
    }

    @Test
    void transfer_savings_to_checking() {
        assertTrue(commandValidator.validate("Transfer 23456789 12345678 1000"));
    }

    @Test
    void transfer_checking_to_cd() {
        assertFalse(commandValidator.validate("Transfer 12345678 98765432 400"));
    }

    @Test
    void transfer_cd_to_checking() {
        assertFalse(commandValidator.validate("Transfer 98765432 12345678 400"));
    }

    @Test
    void transfer_savings_to_cd() {
        assertFalse(commandValidator.validate("Transfer 23456789 98765432 400"));
    }

    @Test
    void transfer_cd_to_savings() {
        assertFalse(commandValidator.validate("Transfer 98765432 23456789 400"));
    }

    @Test
    void cd_not_transferable() {
        assertFalse(account.getAccount().get(CD_ID).canTransfer());
    }

    @Test
    void cd_not_transferable_after_12_months() {
        Pass pass = new Pass(account);
        pass.time(14);
        assertFalse(account.getAccount().get(CD_ID).canTransfer());
    }

    @Test
    void transfer_checking_to_checking() {
        account.create("checking", "87654321", APR);
        assertTrue(commandValidator.validate("Transfer 12345678 87654321 400"));
    }

    @Test
    void transfer_savings_to_savings() {
        account.create("savings", "87654321", APR);
        assertTrue(commandValidator.validate("Transfer 23456789 87654321 400"));
    }

    @Test
    void transfer_same_checking() {
        assertFalse(commandValidator.validate("Transfer 12345678 12345678 400"));
    }

    @Test
    void transfer_same_savings() {
        assertFalse(commandValidator.validate("Transfer 23456789 23456789 400"));
    }

    @Test
    void transfer_case_insensitive() {
        assertTrue(commandValidator.validate("TrANSfeR 12345678 23456789 400"));
    }

    @Test
    void transfer_spelling_typo() {
        assertFalse(commandValidator.validate("Transformar 12345678 23456789 400"));
        assertFalse(commandValidator.validate("Transist 12345678 23456789 400"));
    }

    @Test
    void non_existed_id() {
        assertFalse(commandValidator.validate("Transfer 456789123 23456789 400"));
        assertFalse(commandValidator.validate("Transfer 23456789 987412563 400"));
    }

    @Test
    void validate_id_length() {
        assertFalse(commandValidator.validate("Transfer 123456789 23456789 400"));
        assertFalse(commandValidator.validate("Transfer 123456 234567890 400"));
    }

    @Test
    void validate_id_regex() {
        assertFalse(commandValidator.validate("Transfer 12345abc 23456789 400"));
        assertFalse(commandValidator.validate("Transfer 12345678 2345abc9 400"));
    }

    @Test
    void missing_one_id() {
        assertFalse(commandValidator.validate("Transfer 23456789 400"));
    }

    @Test
    void validate_amount_data_type() {
        assertTrue(commandValidator.validate("Transfer 12345678 23456789 400"));
        assertTrue(commandValidator.validate("Transfer 12345678 23456789 300.2564879"));
    }

    @Test
    void validate_amount_not_number() {
        assertFalse(commandValidator.validate("Transfer 12345678 23456789 abc"));
        assertFalse(commandValidator.validate("Transfer 12345678 23456789 1k"));
    }

    @Test
    void missing_transfer_amount() {
        assertFalse(commandValidator.validate("Transfer 12345678 23456789"));
    }

    @Test
    void transfer_checking_zero() {
        assertTrue(commandValidator.validate("Transfer 12345678 23456789 0"));
    }

    @Test
    void transfer_savings_zero() {
        assertTrue(commandValidator.validate("Transfer 23456789 12345678 0"));
    }

    @Test
    void transfer_checking_bound() {
        assertTrue(commandValidator.validate("Transfer 12345678 23456789 400"));
    }

    @Test
    void transfer_savings_bound() {
        assertTrue(commandValidator.validate("Transfer 23456789 12345678 1000"));
    }

    @Test
    void transfer_negative_amount_from_checking() {
        assertFalse(commandValidator.validate("Transfer 12345678 23456789 -20"));
    }

    @Test
    void transfer_negative_amount_from_savings() {
        assertFalse(commandValidator.validate("Transfer 12345678 23456789 401.5"));
    }

    @Test
    void transfer_exceeds_checking_max() {
        assertFalse(commandValidator.validate("Transfer 12345678 23456789 400.50"));
    }

    @Test
    void transfer_exceeds_savings_max() {
        assertFalse(commandValidator.validate("Transfer 23456789 12345678 1000.25"));
    }

    @Test
    void transfer_checking_amount_overflow() {
        assertFalse(commandValidator.validate("Transfer 12345678 23456789 99999999999999999999999999999"));
    }

    @Test
    void transfer_savings_amount_overflow() {
        assertFalse(commandValidator.validate("Transfer 23456789 12345678 99999999999999999999999999999"));
    }

    @Test
    void no_extra_space_between_command() {
        assertFalse(commandValidator.validate("Transfer 12345678      23456789  0"));
    }

    @Test
    void command_takes_correct_arguments() {
        assertFalse(commandValidator.validate("12345678 Transfer 23456789 250"));
        assertFalse(commandValidator.validate("Transfer 23456789 50 hello world"));
    }
}
