package dev.lpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
    public static void main(String[] args) {

    }
}


// Transaction class
    // int routingNumber
    // int customerID
    // long transactionID
    // int transactionAmount
// More Txn details
    // getters/setters for all fields
    // constructor that takes all fields for ease of use

// modify BA class
    // make balance mutable
    // include Transaction Collection
        // Map<Long, Transaction> transactions
    // getTransactions: Map<Long,Transaction>
    // commitTransaction(int routingNumber, long transactionId, String customerID, double amount)

// Modify BC
    // getCustomerID:String; customerID is 15 digit string with leading zeros
    // Code in other packages can't instantiate a new BC
    // getAccounts: List<BankAccount>
        // Return defensive copy of accounts
        // multipe accts
    // getAccount(AccountType type): Account
        // single acct
        // assume cr will have exactly one checking and one savings

// Bank class
    // Can look up cr by customerID
    // routingNumber: int
    // lastTransactionID: long
    // Map<String, BankCustomer> customers
    // fns:
        // getCustomer(String id): BankCustomer
        // addCustomer (String name, double checkingInitialDeposit, double savingsInitialDeposit)
        // doTransaction (String id, AccountType type, double amount)
            // I think the id is the customer's ID
            // neg is withdrawal, pos is deposit
            // Dont let balance go below zero

// MAIN
    // make bank, add cr
    // let client get a BC instance by cr id, and review txns from single selected acct.
        // txns should be UNMODIFIABLE. No side effects.

// NOTES:
    // ONLY Bank instance can withdraw/deposit, by passing customerID, acct type and amount
    // So, mn shouldn't have access to the commit transaction code on BA itself.


class BankAccount {

    public enum AccountType {CHECKING, SAVINGS}

    private final AccountType accountType;
    private final double balance;

    BankAccount(AccountType accountType, double balance) {
        this.accountType = accountType;
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "%s $%.2f".formatted(accountType, balance);
    }
}


class BankCustomer {

    private static int lastCustomerId = 10_000_000;

    private final String name;
    private final int customerId;
    private final List<BankAccount> accounts = new ArrayList<>();

    public BankCustomer(String name, double checkingAmount, double savingsAmount) {
        this.name = name;
        this.customerId = lastCustomerId++;
        accounts.add(new BankAccount(BankAccount.AccountType.CHECKING, checkingAmount));
        accounts.add(new BankAccount(BankAccount.AccountType.SAVINGS, savingsAmount));
    }

    public String getName() {
        return name;
    }

    public List<BankAccount> getAccounts() {
        return new ArrayList<>(accounts);
    }

    @Override
    public String toString() {

        String[] accountStrings = new String[accounts.size()];
        Arrays.setAll(accountStrings, i -> accounts.get(i).toString());
        return "Customer: %s (id:%015d)%n\t%s%n".formatted(name, customerId,
                String.join("\n\t", accountStrings));
    }
}
