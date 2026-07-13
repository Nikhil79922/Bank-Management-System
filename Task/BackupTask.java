package Projects.BankManagement.Task;

import Projects.BankManagement.Services.BackupService;

public class BackupTask implements Runnable{

    private final BackupService backup ;

    public BackupTask(BackupService backup) {
        this.backup = backup;
    }

    @Override
    public void run() {
        try {
            backup.backupAll();
        } catch (Exception e) {
            System.err.println("Backup task failed: " + e.getMessage());
        }
    }
}
