package Projects.BankManagement.Services;

import Projects.BankManagement.Exceptions.NotfoundException;
import Projects.BankManagement.Models.Customer;
import java.util.Map;

import static Projects.BankManagement.Storeage.FileManagment.*;
import static Projects.BankManagement.Utils.IdGenerator.nextCustomerId;

public class CustomerService {
    //helper methods..
    private static Customer findCustomer(Map<Integer, Customer> customers ,int userId) throws NotfoundException {
        Customer customer = customers.get(userId);

        if (customer == null) {
            throw new NotfoundException("Customer not found with customer ID: " + userId);
        }
        return customer;
    }

    public void createCustomer(String name, String email, String phone) {
        try {
            int userId = nextCustomerId();
            Customer newcus = new Customer(userId, name, email, phone);
            saveCustomer(newcus);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Customer getCustomer(int userId) throws NotfoundException {
        Map<Integer, Customer> customers = loadCustomers();
return findCustomer(customers , userId);
    }

    public void deleteCustomer(int userId) throws NotfoundException {
        Map<Integer, Customer> customers = loadCustomers();
        Customer customer = customers.remove(userId);

        if (customer == null) {
            throw new NotfoundException("Customer not found with customer ID: " + userId);
        }

        saveAllCustomer(customers);
    }

    public void updateEmail(int userId, String email) throws NotfoundException {
        Map<Integer, Customer> customers = loadCustomers();
        Customer customer = findCustomer(customers , userId);

        customer.setEmail(email); // as Map stores the referecne of the same object , it this will modify the same.
        saveAllCustomer(customers);
    }

    public void updateName(int userId, String name) throws NotfoundException {
        Map<Integer, Customer> customers = loadCustomers();
        Customer customer = findCustomer(customers , userId);

        customer.setName(name); // as Map stores the referecne of the same object , it this will modify the same.
        saveAllCustomer(customers);
    }

    public void updatePhone(int userId, String phone) throws NotfoundException {
        Map<Integer, Customer> customers = loadCustomers();
        Customer customer = findCustomer(customers , userId);

        customer.setPhone(phone); // as Map stores the referecne of the same object , it this will modify the same.
        saveAllCustomer(customers);
    }

}
