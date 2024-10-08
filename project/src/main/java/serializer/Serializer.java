package serializer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

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
 * Many exceptions are bubbled back up to the caller as Jasypt has vague exceptions. A few simple exceptions may be handled here as well.  
 */

public class Serializer {
	//Generates the serialized string the account uses for PBE. 
	//This is then serialized by both user password and the user security questions for account retrieval. 
	public static String generateAccoutSerializerString() throws Exception {
		int n = 30;
		byte[] array = new byte[256]; // length is bounded by 7
	    new Random().nextBytes(array);
	    String randomString 
	    = new String(array, Charset.forName("UTF-8")); 
	    
	    // Create a StringBuffer to store the result 
	    StringBuffer r = new StringBuffer(); 
	   
	    // Append first 20 alphanumeric characters 
	    // from the generated random String into the result 
	    for (int k = 0; k < randomString.length(); k++) { 
	   
	     char ch = randomString.charAt(k); 
	   
	     if (((ch >= 'a' && ch <= 'z') 
	      || (ch >= 'A' && ch <= 'Z') 
	      || (ch >= '0' && ch <= '9')) 
	      && (n > 0)) { 
	   
	      r.append(ch); 
	      n--; 
	     } 
	    } 
	   
	    // return the resultant string 
	    return r.toString();
	}
	
	public static void addAccount(AccountInfo newAccount) throws Exception{
		//serialize account
		AccountInfo acc = encryptAccountInfo(newAccount);
		
		//Send off to update storage
		UpdateStorage.writeAccount(acc);
	}
	
	//finds and returns AccountInfo. Returns null if account is not found.
	public static AccountInfo logIn(String username, String password) throws Exception{
		//get the accounts. 
		ArrayList<AccountInfo> accounts = UpdateStorage.readAccountStorage(UpdateStorage.findPropertiesFile());
		
		//See if the accounts list contains the username. 
		for (AccountInfo acc : accounts) {
			
			//account found
			if (acc.getName().equals(username)) {
				String secCode = decryptString(acc.getSecCodePass(), password);
				if (decryptString(acc.getPassword(), secCode).equals(password)) {
					return decryptAccountInfo(acc, secCode);	
				}
			}
		}
		return null;
	}
	
	//Returns the security questions of an account if the account name exists, else null.
	public static ArrayList<String> accountExistsGetQuestions(String username) throws Exception {
		ArrayList<AccountInfo> accounts = UpdateStorage.readAccountStorage(UpdateStorage.findPropertiesFile());
		ArrayList<String> questions = new ArrayList<String>();
		
		//See if the accounts list contains the username. 
		for (AccountInfo acc : accounts) {
			if (acc.getName().equals(username)) {
				questions.add(acc.getSecQ1());
				questions.add(acc.getSecQ2());
				questions.add(acc.getSecQ3());
				return questions;
			}
		}
		
		return null;
	}
	
	//check if the account exists based off a user name. 
	public static Boolean accountExists(String username) throws Exception {
		ArrayList<AccountInfo> accounts = UpdateStorage.readAccountStorage(UpdateStorage.findPropertiesFile());
		
		//See if the accounts list contains the username. 
		for (AccountInfo acc : accounts) {
			if (acc.getName().equals(username)) {
				return true;
			}
		}
		
		return false;
	}
	
	//uses the security questions to access and return AccountInfo. returns null if invalid. 
	public static AccountInfo forgotPassword(String userName, String secAnswers) throws Exception{
		//search through accounts till you find the right one. 
		//get the accounts. 
		ArrayList<AccountInfo> accounts = UpdateStorage.readAccountStorage(UpdateStorage.findPropertiesFile());
		
		//See if the accounts list contains the username. 
		for (AccountInfo acc : accounts) {
			if (acc == null || acc.getSecCodeAns() == null)
				continue;
			//get security code
			String secCode = null;
			try {
				secCode = decryptString(acc.getSecCodeAns(), secAnswers);
			} catch (Exception e) {
				//Exception could be due to incorrect security answers, or corrupted data. 
				continue;
			}
			//compare usernames. 
			if (secCode != null && acc.getName().equals(userName)) {
				//compare security questions. 
				if (secAnswers.contains(decryptString(acc.getSecAns1(), secCode)) && 
						secAnswers.contains(decryptString(acc.getSecAns2(), secCode)) &&
						secAnswers.contains(decryptString(acc.getSecAns3(), secCode)))
					return decryptAccountInfo(acc, secCode);
				
			}
		}
		return null;
	}
	
	//updates a user account based on username. Verification done in controller.
	public static void changeAccountInfo(String username, AccountInfo acc) throws Exception {
		//get accounts list:
		ArrayList<AccountInfo> accounts = UpdateStorage.readAccountStorage(UpdateStorage.findPropertiesFile());
		
		//find the account
		for (AccountInfo a : accounts) {
			if (a.getName().equals(username)) {
				accounts.set(accounts.indexOf(a), encryptAccountInfo(acc));
				break;
			}
		}
		//update
		UpdateStorage.writeAccount(accounts);
	}
	
	
	/* FILE HANDLING */
	public static void encryptFile(String loc, String password, Boolean deleteOld, String homeDir) {
		encryptFile(Paths.get(loc), password, deleteOld, homeDir);
	}
	public static void encryptFile(Path p, String password, Boolean deleteOld, String homeDir) {
		//create encryption object. 
		StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();
		encryptor.setPassword(password);
		
		//get file name and extension.
		int dotIndex = p.toFile().getName().lastIndexOf('.');
		String name = (dotIndex == -1) ? "" : p.toFile().getName().substring(0, dotIndex);
		String ext = (dotIndex == -1) ? "" : p.toFile().getName().substring(dotIndex + 1);
		if (name.isEmpty() || ext.isEmpty()) {
			FileHelper.errorMessage("Error", "Unable to retrieve file information " + p.getFileName() + ". ");
			return;
		}
		
		//read the file
		byte[] data = null;
		try {
			data = Files.readAllBytes(p);
		} catch (IOException e) {
			FileHelper.errorMessage("Error", "Unable to open the selected file " + p.getFileName() + ". " + e);
			e.printStackTrace();
			return;
		}
		
		//create new file for the serialized information
		String outputFile = homeDir + "/" + name + "_serialized." + ext;
		try {
			Files.write(Paths.get(outputFile), encryptor.encrypt(data));
		} catch (Exception e) {
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
	
	public static void decryptFile(String loc, String password, Boolean deleteOld, String homeDir) {
		decryptFile(Paths.get(loc), password, deleteOld, homeDir);
	}
	public static void decryptFile(Path p, String password, Boolean deleteOld, String homeDir) {
		//create encryption object. 
		StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();
		encryptor.setPassword(password);
		
		//get file name and extension.
		int dotIndex = p.toFile().getName().lastIndexOf('.');
		String name = (dotIndex == -1) ? "" : p.toFile().getName().substring(0, dotIndex).replace("_serialized", "");
		String ext = (dotIndex == -1) ? "" : p.toFile().getName().substring(dotIndex + 1);
		if (name.isEmpty() || ext.isEmpty()) {
			FileHelper.errorMessage("Error", "Unable to retrieve file information " + p.getFileName() + ". ");
			return;
		}
		
		//read the file
		byte[] data = null;
		try {
			data = Files.readAllBytes(p);
		} catch (IOException e) {
			FileHelper.errorMessage("Error", "Unable to open the selected file " + p.getFileName() + ". " + e);
			e.printStackTrace();
		}
		
		//create new file for the serialized information
		String outputFile = homeDir + "/" + name + "." + ext;
		try {
			Files.write(Paths.get(outputFile), encryptor.decrypt(data));
		} catch (Exception e) {
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
	
	
	/* ACCOUNT_INFO HANDLING */
	private static AccountInfo encryptAccountInfo(AccountInfo account) throws Exception{
		String password = account.getSecCodePass();
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		
		return new AccountInfo(
				account.getName(),
				encryptor.encrypt(account.getPassword()),
				account.getSecQ1(),
				encryptor.encrypt(account.getSecAns1()),
				account.getSecQ2(),
				encryptor.encrypt(account.getSecAns2()),
				account.getSecQ3(),
				encryptor.encrypt(account.getSecAns3()),
				encryptor.encrypt(account.getHomeDirectory()),
				encryptString(account.getSecCodePass(), account.getPassword()),
				encryptString(account.getSecCodeAns(), account.allSecurityAnswers())
				);
		
	}
	private static AccountInfo decryptAccountInfo(AccountInfo account, String password) throws Exception {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		
		AccountInfo acc = new AccountInfo(
				account.getName(),
				encryptor.decrypt(account.getPassword()),
				account.getSecQ1(),
				encryptor.decrypt(account.getSecAns1()),
				account.getSecQ2(),
				encryptor.decrypt(account.getSecAns2()),
				account.getSecQ3(),
				encryptor.decrypt(account.getSecAns3()),
				encryptor.decrypt(account.getHomeDirectory()),
				"",""
				);
		acc.setSecCodePass(decryptString(account.getSecCodePass(), acc.getPassword()));
		acc.setSecCodeAns(decryptString(account.getSecCodeAns(), acc.allSecurityAnswers()));
		return acc;
	}
	
	
	
	/* STRING HANDLING */
	private static String encryptString(String toEncrypt, String password) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		
		return encryptor.encrypt(toEncrypt);
	}
	private static String decryptString(String toDecrypt, String password) {
		if (toDecrypt == null || password == null)
			return null;
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		
		return encryptor.decrypt(toDecrypt);
	}

}
