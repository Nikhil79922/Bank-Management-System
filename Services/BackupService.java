package Projects.BankManagement.Services;

import static Projects.BankManagement.Storeage.AccountFileManagement.loadAccounts;
import static Projects.BankManagement.Storeage.BackupFileManagement.*;
import static Projects.BankManagement.Storeage.CustomerFileManagement.loadCustomers;
import static Projects.BankManagement.Storeage.TransactionFileManagement.loadTransactions;

public class BackupService {

        public void backupAll() {

            System.out.println("\n========== BACKUP STARTED ==========");

            backupAllCustomers(loadCustomers());
            System.out.println("✓ Customers backed up.");

            backupAllAccounts(loadAccounts());
            System.out.println("✓ Accounts backed up.");

            backupAllTransactions(loadTransactions());
            System.out.println("✓ Transactions backed up.");

            System.out.println("========== BACKUP COMPLETED ==========\n");
        }
    }
