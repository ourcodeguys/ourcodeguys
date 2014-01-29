/**
 * 
 */
package service;

/**
 * @author KhoaHV
 *
 */
public class ParametersNotSetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1455695224915499170L;
	/**
	 * 
	 */
	public ParametersNotSetException() {
		System.err.println("Parameters Not Set Exceptions !");
	}

	/**
	 * @param arg0
	 */
	public ParametersNotSetException(String arg0) {
		super(arg0);
		System.err.println("Parameters Not Set Exceptions !");
	}

	/**
	 * @param arg0
	 */
	public ParametersNotSetException(Throwable arg0) {
		super(arg0);
		System.err.println("Parameters Not Set Exceptions !");
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ParametersNotSetException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		System.err.println("Parameters Not Set Exceptions !");
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public ParametersNotSetException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		System.err.println("Parameters Not Set Exceptions !");
	}

}
