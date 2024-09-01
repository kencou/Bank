package banking;

public class ProcessDeposit extends CommandProcessor {

    ProcessDeposit(Bank account) {
        super(account);
    }

    public void processCommand(String command) {
        String[] commandArray = parse(command);
        collectValidCommand(commandArray[1], command);
        account.deposit(commandArray[1], Double.parseDouble(commandArray[2]));
    }
}