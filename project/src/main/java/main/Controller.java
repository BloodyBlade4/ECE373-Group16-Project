package main;

import storage.AccountInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.WindowAccountInfo;
import gui.WindowCreateAccount;
import gui.WindowForgotPassword;
import gui.WindowMenu;
import gui.WindowSignIn;
import serializer.Serializer;


/*
 * TODO:
 * 1. Set up the account info window. This needs to be able to update user information, but NOT PASSWORD. currently, user password is used in encryption.
 * 2. Set up required formatting for different fields, and could use JPasswordField for password security. 
 * 3. Figure out file storage. Ideas:
 * 		-  The user will have selected a home directory to store encrypted files in
 * 		- Using the file navigation in Swing is fairly easy. Users can select files like this. 
 * 		- prompt users if they want to delete the file after encryption/decryption. 
 * 4. include all fields when creating an account. 
 * 5. Figure out how to encrypt/decrypt files. 
 * 6. implement the file storage gui and application. 
 * 
 * 
 * Future steps:
 * - follow exceptions through and ensure their keeping.
 * - clean up gui using the styling file.
 * - clean coding using helper funcitons. 
 */

public class Controller {
	/* VARIABLES */
	public enum State {
		LogIn, SignIn, AccountCreation, MainMenu, 
	}
	private Controller.State curState = State.LogIn;
	
	
	private WindowSignIn signInWindow;
	private WindowCreateAccount createAccountWindow;
	private WindowAccountInfo accountInfoWindow;
	private WindowForgotPassword forgotPasswordWindow;
	private WindowMenu menuWindow;
	
	private String username = null;
	private String password = null;
	
	
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
				System.out.println("submit sign in button hit.");
				String username = signInWindow.getTextFieldUsername().getText();
				String password = signInWindow.getTextFieldPassword().getText();
				System.out.println("The username and password are: " + username + ", " + password);
				
				//Verify the information.
				AccountInfo account = null;
				try {
					account = Serializer.logIn(username, password);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//change state or warn user. 
				if (account == null) {
					//TODO: Prompt the user that the information is incorrect.
					System.out.println("Incorrect user information.");
					return;
				}
				System.out.println("Success!");
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
		
		ActionListener submitAccountInfo = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("submit account info button hit.");
				String secQOne = accountInfoWindow.getTextFieldSecQOne().getText();
				String secAOne = accountInfoWindow.getTextFieldSecAOne().getText();
				System.out.println(secQOne + ". Answer: " + secAOne);
				
				//TODO: Verify the information. formatting, etc.
				
				//create new account object
				AccountInfo newAccount = new AccountInfo(username, password,
						secQOne, secAOne);
				
				//write information to storage
				try {
					Serializer.addAccount(newAccount);
				} catch (Exception e1) {
					System.out.println(e1);
				}
				
				//TODO: update any info for the user?
				hideAllWindows();
				menuWindow.setVisible(true);
			}
		};
		
		
		signInWindow = 
				new WindowSignIn(submitSignIn, forgotPasswordStateChange,createAccountStateChange);
		signInWindow.setVisible(true);
		createAccountWindow = new WindowCreateAccount(submitCreateAccount, 
				signInStateChange, forgotPasswordStateChange);
		accountInfoWindow = new WindowAccountInfo(submitAccountInfo);
		menuWindow = new WindowMenu(openAccountInfo);
		
		
	}
	
	private void hideAllWindows() {
		if (this.signInWindow != null)
			this.signInWindow.setVisible(false);
		if(this.createAccountWindow != null)
			this.createAccountWindow.setVisible(false);
		if(this.accountInfoWindow != null)
			this.createAccountWindow.setVisible(false);
		if(this.forgotPasswordWindow != null)
			this.forgotPasswordWindow.setVisible(false);
		
	}
	
	
	
	
	/* Getters and Setters */
	//TODO: ADD VERIFICATION FOR EACH STATE!
	public void setState(Controller.State s) {
		this.curState = s;
	}
	public Controller.State getState() {
		return this.curState;
	}
}
