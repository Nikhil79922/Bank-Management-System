package Projects.BankManagement.Task;

import Projects.BankManagement.Services.FraudDetectionService;

public class FraudDetectionTask implements Runnable{

    private final FraudDetectionService fraudDetection;

    public FraudDetectionTask(FraudDetectionService fraudDetection){
        this.fraudDetection=fraudDetection;
    }

    @Override
    public void run() {
        try {
            fraudDetection.scanAccounts();
        } catch (Exception e) {
            System.err.println("Fraud detection task failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
