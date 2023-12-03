package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class WindowForgotPassword extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private AnswerPanel QOne, QTwo, QThree;
	
	public WindowForgotPassword(ActionListener submitForgotPassword, ActionListener signInStateChange) {
		super("Create Account");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 400);
		this.setLocationRelativeTo(null); //Center of screen.
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelFooter = new JPanel();
		getContentPane().add(panelFooter, BorderLayout.SOUTH);
		
		JPanel panelCenter = new JPanel();
		getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		
		JSeparator separator = new JSeparator();
		panelCenter.add(separator);
		
		QOne = new AnswerPanel("", "Answer");
		panelCenter.add(QOne);
		QTwo = new AnswerPanel("", "Answer");
		panelCenter.add(QTwo);
		QThree = new AnswerPanel("", "Answer");
		panelCenter.add(QThree);
		
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(submitForgotPassword);
		
		JButton btnCreateAccount = Styling.navigationButton("Sign in");
		btnCreateAccount.addActionListener(signInStateChange);
		panelFooter.add(btnCreateAccount);

		btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCenter.add(btnSubmit);
		
		JSeparator separator_1 = new JSeparator();
		panelCenter.add(separator_1);
	}

	public String getAns1() {
		return this.QOne.textFieldOne.getText();
	}
	public String getAns2() {
		return this.QTwo.textFieldOne.getText();
	}
	public String getAns3() {
		return this.QThree.textFieldOne.getText();
	}
	
	public void setAllQuestions(ArrayList<String> q) {
		this.QOne.lblQ.setText(q.get(0));
		this.QTwo.lblQ.setText(q.get(1));
		this.QThree.lblQ.setText(q.get(2));
	}
	public void setQuestion1(String q) {
		this.QOne.lblQ.setText(q);
	}
	public void setQuestion2(String q) {
		this.QTwo.lblQ.setText(q);
	}
	public void setQuestion3(String q) {
		this.QThree.lblQ.setText(q);
	}
	
}

class AnswerPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextField textFieldOne;
	public JLabel lblQ;
	
	public AnswerPanel(String lblOne, String lblTwo) {
		//JPanel panelFields = new JPanel();
		//panelCenter.add(panelFields);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		lblQ = new JLabel("Security Question 1: ");
		lblQ.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(lblQ);
		
		JPanel panelField = new JPanel();
		panelField.setMaximumSize(new Dimension(200, 30));
		this.add(panelField);
		
		JLabel lblNewLabel_1 = new JLabel(lblOne);
		panelField.add(lblNewLabel_1);

		
		JPanel panelField_1 = new JPanel();
		panelField_1.setMaximumSize(new Dimension(200, 30));
		this.add(panelField_1);
		
		JLabel lblNewLabel_1_2_3 = new JLabel(lblTwo);
		panelField_1.add(lblNewLabel_1_2_3);
		
		textFieldOne = new JTextField();
		textFieldOne.setColumns(10);
		panelField_1.add(textFieldOne);
	}
}
