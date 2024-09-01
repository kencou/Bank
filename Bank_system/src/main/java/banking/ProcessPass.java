package banking;

public class ProcessPass extends CommandProcessor {
    private Pass pass;

    public ProcessPass(Bank account) {
        super(account);
        pass = new Pass(account);
    }

    public void processCommand(String command) {
        String[] commandArray = parse(command);
        pass.time(Integer.parseInt(commandArray[1]));
    }
}
