package Projects.BankManagement.Task;

import Projects.BankManagement.Exceptions.InsufficientBalanceException;
import Projects.BankManagement.Services.InterestService;

public class InterestTask implements Runnable {

    private final InterestService interestService;

    public InterestTask(InterestService interestService) {
        this.interestService = interestService;
    }

    @Override
    public void run() {
        try {
            interestService.applyInterest();
        } catch (Exception e) {
            System.err.println("Interest task failed: " + e.getMessage());
        }
    }
}




