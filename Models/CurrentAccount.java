package Projects.BankManagement.Models;
import java.time.LocalDateTime;
import static Projects.BankManagement.Utils.Constants.*;

public class CurrentAccount extends Accounts {
    public CurrentAccount(int accountId, int userId, double balance, boolean active, LocalDateTime created_at) {
        super(accountId, userId, CURRENT , balance, active, 0, created_at);
    }
}
