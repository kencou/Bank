package banking;

import java.util.List;

public class MasterControl {
    private CommandValidator commandValidator;
    private CommandProcessor commandProcessor;
    private CommandStorage commandStorage;
    private Bank account;

    public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor, CommandStorage commandStorage, Bank account) {
        this.commandValidator = commandValidator;
        this.commandProcessor = commandProcessor;
        this.commandStorage = commandStorage;
        this.account = account;
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (commandValidator.validate(command)) {
                commandProcessor.process(command);
            } else {
                commandStorage.collectInvalidCommands(command);
            }
        }
        generateOpenAccountsCommands();
        return commandStorage.getOutput();
    }

    public void generateOpenAccountsCommands() {
        commandStorage.collectOpenAccounts(account);
    }
}
