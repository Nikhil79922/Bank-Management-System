package Projects.BankManagement.Task;

import Projects.BankManagement.Services.FraudDetectionService;

public class ClearSuspicionTask implements Runnable {

    private final FraudDetectionService fraudDetectionService;

    public ClearSuspicionTask(FraudDetectionService fraudDetectionService) {
        this.fraudDetectionService = fraudDetectionService;
    }

    @Override
    public void run() {
        try {
            fraudDetectionService.clearExpiredSuspicion();
        } catch (Exception e) {
            System.err.println("Clear Suspicion Task failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}