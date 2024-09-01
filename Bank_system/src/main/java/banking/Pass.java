package banking;

import java.util.Iterator;
import java.util.Map;

public class Pass {
    private Bank account;

    Pass(Bank account) {
        this.account = account;
    }

    private void passEachAccount() {
        Iterator<Map.Entry<String, Account>> current = account.getAccount().entrySet().iterator();
        Map.Entry<String, Account> entry;
        while (current.hasNext()) {
            entry = current.next();
            if (account.getAccount().get(entry.getKey()).getAmount() == 0) {
                current.remove();
            } else {
                account.getAccount().get(entry.getKey()).pass();
            }
        }
    }

    public void time(int time) {
        for (int i = 0; i < time; ++i) {
            passEachAccount();
        }
    }
}