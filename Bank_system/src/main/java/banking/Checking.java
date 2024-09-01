package banking;

public class Checking extends Account {
    Checking(double apr) {
        super(apr, 0);
    }

    @Override
    public boolean depositCheck(double depositAmount) {
        return depositAmount >= 0 && depositAmount <= 1000;
    }

    @Override
    public boolean withdrawCheck(double withdrawAmount) {
        return withdrawAmount >= 0 && withdrawAmount <= 400;
    }

    @Override
    public String getCurrentState(String id) {
        truncate();
        return String.format("Checking %s %.2f %.2f", id, amount, apr);
    }
}
