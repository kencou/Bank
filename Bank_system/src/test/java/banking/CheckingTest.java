package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckingTest {
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
    void create_checking() {
        account.create("checking", ID, APR);
        assertEquals(APR, account.getAccount().get(ID).getApr());
        assertEquals(0, account.getAccount().get(ID).getAmount());
    }

    @Test
    void checking_case_insensitive() {
        account.create("CheckING", ID, APR);
        assertEquals(APR, account.getAccount().get(ID).getApr());
    }

    @Test
    void checking_amount_zero_initially() {
        account.create("checking", ID, APR);
        assertEquals(0, account.getAccount().get(ID).getAmount());
    }

    @Test
    void deposit_checking() {
        account.create("checking", ID, APR);
        account.deposit(ID, AMOUNT);
        assertEquals(AMOUNT, account.getAccount().get(ID).getAmount());
    }

    @Test
    void withdraw_checking() {
        account.create("checking", ID, APR);
        account.deposit(ID, AMOUNT);
        account.withdraw(ID, 400);
        assertEquals(1100, account.getAccount().get(ID).getAmount());
    }
}
