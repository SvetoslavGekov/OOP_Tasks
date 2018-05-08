package docks;

public class InvalidDockDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6737874648148361386L;

	public InvalidDockDataException() {
	}

	public InvalidDockDataException(String message) {
		super(message);
	}

	public InvalidDockDataException(Throwable cause) {
		super(cause);
	}

	public InvalidDockDataException(String message, Throwable cause) {
		super(message, cause);
	}


}
