package Projects.BankManagement.Models;

import java.time.LocalDateTime;

public class Transaction {
    private final int transactionId;
    private final int accountId;
    private final double amount;
    private final String transactionType;
    private final String note;
    private final LocalDateTime created_at;

    public Transaction(int transactionId , int accountId , double amount , String transactionType , String note , LocalDateTime created_at){
        this.transactionId = transactionId;
        this.accountId=accountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.note = note;
        this.created_at = created_at;
    }


    // Getter
    public String getTransactionType() {
        return transactionType;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getNote() {
        return note;
    }

    public double getAmount() {
        return amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                ", note='" + note + '\'' +
                ", created_at=" + created_at +
                '}';
    }

    public String toFileString() {
        return transactionId + "," +
                accountId + "," +
                amount + "," +
                transactionType + "," +
                note + "," +
                created_at;
    }
}
