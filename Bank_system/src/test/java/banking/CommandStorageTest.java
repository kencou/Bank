package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandStorageTest {
    CommandStorage commandStorage;
    Bank account;

    @BeforeEach
    void setUp() {
        commandStorage = new CommandStorage();
        account = new Bank();
    }

    @Test
    void storage_is_initially_empty() {
        assertTrue(commandStorage.getInvalidCommands().isEmpty());
    }

    @Test
    void adding_one_invalid_command() {
        commandStorage.collectInvalidCommands("this command is invalid");
        assertEquals("this command is invalid", commandStorage.getInvalidCommands().get(0));
    }

    @Test
    void check_size_of_many_invalid_commands() {
        for (int i = 0; i < 10; ++i) {
            commandStorage.collectInvalidCommands(String.valueOf(i));
        }
        assertEquals(10, commandStorage.getInvalidCommands().size());
    }

    @Test
    void adding_many_invalid_commands() {
        for (int i = 0; i < 10; ++i) {
            commandStorage.collectInvalidCommands(String.valueOf(i));
        }
        for (int i = 0; i < 10; ++i) {
            assertEquals(String.valueOf(i), commandStorage.getInvalidCommands().get(i));
        }
    }

    @Test
    void valid_storage_is_initially_empty() {
        assertTrue(commandStorage.getOpenAccounts().isEmpty());
    }

    @Test
    void adding_one_valid_command() {
        account.create("checking", "12345678", 0.6);
        commandStorage.collectOpenAccounts(account);
        assertEquals("Checking 12345678 0.00 0.60", commandStorage.getOpenAccounts().get(0));
    }

    @Test
    void creating_two_valid_commands() {
        account.create("checking", "12345678", 0.6);
        account.create("cd", "23456789", 0.6, 1400);
        commandStorage.collectOpenAccounts(account);
        assertEquals("Checking 12345678 0.00 0.60", commandStorage.getOpenAccounts().get(0));
        assertEquals("Cd 23456789 1400.00 0.60", commandStorage.getOpenAccounts().get(1));
    }

    @Test
    void creating_many_valid_commands() {
        account.create("checking", "12345678", 0.6);
        account.create("savings", "23456789", 0.6);
        account.create("cd", "34567890", 0.6, 1400);
        commandStorage.collectOpenAccounts(account);
        assertEquals("Checking 12345678 0.00 0.60", commandStorage.getOpenAccounts().get(0));
        assertEquals("Savings 23456789 0.00 0.60", commandStorage.getOpenAccounts().get(1));
        assertEquals("Cd 34567890 1400.00 0.60", commandStorage.getOpenAccounts().get(2));
    }

}
