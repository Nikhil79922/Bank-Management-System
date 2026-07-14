package Projects.BankManagement.Task;

import Projects.BankManagement.Services.TransactionService;

public class withDrawTransactionTask implements Runnable {

    private final TransactionService transactionService;
    private final int accountId;
    private final double amount;
    private final String note;

    public withDrawTransactionTask(
            TransactionService transactionService,
            int accountId,
            double amount,
            String note) {

        this.transactionService = transactionService;
        this.accountId = accountId;
        this.amount = amount;
        this.note = note;
    }

    @Override
    public void run() {
        try {
            transactionService.withdraw(accountId, amount, note);
        } catch (Exception e) {
            System.out.println(
                    "Withdrawal Task Failed : " + e.getMessage());
        }
    }
}
