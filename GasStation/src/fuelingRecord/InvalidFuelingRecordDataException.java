package fuelingRecord;

public class InvalidFuelingRecordDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2235100747230286626L;

	public InvalidFuelingRecordDataException() {
	}

	public InvalidFuelingRecordDataException(String message) {
		super(message);
	}

	public InvalidFuelingRecordDataException(Throwable cause) {
		super(cause);
	}

	public InvalidFuelingRecordDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
