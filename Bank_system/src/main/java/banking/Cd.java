package banking;

public class Cd extends Account {
    Cd(double apr, double amount) {
        super(apr, amount);
    }

    @Override
    public boolean depositCheck(double depositAmount) {
        return false;
    }

    @Override
    public boolean withdrawCheck(double withdrawAmount) {
        return time >= 12 && withdrawAmount >= amount;
    }

    @Override
    public boolean canTransfer() {
        return false;
    }

    @Override
    public double calculateApr() {
        double currentAmount = amount;
        for (int i = 0; i < 4; ++i) {
            currentAmount += (currentAmount * apr / 1200);
        }
        return currentAmount - amount;
    }

    @Override
    public void deposit(double depositAmount) {
        return;
    }

    @Override
    public void withdraw(double withdrawAmount) {
        amount = 0;
    }

    @Override
    public String getCurrentState(String id) {
        truncate();
        return String.format("Cd %s %.2f %.2f", id, amount, apr);
    }
}
