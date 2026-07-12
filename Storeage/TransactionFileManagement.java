package Projects.BankManagement.Storeage;

import Projects.BankManagement.Models.CreditTransaction;
import Projects.BankManagement.Models.DebitTransaction;
import Projects.BankManagement.Models.Transaction;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static Projects.BankManagement.Utils.Constants.*;

public class TransactionFileManagement {

    public static Map<Integer, Transaction> loadTransactions() {

        try (BufferedReader fsread = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {

            Map<Integer, Transaction> transactions = new HashMap<>();
            String line;

            while ((line = fsread.readLine()) != null) {

                if (line.isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data.length != TRANSACTION_FIELDS) {
                    continue;
                }

                int transactionId = Integer.parseInt(data[0]);
                int accountId = Integer.parseInt(data[1]);
                double amount = Double.parseDouble(data[2]);
                String transactionType = data[3];
                String note = data[4];
                LocalDateTime createdAt = LocalDateTime.parse(data[5]);

                Transaction transaction;

                if (CREDIT.equals(transactionType)) {
                    transaction = new CreditTransaction(
                            transactionId,
                            accountId,
                            amount,
                            note,
                            createdAt
                    );
                } else if (DEBIT.equals(transactionType)) {
                    transaction = new DebitTransaction(
                            transactionId,
                            accountId,
                            amount,
                            note,
                            createdAt
                    );
                } else {
                    continue;
                }

                transactions.put(transactionId, transaction);
            }

            return transactions;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveTransaction(Transaction transaction) {

        try (BufferedWriter fswrite =
                     new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {

            fswrite.write(transaction.toFileString());
            fswrite.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveAllTransactions(Map<Integer, Transaction> transactions) {

        try (BufferedWriter fswrite =
                     new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {

            for (Transaction transaction : transactions.values()) {

                fswrite.write(transaction.toFileString());
                fswrite.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}