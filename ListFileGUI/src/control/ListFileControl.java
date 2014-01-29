/**
 * 
 */
package control;

import gui.ListFileMain;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import service.ListFileThread;

/**
 * Control thread.
 * 
 * @author KhoaHV
 * 
 */
public class ListFileControl extends Thread {

	private ListFileThread listFileThread;
	private ListFileMain mainForm;

	public ListFileControl(ListFileMain mainForm) {
		listFileThread = new ListFileThread();
		this.mainForm = mainForm;
	}

	public ListFileControl(ListFileMain mainForm,
			DefaultListModel<String> listResult, String option) {
		super(option);
		this.mainForm = mainForm;
		listFileThread = new ListFileThread(listResult, option);
	}

	public void setListResult(DefaultListModel<String> listResult) {
		if (listResult == null) {
			JOptionPane.showMessageDialog(mainForm.getFrame(),
					"Runtime error!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		listFileThread.setListResult(listResult);
	}

	public void setParameter(String path, String option,
			DefaultListModel<String> list) {
		try {
			listFileThread.setParameter(path, option, list);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(mainForm.getFrame(),
					"Runtime error!\n" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void run() {
		listFileThread.start();
		while (listFileThread.isAlive()) {
			try {
				sleep(1000);
				//mainForm.updateList();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(mainForm.getFrame(),
						"Runtime error!\n" + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		switch (listFileThread.getRc()) {
		case ListFileThread.FILE_NOT_FOUND_ERROR:
			JOptionPane.showMessageDialog(mainForm.getFrame(),
					"Path not found!\n", "Error",
					JOptionPane.ERROR_MESSAGE);
			break;
		case ListFileThread.FULL_STACK_ERROR:
			JOptionPane.showMessageDialog(mainForm.getFrame(),
					"Runtime Error: Stack is full!\n", "Error",
					JOptionPane.ERROR_MESSAGE);
			break;
		case ListFileThread.EMPTY_STACK_ERROR:
			JOptionPane.showMessageDialog(mainForm.getFrame(),
					"Runtime error: Stack is empty!\n", "Error",
					JOptionPane.ERROR_MESSAGE);
			break;
		case ListFileThread.IO_ERROR:
			JOptionPane.showMessageDialog(mainForm.getFrame(),
					"Runtime error: Input\\Output error!\n", "Error",
					JOptionPane.ERROR_MESSAGE);
			break;
		case ListFileThread.EMPTY_QUEUE_ERROR:
			JOptionPane.showMessageDialog(mainForm.getFrame(),
					"Runtime error: Queue is empty!\n", "Error",
					JOptionPane.ERROR_MESSAGE);
			break;
		}
		mainForm.clickOnBtnStop();
	}
	
	@SuppressWarnings("deprecation")
	public void pause() {
		listFileThread.doSuspend();
		super.suspend();
	}
	
	@SuppressWarnings("deprecation")
	public void resumeListFile() {
		listFileThread.doContinue();
		super.resume();
	}

}
