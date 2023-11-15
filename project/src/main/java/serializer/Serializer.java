package serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import storage.AccountInfo;
import storage.FileHelper;
import storage.UpdateStorage;

/*
 * Files and information is serialized to protect/lock it from being interacted with by outside users or anything else 
 * 
 * utilizes Password-Based Encryption (PBE) 
 * For additional information and implementation, refer to Jasypt's official documentation: http://www.jasypt.org/general-usage.html
 * 
 * 
 * ALL exceptions bubbled back up to the caller. 
 */

public class Serializer {
	public static void addAccount(AccountInfo newAccount) throws Exception{
		AccountInfo acc = encryptAccountInfo(newAccount);
		
		//Send off to update storage
		UpdateStorage.writeAccount(acc);
	}
	
	public static AccountInfo logIn(String username, String password) throws Exception{
		//get the accounts. 
		ArrayList<AccountInfo> accounts = UpdateStorage.readAccountStorage(UpdateStorage.findPropertiesFile());
		
		//See if the accounts list contains the username. 
		System.out.println("accounts is: " + accounts.toString());
		for (AccountInfo acc : accounts) {
			
			//account found
			if (decryptString(acc.getName(), password).equals(username)) {
				if (decryptString(acc.getPassword(), password).equals(password)) {
					return decryptAccountInfo(acc, password);	
				}
			}
		}
		return null;
	}
	
	
	
	
	
	/* File Handling */
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
			FileHelper.errorMessage("Error", "Unable to open the selected file " + p.getFileName() + ". " + e);
			e.printStackTrace();
		}
		
		//create new file for the serialized information
		String outputFile = homeDir + "/testing_serialized.txt";
		try {
			Files.write(Paths.get(outputFile), encryptor.encrypt(data));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FileHelper.errorMessage("Error", "Unable to encrypt file. " + e);
			e.printStackTrace();
		} 
		
		//Do we delete the old file?
		if(deleteOld) {
			try {Files.delete(p);}
			catch (IOException e) {
				FileHelper.errorMessage("Error", "Could not delete file \"" + p.getFileName() + "\"");
			}
		}
		
	}
	
	//takes the location of the file to decrypt, and the users password. 
	public void decryptFile(String loc, String password, Boolean deleteOld, String homeDir) {
		decryptFile(Paths.get(loc), password, deleteOld, homeDir);
	}
	public static void decryptFile(Path p, String password, Boolean deleteOld, String homeDir) {
		//create encryption object. 
		StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();
		encryptor.setPassword(password);
		
		byte[] data = null;
		try {
			data = Files.readAllBytes(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			FileHelper.errorMessage("Error", "Unable to open the selected file " + p.getFileName() + ". " + e);
			e.printStackTrace();
		}
		
		//create new file for the serialized information
		String outputFile = homeDir + "/testing_deserialized.txt";
		System.out.println("will deserialize to: " + outputFile);
		try {
			Files.write(Paths.get(outputFile), encryptor.decrypt(data));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FileHelper.errorMessage("Error", "Unable to decrypt file. " + e);
			e.printStackTrace();
		} 
		
		//Do we delete the old file?
		if(deleteOld) {
			try {Files.delete(p);}
			catch (IOException e) {
				FileHelper.errorMessage("Error", "Could not delete file \"" + p.getFileName() + "\"");
			}
		}		
	}
	
	
	
	
	/*AccountInfo Object handling */
	private static AccountInfo encryptAccountInfo(AccountInfo account) throws Exception{
		String password = account.getPassword();
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		
		return new AccountInfo(
				encryptor.encrypt(account.getName()),
				encryptor.encrypt(account.getPassword()),
				encryptor.encrypt(account.getSecQ1()),
				encryptor.encrypt(account.getSecAns1()),
				encryptor.encrypt(account.getSecQ2()),
				encryptor.encrypt(account.getSecAns2()),
				encryptor.encrypt(account.getSecQ3()),
				encryptor.encrypt(account.getSecAns3()),
				encryptor.encrypt(account.getHomeDirectory())
				);
		
	}
	private static AccountInfo decryptAccountInfo(AccountInfo account, String password) throws Exception {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		//encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");// optionally set the algorithm
		
		return new AccountInfo(
				encryptor.decrypt(account.getName()),
				encryptor.decrypt(account.getPassword()),
				encryptor.decrypt(account.getSecQ1()),
				encryptor.decrypt(account.getSecAns1()),
				encryptor.decrypt(account.getSecQ2()),
				encryptor.decrypt(account.getSecAns2()),
				encryptor.decrypt(account.getSecQ3()),
				encryptor.decrypt(account.getSecAns3()),
				encryptor.decrypt(account.getHomeDirectory())
				);
		
	}
	
	
	
	/* String handling */
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

}
