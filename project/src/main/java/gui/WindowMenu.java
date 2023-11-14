package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JButton;

public class WindowMenu extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WindowMenu(ActionListener openAccountInfo) {
		/* INITIALIZATION */
		super("Main");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(480, 475);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelHeader = new JPanel();
		getContentPane().add(panelHeader, BorderLayout.NORTH);
		
		JPanel panelLeft = new JPanel();
		getContentPane().add(panelLeft, BorderLayout.WEST);
		
		JButton btnAccountInfo = new JButton("Account");
		btnAccountInfo.addActionListener(openAccountInfo);
		panelLeft.add(btnAccountInfo);
		
		JPanel panelCenter = new JPanel();
		getContentPane().add(panelCenter, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		panelCenter.add(scrollPane);
		
		JPanel panelFooter = new JPanel();
		getContentPane().add(panelFooter, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("New button");
		panelFooter.add(btnNewButton);
		/* End initialization */
		
		
	}

}
