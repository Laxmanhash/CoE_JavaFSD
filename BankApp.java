import java.util.Scanner;

class Account {
    private double balance;

    public Account(double initialAmount) {
        this.balance = initialAmount;
    }

    public synchronized void deposit(double amount) {
        if (amount > 0) balance += amount;
        System.out.println(Thread.currentThread().getName() + " deposited: " + amount + " | Balance: " + balance);
    }

    public synchronized void withdraw(double amount) {
        if (amount > 0 && amount <= balance) balance -= amount;
        else System.out.println(Thread.currentThread().getName() + " insufficient funds.");
        System.out.println("Balance: " + balance);
    }
}

class Transaction implements Runnable {
    private Account account;
    private boolean isDeposit;
    private double amount;

    public Transaction(Account account, boolean isDeposit, double amount) {
        this.account = account;
        this.isDeposit = isDeposit;
        this.amount = amount;
    }

    @Override
    public void run() {
        if (isDeposit) account.deposit(amount);
        else account.withdraw(amount);
    }
}

public class BankApp {
    public static void main(String[] args) {
        Account account = new Account(1000);
        new Thread(new Transaction(account, true, 400), "User1").start();
        new Thread(new Transaction(account, false, 200), "User2").start();
        new Thread(new Transaction(account, false, 1200), "User3").start();
        processInput();
    }

    public static void processInput() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter a number: ");
            System.out.println("Reciprocal: " + (1 / sc.nextDouble()));
        } catch (ArithmeticException e) {
            System.out.println("Error: Division by zero.");
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }
}

