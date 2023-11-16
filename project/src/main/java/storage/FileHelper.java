package storage;

import java.io.File;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileHelper {
	public static Path selectFile(String openingDirectory, String title, String extDetail, String extName) {
		File directory = (openingDirectory==null)? new File(System.getProperty("user.dir"))
												: new File(openingDirectory);
		JFileChooser chooser = new JFileChooser(directory);
		chooser.setDialogTitle(title);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter(extDetail, extName));
		chooser.setAcceptAllFileFilterUsed(true);
		

		int option = chooser.showOpenDialog(null);
		if(option == JFileChooser.APPROVE_OPTION) 
			return chooser.getSelectedFile().toPath();
		return null;
	}
	
	public static Boolean confirmationWindow(String message) {
		int dialogResult = JOptionPane.showConfirmDialog(null, message);
		return dialogResult == JOptionPane.YES_OPTION;
	}
	
	public static void errorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null,
			    "Error: " + message,
			    title,
			    JOptionPane.ERROR_MESSAGE);
	}
	public static void infoMessage(String title, String message) {
		JOptionPane.showMessageDialog(null,
			    "Error: " + message,
			    title,
			    JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static Boolean errorTryAgainMessage(String title, String message) {
		int dialogResult = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
		return dialogResult == JOptionPane.YES_OPTION;
	}
	
	public static Boolean passwordValid(String password) {
		//check for password length
		//check for a mix of uppercase, lowercase, digits, and special characters
		boolean validCheck = false;
		if (password.length () >= 8) {
			if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$") == true) {
				validCheck = true;
				JOptionPane.showMessageDialog(null, "Password is valid!");
			}
			else {
				JOptionPane.showInputDialog(null, "Password must have a mix of uppercase, lowercase, digits, and special characters", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			JOptionPane.showInputDialog(null, "Password must be at least 8 characters long", JOptionPane.ERROR_MESSAGE);
		}
		
		return validCheck;
	}
}
