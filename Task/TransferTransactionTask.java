package Projects.BankManagement.Task;

import Projects.BankManagement.Services.TransactionService;

public class TransferTransactionTask implements Runnable{

    private final TransactionService transactionService;

    private final int fromAccountId;
    private final int toAccountId;

    private final double amount;
    private final String note;

    public TransferTransactionTask (
            TransactionService transactionService,
            int fromAccountId,
            int toAccountId,
            double amount,
            String note) {

        this.transactionService = transactionService;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.note = note;
    }

    @Override
    public void run() {

        try {

            transactionService.transferMoney(fromAccountId, toAccountId, amount, note);

        } catch (Exception e) {

            System.err.println(
                    "Transfer failed from Account "
                            + fromAccountId
                            + " to Account "
                            + toAccountId
                            + " : "
                            + e.getMessage());

        }
    }
}
