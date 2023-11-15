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
	
	public static Boolean errorTryAgainMessage(String title, String message) {
		int dialogResult = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
		return dialogResult == JOptionPane.YES_OPTION;
	}
}
