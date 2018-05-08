package people;

public class InvalidDistributorDataException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7545583339861185967L;

	public InvalidDistributorDataException() {
		super();
	}

	public InvalidDistributorDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidDistributorDataException(String message) {
		super(message);
	}

	public InvalidDistributorDataException(Throwable cause) {
		super(cause);
	}


}
