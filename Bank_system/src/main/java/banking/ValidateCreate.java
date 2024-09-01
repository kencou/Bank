package banking;

public class ValidateCreate extends CommandValidator {
    ValidateCreate(Bank account) {
        super(account);
    }

    private boolean validateApr(String aprStr) {
        if (validateDouble(aprStr)) {
            double apr = Double.parseDouble(aprStr);
            return apr >= 0 && apr <= 10;
        }
        return false;
    }

    public boolean validateCommand(String[] commandArray) {
        boolean status = false;
        if (commandArray[1].equalsIgnoreCase("checking")
                || commandArray[1].equalsIgnoreCase("savings")) {
            status = validateCheckingOrSavings(commandArray);
        } else if (commandArray[1].equalsIgnoreCase("cd")) {
            status = validateCd(commandArray);
        }
        return status;
    }

    private boolean validateCheckingOrSavings(String[] commandArray) {
        return commandArray.length == 4 && !accountExists(commandArray[2])
                && validateId(commandArray[2]) && validateApr(commandArray[3]);
    }

    private boolean validateCdBound(String[] commandArray) {
        return validateId(commandArray[2]) && !accountExists(commandArray[2])
                && validateApr(commandArray[3]) && validateDouble(commandArray[4]);
    }

    private boolean validateCd(String[] commandArray) {
        if (commandArray.length == 5 && validateCdBound(commandArray)) {
            double amount = Double.parseDouble(commandArray[4]);
            return amount >= 1000 && amount <= 10000;
        }
        return false;
    }
}
