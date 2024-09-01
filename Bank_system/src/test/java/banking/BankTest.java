package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {
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
    }

    @Test
    void Bank_has_no_accounts_initially() {
        assertTrue(account.getAccount().isEmpty());
    }

    @Test
    void create_accounts() {
        account.create("checking", CHECKING_ID, APR);
        assertEquals(APR, account.getAccount().get(CHECKING_ID).getApr());
    }

    @Test
    void create_multiple_accounts() {
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, 2);
        account.create("cd", CD_ID, 3, AMOUNT);
        assertEquals(APR, account.getAccount().get(CHECKING_ID).getApr());
        assertEquals(2, account.getAccount().get(SAVINGS_ID).getApr());
        assertEquals(3, account.getAccount().get(CD_ID).getApr());
    }

    @Test
    void deposit_accounts() {
        account.create("checking", CHECKING_ID, APR);
        account.deposit(CHECKING_ID, AMOUNT);
        assertEquals(AMOUNT, account.getAccount().get(CHECKING_ID).getAmount());
    }

    @Test
    void withdraw_accounts() {
        account.create("checking", CHECKING_ID, APR);
        account.deposit(CHECKING_ID, AMOUNT);
        account.withdraw(CHECKING_ID, 1000);
        assertEquals(AMOUNT - 1000, account.getAccount().get(CHECKING_ID).getAmount());
        account.withdraw(CHECKING_ID, 1000);
        assertEquals(0, account.getAccount().get(CHECKING_ID).getAmount());
    }

    @Test
    void transfer_checking_to_savings() {
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.deposit(CHECKING_ID, AMOUNT);
        account.deposit(SAVINGS_ID, AMOUNT);
        account.transfer(CHECKING_ID, SAVINGS_ID, 400);
        assertEquals(600, account.getAccount().get(CHECKING_ID).getAmount());
        assertEquals(1400, account.getAccount().get(SAVINGS_ID).getAmount());
    }

    @Test
    void transfer_savings_to_checking() {
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.deposit(CHECKING_ID, AMOUNT);
        account.deposit(SAVINGS_ID, AMOUNT);
        account.transfer(SAVINGS_ID, CHECKING_ID, 400);
        assertEquals(1400, account.getAccount().get(CHECKING_ID).getAmount());
        assertEquals(600, account.getAccount().get(SAVINGS_ID).getAmount());
    }

    @Test
    void transfer_checking_to_checking() {
        account.create("checking", CHECKING_ID, APR);
        account.deposit(CHECKING_ID, AMOUNT);
        account.create("checking", "87654321", APR);
        account.deposit("87654321", 1000);
        account.transfer(CHECKING_ID, "87654321", 400);
        assertEquals(600, account.getAccount().get(CHECKING_ID).getAmount());
        assertEquals(1400, account.getAccount().get("87654321").getAmount());
    }

    @Test
    void transfer_savings_to_savings() {
        account.create("savings", SAVINGS_ID, APR);
        account.deposit(SAVINGS_ID, AMOUNT);
        account.create("savings", "87654321", APR);
        account.deposit("87654321", 1000);
        account.transfer(SAVINGS_ID, "87654321", 400);
        assertEquals(600, account.getAccount().get(SAVINGS_ID).getAmount());
        assertEquals(1400, account.getAccount().get("87654321").getAmount());
    }

    @Test
    void transfer_zero() {
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.deposit(CHECKING_ID, AMOUNT);
        account.deposit(SAVINGS_ID, AMOUNT);
        account.transfer(CHECKING_ID, SAVINGS_ID, 0);
        assertEquals(1000, account.getAccount().get(CHECKING_ID).getAmount());
        assertEquals(1000, account.getAccount().get(SAVINGS_ID).getAmount());
    }

    @Test
    void transfer_more_than_account_amount() {
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.deposit(CHECKING_ID, AMOUNT);
        account.deposit(SAVINGS_ID, AMOUNT);
        account.withdraw(CHECKING_ID, 800);
        account.transfer(CHECKING_ID, SAVINGS_ID, 300);
        assertEquals(0, account.getAccount().get(CHECKING_ID).getAmount());
        assertEquals(1200, account.getAccount().get(SAVINGS_ID).getAmount());
    }

    double roundtwo(double n) {
        return Double.parseDouble(String.format("%.2f", n));
    }

    @Test
    void pass_accounts() {
        account.create("checking", CHECKING_ID, APR);
        account.deposit(CHECKING_ID, AMOUNT);
        pass.time(1);
        assertEquals(1000.5, account.getAccount().get(CHECKING_ID).getAmount());
    }

    @Test
    void pass_delete_amount_zero() {
        account.create("checking", CHECKING_ID, APR);
        account.create("savings", SAVINGS_ID, APR);
        account.create("cd", CD_ID, APR, AMOUNT);
        account.deposit(CHECKING_ID, AMOUNT);
        account.deposit(SAVINGS_ID, AMOUNT);
        account.withdraw(CHECKING_ID, AMOUNT);
        pass.time(1);
        assertEquals(2, account.getAccount().size());
    }

    @Test
    void pass_accounts_less_than_a_hundred() {
        account.create("checking", CHECKING_ID, APR);
        account.deposit(CHECKING_ID, AMOUNT);
        account.withdraw(CHECKING_ID, 920);
        pass.time(1);
        assertEquals(55.03, roundtwo(account.getAccount().get(CHECKING_ID).getAmount()));
    }
}
