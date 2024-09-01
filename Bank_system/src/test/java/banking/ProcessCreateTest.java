package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessCreateTest {
    CommandProcessor commandProcessor;
    Bank account;

    @BeforeEach
    void setUp() {
        account = new Bank();
        commandProcessor = new CommandProcessor(account);
    }

    @Test
    void create_checking() {
        commandProcessor.process("create checking 12345678 2.5");
        assertEquals(2.5, account.getAccount().get("12345678").getApr());
    }

    @Test
    void validate_checking_type() {
        commandProcessor.process("create checking 12345678 2.5");
        assertTrue(account.getAccount().get("12345678") instanceof Checking);
    }

    @Test
    void create_savings() {
        commandProcessor.process("create savings 12345678 2.5");
        assertEquals(2.5, account.getAccount().get("12345678").getApr());
    }

    @Test
    void validate_savings_type() {
        commandProcessor.process("create savings 12345678 2.5");
        assertTrue(account.getAccount().get("12345678") instanceof Savings);
    }

    @Test
    void create_cd() {
        commandProcessor.process("create cd 12345678 2.5 1500");
        assertEquals(2.5, account.getAccount().get("12345678").getApr());
        assertEquals(1500, account.getAccount().get("12345678").getAmount());
    }

    @Test
    void validate_cd_type() {
        commandProcessor.process("create cd 12345678 2.5 1500");
        assertTrue(account.getAccount().get("12345678") instanceof Cd);
    }

}
