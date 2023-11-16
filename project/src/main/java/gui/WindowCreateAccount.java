package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class WindowCreateAccount extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldUsername;
	private JPasswordField textFieldPassword;
	
	
	public WindowCreateAccount(ActionListener submitCreateAccount, 
			ActionListener signInStateChange, ActionListener forgotPasswordStateChange) {
		/* INITIALIZATION */
		super("Create Account");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 400);
		this.setLocationRelativeTo(null); //Center of screen.
		getContentPane().setLayout(new BorderLayout(0, 0));
		/* End initialization */
		
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
		
		//Panel field here. Duplicate 
		JPanel panelField = new JPanel();
		panelField.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		panelField.add(lblNewLabel_1);
		
		textFieldUsername = new JTextField();
		panelField.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		//end panel field
		
		JPanel panelField_1 = new JPanel();
		panelField_1.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		panelField_1.add(lblNewLabel_1_1);
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setColumns(10);
		panelField_1.add(textFieldPassword);
		
		JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Toggle the echo character of the password field
                textFieldPassword.setEchoChar(showPasswordCheckBox.isSelected() ? 0 : '*');
            }
        });
		
		/*BUTTONS */
		JButton btnSubmit = new JButton("Create Account");
		btnSubmit.addActionListener(submitCreateAccount);
		btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCenter.add(btnSubmit);
		
		JButton btnSignIn = Styling.navigationButton("Sign In");
		btnSignIn.addActionListener(signInStateChange);
		panelFooter.add(btnSignIn);
		
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
