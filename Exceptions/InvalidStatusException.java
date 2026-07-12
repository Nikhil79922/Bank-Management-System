package Projects.BankManagement.Exceptions;

public class InvalidStatusException extends Exception{
    public InvalidStatusException(){
        super("Inactive status");
    }

    public InvalidStatusException(String message){
        super(message);
    }
}
