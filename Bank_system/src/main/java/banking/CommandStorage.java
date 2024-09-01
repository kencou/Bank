package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandStorage {
    private List<String> openAccounts;
    private List<String> invalidCommands;

    CommandStorage() {
        openAccounts = new ArrayList<>();
        invalidCommands = new ArrayList<>();
    }

    public void collectInvalidCommands(String command) {
        invalidCommands.add(command);
    }

    public List<String> getInvalidCommands() {
        return invalidCommands;
    }

    public void collectCurrentState(String command) {
        openAccounts.add(command);
    }

    public void collectTransactionHistory(List<String> commandArray) {
        for (String command : commandArray) {
            openAccounts.add(command);
        }
    }

    public void collectOpenAccounts(Bank account) {
        for (Map.Entry<String, Account> entry : account.account.entrySet()) {
            collectCurrentState(account.getAccount().get(entry.getKey()).getCurrentState(entry.getKey()));
            collectTransactionHistory(account.getAccount().get(entry.getKey()).getTransactionHistory());
        }
    }

    public List<String> getOpenAccounts() {
        return openAccounts;
    }

    public List<String> getOutput() {
        List<String> output = new ArrayList<>();
        output.addAll(getOpenAccounts());
        output.addAll(getInvalidCommands());
        return output;
    }
}
