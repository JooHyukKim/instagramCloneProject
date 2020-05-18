package instagram.exception;

public class DuplicateRercordException extends Exception{
	public DuplicateRercordException() {
		this("this is DuplicateUserIdException");
	}
	
	public DuplicateRercordException(String message) {
		super(message);
	}
}
