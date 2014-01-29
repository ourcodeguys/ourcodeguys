package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListFileAbout extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3102127089377036605L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public ListFileAbout() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon(ListFileAbout.class.getResource("/icon/Kumi-work.png")));
		lblImage.setBounds(10, 11, 320, 386);
		contentPanel.add(lblImage);
		
		JLabel lblNewLabel = new JLabel("List File GUI");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(340, 11, 274, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblBuildV = new JLabel("Version v1.1");
		lblBuildV.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBuildV.setBounds(340, 36, 274, 14);
		contentPanel.add(lblBuildV);
		
		JLabel lblThisIsAn = new JLabel("This is an example of Java Programming");
		lblThisIsAn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblThisIsAn.setBounds(340, 61, 274, 14);
		contentPanel.add(lblThisIsAn);
		
		JLabel lblHomepage = new JLabel("Homepage: https://github.com/khoahv/khoahv/");
		lblHomepage.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHomepage.setBounds(340, 86, 274, 14);
		contentPanel.add(lblHomepage);
		
		JLabel lblHttpsgithubcomkhoahvkhoahv = new JLabel("Copyright (c) 2014 Hoàng Văn Khoa");
		lblHttpsgithubcomkhoahvkhoahv.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHttpsgithubcomkhoahvkhoahv.setBounds(340, 111, 274, 14);
		contentPanel.add(lblHttpsgithubcomkhoahvkhoahv);
		
		JTextPane txtpnThisProgramIs = new JTextPane();
		txtpnThisProgramIs.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txtpnThisProgramIs.setEditable(false);
		txtpnThisProgramIs.setText("This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.\r\n\r\nThis program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. \r\n\r\nYou should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.");
		txtpnThisProgramIs.setBounds(340, 136, 274, 261);
		contentPanel.add(txtpnThisProgramIs);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						clickBtnOK();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void clickBtnOK() {
		this.hide();
	}
}
