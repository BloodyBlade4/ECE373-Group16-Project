package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

/*
 * Used for storing GUI styling formats. 
 */

public class Styling {
	public final static Font headerFont = new Font("Times New Roman", Font.BOLD, 20);
	
	public static void addPlaceholderStyle(JTextField textField) {
        Font font = textField.getFont() ;
        font = font.deriveFont(Font.ITALIC) ;
        textField.setFont(font) ;
        textField.setForeground(Color.gray);

    }

    public static void removePlaceholderStyle(JTextField textField) {
        Font font = textField.getFont() ;
        font = font.deriveFont(Font.PLAIN) ;
        textField.setFont(font) ;
        textField.setForeground(Color.black);
    }
	
	
	public final static JButton navigationButton(String text) {
		JButton btn = new JButton("<HTML><U>" + text + "</U></HTML>");
		btn.setForeground(new Color(0, 128, 255));
		btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
		btn.setBorder(BorderFactory.createEmptyBorder());
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}

	
	public final static JPanel rightSideOfForms(ArrayList<JButton> btnList) {

		JPanel GreyPanel = new JPanel();
		JPanel footer = new JPanel();
		JLabel Logo = new JLabel("");
		JLabel lblNewLabel = new JLabel("LockBox");
		
		// Grey Panel
		GreyPanel.setBackground(Color.WHITE);
		GreyPanel.setBounds(300, 0, 600, 500);
		
		// Footer panel
		footer.setBackground(Color.WHITE);
		footer.setSize(600, 50);
		footer.setPreferredSize(new java.awt.Dimension(600, 50));
		SpringLayout sl_GreyPanel = new SpringLayout();
		sl_GreyPanel.putConstraint(SpringLayout.NORTH, footer, 480, SpringLayout.NORTH, GreyPanel);
		sl_GreyPanel.putConstraint(SpringLayout.WEST, footer, 0, SpringLayout.WEST, GreyPanel);
		sl_GreyPanel.putConstraint(SpringLayout.SOUTH, footer, 50, SpringLayout.SOUTH, GreyPanel);
		sl_GreyPanel.putConstraint(SpringLayout.EAST, footer, 0, SpringLayout.EAST, GreyPanel);
		GreyPanel.add(footer);
				
		
		// ICON //
		sl_GreyPanel.putConstraint(SpringLayout.NORTH, Logo, 100, SpringLayout.NORTH, GreyPanel);
		sl_GreyPanel.putConstraint(SpringLayout.WEST, Logo, 200, SpringLayout.WEST, GreyPanel);
		GreyPanel.setLayout(sl_GreyPanel);
		GreyPanel.add(Logo);
		Logo.setHorizontalAlignment(SwingConstants.CENTER);
		
		// APP NAME //
		sl_GreyPanel.putConstraint(SpringLayout.WEST, lblNewLabel, 100, SpringLayout.WEST, GreyPanel);
		sl_GreyPanel.putConstraint(SpringLayout.EAST, lblNewLabel, -100, SpringLayout.EAST, GreyPanel);
		sl_GreyPanel.putConstraint(SpringLayout.SOUTH, lblNewLabel, -100, SpringLayout.SOUTH, GreyPanel);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		GreyPanel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(46, 139, 87));
		lblNewLabel.setFont(new Font("Avenir Next", Font.PLAIN, 60));
		
		for (JButton btn : btnList) {
			footer.add(btn);
		}
		
		try {
			ImageIcon Icon = new ImageIcon(
					new ImageIcon(WindowMenu.class.getClassLoader().getResource("Lock Icon.png")).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)
				); 
			Logo.setIcon(Icon);	
		} catch (Exception e) {
			System.out.println("Unable to load image.");
		}
		//MainPanel.add(GreenPanel);
		return GreyPanel;
	}
	
	public final static JPanel createGradientPanel() {
		return new GradientPanel();
	}
	
	
}

//Gradient
class GradientPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();
        Color color1 = new Color(102, 205, 154);
        Color color2 = new Color(0, 102, 51);
        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
