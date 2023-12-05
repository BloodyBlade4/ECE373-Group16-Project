package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class WindowMenu extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public Label welcomeMessage;

	public WindowMenu(ActionListener openSecurityPreferences, ActionListener resetPassword, 
			ActionListener encryptFile, ActionListener decryptFile) {
		/* INITIALIZATION */
		super("Main");
		setTitle("LockBox");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(480, 475);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setMinimumSize(new Dimension(400,450));
		this.setIconImage(Styling.LOGO_IMAGE);
		
		JPanel panelHeader = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelHeader.getLayout();
		flowLayout.setVgap(10);
		getContentPane().add(panelHeader, BorderLayout.NORTH);
		
		welcomeMessage = new Label("Welcome");	// TODO change "user" to user's name
		welcomeMessage.setAlignment(Label.CENTER);
		welcomeMessage.setFont(new Font("Avenir Next", Font.PLAIN, 14));
		welcomeMessage.setForeground(Color.DARK_GRAY);
		panelHeader.add(welcomeMessage);
		
		JPanel panelCenter = new JPanel();
		getContentPane().add(panelCenter, BorderLayout.CENTER);
	    this.pack();
	    JLabel label1 = new JLabel("");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setBounds(628, 28, 169, 125);
		try {
			ImageIcon Icon = new ImageIcon(
					new ImageIcon(WindowMenu.class.getClassLoader().getResource("Lock Icon.png")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)
				); 
			label1.setIcon(Icon);
		} catch (Exception e) {
			System.out.println("image failed to load.");
		}
		
		JLabel lblNewLabel = new JLabel("LockBox");
		lblNewLabel.setForeground(new Color(46, 139, 87));
		lblNewLabel.setFont(new Font("Avenir Next", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelCenter.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		panelCenter.add(separator);

		panelCenter.add(label1);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu_1 = new JMenu("File");
		mnNewMenu_1.setFont(new Font("Avenir Next", Font.PLAIN, 14));
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmMenuThing = new JMenuItem("Lock File");
		mntmMenuThing.addActionListener(encryptFile);
		mntmMenuThing.setFont(new Font("Avenir Next", Font.PLAIN, 14));
		mnNewMenu_1.add(mntmMenuThing);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Unlock File");
		mntmNewMenuItem_4.addActionListener(decryptFile);
		mntmNewMenuItem_4.setFont(new Font("Avenir Next", Font.PLAIN, 14));
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenu mnNewMenu_2 = new JMenu("Manage Account");
		mnNewMenu_2.setFont(new Font("Avenir Next", Font.PLAIN, 14));
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Security Preferences");
		mntmNewMenuItem_1.addActionListener(openSecurityPreferences);
		mntmNewMenuItem_1.setFont(new Font("Avenir Next", Font.PLAIN, 14));
		mnNewMenu_2.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Change Password");
		mntmNewMenuItem_2.addActionListener(resetPassword);
		mntmNewMenuItem_2.setFont(new Font("Avenir Next", Font.PLAIN, 14));
		mnNewMenu_2.add(mntmNewMenuItem_2);
		/* End initialization */
		
		
		
		
	}

}
