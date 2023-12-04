package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/* Follows account creation for further information. */

public class WindowAccountInfo extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTextField textFieldSecQOne;
	private JTextField textFieldSecAOne;
	private JTextField textFieldSecQTwo;
	private JTextField textFieldSecATwo;
	private JTextField textFieldSecQThree;
	private JTextField textFieldSecAThree;
	private JLabel lblHomeDir;
	
	public WindowAccountInfo(String title, ActionListener submitAccountInfo) {
		/* INITIALIZATION */
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 540);

		this.setLocationRelativeTo(null); //Center of screen.
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		/* End initialization */
		
		// DECLARATIONS //
		//Integer distances between gui objects. 
		final int DIST_RELATED = 10;
		final int DIST_SEPARATE = 25;
		
		JPanel MainPanel = new JPanel();
		JPanel GreenPanel = Styling.createGradientPanel();
		
		textFieldSecQOne = Styling.basicTextField("Question One");
		textFieldSecAOne = Styling.basicTextField("Answer");
		
		textFieldSecQTwo = Styling.basicTextField("Question Two");
		textFieldSecATwo = Styling.basicTextField("Answer");
		
		textFieldSecQThree = Styling.basicTextField("Question Three");
		textFieldSecAThree = Styling.basicTextField("Answer");
		
		lblHomeDir = new JLabel("No directory selected");
		JButton btnSelectHomeDir = new JButton("Search");
		
		JButton CreateButton = new JButton("Submit");
		CreateButton.addActionListener(submitAccountInfo);
		CreateButton.setForeground(new Color(46, 139, 87));

		JPanel GreyPanel = Styling.rightSideOfForms(new ArrayList<JButton>());
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
		sl_GreenPanel.putConstraint(SpringLayout.WEST, textFieldSecQOne, 50, SpringLayout.WEST, GreenPanel); // left side of text box
		sl_GreenPanel.putConstraint(SpringLayout.EAST, textFieldSecQOne, -50, SpringLayout.EAST, GreenPanel); // right side of text box is x away from right side of green panel
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, textFieldSecQOne, 75, SpringLayout.NORTH, GreenPanel);
		GreenPanel.add(textFieldSecQOne);
		
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, textFieldSecAOne, DIST_RELATED, SpringLayout.SOUTH, textFieldSecQOne);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, textFieldSecAOne, -50, SpringLayout.EAST, GreenPanel);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, textFieldSecAOne, 50, SpringLayout.WEST, GreenPanel);
		GreenPanel.add(textFieldSecAOne);
		
		// Question 2 Field
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, textFieldSecQTwo, DIST_SEPARATE, SpringLayout.SOUTH, textFieldSecAOne);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, textFieldSecQTwo, 50, SpringLayout.WEST, GreenPanel); // left side of text box
		sl_GreenPanel.putConstraint(SpringLayout.EAST, textFieldSecQTwo, -50, SpringLayout.EAST, GreenPanel); // right side of text box is x away from right side of green panel
		GreenPanel.add(textFieldSecQTwo);
		
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, textFieldSecATwo, DIST_RELATED, SpringLayout.SOUTH, textFieldSecQTwo);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, textFieldSecATwo, -50, SpringLayout.EAST, GreenPanel);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, textFieldSecATwo, 50, SpringLayout.WEST, GreenPanel);
		GreenPanel.add(textFieldSecATwo);
		
		// Question 3 Field
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, textFieldSecQThree, DIST_SEPARATE, SpringLayout.SOUTH, textFieldSecATwo);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, textFieldSecQThree, 50, SpringLayout.WEST, GreenPanel); // left side of text box
		sl_GreenPanel.putConstraint(SpringLayout.EAST, textFieldSecQThree, -50, SpringLayout.EAST, GreenPanel); // right side of text box is x away from right side of green panel
		GreenPanel.add(textFieldSecQThree);
		
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, textFieldSecAThree, DIST_RELATED, SpringLayout.SOUTH, textFieldSecQThree);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, textFieldSecAThree, -50, SpringLayout.EAST, GreenPanel);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, textFieldSecAThree, 50, SpringLayout.WEST, GreenPanel);
		GreenPanel.add(textFieldSecAThree);
		
		// OTHER ELEMENTS //
		
		//HomeDir label
		lblHomeDir.setFont(new Font("Avenir Next", Font.BOLD, 13));
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, lblHomeDir, DIST_SEPARATE, SpringLayout.SOUTH, textFieldSecAThree);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, lblHomeDir, 0, SpringLayout.EAST, GreenPanel);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, lblHomeDir, 0, SpringLayout.WEST, GreenPanel);
		lblHomeDir.setHorizontalAlignment(JLabel.CENTER);
		GreenPanel.add(lblHomeDir);
		
		//HomeDir button
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, btnSelectHomeDir, DIST_RELATED, SpringLayout.SOUTH, lblHomeDir);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, btnSelectHomeDir, -50, SpringLayout.EAST, GreenPanel);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, btnSelectHomeDir, 50, SpringLayout.WEST, GreenPanel);
		btnSelectHomeDir.setForeground(new Color(46, 139, 87));
		btnSelectHomeDir.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		GreenPanel.add(btnSelectHomeDir);
		btnSelectHomeDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String p = selectDirectory();
					if (p==null)
						return;
					lblHomeDir.setText(p);
				} catch (Exception e1) {
					System.out.println("error selecting directory!");
					e1.printStackTrace();
				}
			}
		});
		
		
		
		// Create Button
		sl_GreenPanel.putConstraint(SpringLayout.NORTH, CreateButton, DIST_SEPARATE, SpringLayout.SOUTH, btnSelectHomeDir);
		sl_GreenPanel.putConstraint(SpringLayout.EAST, CreateButton, 0, SpringLayout.EAST, textFieldSecQOne);
		sl_GreenPanel.putConstraint(SpringLayout.WEST, CreateButton, 0, SpringLayout.WEST, textFieldSecQOne);
		CreateButton.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		GreenPanel.add(CreateButton);
	}
	
	//Prompts the user to select a directory for their home directory. 
	private static String selectDirectory() {
		File directory = new File(System.getProperty("user.dir"));
		JFileChooser chooser = new JFileChooser(directory) {
			private static final long serialVersionUID = 1L;

				public void approveSelection() {
	                if (getSelectedFile().isFile()) {
	                	String name = getSelectedFile().getAbsolutePath();
	                	int p = Math.max(name.lastIndexOf('/'), name.lastIndexOf('\\'));
	                	super.setSelectedFile(new File(name.substring(0, p)));
	                }
                    super.approveSelection();
	            }
	    };
	    
		chooser.setDialogTitle("Select a directory.");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		int option = chooser.showOpenDialog(null);
		if(option == JFileChooser.APPROVE_OPTION) 
			return chooser.getSelectedFile().getAbsolutePath();
		return null;
	}
	
	public JTextField getTextFieldSecQOne() {
		return this.textFieldSecQOne;
	}
	public void setTextFieldSecQOne(String text) {
		this.textFieldSecQOne.setText(text);
	}

	public JTextField getTextFieldSecAOne() {
		return this.textFieldSecAOne;
	}
	public void setTextFieldSecAOne(String text) {
		this.textFieldSecAOne.setText(text);;
	}

	public JTextField getTextFieldSecQTwo() {
		return this.textFieldSecQTwo;
	}
	public void setTextFieldSecQTwo(String text) {
		this.textFieldSecQTwo.setText(text);
	}

	public JTextField getTextFieldSecATwo() {
		return this.textFieldSecATwo;
	}
	public void setTextFieldSecATwo(String text) {
		this.textFieldSecATwo.setText(text);
	}

	public JTextField getTextFieldSecQThree() {
		return this.textFieldSecQThree;
	}
	public void setTextFieldSecQThree(String text) {
		this.textFieldSecQThree.setText(text);
	}

	public JTextField getTextFieldSecAThree() {
		return this.textFieldSecAThree;
	}
	public void setTextFieldSecAThree(String text) {
		this.textFieldSecAThree.setText(text);
	}
	
	public String getHomeDir() {
		return lblHomeDir.getText();
	}
	public void setHomeDir(String text) {
		lblHomeDir.setText(text);
	}
}
