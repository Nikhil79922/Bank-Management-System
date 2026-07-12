package Projects.BankManagement.Models;

import java.time.LocalDateTime;
import static Projects.BankManagement.Utils.Constants.CREDIT;

public class CreditTransaction extends  Transaction{
    public CreditTransaction(int transactionId, int accountId, double amount, String note, LocalDateTime created_at) {
        super(transactionId, accountId, amount, CREDIT, note, created_at);
    }
}
