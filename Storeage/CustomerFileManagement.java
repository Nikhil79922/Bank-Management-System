package Projects.BankManagement.Storeage;
import Projects.BankManagement.Models.Customer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static Projects.BankManagement.Utils.Constants.CUSTOMER_FIELDS;
import static Projects.BankManagement.Utils.Constants.CUSTOMER_FILE;

public class CustomerFileManagement {

    public static Map<Integer,Customer> loadCustomers() {
        try ( BufferedReader fsread = new BufferedReader(new FileReader(CUSTOMER_FILE));){

            Map<Integer ,Customer> customers = new HashMap<>();
            String line;

            while ((line = fsread.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data.length != CUSTOMER_FIELDS) {
                    continue;
                }
                int customerId = Integer.parseInt(data[0]);
                Customer cus = new Customer(
                        customerId,
                        data[1],
                        data[2],
                        data[3]
                );
                customers.put( customerId ,  cus);
            }

            return customers;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveCustomer(Customer cus) {
        try ( BufferedWriter fswrite = new BufferedWriter(new FileWriter(CUSTOMER_FILE , true));
        ){
            String newLine=cus.toString();
            fswrite.write(newLine);
            fswrite.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveAllCustomer(Map<Integer,Customer> customers) {
        try ( BufferedWriter fswrite = new BufferedWriter(new FileWriter(CUSTOMER_FILE));
        ){
            for(Customer cus : customers.values() ){
                String newLine=cus.toString();
                fswrite.write(newLine);
                fswrite.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
