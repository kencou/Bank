package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandProcessorTest {
    CommandProcessor commandProcessor;
    Bank account;

    @BeforeEach
    void setUp() {
        account = new Bank();
        commandProcessor = new CommandProcessor(account);
    }

    @Test
    void bank_is_initially_empty() {
        assertTrue(account.getAccount().isEmpty());
    }

    @Test
    void create() {
        commandProcessor.process("create checking 12345678 2.5");
        commandProcessor.process("create savings 23456789 3.5");
        commandProcessor.process("create cd 87654321 2.5 1500");
        assertEquals(2.5, account.getAccount().get("12345678").getApr());
        assertEquals(3.5, account.getAccount().get("23456789").getApr());
        assertEquals(1500, account.getAccount().get("87654321").getAmount());
    }

    @Test
    void deposit() {
        commandProcessor.process("create checking 12345678 2.5");
        commandProcessor.process("create savings 23456789 3.5");
        commandProcessor.process("deposit 12345678 1500");
        commandProcessor.process("deposit 23456789 1000");
        assertEquals(1500, account.getAccount().get("12345678").getAmount());
        assertEquals(1000, account.getAccount().get("23456789").getAmount());
    }

    @Test
    void withdraw() {
        commandProcessor.process("create checking 12345678 2.5");
        commandProcessor.process("create savings 23456789 3.5");
        commandProcessor.process("deposit 12345678 1000");
        commandProcessor.process("deposit 23456789 1000");
        commandProcessor.process("withdraw 12345678 500");
        commandProcessor.process("withdraw 23456789 500");
        assertEquals(500, account.getAccount().get("12345678").getAmount());
        assertEquals(500, account.getAccount().get("23456789").getAmount());
    }

    @Test
    void transfer() {
        commandProcessor.process("create checking 12345678 2.5");
        commandProcessor.process("create savings 23456789 3.5");
        commandProcessor.process("deposit 12345678 1000");
        commandProcessor.process("deposit 23456789 1000");
        commandProcessor.process("transfer 12345678 23456789 400");
        assertEquals(600, account.getAccount().get("12345678").getAmount());
        assertEquals(1400, account.getAccount().get("23456789").getAmount());
    }

    double roundtwo(double n) {
        return Double.parseDouble(String.format("%.2f", n));
    }

    @Test
    void pass() {
        commandProcessor.process("create checking 12345678 0.6");
        commandProcessor.process("deposit 12345678 1000");
        commandProcessor.process("Pass 1");
        assertEquals(1000.50, roundtwo(account.getAccount().get("12345678").getAmount()));
    }
}
