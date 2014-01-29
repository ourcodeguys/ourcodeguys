package service;

public class FileNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1613751083411980206L;

	public FileNotFoundException() {
		System.err.println("File Not Found Exception !");
	}

	public FileNotFoundException(String arg0) {
		super(arg0);
		System.err.println("File Not Found Exception !");
	}

	public FileNotFoundException(Throwable arg0) {
		super(arg0);
		System.err.println("File Not Found Exception !");
	}

	public FileNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		System.err.println("File Not Found Exception !");
	}

	public FileNotFoundException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		System.err.println("File Not Found Exception !");
	}

}
