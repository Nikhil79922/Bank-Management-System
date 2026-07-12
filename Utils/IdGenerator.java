package Projects.BankManagement.Utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static AtomicInteger customerId = new AtomicInteger();
private static AtomicInteger accountId = new AtomicInteger();
private static  AtomicInteger transactionId = new AtomicInteger();

public static void initialize(int lastCustomerId , int lastAccountId , int lasTransactionId ){
    customerId.set(lastCustomerId);
    accountId.set(lastAccountId);
    transactionId.set(lasTransactionId);
}

    public static int nextCustomerId() {
        return customerId.incrementAndGet();
    }

    public static int nextAccountId() {
        return accountId.incrementAndGet();
    }

    public static int nextTransactionId() {
        return transactionId.incrementAndGet();
    }
}