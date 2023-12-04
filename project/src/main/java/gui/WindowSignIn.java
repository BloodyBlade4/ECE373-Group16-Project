package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/*
 * Default sign in screen. 
 * 
 * 	Could use the JPasswordField instead of JTextField for password. 
 */



public class WindowSignIn extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldUsername;
	private JPasswordField textFieldPassword;
	private JCheckBox showPasswordCheckBox;

	public WindowSignIn(ActionListener submitSignIn, 
			ActionListener forgotPasswordStateChange, ActionListener createAccountStateChange) {
		/* INITIALIZATION */
		super("Sign In");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 540);

		this.setLocationRelativeTo(null); //Center of screen.
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		/* End initialization */
		
		// DECLARATIONS //
		JPanel MainPanel = new JPanel();
		JPanel GreenPanel = Styling.createGradientPanel();
		JButton submitButton = new JButton("Sign In");
		submitButton.addActionListener(submitSignIn);
		submitButton.setForeground(new Color(46, 139, 87));
		textFieldUsername = new JTextField();
		textFieldPassword = new JPasswordField();
		showPasswordCheckBox = new JCheckBox("");
		
		JButton btnCreateAccount = Styling.navigationButton("Create Account");
		btnCreateAccount.addActionListener(createAccountStateChange);
		JButton btnForgotPassword = Styling.navigationButton("Forgot Password");
		btnForgotPassword.addActionListener(forgotPasswordStateChange);
		JPanel GreyPanel = Styling.rightSideOfForms(new ArrayList<JButton>(Arrays.asList(btnCreateAccount, btnForgotPassword)));
		MainPanel.add(GreyPanel);

		
				
		// PANELS //
		
		// Main Panel
		MainPanel.setBackground(Color.WHITE);
		MainPanel.setPreferredSize(new java.awt.Dimension(900, 500));
		MainPanel.setRequestFocusEnabled(false);
		getContentPane().add(MainPanel, BorderLayout.NORTH);
		MainPanel.setLayout(null);
		SpringLayout sl_GreenPanel = new SpringLayout();
		
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, GreenPanel, 5, SpringLayout.NORTH, MainPanel);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, GreenPanel, 200, SpringLayout.WEST, MainPanel);
		
		// Green Panel
		GreenPanel.setBackground(SystemColor.textHighlight);
		GreenPanel.setBounds(0, 0, 300, 500);
		MainPanel.add(GreenPanel);
		GreenPanel.setLayout(sl_GreenPanel);
		
		// FIELDS //
		
		// Username Field
		sl_GreenPanel.putConstraint(SpringLayout.WEST, textFieldUsername, 50, SpringLayout.WEST, GreenPanel); // left side of text box
		sl_GreenPanel.putConstraint(SpringLayout.EAST, textFieldUsername, -50, SpringLayout.EAST, GreenPanel); // right side of text box is x away from right side of green panel
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, textFieldUsername, 185, SpringLayout.NORTH, GreenPanel);
		GreenPanel.add(textFieldUsername);
		textFieldUsername.setHorizontalAlignment(JTextField.CENTER);
		textFieldUsername.setForeground(Color.GRAY);
		textFieldUsername.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		textFieldUsername.setText("Username");
		textFieldUsername.setColumns(10);
		textFieldUsername.addFocusListener(new FocusAdapter() {
			// Focus Gained - Username
			@Override
			public void focusGained(FocusEvent e) {
					if(textFieldUsername.getText().equals("Username")) {
						textFieldUsername.setText(null);
						textFieldUsername.requestFocus();
						Styling.removePlaceholderStyle(textFieldUsername);
					}
			}
			
			// Focus Lost - Username	
			@Override
			public void focusLost(FocusEvent e) {
				if(textFieldUsername.getText().length() == 0) {
					Styling.addPlaceholderStyle(textFieldUsername);
					textFieldUsername.setText("Username");
				}
			}
		});
		
		
		// Password Field
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, textFieldPassword, 10, SpringLayout.SOUTH, textFieldUsername);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, textFieldPassword, -50, SpringLayout.EAST, GreenPanel);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, textFieldPassword, 50, SpringLayout.WEST, GreenPanel);
		GreenPanel.add(textFieldPassword);
		textFieldPassword.setHorizontalAlignment(JTextField.CENTER);
		textFieldPassword.setForeground(Color.GRAY);
		textFieldPassword.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		textFieldPassword.setText("Password");
		textFieldPassword.setColumns(10);
		textFieldPassword.addFocusListener(new FocusAdapter() {
			// Focus Gained - Password
			@Override
			public void focusGained(FocusEvent e) {
				if(new String(textFieldPassword.getPassword()).equals("Password")) {
					textFieldPassword.setText(null);
					textFieldPassword.requestFocus();
					textFieldPassword.setEchoChar('•');
					Styling.removePlaceholderStyle(textFieldPassword);
				}	
			}
				
			// Focus Lost - Password
			@Override
			public void focusLost(FocusEvent e) {
				if(textFieldPassword.getPassword().length == 0) {
					Styling.addPlaceholderStyle(textFieldPassword);
					textFieldPassword.setText("Password");
					if(showPasswordCheckBox.isSelected())
						textFieldPassword.setEchoChar('\u0000');
					else
						textFieldPassword.setEchoChar('•');
				}
			}	
		});
		
		// OTHER ELEMENTS //
		
		// Checkbox
		sl_GreenPanel.putConstraint(SpringLayout.SOUTH, showPasswordCheckBox, 0, SpringLayout.SOUTH, textFieldPassword);
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, showPasswordCheckBox, 0, SpringLayout.NORTH, textFieldPassword);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, showPasswordCheckBox, 10, SpringLayout.EAST, textFieldPassword);
		GreenPanel.add(showPasswordCheckBox);
		showPasswordCheckBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		// Create Button
		sl_GreenPanel.putConstraint(SpringLayout.SOUTH, submitButton, -205, SpringLayout.SOUTH, GreenPanel);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, submitButton, 0, SpringLayout.EAST, textFieldPassword);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, submitButton, 0, SpringLayout.WEST, textFieldPassword);
		submitButton.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, submitButton, 10, SpringLayout.SOUTH, textFieldPassword);
		GreenPanel.add(submitButton);
		showPasswordCheckBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Toggle the echo character of the password field
		        textFieldPassword.setEchoChar(showPasswordCheckBox.isSelected() ? 0 : '*');
		    }
		});
		/*//INITIALIZATION. 
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
			ImageIcon Icon = new ImageIcon(
					new ImageIcon(WindowMenu.class.getClassLoader().getResource("Lock Icon.png")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)
				); 
			label1.setIcon(Icon);
			panelCenter.add(label1);
		} catch (Exception e) {
			System.out.println("Unable to locate Icon image.\n" + e);
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
