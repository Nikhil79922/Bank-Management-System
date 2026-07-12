package Projects.BankManagement.Models;

public class Customer {

    private int customerId;
    private String name;
    private String email;
    private String phone;

    public Customer(int customerId , String name , String email , String phone){
        this.customerId = customerId;
        this.name = name;
        this. email = email;
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return customerId + "," +
                name + "," +
                email + "," +
                phone;
    }
}
