package gui;

import java.io.File;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
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
	/*private JTextField textFieldSecQOne;
	private JTextField textFieldSecAOne;
	
	private JTextField textFieldSecQTwo;
	private JTextField textFieldSecATwo;
	private JTextField textFieldSecQThree;
	private JTextField textFieldSecAThree;*/
	
	private PannelField QOne, QTwo, QThree;
	
	private JLabel lblHomeDir;
	
	public WindowAccountInfo(ActionListener submitAccountInfo) {
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
		
		/* Start Panel 
		JPanel panelFields = new JPanel();
		panelCenter.add(panelFields);
		panelFields.setLayout(new BoxLayout(panelFields, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Security question 1:");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelFields.add(lblNewLabel);
		
		JPanel panelField = new JPanel();
		panelField.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField);
		
		JLabel lblNewLabel_1 = new JLabel("Question");
		panelField.add(lblNewLabel_1);
		
		textFieldSecQOne = new JTextField();
		textFieldSecQOne.setColumns(10);
		panelField.add(textFieldSecQOne);

		
		JPanel panelField_1 = new JPanel();
		panelField_1.setMaximumSize(new Dimension(200, 30));
		panelFields.add(panelField_1);
		
		JLabel lblNewLabel_1_2_3 = new JLabel("Answer");
		panelField_1.add(lblNewLabel_1_2_3);
		
		textFieldSecAOne = new JTextField();
		textFieldSecAOne.setColumns(10);
		panelField_1.add(textFieldSecAOne);
		End Panel */
		
		QOne = new PannelField("Security question 1:", "Question", "Answer");
		panelCenter.add(QOne);
		QTwo = new PannelField("Security question 2:", "Question", "Answer");
		panelCenter.add(QTwo);
		QThree = new PannelField("Security question 3:", "Question", "Answer");
		panelCenter.add(QThree);
		//panelCenter.add(new PannelField("Security question 2:", "Question", textFieldSecQTwo, "Answer", textFieldSecATwo));
		//panelCenter.add(new PannelField("Security question 3:", "Question", textFieldSecQThree, "Answer", textFieldSecAThree));
		
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(submitAccountInfo);
		
		JPanel panelHomeDirectory = new JPanel();
		panelCenter.add(panelHomeDirectory);
		panelHomeDirectory.setLayout(new BoxLayout(panelHomeDirectory, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panelHomeDirectory.add(panel);
		
		JLabel lblNewLabel_2 = new JLabel("Home Directory:");
		panel.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Search");
		
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panelHomeDirectory.add(panel_1);
		
		lblHomeDir = new JLabel("No directory selected.");
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
		panelCenter.add(separator_1);
		
	}
	
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
		return QOne.textFieldTwo;
	}

	public JTextField getTextFieldSecAOne() {
		return QOne.textFieldTwo;
	}

	public JTextField getTextFieldSecQTwo() {
		return QTwo.textFieldTwo;
	}

	public JTextField getTextFieldSecATwo() {
		return QTwo.textFieldOne;
	}

	public JTextField getTextFieldSecQThree() {
		return QThree.textFieldTwo;
	}

	public JTextField getTextFieldSecAThree() {
		return QThree.textFieldOne;
	}
}

class PannelField extends JPanel{
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
