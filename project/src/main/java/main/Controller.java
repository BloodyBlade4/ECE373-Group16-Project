package main;

import storage.AccountInfo;
import storage.FileHelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import gui.WindowAccountInfo;
import gui.WindowCreateAccount;
import gui.WindowForgotPassword;
import gui.WindowMenu;
import gui.WindowSignIn;
import serializer.Serializer;


/*
 * TODO:
 * **** DON'T encrypt the security questions, just the answers. 
 * **** ensure the account name is available. 
 * 1. Set up the account info window. This needs to be able to update user information, but NOT PASSWORD. currently, user password is used in encryption.
 * 2. Set up required formatting for different fields, and could use JPasswordField for password security. 
 * 3. Figure out file storage. Ideas:
 * 		-  The user will have selected a home directory to store encrypted files in
 * 		- Using the file navigation in Swing is fairly easy. Users can select files like this. 
 * 		- prompt users if they want to delete the file after encryption/decryption. 
 * 4. ForgotPassword window!
 * 6. implement the file storage gui
 * 
 * 
 * Future steps:
 * - follow exceptions through and ensure their keeping.
 * - clean up gui using the styling file.
 * - clean coding using helper funcitons. 
 */

public class Controller {
	/* VARIABLES */
	
	
	private WindowSignIn signInWindow;
	private WindowCreateAccount createAccountWindow;
	private WindowAccountInfo accountInfoWindow;
	private WindowForgotPassword forgotPasswordWindow;
	private WindowMenu menuWindow;
	
	private String username = null;
	private String password = null;
	private AccountInfo curAccount;
	
	public static void main(String[] args) {
		Controller cont = new Controller();
	}
	
	
	public Controller() {
		System.out.println("Heyo, the program is starting.");

		/*Action listeners*/
		ActionListener forgotPasswordStateChange = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		ActionListener openAccountInfo = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountInfoWindow.setVisible(true);
			}
		};
		
		
		
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
					//TODO: Prompt the user that the information is incorrect.
					System.out.println("Incorrect user information.");
					return;
				}
				curAccount = account;
				
				hideAllWindows();
				menuWindow.setVisible(true);
			}
		};
		ActionListener submitCreateAccount = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("submit create Account button hit.");
				username = createAccountWindow.getTextFieldUsername().getText();
				password = createAccountWindow.getTextFieldPassword().getText();
				System.out.println("The username and password are: " + username + ", " + password);
				
				//TODO: Verify the information. formatting, etc. 
				
				//change state to submitAccountInfo. 
				hideAllWindows();
				accountInfoWindow.setVisible(true);
			}
		};
		
		ActionListener submitForgotPassword = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Formatting, again. 
				
				//TODO: Account username is needed too!!!!... right? 
				String username = "";
				
				//TODO: Get the security question answers from user. 
				String ansOne = "";
				String ansTwo = "";
				String ansThree ="";
				String pass = ansOne + ansTwo + ansThree;
				
				//decrypt the security code using answers as one long string. 
				AccountInfo acc = null;
				try {
					acc = Serializer.forgotPassword(username, pass);
				} catch (Exception e1) {
					// TODO ERROR inside forgot password. 
					e1.printStackTrace();
				}
				if (acc == null) {
					//TODO: Prompt the user that the info was incorrect. 
				}
				//TODO: Do we as the user to change passwords?
				
				
				curAccount = acc;
				hideAllWindows();
				menuWindow.setVisible(true);
			}
		};
		
		
		ActionListener submitAccountInfo = new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				//TODO: Verify the information. formatting, etc.
				String secCode = null;
				try {
					secCode = Serializer.generateAccoutSerializerString();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//create new account object
				curAccount = new AccountInfo(username, password,
						accountInfoWindow.getTextFieldSecQOne().getText(), accountInfoWindow.getTextFieldSecAOne().getText(),
						accountInfoWindow.getTextFieldSecQOne().getText(), accountInfoWindow.getTextFieldSecATwo().getText(),
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
		
		
		
		/* Windows */
		signInWindow = 
				new WindowSignIn(submitSignIn, forgotPasswordStateChange,createAccountStateChange);
		signInWindow.setVisible(true);
		//TODO: Forgotpassword window!! 
		//forgotPasswordWindow = new forgotPasswordWindow();
		createAccountWindow = new WindowCreateAccount(submitCreateAccount, 
				signInStateChange, forgotPasswordStateChange);
		accountInfoWindow = new WindowAccountInfo(submitAccountInfo);
		menuWindow = new WindowMenu(openAccountInfo, encryptFile, decryptFile);
		
		
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
		
	}
}
