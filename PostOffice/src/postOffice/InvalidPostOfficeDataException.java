package postOffice;

public class InvalidPostOfficeDataException extends Exception {

	public InvalidPostOfficeDataException() {
	}

	public InvalidPostOfficeDataException(String message) {
		super(message);
	}

	public InvalidPostOfficeDataException(Throwable cause) {
		super(cause);
	}

	public InvalidPostOfficeDataException(String message, Throwable cause) {
		super(message, cause);
	}


}
