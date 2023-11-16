package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;

public class WindowCreateAccount extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldUsername;
	private JPasswordField textFieldPassword;
	private JCheckBox showPasswordCheckBox;
	
	
	public WindowCreateAccount(ActionListener submitCreateAccount, 
			ActionListener signInStateChange, ActionListener forgotPasswordStateChange) {
		/* INITIALIZATION */
		super("Create Account");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 400);
		this.setLocationRelativeTo(null); //Center of screen.
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		/* End initialization */
		
		JPanel panelFooter = new JPanel();
		getContentPane().add(panelFooter, BorderLayout.SOUTH);
		
		JPanel panelCenter = new JPanel();
		getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		
		JLabel label1 = new JLabel("");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setBounds(628, 28, 169, 125);
		try {
			ImageIcon Icon = new ImageIcon(new ImageIcon(WindowMenu.class.getResource("Lock Icon.png")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)); //100, 100 add your own size
			label1.setIcon(Icon);
		
		} catch (Exception e) {
			System.out.println("Unable to load image.");
		}
		JSeparator separator_2 = new JSeparator();
		panelCenter.add(separator_2);
		
		JLabel lblNewLabel = new JLabel("LockBox");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(46, 139, 87));
		lblNewLabel.setFont(new Font("Avenir Next", Font.PLAIN, 16));
		panelCenter.add(lblNewLabel);
		panelCenter.add(label1);
		
		JSeparator separator = new JSeparator();
		panelCenter.add(separator);
		
		JPanel panelFields = new JPanel();
		panelCenter.add(panelFields);
		panelFields.setLayout(new BoxLayout(panelFields, BoxLayout.Y_AXIS));
		
		//Panel field here. Duplicate 
		JPanel panelField = new JPanel();
		panelField.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setForeground(Color.GRAY);
		textFieldUsername.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		textFieldUsername.setText("Username");
		panelField.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		//end panel field
		
		JPanel panelField_1 = new JPanel();
		panelField_1.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField_1);
		panelField_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setForeground(Color.GRAY);
		textFieldPassword.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		textFieldPassword.setText("Password");
		textFieldPassword.setColumns(10);
		panelField_1.add(textFieldPassword);
		
		showPasswordCheckBox = new JCheckBox("");
        showPasswordCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Toggle the echo character of the password field
                textFieldPassword.setEchoChar(showPasswordCheckBox.isSelected() ? 0 : '*');
            }
        });
        showPasswordCheckBox.setSize(10, 10);
        panelField_1.add(showPasswordCheckBox);
		
		/*BUTTONS */
		JButton btnSubmit = new JButton("Create Account");
		btnSubmit.setForeground(new Color(46, 139, 87));
		btnSubmit.setFont(new Font("Avenir Next", Font.PLAIN, 13));
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
