package banking;

public class CommandValidator {
    protected Bank account;

    CommandValidator(Bank account) {
        this.account = account;
    }

    protected static boolean validateId(String id) {
        if (id.length() != 8) {
            return false;
        }
        for (int i = 0; i < id.length(); ++i) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    protected boolean accountExists(String id) {
        return account.getAccount().containsKey(id);
    }

    public String[] parse(String command) {
        return command.split(" ");
    }

    protected boolean validateDepositorWithdraw(String[] commandArray) {
        return (commandArray.length == 3) && (validateId(commandArray[1]))
                && (accountExists(commandArray[1])) && (validateDouble(commandArray[2]));
    }

    protected boolean validateDouble(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public boolean validate(String command) {
        ValidateCreate validateCreate = new ValidateCreate(account);
        ValidateDeposit validateDeposit = new ValidateDeposit(account);
        ValidateWithdraw validateWithdraw = new ValidateWithdraw(account);
        ValidateTransfer validateTransfer = new ValidateTransfer(account);
        ValidatePass validatePass = new ValidatePass(account);
        String[] commandArray = parse(command);
        boolean status = false;

        if (commandArray[0].equalsIgnoreCase("create")) {
            status = validateCreate.validateCommand(commandArray);
        } else if (commandArray[0].equalsIgnoreCase("deposit")) {
            status = validateDeposit.validateCommand(commandArray);
        } else if (commandArray[0].equalsIgnoreCase("withdraw")) {
            status = validateWithdraw.validateCommand(commandArray);
        } else if (commandArray[0].equalsIgnoreCase("transfer")) {
            status = validateTransfer.validateCommand(commandArray);
        } else if (commandArray[0].equalsIgnoreCase("pass")) {
            status = validatePass.validateCommand(commandArray);
        }
        return status;
    }

}
