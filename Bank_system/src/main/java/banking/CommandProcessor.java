package banking;

public class CommandProcessor {
    protected Bank account;

    CommandProcessor(Bank account) {
        this.account = account;
    }

    public String[] parse(String command) {
        return command.split(" ");
    }

    public void collectValidCommand(String id, String command) {
        account.getAccount().get(id).addTransactionHistory(command);
    }

    public void process(String command) {
        ProcessCreate processCreate = new ProcessCreate(account);
        ProcessDeposit processDeposit = new ProcessDeposit(account);
        ProcessWithdraw processWithdraw = new ProcessWithdraw(account);
        ProcessTransfer processTransfer = new ProcessTransfer(account);
        ProcessPass processPass = new ProcessPass(account);
        String[] commandArray = parse(command);
        if (commandArray[0].equalsIgnoreCase("create")) {
            processCreate.processCommand(command);
        } else if (commandArray[0].equalsIgnoreCase("deposit")) {
            processDeposit.processCommand(command);
        } else if (commandArray[0].equalsIgnoreCase("withdraw")) {
            processWithdraw.processCommand(command);
        } else if (commandArray[0].equalsIgnoreCase("transfer")) {
            processTransfer.processCommand(command);
        } else if (commandArray[0].equalsIgnoreCase("pass")) {
            processPass.processCommand(command);
        }
    }
}
