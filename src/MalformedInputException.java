/*
 * This exception is thrown when an input is not in the form of an equation
 */
public class MalformedInputException extends Exception {

	public MalformedInputException() {
	}

	public MalformedInputException(String message) {
		super(message);
	}

	public MalformedInputException(Throwable cause) {
		super(cause);
	}

	public MalformedInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public MalformedInputException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
