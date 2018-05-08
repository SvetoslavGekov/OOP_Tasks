package postItems.packages;

import postItems.InvalidPostItemDataException;

public class InvalidPackageDataException extends InvalidPostItemDataException {

	public InvalidPackageDataException() {
	}

	public InvalidPackageDataException(String message) {
		super(message);
	}

	public InvalidPackageDataException(Throwable cause) {
		super(cause);
	}

	public InvalidPackageDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
