package Projects.BankManagement.Models;

import java.time.LocalDateTime;

import static Projects.BankManagement.Utils.Constants.DEBIT;

public class DebitTransaction extends Transaction{
    public DebitTransaction(int transactionId, int accountId, double amount, String note, LocalDateTime created_at) {
        super(transactionId, accountId, amount, DEBIT , note, created_at);
    }
}
