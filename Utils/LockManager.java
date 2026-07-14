package Projects.BankManagement.Utils;

import MultiThread.Locks.ReentrantLocks;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LockManager {
 private static  final ConcurrentHashMap<Integer , ReentrantLock> accountIdLock = new ConcurrentHashMap<>();
 private static final ConcurrentHashMap<Integer , ReentrantLock> customerIdLock = new ConcurrentHashMap<>();

 public static ReentrantLock getAccountLock(int accountId){
     return accountIdLock.computeIfAbsent(accountId , id -> new ReentrantLock());
 }
    public static ReentrantLock getCustomerLock(int customerId){
        return accountIdLock.computeIfAbsent(customerId , id -> new ReentrantLock());
    }
}
