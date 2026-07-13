package Projects.BankManagement.Task;

import Projects.BankManagement.Services.DashboardService;

public class DashboardTask implements Runnable{
    private final DashboardService dashboardService;
    public DashboardTask(DashboardService dashboardService){
        this.dashboardService=dashboardService;
    }
    @Override
    public void run() {
        dashboardService.printDashboard();
    }
}
