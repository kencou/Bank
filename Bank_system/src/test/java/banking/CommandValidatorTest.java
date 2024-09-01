package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {
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
    }

    @Test
    void bank_is_initially_empty() {
        assertTrue(account.getAccount().isEmpty());
    }

    @Test
    void create() {
        assertTrue(commandValidator.validate("Create checking 12345678 2.5"));
        assertTrue(commandValidator.validate("Create savings 12345678 2.5"));
        assertTrue(commandValidator.validate("Create cd 12345678 2.5 1000"));
    }

    @Test
    void deposit() {
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.create("cd", CD_ID, APR, AMOUNT);
        assertTrue(commandValidator.validate("Deposit 12345678 500"));
        assertTrue(commandValidator.validate("Deposit 23456789 500"));
        assertFalse(commandValidator.validate("Deposit 98765432 500"));
    }

    @Test
    void withdraw() {
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.create("cd", CD_ID, APR, AMOUNT);
        assertTrue(commandValidator.validate("Withdraw 12345678 400"));
        assertTrue(commandValidator.validate("Withdraw 23456789 500"));
        assertFalse(commandValidator.validate("Withdraw 98765432 500"));
    }

    @Test
    void transfer() {
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.create("cd", CD_ID, APR, AMOUNT);
        account.deposit(CHECKING_ID, 1000);
        account.deposit(SAVINGS_ID, 1000);
        assertTrue(commandValidator.validate("Transfer 12345678 23456789 400"));
        assertTrue(commandValidator.validate("Transfer 23456789 12345678 1000"));
        assertFalse(commandValidator.validate("Transfer 98765432 12345678 500"));
        assertFalse(commandValidator.validate("Transfer 12345678 98765432 300"));

    }

    @Test
    void pass() {
        assertTrue(commandValidator.validate("Pass 50"));
        assertFalse(commandValidator.validate("Pass 61"));
    }
}
