package people.mailman;

import people.InvalidPersonDataException;

public class InvalidMailmanDataException extends InvalidPersonDataException {

	public InvalidMailmanDataException() {
	}

	public InvalidMailmanDataException(String message) {
		super(message);
	}

	public InvalidMailmanDataException(Throwable cause) {
		super(cause);
	}

	public InvalidMailmanDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
