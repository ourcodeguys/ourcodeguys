package adt;

public class EmptyQueueException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6096668208428404322L;

	public EmptyQueueException() {
		System.err.println("Empty Queue Exception !");
	}

	public EmptyQueueException(String arg0) {
		super(arg0);
		System.err.println("Empty Queue Exception !");
	}

	public EmptyQueueException(Throwable arg0) {
		super(arg0);
		System.err.println("Empty Queue Exception !");
	}

	public EmptyQueueException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		System.err.println("Empty Queue Exception !");
	}

	public EmptyQueueException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		System.err.println("Empty Queue Exception !");
	}

}
