package Projects.BankManagement.Services;

import Projects.BankManagement.Exceptions.NotfoundException;
import Projects.BankManagement.Models.Customer;

import java.util.Map;

import static Projects.BankManagement.Storeage.CustomerFileManagement.*;
import static Projects.BankManagement.Utils.IdGenerator.nextCustomerId;

public class CustomerService {

    // Helper method
    private static Customer findCustomer(Map<Integer, Customer> customers, int userId)
            throws NotfoundException {

        Customer customer = customers.get(userId);

        if (customer == null) {
            throw new NotfoundException(
                    "Customer not found with Customer ID: " + userId);
        }

        return customer;
    }

    public void createCustomer(String name, String email, String phone) {

        System.out.println("========== Customer Creation ==========");
        System.out.println("Initializing customer creation process...");

        int userId = nextCustomerId();

        Customer newCustomer = new Customer(userId, name, email, phone);

        saveCustomer(newCustomer);

        System.out.println("Customer created successfully.");
        System.out.println("Customer ID : " + userId);
        System.out.println("=======================================\n");
    }

    public Customer getCustomer(int userId) throws NotfoundException {

        System.out.println("========== Customer Search ==========");
        System.out.println("Searching customer...");

        Map<Integer, Customer> customers = loadCustomers();

        Customer customer = findCustomer(customers, userId);

        System.out.println("Customer found successfully.");
        System.out.println("Customer ID : " + customer.getCustomerId());
        System.out.println("=====================================\n");

        return customer;
    }

    public void deleteCustomer(int userId) throws NotfoundException {

        System.out.println("========== Customer Deletion ==========");
        System.out.println("Deleting customer...");

        Map<Integer, Customer> customers = loadCustomers();

        Customer customer = customers.remove(userId);

        if (customer == null) {
            throw new NotfoundException(
                    "Customer not found with Customer ID: " + userId);
        }

        saveAllCustomer(customers);

        System.out.println("Customer deleted successfully.");
        System.out.println("Customer ID : " + userId);
        System.out.println("=======================================\n");
    }

    public void updateEmail(int userId, String email)
            throws NotfoundException {

        System.out.println("========== Update Customer Email ==========");
        System.out.println("Updating customer email...");

        Map<Integer, Customer> customers = loadCustomers();

        Customer customer = findCustomer(customers, userId);

        customer.setEmail(email);

        saveAllCustomer(customers);

        System.out.println("Customer email updated successfully.");
        System.out.println("Customer ID : " + userId);
        System.out.println("===========================================\n");
    }

    public void updateName(int userId, String name)
            throws NotfoundException {

        System.out.println("========== Update Customer Name ==========");
        System.out.println("Updating customer name...");

        Map<Integer, Customer> customers = loadCustomers();

        Customer customer = findCustomer(customers, userId);

        customer.setName(name);

        saveAllCustomer(customers);

        System.out.println("Customer name updated successfully.");
        System.out.println("Customer ID : " + userId);
        System.out.println("==========================================\n");
    }

    public void updatePhone(int userId, String phone)
            throws NotfoundException {

        System.out.println("========== Update Customer Phone ==========");
        System.out.println("Updating customer phone number...");

        Map<Integer, Customer> customers = loadCustomers();

        Customer customer = findCustomer(customers, userId);

        customer.setPhone(phone);

        saveAllCustomer(customers);

        System.out.println("Customer phone number updated successfully.");
        System.out.println("Customer ID : " + userId);
        System.out.println("===========================================\n");
    }
}