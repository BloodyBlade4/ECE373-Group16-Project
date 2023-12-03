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
 * 1. Test current encryption. The process should be:
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
 * 2. Optional: Set up required formatting for different fields, and could use JPasswordField for password security. 

 * 4. Optional: OOP!!! Separate fields and utilize Object Oriented Programming to declutter and organize. 

 * 7. Exception checking track. Ensure user is warned of errors. 
 * 8. Comment on every function. 
 * 
 * TODO, From project review:
 * 1. XXX DONE XXX #6 in the above. Expand file encryption. 
 * 2. Delete the Account class
 * 3. Comments on all classes, methods
 * 4. XXX DONE XXXX Trying to reset the password, but not actually resetting it, causes an error. 
 * 5. XXX DONE XXX Manage Account File Directory. 
 * 6. XXX DONE XXX Manage Account Security questions. 
 * 
 * Notes for presentation/commenting, from review: 
 * 1. Highlight the usecase of security code generation, as Discussed in #1 above. 
 * 		This is the meat of the program, the strategy.  
 * 2. Remove "Account" from classes and add in AccountInfo, an object for the account. (Both in a separate place, and used differently) 
 * 3. Update the class diagram, show associations and teh methods present in each class. 
 * 4. Exceptions in use cases (Reviewer 3)? 
 * 5. UML and interaction model. 
 * 6. Update instructions in the read me to match the change file directory (Code also needs work). 
 * 
 * 
 * TODO GUI:
 * 1. Fix the fields, currently you have to delete the old text inside. 
 * 2. Fix/test the hide password option, there was a bug that screwed up placement/submission. 
 * 3. center and beautify. 
 * 4. What are we doing with the homescreen? A nice file browser would be nice, but not really necessary. 
 * 		Approach this last. Also, there's a random submit button. XD
 * 
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
				
				Path toEncryptPath = FileHelper.selectFile(curAccount.getHomeDirectory(), "Select a file to encrypt");
				if (toEncryptPath == null)
					return;
				Serializer.encryptFile(toEncryptPath, curAccount.getSecCodePass(), 
						false /*delete old*/, curAccount.getHomeDirectory());
			}
		};
		ActionListener decryptFile = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Path toDecryptPath = FileHelper.selectFile(curAccount.getHomeDirectory(), "Select a file to decrypt");
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
				//check against designated password requirements
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
