package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessWithdrawTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final String CD_ID = "98765432";
    public static final double APR = 0.6;
    public static final double AMOUNT = 1500;
    CommandProcessor commandProcessor;
    Bank account;

    @BeforeEach
    void setUp() {
        account = new Bank();
        commandProcessor = new CommandProcessor(account);
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.create("cd", CD_ID, APR, AMOUNT);
        account.deposit(CHECKING_ID, 1000);
        account.deposit(SAVINGS_ID, 1000);
    }

    @Test
    void withdraw_checking() {
        commandProcessor.process("withdraw 12345678 500");
        assertEquals(500, account.getAccount().get("12345678").getAmount());
    }

    @Test
    void withdraw_savings() {
        commandProcessor.process("withdraw 23456789 500");
        assertEquals(500, account.getAccount().get("23456789").getAmount());
    }


    @Test
    void withdraw_checking_twice() {
        commandProcessor.process("withdraw 12345678 500");
        assertEquals(500, account.getAccount().get("12345678").getAmount());
        commandProcessor.process("withdraw 12345678 300");
        assertEquals(200, account.getAccount().get("12345678").getAmount());
    }

    @Test
    void withdraw_savings_twice() {
        commandProcessor.process("withdraw 23456789 500");
        assertEquals(500, account.getAccount().get("23456789").getAmount());
        commandProcessor.process("withdraw 23456789 300");
        assertEquals(200, account.getAccount().get("23456789").getAmount());
    }

}
