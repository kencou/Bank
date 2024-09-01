package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SavingsTest {
    public static final String ID = "12345678";
    public static final double APR = 2.5;
    public static final double AMOUNT = 1500;
    Bank account;

    @BeforeEach
    void setUp() {
        account = new Bank();
    }

    @Test
    void Bank_has_no_accounts_initially() {
        assertTrue(account.getAccount().isEmpty());
    }

    @Test
    void create_savings() {
        account.create("savings", ID, APR);
        assertEquals(APR, account.getAccount().get(ID).getApr());
    }

    @Test
    void savings_case_insensitive() {
        account.create("SavinGS", ID, APR);
        assertEquals(APR, account.getAccount().get(ID).getApr());
    }

    @Test
    void savings_amount_zero_initially() {
        account.create("savings", ID, APR);
        assertEquals(0, account.getAccount().get(ID).getAmount());
    }

    @Test
    void deposit_savings() {
        account.create("savings", ID, APR);
        account.deposit(ID, AMOUNT);
        assertEquals(AMOUNT, account.getAccount().get(ID).getAmount());
    }

    @Test
    void withdraw_savings() {
        account.create("savings", ID, APR);
        account.deposit(ID, AMOUNT);
        account.withdraw(ID, 400);
        assertEquals(1100, account.getAccount().get(ID).getAmount());
    }
}
