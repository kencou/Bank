package banking;

public class ValidatePass extends CommandValidator {
    ValidatePass(Bank account) {
        super(account);
    }

    private boolean validateTime(String timeStr) {
        try {
            int time = Integer.parseInt(timeStr);
            return time >= 0 && time <= 60;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean validatePass(String[] commandArray) {
        return commandArray.length == 2 && validateTime(commandArray[1]);
    }

    public boolean validateCommand(String[] commandArray) {
        return validatePass(commandArray);
    }
}
