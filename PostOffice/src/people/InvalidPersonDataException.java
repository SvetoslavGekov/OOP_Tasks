package people;

public class InvalidPersonDataException extends Exception {

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
