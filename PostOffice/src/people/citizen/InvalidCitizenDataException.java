package people.citizen;

import people.InvalidPersonDataException;

public class InvalidCitizenDataException extends InvalidPersonDataException {

	public InvalidCitizenDataException() {
	}

	public InvalidCitizenDataException(String message) {
		super(message);
	}

	public InvalidCitizenDataException(Throwable cause) {
		super(cause);
	}

	public InvalidCitizenDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
