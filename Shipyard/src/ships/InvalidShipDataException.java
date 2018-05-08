package ships;

public class InvalidShipDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1256011505899684014L;

	public InvalidShipDataException() {
		super();
	}

	public InvalidShipDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidShipDataException(String message) {
		super(message);
	}

	public InvalidShipDataException(Throwable cause) {
		super(cause);
	}

	

}
