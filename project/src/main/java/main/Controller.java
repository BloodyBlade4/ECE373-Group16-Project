package main;

/*
 * Main controller for the lockbox application. 
 * 
 * Initializes all needed GUI windows, passing action listeners to them --waiting for and reacting to events accordingly. 
 * Communicates with the Serializer class to access, verify, and store information. 
 * 
 * EXCEPTIONS:
 * 	Almost all exceptions are bubbled up into this class to prompt the user and/or handle the exception appropriately. 
 * 
 * COMMENTING: 
 * I truly believe there to be painful, excessive commenting in these files and have done so only for the sake of this course/you. 
 * the point of function/method/variable names is not just to store variables, but to indicate their intention and use. 
 * I still could not bring myself to comment on things like "hideAllWindows"
 * 
 * ENCRYPTION:
 * 		-User creates a new account, 
 * 			-Serialized info: Password, password security code, security answers security code, and all 3 security answers.
 * 			-unserialized info: Account user name, all 3 security questions
 * 			-The two security code answers are the same but one is serialized with the password and the other the security questions. This allows for account recovery. 
 * 		- Log in
 * 			- Serializer class searches for account name, then deserializes the security code and uses that deserialized code to deserialize and compare stored password. 
 * 		- Forgot password
 * 			- Find the security questions for the given account name. 
 * 			- combine all the security answers into one string and try to deserialize the security code. use that deserialized security code to check if the security answers match. 
 * 
 * INTENTIAL "MISTAKES":
 * - Creating an account with "Username" and "Password" as the username and password, or default on the security questions. Perfectly acceptable, saves user time on a secure PC.
 * 		- My father once had a literal, paid hacker try to access his computer, in-person with a decryption disk, but all the hackers methods couldn't solve his password of "" --of nothing.
 * 	- 
 */


import storage.AccountInfo;
import storage.FileHelper;

import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui.Styling;
import gui.WindowAccountInfo;
import gui.WindowCreateAccount;
import gui.WindowForgotPassword;
import gui.WindowMenu;
import gui.WindowSignIn;
import serializer.Serializer;

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
		try {
			SystemTray mainTray = SystemTray.getSystemTray();
			java.awt.TrayIcon trayIconImage = new java.awt.TrayIcon(Styling.LOGO_IMAGE, "tray icon");
			mainTray.add(trayIconImage);
		} catch (Exception e) {
			System.out.println("Unable to update tray icon");
		}
		
		Controller cont = new Controller();

	}
	
	
	public Controller() {
		/* WINDOW CHANGES */
		//prompt the user for a username, check if it exists. 
		//If it does, load the security questions for the user to answer. 
		ActionListener forgotPasswordStateChange = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Prompt user for username. 
				String name = null;
				name = JOptionPane.showInputDialog("Please enter username.");
				
				//if username is null, exit. 
				if (name == null) {
					return;
				}
				if (name.isBlank()) {
					FileHelper.infoMessage("Info", "Please enter a valid username.");
					return;
				}
				
				
				username = name;
				ArrayList<String> questions = null;
				//Get the accountInfo from database. 
				try {
					questions = Serializer.accountExistsGetQuestions(name);
				} catch (Exception e1) {
					FileHelper.infoMessage("Info", "Account not found. This could be due to incorrect username or corrupted data.");
					e1.printStackTrace();
					return;
				}
				//Account not found? exit. 
				if (questions == null || questions.isEmpty()) {
					FileHelper.infoMessage("Info", "Account not found. This could be due to incorrect username or corrupted data.");
					return;
				}
				
				//Fill and open ForgotPasswordWindow, hide all others. 
				forgotPasswordWindow.setAllQuestions(questions);
				hideAllWindows();
				forgotPasswordWindow.setVisible(true);
			}
		};
		
		//Change to WindowCreateAccount. 
		ActionListener createAccountStateChange = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllWindows();
				createAccountWindow.setVisible(true);
			}
		};
		//Change to WindowSignIn. 
		ActionListener signInStateChange = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAllWindows();
				signInWindow.setVisible(true);
			}
		};

		
		
		/* SUBMIT FORMS */
		//Get the user information from textfields, search for that user in the database.
		//If account found, log in and go to the Main Menu Window. 
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
				
				//set account, load WindowMenu. 
				curAccount = account;
				setWelcomeMessage();
				hideAllWindows();
				menuWindow.setVisible(true);
			}
		};
		
		//submits username and password for a new account, passing onto the accountinfo window if valid.
		ActionListener submitCreateAccount = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = createAccountWindow.getTextFieldUsername().getText();
				password = createAccountWindow.getTextFieldPassword().getText();
				
				//check if info is valid. 
				if (!FileHelper.passwordValid(password))
					return;
				
				//Check that the username is not in use. 
				try {
					if (Serializer.accountExists(username))  {
						FileHelper.infoMessage("Cannot create Account", "The username entered already exists. \nPlease use a different username, or try logging in.");
						username = null;
						password = null;
						return;
					}
				} catch (Exception e2) {
					FileHelper.errorMessage("Error searching database.", "An error occured while checking to see if given account name is available.\nPlease try again.\n" + e2);
					username = null;
					password = null;
					return;
				}
				
				//change state to submitAccountInfo. 
				hideAllWindows();
				accountInfoWindow.setVisible(true);
			}
		};
		//Gets the final questions needed for a new account, then adds the user to the database. 
		ActionListener submitAccountInfo = new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				//Check that no field is empty/null
				if (accountInfoWindow.getTextFieldSecQOne().getText().isBlank() || accountInfoWindow.getTextFieldSecAOne().getText().isBlank() ||
						accountInfoWindow.getTextFieldSecQTwo().getText().isBlank() || accountInfoWindow.getTextFieldSecATwo().getText().isBlank() ||
						accountInfoWindow.getTextFieldSecQThree().getText().isBlank() || accountInfoWindow.getTextFieldSecAThree().getText().isBlank() ||
						accountInfoWindow.getHomeDir().isBlank() || accountInfoWindow.getHomeDir().equals("No directory selected")) {
					FileHelper.infoMessage("Invalid field(s)", "One or more field is blank or only contains white space.\n" +
						"The Security questions are just as important as a password, please fill out the fields carefully.");
					return;
				}
				
				//generate a security code for the account. 
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
				
				//Open Menu Window. 
				setWelcomeMessage();
				hideAllWindows();
				menuWindow.setVisible(true);
			}
		};
		
		//grabs the security answers from the window and passes them to the serializer.
		//The serializer finds the account and tries to deserialize the account "securityAnswers-SecurityCode", secCode serialized with the sequirity answers.
		//The deserialized secCode is then used to compare data. If correct, change password and move into the main menu.  
		ActionListener submitForgotPassword = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					FileHelper.errorMessage("Error", "Error finding account with this user information. Please try again. \n" +
							"If the problem persists, verify your username as well.");
					e1.printStackTrace();
					return;
				}
				if (acc == null) {
					FileHelper.infoMessage("Info", "Incorrect account information.");
					return;
				}
				//Prompt user for a new password
				String newPassword = null;
				newPassword = JOptionPane.showInputDialog("Please enter a new Password.");
				
				//check that the new Password is valid
				if (newPassword == null || !FileHelper.passwordValid(newPassword))
					return;
				if (newPassword.isBlank()) {
					FileHelper.infoMessage("Info", "Please enter a valid password.");
					return;
				}

				
				//update account password. 
				acc.setPassword(newPassword);
				try {
					Serializer.changeAccountInfo(acc.getName(), acc);
				} catch (Exception e1) {
					FileHelper.errorMessage("Error", "There has been an error while trying to update your account password. \nPlease try again. " + e1);
					return;
				}
				
				//Change to Menu Window. 
				curAccount = acc;
				setWelcomeMessage();
				hideAllWindows();
				menuWindow.setVisible(true);
			}
		};
		
		
		
		
		/* MAIN MENU ACTION LISTENERS */
		//Opens gui for user to select a file to encrypt. Sends the file location off to the Serializer encryptFile function.
		ActionListener encryptFile = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Path toEncryptPath = FileHelper.selectFile(curAccount.getHomeDirectory(), "Select a file to encrypt");
				if (toEncryptPath == null)
					return;
				Serializer.encryptFile(toEncryptPath, curAccount.getSecCodePass(), 
						false /*delete old*/, curAccount.getHomeDirectory());
			}
		};
		//Opens gui for user to select a file to decrypt. Sends the file locaiton off to the Serializer decryptFile function.
		ActionListener decryptFile = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Path toDecryptPath = FileHelper.selectFile(curAccount.getHomeDirectory(), "Select a file to decrypt");
				if (toDecryptPath == null)
					return;
				Serializer.decryptFile(toDecryptPath, curAccount.getSecCodePass(), 
						false /*delete old*/, curAccount.getHomeDirectory());
			}
		};
		
		//Opens the security preferences window, to change security questions or home directory. 
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
				//verify password
				String pass = null;
				pass = JOptionPane.showInputDialog("Please enter current password.");
				if (pass == null)
					return;
				//Check against current password. 
				if (curAccount == null || !curAccount.getPassword().equals(pass)) {
					FileHelper.infoMessage("Incorrect Password", "The password you entered does not match this account.");
					return;
				}
				
				//Check that no field is empty/null
				if (accountInfoWindow.getTextFieldSecQOne().getText().isBlank() || securityPreferencesWindow.getTextFieldSecAOne().getText().isBlank() ||
						securityPreferencesWindow.getTextFieldSecQTwo().getText().isBlank() || securityPreferencesWindow.getTextFieldSecATwo().getText().isBlank() ||
						securityPreferencesWindow.getTextFieldSecQThree().getText().isBlank() || securityPreferencesWindow.getTextFieldSecAThree().getText().isBlank() ||
						securityPreferencesWindow.getHomeDir().isBlank()) {
					FileHelper.infoMessage("Invalid field(s)", "One or more field is blank or only contains white space.\n" +
						"The Security questions are just as important as a password, please fill out the fields carefully.");
					return;
				}
				
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
				if (oldPass == null)
					return;
				
				//Check against current password. 
				if (curAccount == null || !curAccount.getPassword().equals(oldPass)) {
					FileHelper.infoMessage("Incorrect Password", "The password you entered does not match this account.");
					return;
				}
				
				//allow user to enter in new password. 
				String newPass = null;
				newPass = JOptionPane.showInputDialog("Please enter new password.");
				if (newPass == null) 
					return;
				//check against password requirements
				if (!FileHelper.passwordValid(newPass)) 
					return;
				
				//Update info. 
				curAccount.setPassword(newPass);
				try {
					Serializer.changeAccountInfo(curAccount.getName(), curAccount);
				} catch (Exception e1) {
					FileHelper.errorMessage("Error", "An error has occured while updating your password. Please try again." + e1);
					e1.printStackTrace();
				}
			}
		};

		
		/* WINDOWS */
		signInWindow = 
				new WindowSignIn(submitSignIn, forgotPasswordStateChange,createAccountStateChange);
		signInWindow.setVisible(true);
		forgotPasswordWindow = new WindowForgotPassword(submitForgotPassword, signInStateChange);
		createAccountWindow = new WindowCreateAccount(submitCreateAccount, 
				signInStateChange, forgotPasswordStateChange);
		accountInfoWindow = new WindowAccountInfo("Create Account", submitAccountInfo);
		menuWindow = new WindowMenu(openSecurityPreferences, resetPassword, encryptFile, decryptFile);
		securityPreferencesWindow = new WindowAccountInfo("Security Preferences", submitUpdateAccountInfo);
	}
	
	private void setWelcomeMessage() {
		if (curAccount != null && !curAccount.getName().isEmpty() && menuWindow.welcomeMessage != null)
			menuWindow.welcomeMessage.setText("Welcome, " + curAccount.getName() + ".");
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
