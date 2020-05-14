package instagram.exception;

public class InvalidTransactionException extends Exception{
	public InvalidTransactionException() {
		this("this is InvalidTransactionException");
	}
	
	public InvalidTransactionException(String message) {
		super(message);
	}
}
