package postItems;

public class InvalidPostItemDataException extends Exception {

	public InvalidPostItemDataException() {
	}

	public InvalidPostItemDataException(String message) {
		super(message);
	}

	public InvalidPostItemDataException(Throwable cause) {
		super(cause);
	}

	public InvalidPostItemDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
