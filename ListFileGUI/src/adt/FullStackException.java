/**
 * 
 */
package adt;

/**
 * @author KhoaHV
 *
 */
public class FullStackException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8657284532481769291L;

	/**
	 * 
	 */
	public FullStackException() {
		System.err.println("Full Stack Exception !");
	}

	/**
	 * @param arg0
	 */
	public FullStackException(String arg0) {
		super(arg0);
		System.err.println("Full Stack Exception !");
	}

	/**
	 * @param arg0
	 */
	public FullStackException(Throwable arg0) {
		super(arg0);
		System.err.println("Full Stack Exception !");
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public FullStackException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		System.err.println("Full Stack Exception !");
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public FullStackException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		System.err.println("Full Stack Exception !");
	}

}
