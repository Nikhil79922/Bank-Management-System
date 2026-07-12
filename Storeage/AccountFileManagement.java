package Projects.BankManagement.Storeage;

import Projects.BankManagement.Models.Accounts;
import Projects.BankManagement.Models.Customer;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static Projects.BankManagement.Utils.Constants.*;

public class AccountFileManagement {
    public static Map<Integer , Accounts> loadAccounts() {
        try(BufferedReader fsread = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            Map<Integer , Accounts> accounts= new HashMap<>();
            while((line = fsread.readLine()) != null){
                if(line.isEmpty()){
                    continue;
                }
                String[] Data= line.split(",");
                if(Data.length != ACCOUNT_FIELDS){
                    continue;
                }
                int accountID =Integer.parseInt(Data[0]);
                int userID =Integer.parseInt(Data[1]);
                String accountType = Data[2];
                double balance = Double.parseDouble(Data[3]);
                boolean active = Boolean.parseBoolean(Data[4]);
                double interest = Double.parseDouble(Data[5]);
                LocalDateTime created_at = LocalDateTime.parse(Data[6]);
                boolean suspicious = Boolean.parseBoolean(Data[7]);

                Accounts acc = new Accounts(
                        accountID,
                        userID,
                        accountType,
                        balance,
                        active,
                        interest,
                        created_at,
                        suspicious
                );
                accounts.put(accountID , acc);
            }
            return accounts;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void saveAccount(Accounts acc) {
        try (BufferedWriter fswrite = new BufferedWriter(new FileWriter(ACCOUNTS_FILE , true));
        ){
            String newLine=acc.toFileString();
            fswrite.write(newLine);
            fswrite.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveAllAccount(Map<Integer,Accounts> accounts) {
        try (BufferedWriter fswrite = new BufferedWriter(new FileWriter(ACCOUNTS_FILE));
        ){
            for(Accounts acc : accounts.values()){
                String newLine=acc.toFileString();
                fswrite.write(newLine);
                fswrite.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
