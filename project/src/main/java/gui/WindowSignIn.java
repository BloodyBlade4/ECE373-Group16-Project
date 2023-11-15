package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
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
 * 
 * Optional:
 * 	Could use the JPasswordField instead of JTextField for password. 
 */



public class WindowSignIn extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldUsername;
	private JTextField textFieldPassword;

	public WindowSignIn(ActionListener submitSignIn, 
			ActionListener forgotPasswordStateChange, ActionListener createAccountStateChange) {
		//INITIALIZATION. 
		super("Sign in");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 400);
		this.setLocationRelativeTo(null); //Center of screen.
		getContentPane().setLayout(new BorderLayout(0, 0));
		//END initialization.
		
		
		JPanel panelFooter = new JPanel();
		getContentPane().add(panelFooter, BorderLayout.SOUTH);
		
		JPanel panelCenter = new JPanel();
		getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		
		JSeparator separator = new JSeparator();
		panelCenter.add(separator);
		
		JPanel panelFields = new JPanel();
		panelCenter.add(panelFields);
		panelFields.setLayout(new BoxLayout(panelFields, BoxLayout.Y_AXIS));
		
		//PANEL FIELD here. Duplicate as needed for forms. 
		JPanel panelField = new JPanel();
		panelField.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField);
		
		JLabel lblNewLabel_1 = new JLabel("User Name");
		panelField.add(lblNewLabel_1);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setText("hi");
		panelField.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		//END panel field
		
		JPanel panelField_1 = new JPanel();
		panelField_1.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		panelField_1.add(lblNewLabel_1_1);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);
		panelField_1.add(textFieldPassword);
		
		/* BUTTONS */
		JButton btnSubmit = new JButton("Sign In");
		btnSubmit.addActionListener(submitSignIn);
		btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCenter.add(btnSubmit);
		
		JButton btnCreateAccount = Styling.navigationButton("Create Account");
		btnCreateAccount.addActionListener(createAccountStateChange);
		panelFooter.add(btnCreateAccount);
		
		JButton btnForgotPassword = Styling.navigationButton("Forgot Password");
		btnForgotPassword.addActionListener(forgotPasswordStateChange);
		panelFooter.add(btnForgotPassword);
		/* END buttons */
		
		JSeparator separator_1 = new JSeparator();
		panelCenter.add(separator_1);
		
	}

	public JTextField getTextFieldUsername() {
		return textFieldUsername;
	}

	public JTextField getTextFieldPassword() {
		return textFieldPassword;
	}
	
	
}
