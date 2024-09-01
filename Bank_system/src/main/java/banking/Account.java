package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected double apr;
    protected double amount;
    protected int time;
    protected List<String> transactionHistory;

    Account(double apr, double amount) {
        this.apr = apr;
        this.amount = amount;
        this.time = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public abstract boolean depositCheck(double amount);

    public abstract boolean withdrawCheck(double amount);

    public boolean canTransfer() {
        return true;
    }

    public abstract String getCurrentState(String id);

    public void deposit(double depositAmount) {
        amount += depositAmount;
    }

    public void withdraw(double withdrawAmount) {
        calculateWithdraw(withdrawAmount);
    }

    protected void calculateWithdraw(double withdrawAmount) {
        if (amount < withdrawAmount) {
            amount = 0;
        } else {
            amount -= withdrawAmount;
        }
    }

    public void addTransactionHistory(String command) {
        transactionHistory.add(command);
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public double getApr() {
        return apr;
    }

    public double getAmount() {
        return amount;
    }

    public int getTime() {
        return time;
    }

    protected void truncate() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        amount = Double.parseDouble(decimalFormat.format(amount));
        apr = Double.parseDouble(decimalFormat.format(apr));
    }

    protected double calculateApr() {
        return (amount * apr / 1200);
    }

    protected void calculatePass() {
        if (amount < 100) {
            amount -= 25;
            if (amount < 0) {
                amount = 0;
            }
        }
        amount += calculateApr();
        ++time;
    }

    public void pass() {
        calculatePass();
    }
}
