// Author: Fafali Caryn Aku Awittor
// Date: 17th October,2025
// Description: A simulation of a Bank Acoount system that supports both current and savings with festures such as deposits, withdrawals, monthly maintenance and transfers between accounts

public class BankAccount {

    // Instantiating data fields
    private AccountType acctType;
    private String acctID;
    private double balance;
    private int numWithdrawals;
    private boolean inTheRed;
    double minBalance;
    double interestRate;
    double maintenanceFee;
    int withdrawalLimit;

    // Instantiating constant data fields
    final double CURRENT_ACCT_MIN_BALANCE=50.0;
    final double SAVINGS_ACCT_MIN_BALANCE=80.0;
    final double SAVINGS_ACCT_INTEREST_RATE=0.10;
    final double CURRENT_ACCT_MAINTENANCE_FEE=60.0;
    final double SAVINGS_WITHDRAWAL_LIMIT=2;



    // First Constructor
    public BankAccount(AccountType Type, String id) {
        acctType = Type;
        acctID = id;
        balance = 0;
        numWithdrawals = 0;

        if (acctType == AccountType.CURRENT) {
            minBalance = CURRENT_ACCT_MIN_BALANCE;
            interestRate = 0;
            maintenanceFee = CURRENT_ACCT_MAINTENANCE_FEE;
            withdrawalLimit=-1;
        } else {
            minBalance = SAVINGS_ACCT_MIN_BALANCE;
            interestRate = SAVINGS_ACCT_INTEREST_RATE;
            maintenanceFee = 0;
            withdrawalLimit= (int) SAVINGS_WITHDRAWAL_LIMIT;

        }

        if(balance < minBalance){
            inTheRed= true;
        }else {
            inTheRed=false;
        }
    }


    //Constructor
    public BankAccount(AccountType type, String id, double openingBalance){
        acctType=type;
        acctID= id;
        balance= openingBalance;

        if (acctType == AccountType.CURRENT) {
            minBalance = CURRENT_ACCT_MIN_BALANCE;
            interestRate = 0;
            maintenanceFee = CURRENT_ACCT_MAINTENANCE_FEE;
        } else {
            minBalance = SAVINGS_ACCT_MIN_BALANCE;
            interestRate = SAVINGS_ACCT_INTEREST_RATE;
            maintenanceFee = 0;
            withdrawalLimit= (int) SAVINGS_WITHDRAWAL_LIMIT;

        }

        if(balance < minBalance){
            inTheRed= true;
        }else {
            inTheRed=false;
        }

    }

    // Accessor for balance
    public double getBalance() {
        return balance;
    }

    // Accessor for Account Type
    public AccountType getAccountType() {
        return acctType;
    }

    // Accessor for  Account ID
    public String getAccountID() {
        return acctID;
    }

    // Accessor for Minimum balance
    public double getMinBalance(){
        return minBalance;
    }

    // Accessor for minimum balance
    public boolean withdraw(double amount){

        //Checking if number of withdrawals has been exceeded
        if (( numWithdrawals >= withdrawalLimit && withdrawalLimit != -1)) {
            System.out.println("Sorry, could not perform withdrawal: Allowed withdrawals have been exceeded");
            return false;
        }

        // Checking if the bank account is in the red or the bank account does not have a sufficient balance
        if (inTheRed || (balance-amount)<minBalance){
            System.out.println(" Sorry, could not perform withdrawal: Insufficient balance");
            return false;

        }

        // adding amount to balance and adding an increment to number of withdrawals
        balance -= amount;
        numWithdrawals++;

        return true;

    }

    // method that adds specified amount to the account balance
    public void deposit(double amount){
        balance +=amount;

    }

    // method that performs monthly maintenance on the account
    public void performMonthlyMaintenance() {

        // Calculating for monthly interest rate and the interest gotten every month
        double monthlyInterestRate = interestRate / 12;
        double interestInMonth = monthlyInterestRate * balance;

        // adding the interest to the balance and subtracting maintenance fee
        balance += interestInMonth;
        balance -= maintenanceFee;
        inTheRed=balance < minBalance;
        numWithdrawals = 0;

        if (inTheRed) {
            System.out.println(" WARNING: This account is in the red! ");

        }

        // Printing formatted summary
        System.out.println("Earned Interest: < " + interestInMonth + ">");
        System.out.println("Maintenance Fee: < " + maintenanceFee + ">");
        System.out.println("Updated Balance: < " + balance + ">");


    }

    // method that  performs a transfer between this bank account and another bank account and returns whether or not the transaction was successful
    public boolean transfer(boolean transferTo,BankAccount otherAccount,double amount) {
        if (transferTo){
            if (withdraw(amount) ){
                otherAccount.deposit(amount);
                return true;
            }
            else
                return false;
        }
        else{
            if (otherAccount.withdraw(amount)){
                this.deposit(amount);
                return true;
            }
            else
                return false;
        }
    }


}

