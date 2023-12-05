package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class WindowForgotPassword extends JFrame{
	private static final long serialVersionUID = 1L;

	private JTextField AOne, ATwo, AThree;
	private JLabel lblQOne, lblQTwo, lblQThree;
	
	public WindowForgotPassword(ActionListener submitForgotPassword, ActionListener signInStateChange) {
		/* INITIALIZATION */
		super("Forgot Password");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 540);
		this.setIconImage(Styling.LOGO_IMAGE);

		this.setLocationRelativeTo(null); //Center of screen.
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		/* End initialization */
		
		// DECLARATIONS //
		JPanel MainPanel = new JPanel();
		JPanel GreenPanel = Styling.createGradientPanel();
		
		lblQOne = Styling.basicLabel("Question One");
		AOne = Styling.basicTextField("Answer");
		
		lblQTwo = Styling.basicLabel("Question One");
		ATwo = Styling.basicTextField("Answer");
		
		lblQThree = Styling.basicLabel("Question One");
		AThree = Styling.basicTextField("Answer");
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(submitForgotPassword);
		submitButton.setForeground(new Color(46, 139, 87));

		JButton btnSignIn = Styling.navigationButton("Sign In");
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
		// Question 1 Field
		sl_GreenPanel.putConstraint(SpringLayout.WEST, lblQOne, 50, SpringLayout.WEST, GreenPanel); // left side of text box
		sl_GreenPanel.putConstraint(SpringLayout.EAST, lblQOne, -50, SpringLayout.EAST, GreenPanel); // right side of text box is x away from right side of green panel
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, lblQOne, 75, SpringLayout.NORTH, GreenPanel);
		GreenPanel.add(lblQOne);
		
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, AOne, Styling.DIST_RELATED, SpringLayout.SOUTH, lblQOne);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, AOne, -50, SpringLayout.EAST, GreenPanel);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, AOne, 50, SpringLayout.WEST, GreenPanel);
		GreenPanel.add(AOne);
		
		// Question 2 Field
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, lblQTwo, Styling.DIST_SEPARATE, SpringLayout.SOUTH, AOne);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, lblQTwo, 50, SpringLayout.WEST, GreenPanel); // left side of text box
		sl_GreenPanel.putConstraint(SpringLayout.EAST, lblQTwo, -50, SpringLayout.EAST, GreenPanel); // right side of text box is x away from right side of green panel
		GreenPanel.add(lblQTwo);
		
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, ATwo, Styling.DIST_SEPARATE, SpringLayout.SOUTH, lblQTwo);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, ATwo, -50, SpringLayout.EAST, GreenPanel);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, ATwo, 50, SpringLayout.WEST, GreenPanel);
		GreenPanel.add(ATwo);
		
		// Question 3 Field
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, lblQThree, Styling.DIST_SEPARATE, SpringLayout.SOUTH, ATwo);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, lblQThree, 50, SpringLayout.WEST, GreenPanel); // left side of text box
		sl_GreenPanel.putConstraint(SpringLayout.EAST, lblQThree, -50, SpringLayout.EAST, GreenPanel); // right side of text box is x away from right side of green panel
		GreenPanel.add(lblQThree);
		
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, AThree, Styling.DIST_RELATED, SpringLayout.SOUTH, lblQThree);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, AThree, -50, SpringLayout.EAST, GreenPanel);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, AThree, 50, SpringLayout.WEST, GreenPanel);
		GreenPanel.add(AThree);
		
		// OTHER ELEMENTS //
		
		
		// Submit Button
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, submitButton, Styling.DIST_SEPARATE, SpringLayout.SOUTH, AThree);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, submitButton, 0, SpringLayout.EAST, AThree);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, submitButton, 0, SpringLayout.WEST, AThree);
		submitButton.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		GreenPanel.add(submitButton);
	}

	public String getAns1() {
		return this.AOne.getText();
	}
	public String getAns2() {
		return this.ATwo.getText();
	}
	public String getAns3() {
		return this.AThree.getText();
	}
	
	public void setAllQuestions(ArrayList<String> q) {
		this.lblQOne.setText(q.get(0));
		this.lblQTwo.setText(q.get(1));
		this.lblQThree.setText(q.get(2));
	}
	public void setQuestion1(String q) {
		this.lblQOne.setText(q);
	}
	public void setQuestion2(String q) {
		this.lblQTwo.setText(q);
	}
	public void setQuestion3(String q) {
		this.lblQThree.setText(q);
	}
	
}
