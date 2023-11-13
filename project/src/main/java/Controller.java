
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.WindowAccountInfo;
import gui.WindowCreateAccount;
import gui.WindowForgotPassword;
import gui.WindowSignIn;

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
		
		
		
		
		ActionListener submitSignIn = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("submit sign in button hit.");
				String username = signInWindow.getTextFieldUsername().getText();
				String password = signInWindow.getTextFieldPassword().getText();
				System.out.println("The username and password are: " + username + ", " + password);
				
				//Verify the information.
				
				//change state or warn user. 
			}
		};
		ActionListener submitCreateAccount = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("submit create Account button hit.");
				String username = createAccountWindow.getTextFieldUsername().getText();
				String password = createAccountWindow.getTextFieldPassword().getText();
				System.out.println("The username and password are: " + username + ", " + password);
				
				//verify that the account is available
				
				//change state to submitAccountInfo. 
				hideAllWindows();
				accountInfoWindow.setVisible(true);
			}
		};
		
		ActionListener submitAccountInfo = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("submit account info button hit.");
				String seqQOne = accountInfoWindow.getTextFieldSecQOne().getText();
				String seqAOne = accountInfoWindow.getTextFieldSecAOne().getText();
				System.out.println(seqQOne + ". Answer: " + seqAOne);
				
				//Verify the information.
				
				//write information to storage
				
				//update state to main menu/signed in. 
			}
		};
		
		
		signInWindow = 
				new WindowSignIn(submitSignIn, forgotPasswordStateChange,createAccountStateChange);
		signInWindow.setVisible(true);
		createAccountWindow = new WindowCreateAccount(submitCreateAccount, 
				signInStateChange, forgotPasswordStateChange);
		accountInfoWindow = new WindowAccountInfo(submitAccountInfo);
		
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
