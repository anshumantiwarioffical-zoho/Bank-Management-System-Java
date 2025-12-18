// Bank Account Management System (Console Based Java Project)

// Import utility packages
import java.util.*;
import java.io.*;

// ------------------------------------------------------
// BankAccount class represents a single bank account
// ------------------------------------------------------
class BankAccount implements Serializable {
    int accountNumber;    // Unique account number
    String name;          // Account holder name
    double balance;       // Current balance

    // Constructor to initialize account
    BankAccount(int accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    // Deposit money into account
    void deposit(double amount) {
        balance += amount;
        System.out.println("Amount deposited successfully. New balance: " + balance);
    }

    // Withdraw money from account
    void withdraw(double amount) {
        if(amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            System.out.println("Amount withdrawn successfully. New balance: " + balance);
        }
    }

    // Display account information
    void display() {
        System.out.println("Account Number: " + accountNumber + ", Name: " + name + ", Balance: " + balance);
    }
}

// ------------------------------------------------------
// Main class for Bank Management System
// ------------------------------------------------------
public class BankManagementSystem {

    // List to store multiple bank accounts
    static ArrayList<BankAccount> accounts = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    // File to save accounts data
    static final String FILE_NAME = "bankAccounts.dat";

    public static void main(String[] args) {

        // Load accounts from file if exists
        loadFromFile();

        int choice = 0;

        // Menu loop
        do {
            System.out.println("\n--- Bank Management System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View Accounts");
            System.out.println("5. Save & Exit");
            System.out.print("Enter choice: ");

            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter numbers only.");
                sc.nextLine();
                continue;
            }

            switch(choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    viewAccounts();
                    break;
                case 5:
                    saveToFile();
                    System.out.println("Data saved. Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while(choice != 5);
    }

    // Create a new bank account
    static void createAccount() {
        System.out.print("Enter account number: ");
        int accNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter account holder name: ");
        String name = sc.nextLine();

        System.out.print("Enter initial balance: ");
        double balance = sc.nextDouble();

        accounts.add(new BankAccount(accNo, name, balance));
        System.out.println("Account created successfully!");
    }

    // Deposit money to a specific account
    static void depositMoney() {
        System.out.print("Enter account number: ");
        int accNo = sc.nextInt();

        BankAccount acc = findAccount(accNo);
        if(acc != null) {
            System.out.print("Enter amount to deposit: ");
            double amt = sc.nextDouble();
            acc.deposit(amt);
        } else {
            System.out.println("Account not found!");
        }
    }

    // Withdraw money from a specific account
    static void withdrawMoney() {
        System.out.print("Enter account number: ");
        int accNo = sc.nextInt();

        BankAccount acc = findAccount(accNo);
        if(acc != null) {
            System.out.print("Enter amount to withdraw: ");
            double amt = sc.nextDouble();
            acc.withdraw(amt);
        } else {
            System.out.println("Account not found!");
        }
    }

    // Display all accounts
    static void viewAccounts() {
        if(accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for(BankAccount acc : accounts) {
                acc.display();
            }
        }
    }

    // Find account by account number
    static BankAccount findAccount(int accNo) {
        for(BankAccount acc : accounts) {
            if(acc.accountNumber == accNo) {
                return acc;
            }
        }
        return null;
    }

    // Save accounts to file
    static void saveToFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(accounts);
            oos.close();
        } catch(IOException e) {
            System.out.println("Error while saving data.");
        }
    }

    // Load accounts from file
    static void loadFromFile() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
            accounts = (ArrayList<BankAccount>) ois.readObject();
            ois.close();
        } catch(Exception e) {
            accounts = new ArrayList<>();
        }
    }
           }
