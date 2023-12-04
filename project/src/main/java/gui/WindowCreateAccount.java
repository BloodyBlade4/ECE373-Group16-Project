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
		this.setSize(900, 540);

		this.setLocationRelativeTo(null); //Center of screen.
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		/* End initialization */
		
		// DECLARATIONS //
		JPanel MainPanel = new JPanel();
		JPanel GreenPanel = Styling.createGradientPanel();
		JButton CreateButton = new JButton("Create");
		CreateButton.addActionListener(submitCreateAccount);
		CreateButton.setForeground(new Color(46, 139, 87));
		textFieldUsername = Styling.basicTextField("Username");
		textFieldPassword = new JPasswordField();
		showPasswordCheckBox = new JCheckBox("");
		
		JButton btnSignIn = Styling.navigationButton("Sign in");
		btnSignIn.addActionListener(signInStateChange);
		JPanel GreyPanel = Styling.rightSideOfForms(new ArrayList<JButton>(Arrays.asList(btnSignIn)));
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
		sl_GreenPanel.putConstraint(SpringLayout.SOUTH, CreateButton, -205, SpringLayout.SOUTH, GreenPanel);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, CreateButton, 0, SpringLayout.EAST, textFieldPassword);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, CreateButton, 0, SpringLayout.WEST, textFieldPassword);
		CreateButton.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, CreateButton, 10, SpringLayout.SOUTH, textFieldPassword);
		GreenPanel.add(CreateButton);
		showPasswordCheckBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Toggle the echo character of the password field
		        textFieldPassword.setEchoChar(showPasswordCheckBox.isSelected() ? 0 : '*');
		    }
		});

		
	}


	public JTextField getTextFieldUsername() {
		return textFieldUsername;
	}


	public JTextField getTextFieldPassword() {
		return textFieldPassword;
	}
	
	
}
