package Projects.BankManagement.Exceptions;

public class InvalidAmountException extends Exception{
    public  InvalidAmountException(){
        super("Invalid Amount is provided");
    }

    public InvalidAmountException(String message){
        super(message);
    }
}
