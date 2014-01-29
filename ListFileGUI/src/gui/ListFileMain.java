package gui;

import java.awt.EventQueue;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;

import control.ListFileControl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListFileMain {

	// Các thuộc tính cho GUI
	private JFrame frame;
	private JTextField txtPath;
	//TODO: set mutex to lock listModelResult
	// share data
	private DefaultListModel<String> listModelResult;
	private JList<String> listResult;
	private JButton btnScanPauseResume;
	private JButton btnStop;
	private JButton btnChoosePath;
	private JButton btnAbout;
	private JCheckBox chckbxHidden;
	private JCheckBox chckbxSystem;
	private JCheckBox chckbxFolder;
	private JCheckBox chckbxNormal;
	private JCheckBox chckbxAllFile;
	private JCheckBox chckbxUseAdtStack;
	private JCheckBox chckbxUseAdtQueue;
	private JButton btnUpdateList;
	private ListFileAbout about;

	// TODO
	// Các thuộc tính cho xử lí dữ liệu
	private String path;
	private ListFileControl control;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListFileMain window = new ListFileMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Runtime error!\n" + e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ListFileMain() {
		// Hiện thư mục hiện thời ở txtPath
		File f = new File("");
		path = f.getAbsolutePath();
		customInit();
		initialize();
		f = null;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panelControl = new JPanel();
		panelControl.setBounds(10, 11, 604, 105);
		frame.getContentPane().add(panelControl);
		panelControl.setLayout(null);

		JLabel lblPath = new JLabel("Path");
		lblPath.setToolTipText("Path you want to scan");
		lblPath.setBounds(10, 4, 62, 14);
		panelControl.add(lblPath);

		txtPath = new JTextField();
		txtPath.setBounds(82, 1, 189, 20);
		txtPath.setText(path);
		panelControl.add(txtPath);
		txtPath.setColumns(10);

		btnScanPauseResume = new JButton("Scan");
		btnScanPauseResume.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickOnBtnScanPauseResume();
			}
		});
		btnScanPauseResume.setToolTipText("Click to start scan");
		btnScanPauseResume.setBounds(426, 0, 90, 23);
		panelControl.add(btnScanPauseResume);

		btnChoosePath = new JButton("...");
		btnChoosePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnChoosePath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				clickOnBtnChoosePath();
			}
		});
		btnChoosePath.setBounds(281, 0, 25, 23);
		panelControl.add(btnChoosePath);

		JLabel lblOption = new JLabel("Option");
		lblOption.setBounds(10, 29, 46, 14);
		panelControl.add(lblOption);

		chckbxHidden = new JCheckBox("Include hidden file");
		chckbxHidden.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickOnChckbxHidden();
			}
		});
		chckbxHidden.setToolTipText("Check to include hidden file in result");
		chckbxHidden.setBounds(82, 25, 140, 23);
		panelControl.add(chckbxHidden);

		chckbxSystem = new JCheckBox("Include system file");
		chckbxSystem.setEnabled(false);
		chckbxSystem.setSelected(true);
		chckbxSystem.setToolTipText("Check to include system file in result");
		chckbxSystem.setBounds(258, 28, 140, 23);
		panelControl.add(chckbxSystem);

		chckbxFolder = new JCheckBox("Include folder");
		chckbxFolder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickOnChckbxFolder();
			}
		});
		chckbxFolder.setToolTipText("Check to include folder in result");
		chckbxFolder.setBounds(464, 28, 130, 23);
		panelControl.add(chckbxFolder);

		chckbxUseAdtStack = new JCheckBox("Use ADT Stack");
		chckbxUseAdtStack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickOnChckbxUseAdtStack();
			}
		});
		chckbxUseAdtStack.setSelected(true);
		chckbxUseAdtStack
				.setToolTipText("Scan using ADT Stack. Note that Stack is limited");
		chckbxUseAdtStack.setBounds(82, 80, 130, 23);
		panelControl.add(chckbxUseAdtStack);

		chckbxUseAdtQueue = new JCheckBox("Use ADT Queue");
		chckbxUseAdtQueue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickOnChckbxUseAdtQueue();
			}
		});
		chckbxUseAdtQueue.setToolTipText("Scan using ADT Queue");
		chckbxUseAdtQueue.setBounds(258, 80, 130, 23);
		panelControl.add(chckbxUseAdtQueue);

		chckbxAllFile = new JCheckBox("Include all file");
		chckbxAllFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickOnChckbxAllFile();
			}
		});
		chckbxAllFile.setToolTipText("Check to include all file in result");
		chckbxAllFile.setBounds(82, 54, 130, 23);
		panelControl.add(chckbxAllFile);

		chckbxNormal = new JCheckBox("Just include normal file");
		chckbxNormal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickOnChckbxNormal();
			}
		});
		chckbxNormal.setSelected(true);
		chckbxNormal.setToolTipText("Just include normal file in the result");
		chckbxNormal.setBounds(258, 54, 336, 23);
		panelControl.add(chckbxNormal);
		
		btnStop = new JButton("Stop");
		btnStop.setEnabled(false);
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickOnBtnStop();
			}
		});
		btnStop.setToolTipText("Click to stop scan");
		btnStop.setBounds(526, 0, 77, 23);
		panelControl.add(btnStop);
		
		btnUpdateList = new JButton("Update list");
		btnUpdateList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				clickOnBtnUpdateList();
			}
		});
		btnUpdateList.setToolTipText("Click to update list");
		btnUpdateList.setEnabled(false);
		btnUpdateList.setBounds(316, 0, 100, 23);
		panelControl.add(btnUpdateList);

		JPanel panelResult = new JPanel();
		panelResult.setBounds(10, 116, 604, 256);
		frame.getContentPane().add(panelResult);
		panelResult.setLayout(new CardLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panelResult.add(scrollPane, "name_177045659893042");

		// TODO: Có lỗi khi khởi tạo bằng cách đã được cmt
		// listResult = new JList<String>(listModelResult);
		listResult = new JList<String>();
		scrollPane.setViewportView(listResult);
		// listResult.setModel(listModelResult);

		JPanel panelIntro = new JPanel();
		panelIntro.setBounds(10, 405, 604, 25);
		frame.getContentPane().add(panelIntro);
		panelIntro.setLayout(null);

		JTextPane textPane = new JTextPane();
		textPane.setText("ListFileGUI is a free and open source Java Example. Click About to see detail.");
		textPane.setEditable(false);
		textPane.setBounds(0, 0, 490, 20);
		panelIntro.add(textPane);

		btnAbout = new JButton("About");
		btnAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickOnBtnAbout();
			}
		});
		btnAbout.setBounds(500, 0, 102, 23);
		panelIntro.add(btnAbout);
	}

	protected void clickOnBtnChoosePath() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().getAbsolutePath();
			txtPath.setText(path);
		}
	}

	public void customInit() {
		listModelResult = new DefaultListModel<String>();
		about = new ListFileAbout();
		control = new ListFileControl(this);
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Xử lí sự kiện khi click chuột vào {@link ListFileMain#chckbxAllFile}: <li>
	 * Nếu click để chọn thì cũng sẽ chọn {@link ListFileMain#chckbxHidden} và
	 * {@link ListFileMain#chckbxFolder} và hủy chọn
	 * {@link ListFileMain#chckbxNormal}. <li>Nếu click để hủy chọn thì cũng sẽ
	 * chọn {@link ListFileMain#chckbxNormal} và hủy chọn
	 * {@link ListFileMain#chckbxHidden} và {@link ListFileMain#chckbxFolder}.
	 */
	private void clickOnChckbxAllFile() {
		if (chckbxAllFile.isSelected()) {
			chckbxHidden.setSelected(true);
			chckbxFolder.setSelected(true);
			chckbxNormal.setSelected(false);
		} else {
			chckbxHidden.setSelected(false);
			chckbxFolder.setSelected(false);
			chckbxNormal.setSelected(true);
		}
	}

	/**
	 * Xử lí sự kiện khi click chuột vào {@link ListFileMain#chckbxNormal}: <li>
	 * Nếu click để chọn thì cũng sẽ bỏ chọn {@link ListFileMain#chckbxHidden}
	 * và {@link ListFileMain#chckbxFolder} và
	 * {@link ListFileMain#chckbxAllFile}. <li>Nếu click để bỏ chọn thì cũng sẽ
	 * chọn {@link ListFileMain#chckbxHidden} và
	 * {@link ListFileMain#chckbxFolder} và {@link ListFileMain#chckbxAllFile}.
	 */
	private void clickOnChckbxNormal() {
		if (chckbxNormal.isSelected()) {
			chckbxHidden.setSelected(false);
			chckbxFolder.setSelected(false);
			chckbxAllFile.setSelected(false);
		} else {
			chckbxHidden.setSelected(true);
			chckbxFolder.setSelected(true);
			chckbxAllFile.setSelected(true);
		}
	}

	/**
	 * Xử lí sự kiện khi click vào {@link ListFileMain#chckbxHidden}: <li>Nếu
	 * click để chọn thì bỏ chọn {@link ListFileMain#chckbxNormal}. Nếu mà
	 * {@link ListFileMain#chckbxFolder} cũng đang được chọn thì chọn luôn
	 * {@link ListFileMain#chckbxAllFile} <li>Nếu click để bỏ chọn thì bỏ chọn
	 * {@link ListFileMain#chckbxAllFile}
	 */
	private void clickOnChckbxHidden() {
		if (chckbxHidden.isSelected()) {
			chckbxNormal.setSelected(false);
			if (chckbxFolder.isSelected())
				chckbxAllFile.setSelected(true);
		} else {
			chckbxAllFile.setSelected(false);
			if (!chckbxFolder.isSelected())
				chckbxNormal.setSelected(true);
		}
	}
	

	/**
	 * Xử lí sự kiện khi click vào {@link ListFileMain#chckbxFolder}: <li>Nếu
	 * click để chọn thì bỏ chọn {@link ListFileMain#chckbxNormal}. Nếu mà
	 * {@link ListFileMain#chckbxHidden} cũng đang được chọn thì chọn luôn
	 * {@link ListFileMain#chckbxAllFile} <li>Nếu click để bỏ chọn thì bỏ chọn
	 * {@link ListFileMain#chckbxAllFile}
	 */
	private void clickOnChckbxFolder() {
		if (chckbxFolder.isSelected()) {
			chckbxNormal.setSelected(false);
			if (chckbxHidden.isSelected())
				chckbxAllFile.setSelected(true);
		} else {
			chckbxAllFile.setSelected(false);
			if (!chckbxHidden.isSelected())
				chckbxNormal.setSelected(true);
		}
	}
	
	/**
	 * Xử lí sự kiện click chuột vào {@link ListFileMain#chckbxUseAdtStack}:
	 * <li>Nếu click để chọn thì hủy chọn {@link ListFileMain#chckbxUseAdtQueue}.
	 * <li>Nếu click để hủy chọn thì chọn {@link ListFileMain#chckbxUseAdtQueue}.
	 */
	private void clickOnChckbxUseAdtStack() {
		if (chckbxUseAdtStack.isSelected()) {
			chckbxUseAdtQueue.setSelected(false);
		} else {
			chckbxUseAdtQueue.setSelected(true);
		}
		
	}
	
	/**
	 * Xử lí sự kiện click chuột vào {@link ListFileMain#chckbxUseAdtQueue}:
	 * <li>Nếu click để chọn thì hủy chọn {@link ListFileMain#chckbxUseAdtStack}.
	 * <li>Nếu click để hủy chọn thì chọn {@link ListFileMain#chckbxUseAdtStack}.
	 */
	private void clickOnChckbxUseAdtQueue() {
		if (chckbxUseAdtQueue.isSelected()) {
			chckbxUseAdtStack.setSelected(false);
		} else {
			chckbxUseAdtStack.setSelected(true);
		}
	}

	/**
	 * Xử lí sự kiện click chuột vào nút {@link ListFileMain#btnScanPauseResume}.
	 * <li>Nếu lúc click, nút có nhãn "Scan" thì tiến hành quét.
	 * <li>Nếu lúc click, nút có nhãn "Pause" thì tạm dừng quét.
	 * <li>Nếu lúc click, nút có nhãn "Resume" thì tiếp tục quét.
	 */
	private void clickOnBtnScanPauseResume() {
		btnStop.setEnabled(true);
		btnUpdateList.setEnabled(true);
		btnChoosePath.setEnabled(false);
		if (btnScanPauseResume.getText().equalsIgnoreCase("Scan")) {
			btnScanPauseResume.setText("Pause");
			resetList();
			String option = getParameters();
			setCheckBoxEnable(false);
			//JOptionPane.showMessageDialog(frame, path + "\n" + option);
			control = new ListFileControl(this, listModelResult, option);
			control.setParameter(path, option, listModelResult);
			control.start();
			return;
		}
		if (btnScanPauseResume.getText().equalsIgnoreCase("Pause")) {
			control.pause();
			updateList();
			btnScanPauseResume.setText("Resume");
			return;
		}
		if (btnScanPauseResume.getText().equalsIgnoreCase("Resume")) {
			control.resumeListFile();
			btnScanPauseResume.setText("Pause");
			return;
		}
	}
	
	/**
	 * Kiểm tra các CheckBox và lấy các tham số.
	 * @return tham số
	 */
	private String getParameters() {
		String option = "";
		if (chckbxUseAdtStack.isSelected()) {
			option += "stack";
		} else {
			option += "queue";
		}
		if (chckbxAllFile.isSelected()) {
			option += ",all";
		} else {
			if (chckbxFolder.isSelected()) option += ",folder";
			if (chckbxHidden.isSelected()) option += ",hidden";
		}
		if (chckbxNormal.isSelected()) {
			option += ",normal";
		}
		return option;
	}
	
	private void setCheckBoxEnable(boolean b) {
		chckbxAllFile.setEnabled(b);
		chckbxFolder.setEnabled(b);
		chckbxHidden.setEnabled(b);
		chckbxNormal.setEnabled(b);
		chckbxUseAdtQueue.setEnabled(b);
		chckbxUseAdtStack.setEnabled(b);
	}
	
	/**
	 * Xử lí sự kiện khi click chuột vào nút {@link ListFileMain#btnStop}.
	 * <li>Bước 1: Dừng luồng hiện tại.
	 * <li>Bước 2: Giải phóng tài nguyên.
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void clickOnBtnStop() {
		btnStop.setEnabled(false);
		btnUpdateList.setEnabled(false);
		btnChoosePath.setEnabled(true);
		setCheckBoxEnable(true);
		btnScanPauseResume.setText("Scan");
		control.stop();
		updateList();
	}
	
	public synchronized void updateList() {
		listResult.setModel(listModelResult);
	}
	
	public void resetList() {
		listModelResult.removeAllElements();
		listResult.setModel(listModelResult);
	}
	
	/**
	 * Xử lí sự kiện khi click vào nút {@link ListFileMain#btnUpdateList}
	 */
	private void clickOnBtnUpdateList() {
		updateList();
	}
	
	@SuppressWarnings("deprecation")
	private void clickOnBtnAbout() {
		about.show();
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
