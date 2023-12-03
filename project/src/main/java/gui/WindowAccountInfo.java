package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.DropMode;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/* Follows account creation for further information. */

public class WindowAccountInfo extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*private JTextField textFieldSecQOne;
	private JTextField textFieldSecAOne;
	
	private JTextField textFieldSecQTwo;
	private JTextField textFieldSecATwo;
	private JTextField textFieldSecQThree;
	private JTextField textFieldSecAThree;*/
	
	private PannelField QOne, QTwo, QThree;
	
	private JLabel lblHomeDir;
	
	public WindowAccountInfo(String title, ActionListener submitAccountInfo) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 400);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(400,450));
		
		JPanel panelCenter = new JPanel();
		SpringLayout sl_panelCenter = new SpringLayout();
		panelCenter.setLayout(sl_panelCenter);
		
		JSeparator separator = new JSeparator();
		sl_panelCenter.putConstraint(SpringLayout.NORTH, separator, 0, SpringLayout.NORTH, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.SOUTH, separator, 1, SpringLayout.NORTH, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.EAST, separator, 400, SpringLayout.WEST, panelCenter);
		panelCenter.add(separator);
		

		
		QOne = new PannelField("Security Question 1", "", "");
		sl_panelCenter.putConstraint(SpringLayout.NORTH, QOne, 0, SpringLayout.SOUTH, separator);
		QOne.textFieldTwo.setForeground(Color.GRAY);
		QOne.textFieldTwo.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		QOne.textFieldOne.setForeground(Color.GRAY);
		QOne.textFieldOne.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		QOne.textFieldTwo.setText("Answer");
		QOne.textFieldTwo.setDropMode(DropMode.INSERT);
		QOne.textFieldOne.setDropMode(DropMode.INSERT);
		QOne.textFieldOne.setText("Question");
		sl_panelCenter.putConstraint(SpringLayout.WEST, QOne, 100, SpringLayout.WEST, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.EAST, QOne, 300, SpringLayout.WEST, panelCenter);
		panelCenter.add(QOne);
		QTwo = new PannelField("Security Question 2", "", "");
		QTwo.textFieldTwo.setForeground(Color.GRAY);
		QTwo.textFieldTwo.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		QTwo.textFieldTwo.setText("Answer");
		QTwo.textFieldOne.setForeground(Color.GRAY);
		QTwo.textFieldOne.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		QTwo.textFieldOne.setText("Question");
		sl_panelCenter.putConstraint(SpringLayout.NORTH, QTwo, 89, SpringLayout.NORTH, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.WEST, QTwo, 100, SpringLayout.WEST, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.EAST, QTwo, 300, SpringLayout.WEST, panelCenter);
		panelCenter.add(QTwo);
		QThree = new PannelField("Security Question 3", "", "");
		sl_panelCenter.putConstraint(SpringLayout.NORTH, QThree, 177, SpringLayout.NORTH, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.SOUTH, QOne, -84, SpringLayout.NORTH, QThree);
		QThree.textFieldTwo.setForeground(Color.GRAY);
		QThree.textFieldTwo.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		QThree.textFieldTwo.setText("Answer");
		QThree.textFieldOne.setForeground(Color.GRAY);
		QThree.textFieldOne.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		QThree.textFieldOne.setText("Question");
		sl_panelCenter.putConstraint(SpringLayout.WEST, QThree, 100, SpringLayout.WEST, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.EAST, QThree, 300, SpringLayout.WEST, panelCenter);
		panelCenter.add(QThree);
		//panelCenter.add(new PannelField("Security question 2:", "Question", textFieldSecQTwo, "Answer", textFieldSecATwo));
		//panelCenter.add(new PannelField("Security question 3:", "Question", textFieldSecQThree, "Answer", textFieldSecAThree));
		
		
		JButton btnSubmit = new JButton("Submit");
		sl_panelCenter.putConstraint(SpringLayout.SOUTH, QThree, -61, SpringLayout.NORTH, btnSubmit);
		sl_panelCenter.putConstraint(SpringLayout.EAST, btnSubmit, -160, SpringLayout.EAST, panelCenter);
		btnSubmit.setForeground(new Color(46, 139, 87));
		btnSubmit.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		sl_panelCenter.putConstraint(SpringLayout.NORTH, btnSubmit, 330, SpringLayout.NORTH, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.WEST, btnSubmit, 155, SpringLayout.WEST, panelCenter);
		btnSubmit.addActionListener(submitAccountInfo);
		
		JPanel panelHomeDirectory = new JPanel();
		sl_panelCenter.putConstraint(SpringLayout.SOUTH, QTwo, -84, SpringLayout.NORTH, panelHomeDirectory);
		sl_panelCenter.putConstraint(SpringLayout.NORTH, panelHomeDirectory, 265, SpringLayout.NORTH, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.WEST, panelHomeDirectory, 0, SpringLayout.WEST, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.SOUTH, panelHomeDirectory, 330, SpringLayout.NORTH, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.EAST, panelHomeDirectory, 400, SpringLayout.WEST, panelCenter);
		panelCenter.add(panelHomeDirectory);
		panelHomeDirectory.setLayout(new BoxLayout(panelHomeDirectory, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panelHomeDirectory.add(panel);
		
		JLabel lblNewLabel_2 = new JLabel("Home Directory:");
		lblNewLabel_2.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		panel.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setForeground(new Color(46, 139, 87));
		btnNewButton.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panelHomeDirectory.add(panel_1);
		
		lblHomeDir = new JLabel("No directory selected");
		lblHomeDir.setFont(new Font("Avenir Next", Font.BOLD, 13));;
		panel_1.add(lblHomeDir);
		btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCenter.add(btnSubmit);
		
		btnNewButton.addActionListener(new ActionListener() {
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
		
		JSeparator separator_1 = new JSeparator();
		sl_panelCenter.putConstraint(SpringLayout.SOUTH, btnSubmit, 0, SpringLayout.NORTH, separator_1);
		sl_panelCenter.putConstraint(SpringLayout.NORTH, separator_1, 359, SpringLayout.NORTH, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.WEST, separator_1, 0, SpringLayout.WEST, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.SOUTH, separator_1, 360, SpringLayout.NORTH, panelCenter);
		sl_panelCenter.putConstraint(SpringLayout.EAST, separator_1, 400, SpringLayout.WEST, panelCenter);
		panelCenter.add(separator_1);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panelCenter, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panelCenter, GroupLayout.PREFERRED_SIZE, 422, GroupLayout.PREFERRED_SIZE)
		);
		getContentPane().setLayout(groupLayout);
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
		
		//Filtering option.
		//if (extDetail != null && extName != null)
			//chooser.setFileFilter(new FileNameExtensionFilter(extDetail, extName));
		
		int option = chooser.showOpenDialog(null);
		if(option == JFileChooser.APPROVE_OPTION) 
			return chooser.getSelectedFile().getAbsolutePath();
		return null;
	}
	
	public JTextField getTextFieldSecQOne() {
		return QOne.textFieldOne;
	}
	public void setTextFieldSecQOne(String text) {
		QOne.textFieldOne.setText(text);
	}

	public JTextField getTextFieldSecAOne() {
		return QOne.textFieldTwo;
	}
	public void setTextFieldSecAOne(String text) {
		QOne.textFieldTwo.setText(text);;
	}

	public JTextField getTextFieldSecQTwo() {
		return QTwo.textFieldOne;
	}
	public void setTextFieldSecQTwo(String text) {
		QTwo.textFieldOne.setText(text);
	}

	public JTextField getTextFieldSecATwo() {
		return QTwo.textFieldTwo;
	}
	public void setTextFieldSecATwo(String text) {
		QTwo.textFieldTwo.setText(text);
	}

	public JTextField getTextFieldSecQThree() {
		return QThree.textFieldOne;
	}
	public void setTextFieldSecQThree(String text) {
		QThree.textFieldOne.setText(text);
	}

	public JTextField getTextFieldSecAThree() {
		return QThree.textFieldTwo;
	}
	public void setTextFieldSecAThree(String text) {
		QThree.textFieldTwo.setText(text);
	}
	
	public String getHomeDir() {
		return lblHomeDir.getText();
	}
	public void setHomeDir(String text) {
		lblHomeDir.setText(text);
	}
}

//A work in progress for GUI implementation. 
class PannelField extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextField textFieldOne, textFieldTwo;
	
	public PannelField(String title, String lblOne, String lblTwo) {
		//JPanel panelFields = new JPanel();
		//panelCenter.add(panelFields);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel(title);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(lblNewLabel);
		
		JPanel panelField = new JPanel();
		panelField.setMaximumSize(new Dimension(200, 30));
		this.add(panelField);
		
		JLabel lblNewLabel_1 = new JLabel(lblOne);
		panelField.add(lblNewLabel_1);
		
		textFieldOne = new JTextField();
		textFieldOne.setColumns(10);
		panelField.add(textFieldOne);

		
		JPanel panelField_1 = new JPanel();
		panelField_1.setMaximumSize(new Dimension(200, 30));
		this.add(panelField_1);
		
		JLabel lblNewLabel_1_2_3 = new JLabel(lblTwo);
		panelField_1.add(lblNewLabel_1_2_3);
		
		textFieldTwo = new JTextField();
		textFieldTwo.setColumns(10);
		panelField_1.add(textFieldTwo);
	}
}
