package banking;

import java.util.LinkedHashMap;

public class Bank {
    protected LinkedHashMap<String, Account> account;

    Bank() {
        account = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, Account> getAccount() {
        return account;
    }

    public void create(String type, String id, double apr) {
        if (type.equalsIgnoreCase("checking")) {
            account.put(id, new Checking(apr));
        } else if (type.equalsIgnoreCase("savings")) {
            account.put(id, new Savings(apr));
        }
    }

    public void create(String type, String id, double apr, double amount) {
        if (type.equalsIgnoreCase("cd")) {
            account.put(id, new Cd(apr, amount));
        }
    }

    public void deposit(String id, double amount) {
        account.get(id).deposit(amount);
    }

    public void withdraw(String id, double amount) {
        account.get(id).withdraw(amount);
    }

    public void transfer(String fromId, String toId, double amount) {
        if (amount > account.get(fromId).getAmount()) {
            deposit(toId, account.get(fromId).getAmount());
        } else {
            deposit(toId, amount);
        }
        withdraw(fromId, amount);
    }
}
