package banking;

public class Savings extends Account {
    private boolean canWithdraw = true;

    Savings(double apr) {
        super(apr, 0);
    }

    @Override
    public boolean depositCheck(double depositAmount) {
        return depositAmount >= 0 && depositAmount <= 2500;
    }

    @Override
    public boolean withdrawCheck(double withdrawAmount) {
        return canWithdraw && withdrawAmount >= 0 && withdrawAmount <= 1000;
    }

    @Override
    public void withdraw(double withdrawAmount) {
        calculateWithdraw(withdrawAmount);
        canWithdraw = false;
    }

    @Override
    public void pass() {
        calculatePass();
        canWithdraw = true;
    }

    @Override
    public String getCurrentState(String id) {
        truncate();
        return String.format("Savings %s %.2f %.2f", id, amount, apr);
    }
}
