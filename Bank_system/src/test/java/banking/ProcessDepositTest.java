package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessDepositTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final double APR = 2.5;
    public static final double AMOUNT = 1500;
    CommandProcessor commandProcessor;
    Bank account;

    @BeforeEach
    void setUp() {
        account = new Bank();
        commandProcessor = new CommandProcessor(account);
        account.create("checking", CHECKING_ID, 2.5);
        account.create("savings", SAVINGS_ID, 2.5);
    }

    @Test
    void deposit_checking() {
        commandProcessor.process("deposit 12345678 500");
        assertEquals(500, account.getAccount().get("12345678").getAmount());
    }

    @Test
    void deposit_savings() {
        commandProcessor.process("deposit 23456789 500");
        assertEquals(500, account.getAccount().get("23456789").getAmount());
    }


    @Test
    void deposit_checking_twice() {
        commandProcessor.process("deposit 12345678 500");
        assertEquals(500, account.getAccount().get("12345678").getAmount());
        commandProcessor.process("deposit 12345678 300");
        assertEquals(800, account.getAccount().get("12345678").getAmount());
    }

    @Test
    void deposit_savings_twice() {
        commandProcessor.process("deposit 23456789 500");
        assertEquals(500, account.getAccount().get("23456789").getAmount());
        commandProcessor.process("deposit 23456789 300");
        assertEquals(800, account.getAccount().get("23456789").getAmount());
    }
}
