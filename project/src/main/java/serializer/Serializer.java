package serializer;

import java.nio.file.*;
import org.jasypt.encryption.pbe.*;
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
	
	
	//Used for comparing passwords and the like.
	public Boolean compareUnserializedToSerialized(String unserialized, String serialized) {
		
		return false;
	}
}
