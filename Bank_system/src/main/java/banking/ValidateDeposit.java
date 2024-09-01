package banking;

public class ValidateDeposit extends CommandValidator {
    ValidateDeposit(Bank account) {
        super(account);
    }

    public boolean validateCommand(String[] commandArray) {
        return (validateDepositorWithdraw(commandArray))
                && account.getAccount().get(commandArray[1]).depositCheck(Double.parseDouble(commandArray[2]));
    }
}
