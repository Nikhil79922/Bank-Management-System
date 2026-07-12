package Projects.BankManagement.Services;

import Projects.BankManagement.Exceptions.InsufficientBalanceException;
import Projects.BankManagement.Models.Accounts;
import Projects.BankManagement.Models.CreditTransaction;

import java.time.LocalDateTime;
import java.util.Map;

import static Projects.BankManagement.Storeage.AccountFileManagement.loadAccounts;
import static Projects.BankManagement.Storeage.AccountFileManagement.saveAllAccount;
import static Projects.BankManagement.Storeage.TransactionFileManagement.saveTransaction;
import static Projects.BankManagement.Utils.Constants.SAVINGS;
import static Projects.BankManagement.Utils.IdGenerator.nextTransactionId;

public class InterestService {

    public void applyInterest() throws InsufficientBalanceException {

        System.out.println("\n========== Interest Calculation Started ==========");

        Map<Integer, Accounts> accounts = loadAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No accounts found. Interest calculation skipped.");
            return;
        }

        for (Accounts account : accounts.values()) {

            System.out.println("\nChecking Account : " + account.getAccountId());

            if (!account.getAccountType().equals(SAVINGS)) {
                System.out.println("Skipped -> Not a Savings Account.");
                continue;
            }

            if (!account.getActive()) {
                System.out.println("Skipped -> Account is inactive.");
                continue;
            }

            if (account.isSuspicious()) {
                System.out.println("Skipped -> Account is marked as suspicious.");
                continue;
            }

            double interestAmount = account.getBalance() * account.getInterest();
            double updatedBalance = account.getBalance() + interestAmount;

            account.setBalance(updatedBalance);

            CreditTransaction transaction = new CreditTransaction(
                    nextTransactionId(),
                    account.getAccountId(),
                    interestAmount,
                    "Interest credited to account.",
                    LocalDateTime.now()
            );

            saveTransaction(transaction);

            System.out.println("Interest Applied Successfully");
            System.out.println("Account ID      : " + account.getAccountId());
            System.out.println("Old Balance     : " + (updatedBalance - interestAmount));
            System.out.println("Interest Amount : " + interestAmount);
            System.out.println("New Balance     : " + updatedBalance);
        }

        saveAllAccount(accounts);

        System.out.println("\nAll eligible accounts updated successfully.");
        System.out.println("========== Interest Calculation Completed ==========\n");
    }
}