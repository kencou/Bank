package banking;

public class ValidateTransfer extends CommandValidator {
    public ValidateTransfer(Bank account) {
        super(account);
    }

    private boolean validateTransfer(String[] commandArray) {
        return (commandArray.length == 4) && (validateId(commandArray[1])) && (validateId(commandArray[2]))
                && !(commandArray[1].equals(commandArray[2])) && checkTransferable(commandArray);
    }

    private boolean checkTransferable(String[] commandArray) {
        String fromId = commandArray[1];
        String toId = commandArray[2];
        String amount = commandArray[3];
        return account.getAccount().get(fromId).canTransfer() && account.getAccount().get(toId).canTransfer()
                && validateDouble(amount) && account.getAccount().get(fromId).withdrawCheck(Double.parseDouble(amount))
                && account.getAccount().get(toId).depositCheck(Double.parseDouble(amount));
    }

    public boolean validateCommand(String[] commandArray) {
        return validateTransfer(commandArray);
    }
}
