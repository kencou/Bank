package banking;

public class ProcessTransfer extends CommandProcessor {
    public ProcessTransfer(Bank account) {
        super(account);
    }

    public void processCommand(String command) {
        String[] commandArray = parse(command);
        account.transfer(commandArray[1], commandArray[2], Double.parseDouble(commandArray[3]));
        collectValidCommand(commandArray[1], command);
        collectValidCommand(commandArray[2], command);
    }
}
