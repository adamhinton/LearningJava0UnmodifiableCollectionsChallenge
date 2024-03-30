package dev.lpa;

import java.util.*;


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

class Bank{
    private int routingNumber;
    private long lastTransactionId;
    private Map<String, BankCustomer> customers;

    public BankCustomer getCustomer(String id){
        BankCustomer foundCustomer = customers.get(id);

        if(foundCustomer != null){
            return new BankCustomer(foundCustomer.getName(), 0, 0);
        }

        return null;
    }

    public void addCustomer (String name, double checkingInitialDeposit, double savingsInitialDeposit){
        customers.put("DJFISODS", new BankCustomer(name, checkingInitialDeposit, savingsInitialDeposit));
    }

    public void doTransaction (String id, BankAccount.AccountType type, double amount){

        BankCustomer myCustomer = customers.get(id);

        if (myCustomer != null){
            myCustomer.
        }

    }


}

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
    private double balance;

    // keyed by transactionID
    private Map<Long, Transaction> transactions = new LinkedHashMap<>();

    public Map<Long, Transaction> getTransactions() {
        Map<Long, Transaction> tCopy = new HashMap<>();

        for (Long transactionID : transactions.keySet()){
            Transaction originalTxn = transactions.get(transactionID);

            Transaction copyTxn = new Transaction(originalTxn.getRoutingNUmber(), originalTxn.getCustomerId(),
                    originalTxn.getTransactionId(),
                    originalTxn.getTransactionAmount());

            tCopy.put(transactionID, copyTxn);
        }

        return tCopy;
    }

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

    public void commitTransaction(int routingNumber, long transactionId, int customerID, double amount){

        if (amount > balance){
            return;
        }

        amount += balance;

        Transaction newTxn = new Transaction(routingNumber, customerID, transactionId, amount);

        transactions.put(transactionId, newTxn);
    }

    @Override
    public String toString() {
        return "%s $%.2f".formatted(accountType, balance);
    }
}

class Transaction {
    static int TRANSACTION_ID = 100000;

    private int routingNUmber;
    private int customerId;
    private long transactionId;
    private double transactionAmount;

    public int getRoutingNUmber() {
        return routingNUmber;
    }

    public void setRoutingNUmber(int routingNUmber) {
        this.routingNUmber = routingNUmber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Transaction(int routingNUmber, int customerId, long transactionId, double transactionAmount) {
        this.routingNUmber = routingNUmber;
        this.customerId = customerId;
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
    }


}


// Modify BC
// getCustomerID:String; customerID is 15 digit string with leading zeros
// Code in other packages can't instantiate a new BC
// getAccounts: List<BankAccount>
// Return defensive copy of accounts
// multipe accts
// getAccount(AccountType type): Account
// single acct
// assume cr will have exactly one checking and one savings

class BankCustomer {

    private static int lastCustomerId = 10_000_000;

    private final String name;
    private final String customerId;
    private final List<BankAccount> accounts = new ArrayList<>();

    public BankCustomer(String name, double checkingAmount, double savingsAmount) {
        this.name = name;
        this.customerId = "fjsfiodapfjsaiof";
        accounts.add(new BankAccount(BankAccount.AccountType.CHECKING, checkingAmount));
        accounts.add(new BankAccount(BankAccount.AccountType.SAVINGS, savingsAmount));
    }



    public String getName() {
        return name;
    }

    public List<BankAccount> getAccounts() {
        List<BankAccount> defCopyAccounts = new ArrayList<>();

        for (BankAccount acct : accounts){
            defCopyAccounts.add(new BankAccount(acct.getAccountType(), acct.getBalance()));
        }

        return defCopyAccounts;
    }

    public BankAccount getAccount (BankAccount.AccountType accountType){
        for (BankAccount acct : accounts){
            if (acct.getAccountType() == accountType){
                return new BankAccount(accountType, acct.getBalance());
            }
        }
        return null;
    }


    @Override
    public String toString() {

        String[] accountStrings = new String[accounts.size()];
        Arrays.setAll(accountStrings, i -> accounts.get(i).toString());
        return "Customer: %s (id:%015d)%n\t%s%n".formatted(name, customerId,
                String.join("\n\t", accountStrings));
    }
}
