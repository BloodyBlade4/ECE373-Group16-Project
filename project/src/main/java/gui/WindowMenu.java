package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class WindowMenu extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WindowMenu() {
		/* INITIALIZATION */
		super("Main");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 400);
		this.setLocationRelativeTo(null); //Center of screen.
		getContentPane().setLayout(new BorderLayout(0, 0));
		/* End initialization */
	}

}
