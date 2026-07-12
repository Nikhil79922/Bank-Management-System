package Projects.BankManagement.Models;

import java.time.LocalDateTime;

import static Projects.BankManagement.Utils.Constants.SAVINGS;
import static Projects.BankManagement.Utils.Constants.SAVING_INTEREST;

public class SavingAccount extends Accounts{
    public SavingAccount(int accountId, int userId, double balance, boolean active, LocalDateTime created_at) {
        super(accountId, userId, SAVINGS , balance, active, SAVING_INTEREST, created_at);
    }
}
