/**
 * 
 */
package service;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;

import adt.EmptyQueueException;
import adt.EmptyStackException;
import adt.FullStackException;
import adt.KQueue;
import adt.KStack;

/**
 * <b>{@link ListFileThread}</b> provide attributes, constants, methods to work
 * as a thread to list files in a folder.
 * 
 * @author KhoaHV
 * @version 1.0
 * @see Thread
 * @see KStack
 * @see KQueue
 * 
 */
public class ListFileThread extends Thread {

	public static final int INTERUPTED = -1;
	public static final int RUNNING = 0;
	public static final int SUCCESS = 0;
	public static final int FILE_NOT_FOUND_ERROR = 1;
	public static final int FULL_STACK_ERROR = 2;
	public static final int EMPTY_STACK_ERROR = 3;
	public static final int IO_ERROR = 4;
	public static final int EMPTY_QUEUE_ERROR = 5;

	private DefaultListModel<String> listResult; // shared data
	private File entry;
	private KStack<File> stack;
	private KQueue<File> queue;
	// option:
	private boolean useADTQueue;
	private boolean includeHidden;
	private boolean includeFolder;
	/**
	 * Return code after thread is done.
	 */
	private int rc;

	public ListFileThread() {
		stack = new KStack<File>();
		queue = new KQueue<File>();
		setOption(null);
		rc = INTERUPTED;
	}

	/**
	 * @param name
	 *            type of ADT want to use and extra option.
	 */
	public ListFileThread(DefaultListModel<String> listResult, String name) {
		super(name);
		stack = new KStack<File>();
		queue = new KQueue<File>();
		this.listResult = listResult;
		setOption(name);
		rc = INTERUPTED;
	}

	@SuppressWarnings("deprecation")
	public void doContinue() {
		rc = RUNNING;
		super.resume();
	}

	@SuppressWarnings("deprecation")
	public void doStop() {
		rc = INTERUPTED;
		super.stop();
	}

	@SuppressWarnings("deprecation")
	public void doSuspend() {
		rc = INTERUPTED;
		super.suspend();
	}

	private void enQueueFileArray(File f, String[] sarr) {
		for (int i = 0; i < sarr.length; i++) {
			queue.enQueue(new File(f.getAbsolutePath(), sarr[i]));
		}
	}

	@SuppressWarnings("deprecation")
	public void finish() {
		rc = INTERUPTED;
		super.stop();
	}

	public DefaultListModel<String> getListResult() {
		return listResult;
	}

	public int getRc() {
		return rc;
	}

	public void listFile() throws FileNotFoundException, FullStackException,
			EmptyStackException, IOException, EmptyQueueException {
		if (useADTQueue)
			listUseQueue();
		else
			// use ADT Stack as default
			listUseStack();
	}

	protected void listUseQueue() throws FileNotFoundException, IOException,
			EmptyQueueException {
		if (!entry.exists()) {
			throw new FileNotFoundException(
					"Cannot list the path that isn't exist !");
		}
		File f = new File(entry.getAbsolutePath());
		queue.enQueue(f);
		boolean firstTime = true;
		while (!queue.isEmpty()) {
			f = queue.deQueue();
			if (f.isHidden() && !includeHidden && !firstTime)
				continue;
			if (f.isDirectory() && !includeFolder && !firstTime)
				continue;
			synchronized(listResult) {
				listResult.addElement(f.getAbsolutePath());	
			}
			System.out.println(f.getAbsolutePath()); // preoder
			if (f.isDirectory()) {
				String sarr[] = f.list();
				if (sarr != null)
					enQueueFileArray(f, sarr);
			}
			if (firstTime)
				firstTime = false;
		}
	}

	protected void listUseStack() throws FileNotFoundException,
			FullStackException, EmptyStackException, IOException {
		if (!entry.exists()) {
			throw new FileNotFoundException(
					"Cannot list the path that isn't exist !");
		}
		File f = new File(entry.getAbsolutePath());
		if (!stack.isFull())
			stack.push(f);
		else
			throw new FullStackException("Stack overflow !");
		boolean firstTime = true;
		while (!stack.isEmpty()) {
			f = stack.pop();
			if (f.isHidden() && !includeHidden && !firstTime)
				continue;
			if (f.isDirectory() && !includeFolder && !firstTime)
				continue;
			synchronized(listResult) {
				listResult.addElement(f.getAbsolutePath());	
			}
			System.out.println(f.getAbsolutePath());
			if (f.isDirectory()) {
				String sarr[] = f.list();
				if (sarr != null)
					pushFileArray(f, sarr);
			}
			if (firstTime)
				firstTime = false;
		}
	}

	private void pushFileArray(File f, String[] sarr) throws FullStackException {
		for (int i = 0; i < sarr.length; i++) {
			if (!stack.isFull())
				stack.push(new File(f.getAbsolutePath(), sarr[i]));
			else
				throw new FullStackException("Stack overflow !");
		}
	}

	public void run() {
		try {
			listFile();
		} catch (FileNotFoundException e) {
			rc = FILE_NOT_FOUND_ERROR;
			e.printStackTrace();
		} catch (FullStackException e) {
			rc = FULL_STACK_ERROR;
			e.printStackTrace();
		} catch (EmptyStackException e) {
			rc = EMPTY_STACK_ERROR;
			e.printStackTrace();
		} catch (IOException e) {
			rc = IO_ERROR;
			e.printStackTrace();
		} catch (EmptyQueueException e) {
			rc = EMPTY_QUEUE_ERROR;
			e.printStackTrace();
		}
	}

	public void setListResult(DefaultListModel<String> listResult)
			throws NullPointerException {
		if (listResult == null)
			throw new NullPointerException("Cannot pass null list of KNode !");
		this.listResult = listResult;
	}

	public void setOption(String option) {
		useADTQueue = false; // using ADT Stack is default.
		includeFolder = false; // not including Folder is default
		includeHidden = false; // not including Hidden File is default.
		if (option == null) {
			return;
		}
		if (option.lastIndexOf("queue") >= 0)
			useADTQueue = true;
		if (option.lastIndexOf("all") >= 0) {
			includeHidden = true;
			includeFolder = true;
		} else {
			if (option.lastIndexOf("hidden") >= 0) {
				includeHidden = true;
			}
			if (option.lastIndexOf("folder") >= 0) {
				includeFolder = true;
			}
		}
	}

	public void setParameter(String path, String option,
			DefaultListModel<String> list) throws NullPointerException {
		if (list == null)
			throw new NullPointerException("List cannot be null !");
		if (path == null) {
			File f = new File("");
			entry = new File(f.getAbsolutePath());
			f = null;
		} else
			entry = new File(path);
		System.out.println(entry.getAbsolutePath());
		setOption(option);
		listResult = list;
	}

	public void start() {
		rc = RUNNING;
		super.start();
	}

	public static void main(String args[]) {
		ListFileThread listFileThread = new ListFileThread();
		DefaultListModel<String> list = new DefaultListModel<String>();
		listFileThread.setParameter(
				"C:\\Users\\KhoaHV\\Documents",
				"folder", list);
		try {
			listFileThread.listFile();
		} catch (FileNotFoundException | FullStackException
				| EmptyStackException | IOException | EmptyQueueException e) {
			e.printStackTrace();
		}
	}
}
