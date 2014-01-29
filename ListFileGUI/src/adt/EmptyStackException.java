/**
 * 
 */
package adt;

/**
 * @author KhoaHV
 *
 */
public class EmptyStackException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7438279836686145313L;

	/**
	 * 
	 */
	public EmptyStackException() {
		System.err.println("Empty Stack Exception !");
	}

	/**
	 * @param message
	 */
	public EmptyStackException(String message) {
		super(message);
		System.err.println("Empty Stack Exception !");
	}

	/**
	 * @param cause
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
		System.err.println("Empty Stack Exception !");
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
		System.err.println("Empty Stack Exception !");
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public EmptyStackException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		System.err.println("Empty Stack Exception !");
	}

}
