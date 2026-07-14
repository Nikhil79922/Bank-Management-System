package Projects.BankManagement.Task;

import Projects.BankManagement.Services.TransactionService;

public class DepositTransactionTask implements Runnable {

    private final TransactionService transactionService;
    private final int accountId;
    private final double amount;
    private final String note;

    public DepositTransactionTask(
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

            transactionService.deposit(accountId, amount, note);

        } catch (Exception e) {

            System.out.println(
                    "Deposit Task Failed : " + e.getMessage());

        }
    }
}