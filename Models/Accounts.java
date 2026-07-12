package Projects.BankManagement.Models;

import Projects.BankManagement.Exceptions.InsufficientBalanceException;
import Projects.BankManagement.Exceptions.InvalidAmountException;

import javax.management.InvalidAttributeValueException;
import java.time.LocalDateTime;

public class Accounts {
    private int accountId;
    private int userId;
    private String accountType;
    private boolean active;
    private double balance;
    private double interest;
    private boolean suspicious;
    private LocalDateTime created_at;

    public Accounts(int accountId , int userId , String accountType, double balance , boolean active , double interest , LocalDateTime created_at  ){
        this.accountId = accountId;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
        this.active = active;
        this.interest = interest;
        this.created_at = created_at;
        this.suspicious=false;
    }

    public Accounts(int accountId , int userId , String accountType, double balance , boolean active , double interest , LocalDateTime created_at , boolean suspicious ){
        this.accountId = accountId;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
        this.active = active;
        this.interest = interest;
        this.created_at = created_at;
        this.suspicious=suspicious;
    }


    //Getters
    public boolean getActive() {
        return active;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isSuspicious() {
        return suspicious;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public double getInterest() {
        return interest;
    }

    //setter
        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setBalance(double balance) throws InsufficientBalanceException {
        if (balance < 0) {
            throw new InsufficientBalanceException();
        }
        this.balance = balance;
    }

    public void setInterest(double interest) throws InvalidAmountException {
        if (interest < 0) {
            throw new InvalidAmountException("Interest cannot be negative");
        }
        this.interest = interest;
    }

    public void setSuspicious(boolean suspicious) {
        this.suspicious = suspicious;
    }


    public String toFileString() {
        return accountId + "," +
                userId + "," +
                accountType + "," +
                balance + "," +
                active + "," +
                interest + "," +
                created_at + "," +
                suspicious;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", accountType='" + accountType + '\'' +
                ", active=" + active +
                ", balance=" + balance +
                ", interest=" + interest +
                ", createdAt=" + created_at +
                '}';
    }
}
