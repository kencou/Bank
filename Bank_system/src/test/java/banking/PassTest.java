package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PassTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final String CD_ID = "98765432";
    public static final double APR = 0.6;
    public static final double AMOUNT = 1000;

    Bank account;
    Pass pass;

    @BeforeEach
    void setUp() {
        account = new Bank();
        pass = new Pass(account);

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
    void pass_accounts_0_month() {
        pass.time(0);
        assertEquals(1000.00, roundtwo(account.getAccount().get(CHECKING_ID).getAmount()));
        assertEquals(1000.00, roundtwo(account.getAccount().get(SAVINGS_ID).getAmount()));
        assertEquals(1000.00, roundtwo(account.getAccount().get(CD_ID).getAmount()));
    }

    @Test
    void pass_accounts_1_month() {
        pass.time(1);
        assertEquals(1000.50, roundtwo(account.getAccount().get(CHECKING_ID).getAmount()));
        assertEquals(1000.50, roundtwo(account.getAccount().get(SAVINGS_ID).getAmount()));
        assertEquals(1002.00, roundtwo(account.getAccount().get(CD_ID).getAmount()));
    }

    @Test
    void pass_accounts_many_months() {
        pass.time(8);
        assertEquals(1004.01, roundtwo(account.getAccount().get(CHECKING_ID).getAmount()));
        assertEquals(1004.01, roundtwo(account.getAccount().get(SAVINGS_ID).getAmount()));
        assertEquals(1016.12, roundtwo(account.getAccount().get(CD_ID).getAmount()));
    }

    @Test
    void no_deduct_for_one_hundred() {
        account.withdraw(CHECKING_ID, 900);
        pass.time(1);
        assertEquals(100.05, roundtwo(account.getAccount().get(CHECKING_ID).getAmount()));
    }

    @Test
    void deduct_for_lower_hundred() {
        account.withdraw(CHECKING_ID, 901);
        pass.time(1);
        assertEquals(74.04, roundtwo(account.getAccount().get(CHECKING_ID).getAmount()));
    }

    @Test
    void delete_for_zero_amount() {
        account.withdraw(CHECKING_ID, 1000);
        pass.time(1);
        assertEquals(2, account.getAccount().size());
    }

    @Test
    void dont_delete_if_amount_is_twenty_five() {
        account.withdraw(CHECKING_ID, 975);
        pass.time(1);
        assertEquals(3, account.getAccount().size());
        assertEquals(0, account.getAccount().get(CHECKING_ID).getAmount());
    }

    @Test
    void pass_zero_dont_delete_zero_accounts() {
        pass.time(12);
        account.withdraw(CHECKING_ID, AMOUNT + 100);
        account.withdraw(SAVINGS_ID, AMOUNT + 100);
        account.withdraw(CD_ID, AMOUNT + 100);
        pass.time(0);
        assertEquals(3, account.getAccount().size());
        assertFalse(account.getAccount().get(SAVINGS_ID).withdrawCheck(100));
    }

    @Test
    void check_time() {
        pass.time(12);
        assertEquals(12, account.getAccount().get(SAVINGS_ID).getTime());
    }
}
