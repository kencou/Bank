package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {
    MasterControl masterControl;
    List<String> input;

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @BeforeEach
    void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage(), bank);
    }

    @Test
    void typo_in_create_command_is_invalid() {
        input.add("crate checking 12345678 1.8");
        List<String> actual = masterControl.start(input);
        assertSingleCommand("crate checking 12345678 1.8", actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid() {
        input.add("depositt 12345678 1000");
        List<String> actual = masterControl.start(input);
        assertSingleCommand("depositt 12345678 1000", actual);
    }

    @Test
    void two_typo_commands_both_invalid() {
        input.add("crate checking 12345678 1.8");
        input.add("depositt 12345678 1000");
        List<String> actual = masterControl.start(input);
        assertEquals(2, actual.size());
        assertEquals("crate checking 12345678 1.8", actual.get(0));
        assertEquals("depositt 12345678 1000", actual.get(1));
    }

    @Test
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }

    @Test
    void create_in_order() {
        input.add("Create checking 12345678 0.6");
        input.add("Create savings 23456789 0.6");
        input.add("Create cd 34567890 0.6 1000");
        List<String> actual = masterControl.start(input);

        assertEquals(3, actual.size());
        assertEquals("Checking 12345678 0.00 0.60", actual.get(0));
        assertEquals("Savings 23456789 0.00 0.60", actual.get(1));
        assertEquals("Cd 34567890 1000.00 0.60", actual.get(2));
    }

    @Test
    void add_transactional_commands_in_order() {
        input.add("Create checking 12345678 0.6");
        input.add("Create savings 23456789 0.6");
        input.add("Create cd 34567890 0.6 1000");
        input.add("Deposit 12345678 1000");
        input.add("Deposit 23456789 1000");
        input.add("Transfer 12345678 23456789 300");
        List<String> actual = masterControl.start(input);

        assertEquals(7, actual.size());
        assertEquals("Checking 12345678 700.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 1000", actual.get(1));
        assertEquals("Transfer 12345678 23456789 300", actual.get(2));
        assertEquals("Savings 23456789 1300.00 0.60", actual.get(3));
        assertEquals("Deposit 23456789 1000", actual.get(4));
        assertEquals("Transfer 12345678 23456789 300", actual.get(5));
        assertEquals("Cd 34567890 1000.00 0.60", actual.get(6));
    }

    @Test
    void round_floor_amount_current_state() {
        input.add("Create checking 12345678 0.6");
        input.add("Create savings 23456789 0.6");
        input.add("Create cd 34567890 0.6 1000");
        input.add("Deposit 12345678 1000");
        input.add("Deposit 23456789 1000");
        input.add("Pass 9");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        System.out.println(actual);
        assertEquals("Checking 12345678 1004.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 1000", actual.get(1));
        assertEquals("Savings 23456789 1004.50 0.60", actual.get(2));
        assertEquals("Deposit 23456789 1000", actual.get(3));
        assertEquals("Cd 34567890 1018.15 0.60", actual.get(4));
    }

    @Test
    void cd_can_not_transfer() {
        input.add("Create checking 12345678 0.6");
        input.add("Create savings 23456789 0.6");
        input.add("Create cd 34567890 0.6 1000");
        input.add("Deposit 12345678 1000");
        input.add("Deposit 23456789 1000");
        input.add("Transfer 12345678 34567890 300");
        input.add("Transfer 34567890 12345678 300");
        input.add("Withdraw 34567890 1500");
        List<String> actual = masterControl.start(input);

        assertEquals(8, actual.size());
        assertEquals("Checking 12345678 1000.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 1000", actual.get(1));
        assertEquals("Savings 23456789 1000.00 0.60", actual.get(2));
        assertEquals("Deposit 23456789 1000", actual.get(3));
        assertEquals("Cd 34567890 1000.00 0.60", actual.get(4));
        assertEquals("Transfer 12345678 34567890 300", actual.get(5));
        assertEquals("Transfer 34567890 12345678 300", actual.get(6));
        assertEquals("Withdraw 34567890 1500", actual.get(7));
    }

    @Test
    void no_savings_withdraw_or_transfer_more_than_once() {
        input.add("Create checking 12345678 0.6");
        input.add("Create savings 23456789 0.6");
        input.add("Create cd 34567890 0.6 1000");
        input.add("Deposit 23456789 1000");
        input.add("Transfer 23456789 12345678 300");
        input.add("Withdraw 23456789 200");
        List<String> actual = masterControl.start(input);

        assertEquals(7, actual.size());
        assertEquals("Checking 12345678 300.00 0.60", actual.get(0));
        assertEquals("Transfer 23456789 12345678 300", actual.get(1));
        assertEquals("Savings 23456789 700.00 0.60", actual.get(2));
        assertEquals("Deposit 23456789 1000", actual.get(3));
        assertEquals("Transfer 23456789 12345678 300", actual.get(4));
        assertEquals("Cd 34567890 1000.00 0.60", actual.get(5));
        assertEquals("Withdraw 23456789 200", actual.get(6));
    }

    @Test
    void deposit_withdraw_zero_valid() {
        input.add("Create checking 12345678 0.6");
        input.add("Create savings 23456789 0.6");
        input.add("Create cd 34567890 0.6 1000");
        input.add("Deposit 12345678 1000");
        input.add("deposit 23456789 0");
        input.add("transFer 23456789 12345678 0");
        input.add("Withdraw 12345678 0");
        input.add("wiThdrAw 12345678 0");
        List<String> actual = masterControl.start(input);

        assertEquals(9, actual.size());
        assertEquals("Checking 12345678 1000.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 1000", actual.get(1));
        assertEquals("transFer 23456789 12345678 0", actual.get(2));
        assertEquals("Withdraw 12345678 0", actual.get(3));
        assertEquals("wiThdrAw 12345678 0", actual.get(4));
        assertEquals("Savings 23456789 0.00 0.60", actual.get(5));
        assertEquals("deposit 23456789 0", actual.get(6));
        assertEquals("transFer 23456789 12345678 0", actual.get(7));
        assertEquals("Cd 34567890 1000.00 0.60", actual.get(8));

    }

    @Test
    void cd_amount_withdraw_before_and_after_twelve_months() {
        input.add("Create cd 34567890 0.6 1000");
        input.add("creAte checking 12345678 10");
        input.add("Withdraw 34567890 1000");
        input.add("Pass 12");
        input.add("Withdraw 34567890 100");
        input.add("Withdraw 34567890 1100");
        List<String> actual = masterControl.start(input);

        System.out.println(actual);

        assertEquals(4, actual.size());
        assertEquals("Cd 34567890 0.00 0.60", actual.get(0));
        assertEquals("Withdraw 34567890 1100", actual.get(1));
        assertEquals("Withdraw 34567890 1000", actual.get(2));
        assertEquals("Withdraw 34567890 100", actual.get(3));
    }
}