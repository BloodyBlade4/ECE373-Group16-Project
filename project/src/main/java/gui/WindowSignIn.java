package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/*
 * THIS IS A DEFAULT CONSTRUCTION FOR FIELDS HERE. 
 * Modify accordingly. Things to consider for modification
 * 1. duplicate "panelField" JPanel for as many fields as you need. they already organize them selves. You just need to name and set controls
 * 2. Set the two bottom buttons according to what you want to happen. 
 * 
 * TODO: 
 * 1. Rig the exit button to exit the program.
 * 2. Set up input statements for the windows creation. This can be the number of fields to activate, what the bottom buttons do
 * 		and also how returns/actions are handled. 
 */



public class WindowSignIn extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_1;

	public WindowSignIn(/* input statements here. TODO. */) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelHeader = new JPanel();
		add(panelHeader, BorderLayout.NORTH);
		panelHeader.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Sign in");
		lblNewLabel.setFont(Styling.headerFont);
		panelHeader.add(lblNewLabel, BorderLayout.CENTER);
		
		JButton btnExit = new JButton("X");
		btnExit.setForeground(new Color(0, 0, 0));
		btnExit.setBackground(new Color(255, 0, 0));
		btnExit.setMaximumSize(new Dimension(10, 10));
		panelHeader.add(btnExit, BorderLayout.EAST);
		
		JPanel panelFooter = new JPanel();
		add(panelFooter, BorderLayout.SOUTH);
		
		JButton btnCreateAccount = new JButton("Create Account");
		panelFooter.add(btnCreateAccount);
		
		JButton btnForgotPassword = new JButton("Forgot Password");
		panelFooter.add(btnForgotPassword);
		
		JPanel panelCenter = new JPanel();
		add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		
		JSeparator separator = new JSeparator();
		panelCenter.add(separator);
		
		JPanel panelFields = new JPanel();
		panelCenter.add(panelFields);
		panelFields.setLayout(new BoxLayout(panelFields, BoxLayout.Y_AXIS));
		
		//Panel field here. Duplicate 
		JPanel panelField = new JPanel();
		panelField.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panelField.add(lblNewLabel_1);
		
		textField = new JTextField();
		panelField.add(textField);
		textField.setColumns(10);
		//end panel field
		
		JPanel panelField_1 = new JPanel();
		panelField_1.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("New label");
		panelField_1.add(lblNewLabel_1_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		panelField_1.add(textField_2);
		
		JPanel panelField_2 = new JPanel();
		panelField_2.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField_2);
		
		JLabel lblNewLabel_1_2 = new JLabel("New label");
		panelField_2.add(lblNewLabel_1_2);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		panelField_2.add(textField_1);
		
		JButton btnSubmit = new JButton("New button");
		btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCenter.add(btnSubmit);
		
		JSeparator separator_1 = new JSeparator();
		panelCenter.add(separator_1);
		
	}
}
