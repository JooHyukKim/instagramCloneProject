package broker.three.exception;
public class RecordNotFoundException extends Exception {
	public RecordNotFoundException() {
		this("this is RecordNotFoundException");
	}
	
	public RecordNotFoundException(String message) {
		super(message);
	}
}
