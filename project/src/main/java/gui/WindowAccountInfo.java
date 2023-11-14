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

/* Follows account creation for further information. */

public class WindowAccountInfo extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldSecQOne;
	private JTextField textFieldSecAOne;
	
	public WindowAccountInfo(ActionListener submitAccountInfo) {
		super("Create Account");
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
		
		JPanel panelFields = new JPanel();
		panelCenter.add(panelFields);
		panelFields.setLayout(new BoxLayout(panelFields, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Security question 1:");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelFields.add(lblNewLabel);
		
		JPanel panelField = new JPanel();
		panelField.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("Question");
		panelField.add(lblNewLabel_1_2_2);
		
		textFieldSecQOne = new JTextField();
		textFieldSecQOne.setColumns(10);
		panelField.add(textFieldSecQOne);
		
		JPanel panelField_2 = new JPanel();
		panelField_2.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField_2);
		
		JLabel lblNewLabel_1_2_3 = new JLabel("Answer");
		panelField_2.add(lblNewLabel_1_2_3);
		
		textFieldSecAOne = new JTextField();
		textFieldSecAOne.setColumns(10);
		panelField_2.add(textFieldSecAOne);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(submitAccountInfo);
		btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCenter.add(btnSubmit);
		
		JSeparator separator_1 = new JSeparator();
		panelCenter.add(separator_1);
		
	}

	public JTextField getTextFieldSecQOne() {
		return textFieldSecQOne;
	}

	public JTextField getTextFieldSecAOne() {
		return textFieldSecAOne;
	}
	
	
}
