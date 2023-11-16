package main;

import storage.AccountInfo;
import storage.FileHelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui.WindowAccountInfo;
import gui.WindowCreateAccount;
import gui.WindowForgotPassword;
import gui.WindowMenu;
import gui.WindowSignIn;
import serializer.Serializer;


/*	Program notes:
 * - descriptions are often left to the names of function, as comments inside show execution and the name is descriptive enough. 
 * 
 * 
 * 
 * TODO:
 * 1. Set up the account info window. This needs to be able to update user information, but NOT PASSWORD. currently, user password is used in encryption.
 * 2. Set up required formatting for different fields, and could use JPasswordField for password security. 
 * 
 * 
 * Future steps:
 * - continue to follow exceptions through and ensure their keeping.
 * - clean up gui using the styling file.
 * - clean coding using helper funcitons. 
 * - implement the file storage gui
 */

public class Controller {
	/* VARIABLES */
	
	
	private WindowSignIn signInWindow;
	private WindowCreateAccount createAccountWindow;
	private WindowAccountInfo accountInfoWindow;
	private WindowForgotPassword forgotPasswordWindow;
	private WindowMenu menuWindow;
	private WindowAccountInfo securityPreferencesWindow;
	
	private String username = null;
	private String password = null;
	private AccountInfo curAccount;
	
	public static void main(String[] args) {
		Controller cont = new Controller();
	}
	
	
	public Controller() {
		System.out.println("Heyo, the program is starting.");

		/*Action listeners for simple page changes*/
		ActionListener forgotPasswordStateChange = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Prompt user for username. 
				String name = null;
				name = JOptionPane.showInputDialog("Please enter username.");
				
				//if username isn't null, open forgot password. 
				if (name == null) {
					return;
				}
				if (name.isBlank()) {
					FileHelper.infoMessage("Info", "Please enter a valid username.");
					return;
				}
				
				username = name;
				ArrayList<String> questions = null;
				try {
					questions = Serializer.accountExistsGetQuestions(name);
				} catch (Exception e1) {
					FileHelper.infoMessage("Info", "Account not found. This could be due to incorrect username or corrupted data.");
					e1.printStackTrace();
					return;
				}
				if (questions == null || questions.isEmpty()) {
					System.out.println("Questions weren't found!");
					FileHelper.infoMessage("Info", "Account not found. This could be due to incorrect username or corrupted data.");
					return;
				}
				
				forgotPasswordWindow.setAllQuestions(questions);
				
				hideAllWindows();
				forgotPasswordWindow.setVisible(true);
			}
		};
		ActionListener createAccountStateChange = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllWindows();
				createAccountWindow.setVisible(true);
			}
		};
		ActionListener signInStateChange = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllWindows();
				signInWindow.setVisible(true);
			}
		};

		
		
		//Get the user information from textfields, search for that user in the database.
		ActionListener submitSignIn = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = signInWindow.getTextFieldUsername().getText();
				String password = signInWindow.getTextFieldPassword().getText();
				
				//Verify the information.
				AccountInfo account = null;
				try {
					account = Serializer.logIn(username, password);
				} catch (Exception e1) {
					FileHelper.errorMessage("Error", "There has been an issue compairing your information with the information on this device."+
							"\nPlease try again. \n" + e1);
					return;
				}
				
				//change state or warn user. 
				if (account == null) {
					//Prompt the user that the information is incorrect.
					FileHelper.infoMessage("Info", "Account information is incorrect.");
					return;
				}
				curAccount = account;
				
				hideAllWindows();
				menuWindow.setVisible(true);
			}
		};
		
		//submits username and password for a new account, passing onto the accountinfo window.
		ActionListener submitCreateAccount = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = createAccountWindow.getTextFieldUsername().getText();
				password = createAccountWindow.getTextFieldPassword().getText();
				
				//TODO: Verify the information. formatting, etc. 
				if (!FileHelper.passwordValid(password))
					return;
				
				//change state to submitAccountInfo. 
				hideAllWindows();
				accountInfoWindow.setVisible(true);
			}
		};
		//Gets the final questions needed for a new account, then adds the user to the database. 
		ActionListener submitAccountInfo = new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				//TODO: Verify the information. formatting, etc.
				
				
				String secCode = null;
				try {
					secCode = Serializer.generateAccoutSerializerString();
				} catch (Exception e1) {
					FileHelper.errorMessage("Error", "There has been an issue with serialization.\n" +
							"Our appologies, this fault is on our end. \n" +
							"Please try again. " + e1);
					e1.printStackTrace();
				}
				//create new account object
				curAccount = new AccountInfo(username, password,
						accountInfoWindow.getTextFieldSecQOne().getText(), accountInfoWindow.getTextFieldSecAOne().getText(),
						accountInfoWindow.getTextFieldSecQTwo().getText(), accountInfoWindow.getTextFieldSecATwo().getText(),
						accountInfoWindow.getTextFieldSecQThree().getText(), accountInfoWindow.getTextFieldSecAThree().getText(),
						accountInfoWindow.getHomeDir(), secCode, secCode);

				//write information to storage
				try {
					Serializer.addAccount(curAccount);
				} catch (Exception e1) {
					FileHelper.errorMessage("Error", "Not able to add your account. Please try again. \n " + e1);
					e1.printStackTrace();
					return;
				}
				
				hideAllWindows();
				menuWindow.setVisible(true);
			}
		};
		
		//grabs the info from the gui and checks the database for the correct user, and checks that the information is correct. 
		ActionListener submitForgotPassword = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Formatting, again. 
				
				//Get the security question answers from user. 
				String ansOne = forgotPasswordWindow.getAns1();
				String ansTwo = forgotPasswordWindow.getAns2();
				String ansThree = forgotPasswordWindow.getAns3();
				String pass = ansOne + ansTwo + ansThree;
				
				//decrypt the security code using answers as one long string. 
				AccountInfo acc = null;
				try {
					acc = Serializer.forgotPassword(username, pass);
				} catch (Exception e1) {
					// TODO ERROR inside forgot password. 
					FileHelper.errorMessage("Error", "Error finding account with this user information. Please try again. \n" +
							"If the problem persists, verify your username as well.");
					e1.printStackTrace();
					return;
				}
				if (acc == null) {
					FileHelper.infoMessage("Info", "Incorrect account information.");
					return;
				}
				//TODO: Do we ask the user to change passwords?
				
				
				curAccount = acc;
				hideAllWindows();
				menuWindow.setVisible(true);
			}
		};
		
		ActionListener encryptFile = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Path toEncryptPath = FileHelper.selectFile(curAccount.getHomeDirectory(), "Select a file to encrypt", 
						"Text files?" , "txt");
				if (toEncryptPath == null)
					return;
				Serializer.encryptFile(toEncryptPath, curAccount.getSecCodePass(), 
						false /*delete old*/, curAccount.getHomeDirectory());
			}
		};
		ActionListener decryptFile = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Path toDecryptPath = FileHelper.selectFile(curAccount.getHomeDirectory(), "Select a file to decrypt", 
						"Text files?" , "txt");
				if (toDecryptPath == null)
					return;
				Serializer.decryptFile(toDecryptPath, curAccount.getSecCodePass(), 
						false /*delete old*/, curAccount.getHomeDirectory());
			}
		};
		
		
		
		ActionListener openSecurityPreferences = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//set fields accordingly. 
				securityPreferencesWindow.setTextFieldSecQOne(curAccount.getSecQ1());
				securityPreferencesWindow.setTextFieldSecAOne(curAccount.getSecAns1());
				
				securityPreferencesWindow.setTextFieldSecQTwo(curAccount.getSecQ2());
				securityPreferencesWindow.setTextFieldSecATwo(curAccount.getSecAns2());
				
				securityPreferencesWindow.setTextFieldSecQThree(curAccount.getSecQ3());
				securityPreferencesWindow.setTextFieldSecAThree(curAccount.getSecAns3());
				
				securityPreferencesWindow.setHomeDir(curAccount.getHomeDirectory());
				
				//Open window to change information. 
				securityPreferencesWindow.setVisible(true);
				securityPreferencesWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				
			}
		};
		ActionListener submitUpdateAccountInfo = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: verify password
				
				//TODO: formatting checks?
				
				//grab information
				curAccount = new AccountInfo(curAccount.getName(), curAccount.getPassword(),
						securityPreferencesWindow.getTextFieldSecQOne().getText(), securityPreferencesWindow.getTextFieldSecAOne().getText(),
						securityPreferencesWindow.getTextFieldSecQTwo().getText(), securityPreferencesWindow.getTextFieldSecATwo().getText(),
						securityPreferencesWindow.getTextFieldSecQThree().getText(), securityPreferencesWindow.getTextFieldSecAThree().getText(),
						securityPreferencesWindow.getHomeDir(), curAccount.getSecCodePass(), curAccount.getSecCodeAns());
				
				try {
					Serializer.changeAccountInfo(curAccount.getName(), curAccount);
				} catch (Exception e1) {
					FileHelper.errorMessage("Error", "An error has occured while updating your account information. Please try again." + e1);
					e1.printStackTrace();
					return;
				}
				securityPreferencesWindow.setVisible(false);
			}
		};
		
		ActionListener resetPassword = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//verify password
				String oldPass = null;
				oldPass = JOptionPane.showInputDialog("Please enter current password.");
				
				//allow user to enter in new password. 
				String newPass = null;
				newPass = JOptionPane.showInputDialog("Please enter new password.");
				
				curAccount.setPassword(newPass);
				
				try {
					Serializer.changeAccountInfo(curAccount.getName(), curAccount);
				} catch (Exception e1) {
					FileHelper.errorMessage("Error", "An error has occured while updating your password. Please try again." + e1);
					e1.printStackTrace();
				}
			}
		};

		
		/* Windows */
		signInWindow = 
				new WindowSignIn(submitSignIn, forgotPasswordStateChange,createAccountStateChange);
		signInWindow.setVisible(true);
		forgotPasswordWindow = new WindowForgotPassword(submitForgotPassword, signInStateChange);
		createAccountWindow = new WindowCreateAccount(submitCreateAccount, 
				signInStateChange, forgotPasswordStateChange);
		accountInfoWindow = new WindowAccountInfo(submitAccountInfo);
		menuWindow = new WindowMenu(openSecurityPreferences, resetPassword, encryptFile, decryptFile);
		securityPreferencesWindow = new WindowAccountInfo(submitUpdateAccountInfo);
		
		
	}
	
	private void hideAllWindows() {
		if (this.signInWindow != null)
			this.signInWindow.setVisible(false);
		if(this.createAccountWindow != null)
			this.createAccountWindow.setVisible(false);
		if(this.accountInfoWindow != null)
			this.accountInfoWindow.setVisible(false);
		if(this.forgotPasswordWindow != null)
			this.forgotPasswordWindow.setVisible(false);
		if(this.securityPreferencesWindow != null)
			this.securityPreferencesWindow.setVisible(false);
	}
}
