package Projects.BankManagement.Services;

import Projects.BankManagement.Exceptions.InsufficientBalanceException;
import Projects.BankManagement.Exceptions.InvalidAmountException;
import Projects.BankManagement.Exceptions.InvalidStatusException;
import Projects.BankManagement.Exceptions.NotfoundException;
import Projects.BankManagement.Models.Accounts;
import Projects.BankManagement.Models.CreditTransaction;
import Projects.BankManagement.Models.DebitTransaction;
import Projects.BankManagement.Models.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Projects.BankManagement.Storeage.AccountFileManagement.loadAccounts;
import static Projects.BankManagement.Storeage.AccountFileManagement.saveAllAccount;
import static Projects.BankManagement.Storeage.TransactionFileManagement.loadTransactions;
import static Projects.BankManagement.Storeage.TransactionFileManagement.saveTransaction;
import static Projects.BankManagement.Utils.IdGenerator.nextTransactionId;

public class TransactionService {

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

    // Helper Method
    private static void validateAccount(Accounts account)
            throws InvalidStatusException {

        if (!account.getActive()) {
            throw new InvalidStatusException(
                    "This account is inactive. Transaction cannot be processed.");
        }

        if (account.isSuspicious()) {
            throw new InvalidStatusException(
                    "This account has been marked as suspicious. Transaction cannot be processed.");
        }
    }

    public void withdraw(int accountId, double amount, String note)
            throws NotfoundException,
            InvalidAmountException,
            InsufficientBalanceException,
            InvalidStatusException {

        System.out.println("========== Withdrawal ==========");
        System.out.println("Initializing withdrawal...");

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero.");
        }

        Map<Integer, Accounts> accounts = loadAccounts();

        Accounts account = findAccount(accounts, accountId);

        validateAccount(account);

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException(
                    "Insufficient balance to complete this withdrawal.");
        }

        double updatedBalance = account.getBalance() - amount;

        account.setBalance(updatedBalance);

        saveAllAccount(accounts);

        DebitTransaction transaction = new DebitTransaction(
                nextTransactionId(),
                accountId,
                amount,
                note,
                LocalDateTime.now()
        );

        saveTransaction(transaction);

        System.out.println("Withdrawal completed successfully.");
        System.out.println("Account ID      : " + accountId);
        System.out.println("Amount          : " + amount);
        System.out.println("Updated Balance : " + updatedBalance);
        System.out.println("=================================\n");
    }

    public void deposit(int accountId, double amount, String note)
            throws NotfoundException,
            InvalidAmountException,
            InvalidStatusException,
            InsufficientBalanceException {

        System.out.println("========== Deposit ==========");
        System.out.println("Initializing deposit...");

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero.");
        }

        Map<Integer, Accounts> accounts = loadAccounts();

        Accounts account = findAccount(accounts, accountId);

        validateAccount(account);

        double updatedBalance = account.getBalance() + amount;

        account.setBalance(updatedBalance);

        saveAllAccount(accounts);

        CreditTransaction transaction = new CreditTransaction(
                nextTransactionId(),
                accountId,
                amount,
                note,
                LocalDateTime.now()
        );

        saveTransaction(transaction);

        System.out.println("Deposit completed successfully.");
        System.out.println("Account ID      : " + accountId);
        System.out.println("Amount          : " + amount);
        System.out.println("Updated Balance : " + updatedBalance);
        System.out.println("=============================\n");
    }

    public void transferMoney(int fromAccountId,
                              int toAccountId,
                              double amount,
                              String note)
            throws NotfoundException,
            InvalidAmountException,
            InvalidStatusException,
            InsufficientBalanceException {

        System.out.println("========== Fund Transfer ==========");
        System.out.println("Initializing fund transfer...");

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Transfer amount must be greater than zero.");
        }

        if (fromAccountId == toAccountId) {
            throw new InvalidAmountException(
                    "Source and destination accounts cannot be the same.");
        }

        Map<Integer, Accounts> accounts = loadAccounts();

        Accounts fromAccount = findAccount(accounts, fromAccountId);
        Accounts toAccount = findAccount(accounts, toAccountId);

        validateAccount(fromAccount);
        validateAccount(toAccount);

        if (fromAccount.getBalance() < amount) {
            throw new InsufficientBalanceException(
                    "Insufficient balance to complete this transfer.");
        }

        double updatedSenderBalance = fromAccount.getBalance() - amount;
        double updatedReceiverBalance = toAccount.getBalance() + amount;

        fromAccount.setBalance(updatedSenderBalance);
        toAccount.setBalance(updatedReceiverBalance);

        saveAllAccount(accounts);

        LocalDateTime transferTime = LocalDateTime.now();

        DebitTransaction debitTransaction = new DebitTransaction(
                nextTransactionId(),
                fromAccountId,
                amount,
                note,
                transferTime
        );

        saveTransaction(debitTransaction);

        CreditTransaction creditTransaction = new CreditTransaction(
                nextTransactionId(),
                toAccountId,
                amount,
                note,
                transferTime
        );

        saveTransaction(creditTransaction);

        System.out.println("Fund transfer completed successfully.");
        System.out.println("From Account    : " + fromAccountId);
        System.out.println("To Account      : " + toAccountId);
        System.out.println("Transferred     : " + amount);
        System.out.println("Sender Balance  : " + updatedSenderBalance);
        System.out.println("Receiver Balance: " + updatedReceiverBalance);
        System.out.println("=====================================\n");
    }

    public List<Transaction> getTransactionHistory(int accountId)
            throws NotfoundException, InvalidStatusException {

        System.out.println("========== Transaction History ==========");
        System.out.println("Fetching transaction history...");

        Map<Integer, Accounts> accounts = loadAccounts();

        Accounts account = findAccount(accounts, accountId);

        validateAccount(account);

        Map<Integer, Transaction> transactions = loadTransactions();

        List<Transaction> history = new ArrayList<>();

        for (Transaction transaction : transactions.values()) {

            if (transaction.getAccountId() == accountId) {
                history.add(transaction);
            }
        }

        if (history.isEmpty()) {
            System.out.println("No transaction history found.");
            System.out.println("Account ID : " + accountId);
        } else {
            System.out.println("Transaction history fetched successfully.");
            System.out.println("Account ID          : " + accountId);
            System.out.println("Total Transactions  : " + history.size());

            System.out.println("-----------------------------------------");

            for (Transaction transaction : history) {
                System.out.println(transaction);
            }

            System.out.println("-----------------------------------------");
        }

        System.out.println("=========================================\n");

        return history;
    }
}