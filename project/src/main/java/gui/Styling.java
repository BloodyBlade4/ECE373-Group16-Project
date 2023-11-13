package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/*
 * Used for storing GUI styling formats. 
 */

public class Styling {
	public final static Font headerFont = new Font("Times New Roman", Font.BOLD, 20);
	
	
	
	
	public final static JButton navigationButton(String text) {
		JButton btn = new JButton("<HTML><U>" + text + "</U></HTML>");
		btn.setForeground(new Color(0, 128, 255));
		btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
		btn.setBorder(BorderFactory.createEmptyBorder());
		
		return btn;
	}
}
