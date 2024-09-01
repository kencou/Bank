package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessTransferTest {
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
        account.deposit(CHECKING_ID, 1000);
        account.deposit(SAVINGS_ID, 1000);
    }

    @Test
    void transfer_checking_to_savings() {
        commandProcessor.process("transfer 12345678 23456789 400");
        assertEquals(600, account.getAccount().get("12345678").getAmount());
        assertEquals(1400, account.getAccount().get("23456789").getAmount());
    }

    @Test
    void transfer_checking_to_savings_twice() {
        commandProcessor.process("transfer 12345678 23456789 400");
        commandProcessor.process("transfer 23456789 12345678 400");
        assertEquals(1000, account.getAccount().get("12345678").getAmount());
        assertEquals(1000, account.getAccount().get("23456789").getAmount());
    }
}
