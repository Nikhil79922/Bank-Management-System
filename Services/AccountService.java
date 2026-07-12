package Projects.BankManagement.Services;

import Projects.BankManagement.Exceptions.InvalidAmountException;
import Projects.BankManagement.Exceptions.NotfoundException;
import Projects.BankManagement.Models.Accounts;
import Projects.BankManagement.Models.CurrentAccount;
import Projects.BankManagement.Models.SavingAccount;

import java.time.LocalDateTime;
import java.util.Map;

import static Projects.BankManagement.Storeage.AccountFileManagement.*;
import static Projects.BankManagement.Utils.Constants.*;
import static Projects.BankManagement.Utils.IdGenerator.nextAccountId;

public class AccountService {

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

    public void createAccount(int userId, boolean active, String accountType) {

        System.out.println("========== Account Creation ==========");
        System.out.println("Initializing account creation process...");

        int accountId = nextAccountId();

        Accounts account;
        LocalDateTime createdAt = LocalDateTime.now();

        if (SAVINGS.equals(accountType)) {
            account = new SavingAccount(accountId, userId, active, createdAt);
        } else if (CURRENT.equals(accountType)) {
            account = new CurrentAccount(accountId, userId, active, createdAt);
        } else {
            throw new IllegalArgumentException("Invalid account type.");
        }

        saveAccount(account);

        System.out.println("Account created successfully.");
        System.out.println("Account ID   : " + accountId);
        System.out.println("Customer ID  : " + userId);
        System.out.println("Account Type : " + accountType);
        System.out.println("======================================\n");
    }

    public Accounts searchAccount(int accountId)
            throws NotfoundException {

        System.out.println("========== Account Search ==========");
        System.out.println("Searching account...");

        Map<Integer, Accounts> accounts = loadAccounts();

        Accounts account = findAccount(accounts, accountId);

        System.out.println("Account found successfully.");
        System.out.println("Account ID : " + accountId);
        System.out.println("====================================\n");

        return account;
    }

    public double checkAccountBalance(int accountId)
            throws NotfoundException {

        System.out.println("========== Account Balance ==========");

        Map<Integer, Accounts> accounts = loadAccounts();

        Accounts account = findAccount(accounts, accountId);

        System.out.println("Balance fetched successfully.");
        System.out.println("Account ID : " + accountId);
        System.out.println("Balance    : " + account.getBalance());
        System.out.println("=====================================\n");

        return account.getBalance();
    }

    public void deleteAccount(int accountId)
            throws NotfoundException {

        System.out.println("========== Account Deletion ==========");
        System.out.println("Deleting account...");

        Map<Integer, Accounts> accounts = loadAccounts();

        Accounts account = accounts.remove(accountId);

        if (account == null) {
            throw new NotfoundException(
                    "Bank account not found with Account ID: " + accountId);
        }

        saveAllAccount(accounts);

        System.out.println("Account deleted successfully.");
        System.out.println("Account ID : " + accountId);
        System.out.println("======================================\n");
    }

    public void updateAccountStatus(int accountId, boolean active)
            throws NotfoundException {

        System.out.println("========== Update Account Status ==========");
        System.out.println("Updating account status...");

        Map<Integer, Accounts> accounts = loadAccounts();

        Accounts account = findAccount(accounts, accountId);

        account.setActive(active);

        saveAllAccount(accounts);

        System.out.println("Account status updated successfully.");
        System.out.println("Account ID : " + accountId);
        System.out.println("Status     : " + (active ? "ACTIVE" : "INACTIVE"));
        System.out.println("===========================================\n");
    }

    public void updateAccountInterest(int accountId, double interest)
            throws NotfoundException, InvalidAmountException {

        System.out.println("========== Update Interest ==========");

        if (interest < 0) {
            throw new InvalidAmountException(
                    "Interest cannot be negative.");
        }

        Map<Integer, Accounts> accounts = loadAccounts();

        Accounts account = findAccount(accounts, accountId);

        account.setInterest(interest);

        saveAllAccount(accounts);

        System.out.println("Interest updated successfully.");
        System.out.println("Account ID : " + accountId);
        System.out.println("Interest   : " + interest + "%");
        System.out.println("=====================================\n");
    }

    public void updateAccountType(int accountId, String accountType)
            throws NotfoundException, InvalidAmountException {

        System.out.println("========== Update Account Type ==========");

        if (!SAVINGS.equals(accountType) && !CURRENT.equals(accountType)) {
            throw new IllegalArgumentException(
                    "Invalid account type.");
        }

        Map<Integer, Accounts> accounts = loadAccounts();

        Accounts account = findAccount(accounts, accountId);

        account.setAccountType(accountType);

        if (CURRENT.equals(accountType)) {
            account.setInterest(0);
        } else {
            account.setInterest(SAVING_INTEREST);
        }

        saveAllAccount(accounts);

        System.out.println("Account type updated successfully.");
        System.out.println("Account ID   : " + accountId);
        System.out.println("Account Type : " + accountType);
        System.out.println("=========================================\n");
    }
}