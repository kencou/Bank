package banking;

public class ProcessWithdraw extends CommandProcessor {
    public ProcessWithdraw(Bank account) {
        super(account);
    }

    public void processCommand(String command) {
        String[] commandArray = parse(command);
        account.withdraw(commandArray[1], Double.parseDouble(commandArray[2]));
        collectValidCommand(commandArray[1], command);
    }
}
