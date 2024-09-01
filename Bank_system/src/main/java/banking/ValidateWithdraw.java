package banking;

public class ValidateWithdraw extends CommandValidator {
    ValidateWithdraw(Bank account) {
        super(account);
    }

    public boolean validateCommand(String[] commandArray) {
        if (validateDepositorWithdraw(commandArray)) {
            return account.getAccount().get(commandArray[1]).withdrawCheck(Double.parseDouble(commandArray[2]));
        }
        return false;
    }
}
