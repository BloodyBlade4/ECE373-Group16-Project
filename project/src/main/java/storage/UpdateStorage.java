package storage;

/*
 * Used to interact with encrypted files/user information. A kind of service for the inbetween stages
 */

public class UpdateStorage {
	
	//Finds the account upon user signing in
	public AccountInfo retrieveAccount(String name, String password) {
		//Grab the list of account info's from storage. 
		
		//Use the serializer to usernames one by one. 
		
		//When the correct username is found, compare passwords. 
		
		//Either fill an AccountInfo object and return it, or return a way to inform the user information was incorrect. 
		
		return new AccountInfo();
	}
	
	
}
