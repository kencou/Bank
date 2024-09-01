package banking;

public class ProcessCreate extends CommandValidator {
    ProcessCreate(Bank account) {
        super(account);
    }

    public void processCommand(String command) {
        String[] commandArray = parse(command);
        if (commandArray[1].equalsIgnoreCase("checking")
                || commandArray[1].equalsIgnoreCase("savings")) {
            processCheckingOrSavings(commandArray);
        } else if (commandArray[1].equalsIgnoreCase("cd")) {
            processCd(commandArray);
        }
    }

    private void processCheckingOrSavings(String[] commandArray) {
        String type = commandArray[1];
        String id = commandArray[2];
        double apr = Double.parseDouble(commandArray[3]);
        account.create(type, id, apr);
    }

    private void processCd(String[] commandArray) {
        String type = commandArray[1];
        String id = commandArray[2];
        double apr = Double.parseDouble(commandArray[3]);
        double amount = Double.parseDouble(commandArray[4]);
        account.create(type, id, apr, amount);
    }
}
