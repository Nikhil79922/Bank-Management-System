package Projects.BankManagement.Storeage;

import Projects.BankManagement.Models.Accounts;
import Projects.BankManagement.Models.Customer;
import Projects.BankManagement.Models.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static Projects.BankManagement.Utils.Constants.*;


public class BackupFileManagement {
    public static void backupAllTransactions(Map<Integer, Transaction> transactions) {

        try (BufferedWriter fswrite =
                     new BufferedWriter(new FileWriter(TRANSACTIONS_BACKUP_FILE))) {

            for (Transaction transaction : transactions.values()) {

                fswrite.write(transaction.toFileString());
                fswrite.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void backupAllCustomers(Map<Integer, Customer> customers) {
        try ( BufferedWriter fswrite = new BufferedWriter(new FileWriter(CUSTOMER_BACKUP_FILE));
        ){
            for(Customer cus : customers.values() ){
                String newLine=cus.toString();
                fswrite.write(newLine);
                fswrite.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void backupAllAccounts(Map<Integer, Accounts> accounts) {
        try (BufferedWriter fswrite = new BufferedWriter(new FileWriter(ACCOUNTS_BACKUP_FILE));
        ){
            for(Accounts acc : accounts.values()){
                String newLine=acc.toFileString();
                fswrite.write(newLine);
                fswrite.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
