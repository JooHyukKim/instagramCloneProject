package instagram.exception;

public class DuplicateUserIdException extends Exception{
	public DuplicateUserIdException() {
		this("this is DuplicateUserIdException");
	}
	
	public DuplicateUserIdException(String message) {
		super(message);
	}
}
