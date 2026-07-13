package Projects.BankManagement.Main;

import Projects.BankManagement.Exceptions.InsufficientBalanceException;
import Projects.BankManagement.Exceptions.InvalidAmountException;
import Projects.BankManagement.Exceptions.InvalidStatusException;
import Projects.BankManagement.Exceptions.NotfoundException;
import Projects.BankManagement.Models.Accounts;
import Projects.BankManagement.Models.Customer;
import Projects.BankManagement.Models.Transaction;
import Projects.BankManagement.Services.AccountService;
import Projects.BankManagement.Services.BackupService;
import Projects.BankManagement.Services.CustomerService;
import Projects.BankManagement.Services.DashboardService;
import Projects.BankManagement.Services.FraudDetectionService;
import Projects.BankManagement.Services.InterestService;
import Projects.BankManagement.Services.TransactionService;
import Projects.BankManagement.Task.BackupTask;
import Projects.BankManagement.Task.ClearSuspicionTask;
import Projects.BankManagement.Task.DashboardTask;
import Projects.BankManagement.Task.FraudDetectionTask;
import Projects.BankManagement.Task.InterestTask;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static Projects.BankManagement.Utils.Constants.*;

public class Mains {

    // Services
    private static final CustomerService customerService = new CustomerService();
    private static final AccountService accountService = new AccountService();
    private static final TransactionService transactionService = new TransactionService();
    private static final DashboardService dashboardService = new DashboardService();
    private static final FraudDetectionService fraudDetectionService = new FraudDetectionService();
    private static final InterestService interestService = new InterestService();
    private static final BackupService backupService = new BackupService();

    private static final Scanner scanner = new Scanner(System.in);

    // Background scheduler
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    public static void main(String[] args) {

        startBackgroundTasks();

        boolean running = true;

        System.out.println("\n########################################");
        System.out.println("#   WELCOME TO BANK MANAGEMENT SYSTEM   #");
        System.out.println("########################################\n");

        while (running) {
            printMainMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> customerMenu();
                case 2 -> accountMenu();
                case 3 -> transactionMenu();
                case 4 -> dashboardService.printDashboard();
                case 5 -> fraudMenu();
                case 6 -> backupService.backupAll();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice. Please try again.\n");
            }
        }

        shutdown();
    }

    // ==================== MENUS ====================

    private static void printMainMenu() {
        System.out.println("================ MAIN MENU ================");
        System.out.println("1. Customer Management");
        System.out.println("2. Account Management");
        System.out.println("3. Transactions");
        System.out.println("4. Dashboard");
        System.out.println("5. Fraud Detection");
        System.out.println("6. Backup Now");
        System.out.println("0. Exit");
        System.out.println("============================================");
    }

    private static void customerMenu() {

        System.out.println("\n---------- Customer Management ----------");
        System.out.println("1. Create Customer");
        System.out.println("2. Search Customer");
        System.out.println("3. Update Customer Name");
        System.out.println("4. Update Customer Email");
        System.out.println("5. Update Customer Phone");
        System.out.println("6. Delete Customer");
        System.out.println("0. Back to Main Menu");
        System.out.println("------------------------------------------");

        int choice = readInt("Enter your choice: ");

        try {
            switch (choice) {
                case 1 -> {
                    String name = readString("Enter Name: ");
                    String email = readString("Enter Email: ");
                    String phone = readString("Enter Phone: ");
                    customerService.createCustomer(name, email, phone);
                }
                case 2 -> {
                    int id = readInt("Enter Customer ID: ");
                    Customer customer = customerService.getCustomer(id);
                    System.out.println(customer);
                }
                case 3 -> {
                    int id = readInt("Enter Customer ID: ");
                    String name = readString("Enter New Name: ");
                    customerService.updateName(id, name);
                }
                case 4 -> {
                    int id = readInt("Enter Customer ID: ");
                    String email = readString("Enter New Email: ");
                    customerService.updateEmail(id, email);
                }
                case 5 -> {
                    int id = readInt("Enter Customer ID: ");
                    String phone = readString("Enter New Phone: ");
                    customerService.updatePhone(id, phone);
                }
                case 6 -> {
                    int id = readInt("Enter Customer ID: ");
                    customerService.deleteCustomer(id);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.\n");
            }
        } catch (NotfoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void accountMenu() {

        System.out.println("\n---------- Account Management ----------");
        System.out.println("1. Create Account");
        System.out.println("2. Search Account");
        System.out.println("3. Check Balance");
        System.out.println("4. Update Account Status");
        System.out.println("5. Update Account Interest");
        System.out.println("6. Update Account Type");
        System.out.println("7. Delete Account");
        System.out.println("0. Back to Main Menu");
        System.out.println("------------------------------------------");

        int choice = readInt("Enter your choice: ");

        try {
            switch (choice) {
                case 1 -> {
                    int userId = readInt("Enter Customer ID: ");
                    String type = readString("Enter Account Type (SAVINGS/CURRENT): ").trim().toUpperCase();
                    boolean active = readBoolean("Is Active? (true/false): ");
                    accountService.createAccount(userId, active, type);
                }
                case 2 -> {
                    int id = readInt("Enter Account ID: ");
                    Accounts account = accountService.searchAccount(id);
                    System.out.println(account);
                }
                case 3 -> {
                    int id = readInt("Enter Account ID: ");
                    double balance = accountService.checkAccountBalance(id);
                    System.out.println("Balance: ₹" + balance);
                }
                case 4 -> {
                    int id = readInt("Enter Account ID: ");
                    boolean active = readBoolean("Set Active? (true/false): ");
                    accountService.updateAccountStatus(id, active);
                }
                case 5 -> {
                    int id = readInt("Enter Account ID: ");
                    double interest = readDouble("Enter New Interest Rate: ");
                    accountService.updateAccountInterest(id, interest);
                }
                case 6 -> {
                    int id = readInt("Enter Account ID: ");
                    String type = readString("Enter New Account Type (SAVINGS/CURRENT): ").trim().toUpperCase();
                    accountService.updateAccountType(id, type);
                }
                case 7 -> {
                    int id = readInt("Enter Account ID: ");
                    accountService.deleteAccount(id);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.\n");
            }
        } catch (NotfoundException | InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void transactionMenu() {

        System.out.println("\n---------- Transactions ----------");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer Money");
        System.out.println("4. Transaction History");
        System.out.println("0. Back to Main Menu");
        System.out.println("-----------------------------------");

        int choice = readInt("Enter your choice: ");

        try {
            switch (choice) {
                case 1 -> {
                    int id = readInt("Enter Account ID: ");
                    double amount = readDouble("Enter Amount: ");
                    String note = readString("Enter Note: ");
                    transactionService.deposit(id, amount, note);
                }
                case 2 -> {
                    int id = readInt("Enter Account ID: ");
                    double amount = readDouble("Enter Amount: ");
                    String note = readString("Enter Note: ");
                    transactionService.withdraw(id, amount, note);
                }
                case 3 -> {
                    int fromId = readInt("Enter From Account ID: ");
                    int toId = readInt("Enter To Account ID: ");
                    double amount = readDouble("Enter Amount: ");
                    String note = readString("Enter Note: ");
                    transactionService.transferMoney(fromId, toId, amount, note);
                }
                case 4 -> {
                    int id = readInt("Enter Account ID: ");
                    List<Transaction> history = transactionService.getTransactionHistory(id);
                    if (!history.isEmpty()) {
                        history.forEach(System.out::println);
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.\n");
            }
        } catch (NotfoundException | InvalidAmountException
                 | InsufficientBalanceException | InvalidStatusException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void fraudMenu() {

        System.out.println("\n---------- Fraud Detection ----------");
        System.out.println("1. Scan All Accounts");
        System.out.println("2. Mark Account Suspicious");
        System.out.println("3. Check Account Suspicious Status");
        System.out.println("4. Clear Expired Suspicion Flags");
        System.out.println("0. Back to Main Menu");
        System.out.println("--------------------------------------");

        int choice = readInt("Enter your choice: ");

        try {
            switch (choice) {
                case 1 -> fraudDetectionService.scanAccounts();
                case 2 -> {
                    int id = readInt("Enter Account ID: ");
                    fraudDetectionService.markSuspicious(id);
                }
                case 3 -> {
                    int id = readInt("Enter Account ID: ");
                    boolean suspicious = fraudDetectionService.isSuspicious(id);
                    System.out.println("Suspicious: " + suspicious);
                }
                case 4 -> fraudDetectionService.clearExpiredSuspicion();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.\n");
            }
        } catch (NotfoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==================== BACKGROUND TASKS ====================

    private static void startBackgroundTasks() {

        // Apply interest once a day (demo: every 24 hours)
        scheduler.scheduleAtFixedRate(
                new InterestTask(interestService), 1, 24, TimeUnit.HOURS);

        // Scan for fraud every 5 minutes
        scheduler.scheduleAtFixedRate(
                new FraudDetectionTask(fraudDetectionService), 1, 5, TimeUnit.MINUTES);

        // Clear expired suspicion flags every hour
        scheduler.scheduleAtFixedRate(
                new ClearSuspicionTask(fraudDetectionService), 10, 60, TimeUnit.MINUTES);

        // Backup data every 30 minutes
        scheduler.scheduleAtFixedRate(
                new BackupTask(backupService), 30, 30, TimeUnit.MINUTES);

        // Print dashboard snapshot every 15 minutes
        scheduler.scheduleAtFixedRate(
                new DashboardTask(dashboardService), 15, 15, TimeUnit.MINUTES);

        System.out.println("Background tasks scheduled: Interest, Fraud Detection, " +
                "Clear Suspicion, Backup, Dashboard.\n");
    }

    private static void shutdown() {

        System.out.println("\nShutting down background tasks...");

        scheduler.shutdown();

        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Goodbye!");
        scanner.close();
    }

    // ==================== INPUT HELPERS ====================

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(input);
            }
            System.out.println("Please enter 'true' or 'false'.");
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}