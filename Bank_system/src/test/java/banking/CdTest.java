package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CdTest {
    public static final String ID = "12345678";
    public static final double APR = 2.5;
    public static final double AMOUNT = 1500;
    Bank account;
    Pass pass;

    @BeforeEach
    void setUp() {
        account = new Bank();
        pass = new Pass(account);
    }

    @Test
    void Bank_has_no_accounts_initially() {
        assertTrue(account.getAccount().isEmpty());
    }

    @Test
    void create_cd() {
        account.create("cd", ID, APR, AMOUNT);
        assertEquals(APR, account.getAccount().get(ID).getApr());
    }

    @Test
    void cd_case_insensitive() {
        account.create("CD", ID, APR, AMOUNT);
        assertEquals(APR, account.getAccount().get(ID).getApr());
    }

    @Test
    void create_cd_with_no_amount_not_allowed() {
        account.create("cd", ID, APR);
        assertTrue(account.getAccount().isEmpty());
    }

    @Test
    void cd_amount_check_initially() {
        account.create("cd", ID, APR, AMOUNT);
        assertEquals(AMOUNT, account.getAccount().get(ID).getAmount());
    }

    @Test
    void deposit_cd() {
        account.create("cd", ID, APR, AMOUNT);
        account.deposit(ID, AMOUNT);
        assertEquals(AMOUNT, account.getAccount().get(ID).getAmount());
    }

    @Test
    void withdraw_cd_after_twelve_months() {
        account.create("cd", ID, APR, AMOUNT);
        pass.time(12);
        account.withdraw(ID, 2000);
        assertEquals(0, account.getAccount().get(ID).getAmount());
    }
}
