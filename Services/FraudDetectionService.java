package Projects.BankManagement.Services;

import Projects.BankManagement.Exceptions.NotfoundException;
import Projects.BankManagement.Models.Accounts;
import Projects.BankManagement.Models.Transaction;

import java.time.LocalDateTime;
import java.util.Map;

import static Projects.BankManagement.Storeage.AccountFileManagement.loadAccounts;
import static Projects.BankManagement.Storeage.AccountFileManagement.saveAllAccount;
import static Projects.BankManagement.Storeage.TransactionFileManagement.loadTransactions;

public class FraudDetectionService {

    // Helper Method
    private static Accounts findAccount(Map<Integer, Accounts> accounts, int accountId)
            throws NotfoundException {

        Accounts account = accounts.get(accountId);

        if (account == null) {
            throw new NotfoundException(
                    "Bank account not found with Account ID: " + accountId);
        }

        return account;
    }

    public void scanAccounts() throws NotfoundException {

        System.out.println("\n========== FRAUD SCAN STARTED ==========");

        Map<Integer, Accounts> accounts = loadAccounts();
        Map<Integer, Transaction> transactions = loadTransactions();

        int suspiciousCount = 0;
        LocalDateTime now = LocalDateTime.now();

        for (Accounts account : accounts.values()) {

            System.out.println("\nChecking Account : " + account.getAccountId());

            if (!account.getActive()) {
                System.out.println("Skipped -> Account is inactive.");
                continue;
            }

            if (account.isSuspicious()) {
                System.out.println("Already marked as suspicious.");
                suspiciousCount++;
                continue;
            }

            boolean suspicious = false;
            int recentTransactions = 0;

            for (Transaction transaction : transactions.values()) {

                if (transaction.getAccountId() != account.getAccountId()) {
                    continue;
                }

                System.out.println("Checking Transaction : " + transaction.getTransactionId());

                // Rule 1 : Large Transaction
                if (transaction.getAmount() >= 100000) {

                    suspicious = true;

                    System.out.println("⚠ High-value transaction detected.");
                    System.out.println("Transaction Amount : ₹" + transaction.getAmount());
                    System.out.println("Account marked as suspicious.");

                    break;
                }

                // Rule 2 : More than 5 transactions in last minute
                if (transaction.getCreated_at().isAfter(now.minusMinutes(1))) {
                    recentTransactions++;
                }
            }

            if (!suspicious && recentTransactions > 5) {
                markSuspicious(account.getAccountId());
                suspicious = true;

                System.out.println("⚠ Too many transactions detected.");
                System.out.println("Transactions in last minute : " + recentTransactions);
                System.out.println("Account marked as suspicious.");
            }

            if (suspicious) {

                suspiciousCount++;
            } else {
                System.out.println("No suspicious activity detected.");
            }
        }

        saveAllAccount(accounts);

        System.out.println("\nFraud Scan Completed Successfully.");
        System.out.println("Suspicious Accounts Found : " + suspiciousCount);
        System.out.println("========================================\n");
    }

    public void markSuspicious(int accountId) throws NotfoundException {

        System.out.println("\n========== MARK ACCOUNT AS SUSPICIOUS ==========");

        Map<Integer, Accounts> accounts = loadAccounts();
        Accounts account = findAccount(accounts, accountId);

        if (account.isSuspicious()) {
            System.out.println("Account is already marked as suspicious.");
            return;
        }

        account.setSuspicious(true);
        saveAllAccount(accounts);

        System.out.println("Account ID : " + accountId);
        System.out.println("Status     : Suspicious");
        System.out.println("Account marked successfully.");
        System.out.println("===============================================\n");
    }

    public void clearExpiredSuspicion() {

        System.out.println("\n========== CLEARING SUSPICIOUS FLAGS ==========");

        Map<Integer, Accounts> accounts = loadAccounts();

        int cleared = 0;

        for (Accounts account : accounts.values()) {

            if (account.isSuspicious()) {

                // Dummy logic for now.
                // Later you can replace this with a timestamp-based rule.
                account.setSuspicious(false);

                System.out.println("Cleared suspicious flag for Account : "
                        + account.getAccountId());

                cleared++;
            }
        }

        saveAllAccount(accounts);

        System.out.println("Total Accounts Cleared : " + cleared);
        System.out.println("===============================================\n");
    }

    public boolean isSuspicious(int accountId) throws NotfoundException {

        Map<Integer, Accounts> accounts = loadAccounts();
        Accounts account = findAccount(accounts, accountId);

        System.out.println("\n========== ACCOUNT STATUS ==========");
        System.out.println("Account ID : " + accountId);
        System.out.println("Suspicious : " + account.isSuspicious());
        System.out.println("====================================\n");

        return account.isSuspicious();
    }
}