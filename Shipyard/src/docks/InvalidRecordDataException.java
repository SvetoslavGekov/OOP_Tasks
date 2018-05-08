package docks;

public class InvalidRecordDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -666507480411674295L;

	public InvalidRecordDataException() {
	}

	public InvalidRecordDataException(String message) {
		super(message);
	}

	public InvalidRecordDataException(Throwable cause) {
		super(cause);
	}

	public InvalidRecordDataException(String message, Throwable cause) {
		super(message, cause);
	}


}
