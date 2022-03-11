package bus;

public class InsufficientAmountException extends Exception {

	private static final long serialVersionUID = 7002855752942539527L;

	InsufficientAmountException(){
		super();
	}
	
	InsufficientAmountException(String message){
		super(message);
	}
	
}
