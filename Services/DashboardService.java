package Projects.BankManagement.Services;

import Projects.BankManagement.Exceptions.InvalidStatusException;
import Projects.BankManagement.Models.Accounts;
import Projects.BankManagement.Models.Customer;
import Projects.BankManagement.Models.Transaction;

import java.util.Map;

import static Projects.BankManagement.Storeage.AccountFileManagement.loadAccounts;
import static Projects.BankManagement.Storeage.CustomerFileManagement.loadCustomers;
import static Projects.BankManagement.Storeage.TransactionFileManagement.loadTransactions;
import static Projects.BankManagement.Utils.Constants.*;

public class DashboardService {
    public int getTotalCustomers(){
        Map<Integer , Customer> customerMap = loadCustomers();
        return customerMap.size();
    }

    public int getTotalAccounts(){
        Map<Integer , Accounts> accountsMap = loadAccounts();
        return accountsMap.size();
    };

    public int getActiveAccounts(){
        Map<Integer , Accounts> accountsMap = loadAccounts();
        int count = 0;
                for(Accounts acc : accountsMap.values()){
                    if(acc.getActive()){
                        count++;
                    }
                }
        return count;
    };

    public int getInactiveAccounts(){
        Map<Integer , Accounts> accountsMap = loadAccounts();
        int count = 0;
        for(Accounts acc : accountsMap.values()){
            if(!acc.getActive()){
                count++;
            }
        }
        return count;
    };

    public int getSuspiciousAccounts(){
        Map<Integer , Accounts> accountsMap = loadAccounts();
        int count = 0;
        for(Accounts acc : accountsMap.values()){
            if(acc.isSuspicious()){
                count++;
            }
        }
        return count;
    };

    public double getTotalBankBalance(){
        Map<Integer , Accounts> accountsMap = loadAccounts();
        double count = 0;
        for(Accounts acc : accountsMap.values()){
                count  += acc.getBalance();
        }
        return count;
    };

    public double getTotalSavingsBalance(){
        Map<Integer , Accounts> accountsMap = loadAccounts();
        double count = 0;
        for(Accounts acc : accountsMap.values()){
            if(acc.getAccountType().equals(SAVINGS)){
                count  += acc.getBalance();
            }
        }
        return count;
    };

    public double getTotalCurrentBalance(){
        Map<Integer , Accounts> accountsMap = loadAccounts();
        double count = 0;
        for(Accounts acc : accountsMap.values()){
            if(acc.getAccountType().equals(CURRENT)){
                count  += acc.getBalance();
            }
        }
        return count;
    };

    public int getTotalTransactions(){
        Map<Integer , Transaction> transactionMap = loadTransactions();
        return transactionMap.size();
    };

    public int getTotalCreditTransactions(){
        Map<Integer , Transaction> transactionMap = loadTransactions();
        int count=0;
        for(Transaction trans : transactionMap.values()){
            if(trans.getTransactionType().equals(CREDIT)){
                count  ++;
            }
        }
        return count;
    };

    public int getTotalDebitTransactions(){
        Map<Integer , Transaction> transactionMap = loadTransactions();
        int count=0;
        for(Transaction trans : transactionMap.values()){
            if(trans.getTransactionType().equals(DEBIT)){
                count  ++;
            }
        }
        return count;
    };

    public void printDashboard() {

        System.out.println("\n========== BANK DASHBOARD ==========\n");

        System.out.println("Customers              : " + getTotalCustomers());

        System.out.println("Accounts               : " + getTotalAccounts());
        System.out.println("Active Accounts        : " + getActiveAccounts());
        System.out.println("Inactive Accounts      : " + getInactiveAccounts());
        System.out.println("Suspicious Accounts    : " + getSuspiciousAccounts());

        System.out.println();

        System.out.println("Total Bank Balance     : ₹" + getTotalBankBalance());
        System.out.println("Savings Balance        : ₹" + getTotalSavingsBalance());
        System.out.println("Current Balance        : ₹" + getTotalCurrentBalance());

        System.out.println();

        System.out.println("Total Transactions     : " + getTotalTransactions());
        System.out.println("Credit Transactions    : " + getTotalCreditTransactions());
        System.out.println("Debit Transactions     : " + getTotalDebitTransactions());

        System.out.println("\n====================================\n");
    }
}
