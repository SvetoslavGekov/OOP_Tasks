package people;

public class InvalidPersonDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2912054463968695865L;

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
