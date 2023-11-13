package serializer;

import storage.AccountInfo;

import java.io.File;
import java.nio.file.*;
import org.jasypt.encryption.pbe.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * Files and information is serialized to protect/lock it from being interacted with by outside users or anything else 
 * 
 * We will be using Password-Based Encryption (PBE) 
 * For additional information and implementation, refer to Jasypt's official documentation: http://www.jasypt.org/general-usage.html
 */

public class Serializer {
	
	//takes the location of the file to encrypt, and the users password.
	public void encryptFile(String loc, String password) {
		encryptFile(Paths.get(loc), password);
	}
	public void encryptFile(Path p, String password) {
		//create encryption object. 
		StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();
		encryptor.setPassword(password);
		
		
		//create new file for the serialized information
		
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
	
	public static void addAccount(AccountInfo newAccount) {
		//Get file

		File dir;
		try {
			dir = findPropertiesFile().toFile();
		} catch (Exception e) {
			// TODO Warn the user there is an issue finding the properties file.. 
			//This could be due to different platforms such as mac/linux. Addapt for these.
			//exit.
			e.printStackTrace();
			return;
		}

		//Write to file. 
		Properties props = new Properties();
		FileInputStream fileIn = null;
		FileOutputStream fileOut = null;
		try {
			fileIn = new FileInputStream(dir);
			props.load(fileIn);
			props.setProperty("Accounts", newAccount.getInfoAsString());
			fileOut = new FileOutputStream(dir);
			props.store(fileOut, "sample properties");
		} catch (Exception e) {
			//TODO, exception writing!
			e.printStackTrace();
		} finally {
			try {
				fileOut.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		
		
		
	}
	
	public static Boolean accountNameExists(String username) {
		//TODO: Search through the provided file to see if the name is already in use. 
		return false;
	}
	
	public static Path findPropertiesFile() throws Exception{
		String DATA_DIR = System.getenv("APPDATA") + "\\LockBox";
		String DATA_FILE = "/AccountStorage.properties";
		Path dir = Paths.get(DATA_DIR);
		if (!Files.exists(dir)) {Files.createDirectory(dir);}
		
		Path file = Paths.get(DATA_DIR + DATA_FILE);
		if (!Files.exists(file)) {Files.createFile(file);}
		return file;
	}
	
	
	//Used for comparing passwords and the like.
	public Boolean compareUnserializedToSerialized(String unserialized, String serialized) {
		
		return false;
	}
}
