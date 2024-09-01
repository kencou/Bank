package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessPassTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final String CD_ID = "98765432";
    public static final double APR = 0.6;
    public static final double AMOUNT = 1000;
    CommandProcessor commandProcessor;
    Bank account;

    @BeforeEach
    void setUp() {
        account = new Bank();
        commandProcessor = new CommandProcessor(account);
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.create("cd", CD_ID, APR, AMOUNT);
        account.deposit(CHECKING_ID, AMOUNT);
        account.deposit(SAVINGS_ID, AMOUNT);
    }

    double roundtwo(double n) {
        return Double.parseDouble(String.format("%.2f", n));
    }

    @Test
    void pass_accounts_1_month() {
        commandProcessor.process("Pass 1");
        assertEquals(1000.50, roundtwo(account.getAccount().get(CHECKING_ID).getAmount()));
        assertEquals(1000.50, roundtwo(account.getAccount().get(SAVINGS_ID).getAmount()));
        assertEquals(1002.00, roundtwo(account.getAccount().get(CD_ID).getAmount()));
    }
}
