package people;

public class InvalidPersonDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3606049199363635160L;

	public InvalidPersonDataException() {
	}

	public InvalidPersonDataException(String message) {
		super(message);
	}

	public InvalidPersonDataException(Throwable cause) {
		super(cause);
	}

	public InvalidPersonDataException(String message, Throwable cause) {
		super(message, cause);
	}


}
