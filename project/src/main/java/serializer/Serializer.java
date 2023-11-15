package serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import storage.AccountInfo;
import storage.UpdateStorage;

/*
 * Files and information is serialized to protect/lock it from being interacted with by outside users or anything else 
 * 
 * We will be using Password-Based Encryption (PBE) 
 * For additional information and implementation, refer to Jasypt's official documentation: http://www.jasypt.org/general-usage.html
 */

public class Serializer {
	
	//takes the location of the file to encrypt, and the users password.
	public void encryptFile(String loc, String password, Boolean deleteOld, String homeDir) {
		encryptFile(Paths.get(loc), password, deleteOld, homeDir);
	}
	public static void encryptFile(Path p, String password, Boolean deleteOld, String homeDir) {
		//create encryption object. 
		StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();
		encryptor.setPassword(password);
		
		
		byte[] data = null;
		try {
			data = Files.readAllBytes(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//create new file for the serialized information
		//TODO!!!! Home directory isn't being saved to the userInfo.. 
		//encryption, however, seems to be working. 
		String outputFile = homeDir + "/testing.txt";
		System.out.println("Writing file now with " + encryptor.encrypt(data));
		System.out.println("writing into " + outputFile);
		try {
			Files.write(Paths.get(outputFile), encryptor.encrypt(data));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//read through the old file and encrypt the data into the new file
			//uses the method encryptor.encrypt(Text). 
		
		//Do we delete the old file?
		
	}
	
	//takes the location of the file to decrypt, and the users password. 
	public void decryptFile(String loc, String password) {
		decryptFile(Paths.get(loc), password);
	}
	public void decryptFile(Path loc, String password) {
		//create encryption object. 
		StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();
		encryptor.setPassword(password);
		
		
		//create new file for the decrypted information
		
		//read through the old file and decrypt the data into the new file
			//uses the method encryptor.decrypt(Text). 
		
		//Do we delete the old file?
		
	}
	
	public static void addAccount(AccountInfo newAccount) throws Exception{
		//TODO: encrypt newAccount via password
		AccountInfo acc = encryptAccountInfo(newAccount);
		
		//Send off to update storage
		UpdateStorage.writeAccount(acc);
	}
	
	//bubble exceptions to controller.
	public static AccountInfo logIn(String username, String password) throws Exception{
		//get the accounts. 
		ArrayList<AccountInfo> accounts = UpdateStorage.readAccountStorage(UpdateStorage.findPropertiesFile());

		
		//See if the accounts list contains the username. 
		System.out.println("accounts is: " + accounts.toString());
		for (AccountInfo acc : accounts) {
			
			//account found
			if (decryptString(acc.getName(), password).equals(username)) {
				if (decryptString(acc.getPassword(), password).equals(password))
					return acc;	
			}
		}
		return null;
	}
	
	private static AccountInfo encryptAccountInfo(AccountInfo account) {
		String password = account.getPassword();
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		//encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");// optionally set the algorithm
		System.out.println("Why isn't this encrypting?");
		
		return new AccountInfo(
				encryptor.encrypt(account.getName()),
				encryptor.encrypt(account.getPassword()),
				encryptor.encrypt(account.getSecQ1()),
				encryptor.encrypt(account.getSecAns1())
				);
		
	}
	private static AccountInfo decryptAccountInfo(AccountInfo account, String password) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");// optionally set the algorithm
		
		return new AccountInfo(
				encryptor.decrypt(account.getName()),
				encryptor.decrypt(account.getPassword()),
				encryptor.decrypt(account.getSecQ1()),
				encryptor.decrypt(account.getSecAns1())
				);
		
	}
	
	private static String encryptString(String toEncrypt, String password) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");   // optionally set the algorithm
		// what's this for? encryptor.setIvGenerator(new RandomIvGenerator());
		
		return encryptor.encrypt(toEncrypt);
	}
	private static String decryptString(String toDecrypt, String password) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		//encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");   // optionally set the algorithm
		// what's this for? encryptor.setIvGenerator(new RandomIvGenerator());
		
		return encryptor.decrypt(toDecrypt);
	}
	
	public static Boolean accountNameExists(String username) {
		//TODO: Search through the provided file to see if the name is already in use. 
		return false;
	}
	

	
	
	//Used for comparing passwords and the like.
	public Boolean compareUnserializedToSerialized(String unserialized, String serialized) {
		
		return false;
	}
}
