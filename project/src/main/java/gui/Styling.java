package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

/*
 * Used for storing GUI styling formats and objects for quick duplication. 
 */

public class Styling {
	public final static Font headerFont = new Font("Times New Roman", Font.BOLD, 20);
	
	//Add the styling used in the placeholder of text fields. 
	public static void addPlaceholderStyle(JTextField textField) {
        Font font = textField.getFont() ;
        font = font.deriveFont(Font.ITALIC) ;
        textField.setFont(font) ;
        textField.setForeground(Color.gray);

    }
	
	//Remove the styling used in the placeholder of text fields. 
    public static void removePlaceholderStyle(JTextField textField) {
        Font font = textField.getFont() ;
        font = font.deriveFont(Font.PLAIN) ;
        textField.setFont(font) ;
        textField.setForeground(Color.black);
    }
	
	//Navigation button. Styling. Accepts a string for what the button is to say. 
	public final static JButton navigationButton(String text) {
		JButton btn = new JButton("<HTML><U>" + text + "</U></HTML>");
		btn.setForeground(new Color(0, 128, 255));
		btn.setFont(new Font("Tahoma", Font.ITALIC, 11));
		btn.setBorder(BorderFactory.createEmptyBorder());
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}
	
	public final static JTextField basicTextField(String text) {
		return new BasicTextField(text);
	}

	//returns the decorative panel used on different welcome screens.
	//accepts a list of navigation buttons to be added to the footer. 
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
		
		//For every btn given add to the footer. 
		//These are the navigation buttons. 
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
		return GreyPanel;
	}
	
	public final static JPanel createGradientPanel() {
		return new GradientPanel();
	}
	
	
}

//Gradient background for the Sign in, ForgotPassword, create account, and account info windows. 
class GradientPanel extends JPanel {
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

//Generates a text field used for many different formats, such as username or security questions. 
//Accepts text for the default text in the field, which will disappear when the text field is selected. 
class BasicTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	private JTextField curTextField;
	public BasicTextField(final String text) {
		//reference
		curTextField = this;
		//styling
		this.setHorizontalAlignment(JTextField.CENTER);
		this.setForeground(Color.GRAY);
		this.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		this.setText(text);
		this.setColumns(10);
		
		this.addFocusListener(new FocusAdapter() {
			// Focus Gained - Hide the background text if it's equal to the default text.
			@Override
			public void focusGained(FocusEvent e) {
					if(curTextField.getText().equals(text)) {
						curTextField.setText(null);
						curTextField.requestFocus();
						Styling.removePlaceholderStyle(curTextField);
					}
			}
			
			// Focus Lost - replace the background text if the field is empty. 
			@Override
			public void focusLost(FocusEvent e) {
				if(curTextField.getText().length() == 0) {
					Styling.addPlaceholderStyle(curTextField);
					curTextField.setText(text);
				}
			}
		});
	}
}
