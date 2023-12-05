package storage;

/*
 * Used for different file and gui helping functions. 
 */

import java.io.File;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileHelper {
	//file selector gui
	public static Path selectFile(String openingDirectory, String title) {
		File directory = (openingDirectory==null)? new File(System.getProperty("user.dir"))
												: new File(openingDirectory);
		JFileChooser chooser = new JFileChooser(directory);
		chooser.setDialogTitle(title);
		chooser.setAcceptAllFileFilterUsed(true);
		

		int option = chooser.showOpenDialog(null);
		if(option == JFileChooser.APPROVE_OPTION) 
			return chooser.getSelectedFile().toPath();
		return null;
	}
	
	//GUI's used to prompt the user with information.
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

	//Checks password against certain criteria to see if it meets requirements. 
	public static Boolean passwordValid(String password) {
		//check for password length
		boolean validCheck = false;
		if (password != null && password.length () >= 8) {
			validCheck = true;
		}
		else {
			infoMessage("Info", "Password must be at least 8 characters long");
		}
		
		return validCheck;
	}
}
