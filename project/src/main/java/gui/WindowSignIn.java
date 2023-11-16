package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
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
		setTitle("Sign In");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 400);
		this.setLocationRelativeTo(null); //Center of screen.
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		//END initialization.
		
		
		JPanel panelFooter = new JPanel();
		getContentPane().add(panelFooter, BorderLayout.SOUTH);
		
		JLabel lblNewLabel = new JLabel("LockBox");
		lblNewLabel.setForeground(new Color(46, 139, 87));
		lblNewLabel.setFont(new Font("Avenir Next", Font.PLAIN, 16));
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelCenter = new JPanel();
		getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		panelCenter.setSize(400,400);
		
		JSeparator separator = new JSeparator();
		panelCenter.add(separator);
		JLabel label1 = new JLabel("");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setBounds(628, 28, 169, 125);
		try {
			ImageIcon Icon = new ImageIcon(new ImageIcon(WindowMenu.class.getResource("Lock Icon.png")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)); //100, 100 add your own size
			label1.setIcon(Icon);
			panelCenter.add(label1);
		} catch (Exception e) {
			System.out.println("Unable to locate file");
			
		}

		
		JSeparator separator_2 = new JSeparator();
		panelCenter.add(separator_2);
		
		JPanel panelFields = new JPanel();
		panelCenter.add(panelFields);
		panelFields.setLayout(new BoxLayout(panelFields, BoxLayout.Y_AXIS));
		
		//PANEL FIELD here. Duplicate as needed for forms. 
		JPanel Username = new JPanel();
		Username.setMaximumSize(new Dimension(200, 30));
		panelFields.add(Username);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setForeground(Color.GRAY);
		textFieldUsername.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		textFieldUsername.setText("Username");
		textFieldUsername.setBackground(Color.WHITE);
		Username.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		//END panel field
		
		JPanel Password = new JPanel();
		Password.setMaximumSize(new Dimension(200, 30));
		panelFields.add(Password);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setForeground(Color.GRAY);
		textFieldPassword.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		textFieldPassword.setText("Password");
		textFieldPassword.setColumns(10);
		Password.add(textFieldPassword);
		
		/* BUTTONS */
		JButton btnSubmit = new JButton("Sign In");
		btnSubmit.setForeground(new Color(46, 139, 87));
		btnSubmit.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		btnSubmit.addActionListener(submitSignIn);
		btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCenter.add(btnSubmit);
		
		JSeparator separator_1 = new JSeparator();
		panelCenter.add(separator_1);
		
		JButton btnCreateAccount = Styling.navigationButton("Create Account");
		btnCreateAccount.addActionListener(createAccountStateChange);
		panelFooter.add(btnCreateAccount);
		
		JButton btnForgotPassword = Styling.navigationButton("Forgot Password");
		btnForgotPassword.addActionListener(forgotPasswordStateChange);
		panelFooter.add(btnForgotPassword);
		/* END buttons */
		
	}

	public JTextField getTextFieldUsername() {
		return textFieldUsername;
	}

	public JTextField getTextFieldPassword() {
		return textFieldPassword;
	}
	
	
}
