class Account {
    private double balance;

    public Account(double initialAmount) {
        this.balance = initialAmount;
    }

    public synchronized void addFunds(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " added " + amount + ". Updated balance: " + balance);
        }
    }

    public synchronized void withdrawFunds(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ". Updated balance: " + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " tried withdrawing " + amount + " but insufficient balance. Remaining: " + balance);
        }
    }
}

class TransactionTask implements Runnable {
    private Account account;
    private boolean isDeposit;
    private double amount;

    public TransactionTask(Account account, boolean isDeposit, double amount) {
        this.account = account;
        this.isDeposit = isDeposit;
        this.amount = amount;
    }

    @Override
    public void run() {
        if (isDeposit) {
            account.addFunds(amount);
        } else {
            account.withdrawFunds(amount);
        }
    }
}

public class BankingApp {
    public static void main(String[] args) {
        Account account = new Account(1000);

        Thread t1 = new Thread(new TransactionTask(account, true, 400), "Client1");
        Thread t2 = new Thread(new TransactionTask(account, false, 200), "Client2");
        Thread t3 = new Thread(new TransactionTask(account, false, 1200), "Client3");

        t1.start();
        t2.start();
        t3.start();
    }
}