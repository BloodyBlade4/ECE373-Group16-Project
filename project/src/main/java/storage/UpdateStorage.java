package storage;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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
		
		return new AccountInfo("k","k", "k", "k");
	}
	
	//bubble any exceptions back to serializer. 
	public static void writeAccount(AccountInfo encryptedAccount) throws Exception {
		//Get file
		Path p;
		try {
			p = findPropertiesFile();
		} catch (Exception e) {
			// TODO Warn the user there is an issue finding the properties file.. 
			//This could be due to different platforms such as mac/linux. Addapt for these.
			//exit.
			e.printStackTrace();
			throw new Exception("Error finding or creating file! " + e);
		}
		ObjectMapper mapper = new XmlMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		ArrayList<AccountInfo> accounts = readAccountStorage(p);
		System.out.println("Finished reading xml.");
		accounts.add(encryptedAccount);
		System.out.println("Accountlist is now: " + accounts);
		try {
			//rewrite the file with the new account added.
			mapper.writeValue(Files.newOutputStream(p), accounts);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
	
	public static ArrayList<AccountInfo> readAccountStorage(Path p) throws Exception{
		if (p.toFile().length() == 0)
			return new ArrayList<AccountInfo>();
		
		try {
			if(!Files.exists(p))
				throw new Exception("Cannot load settings because the settings file path could not be loaded.");
			ObjectMapper mapper = new XmlMapper(); 
			TypeReference<ArrayList<AccountInfo>> typeRef = new TypeReference<ArrayList<AccountInfo>>() {};
			ArrayList<AccountInfo> accounts = mapper.readValue(Files.newInputStream(p), typeRef);
			return accounts;
		} catch(FileNotFoundException e1) {
			e1.printStackTrace();
			throw new Exception("Failed find the settings file.");
		}
		catch (UnrecognizedPropertyException e1){
			e1.printStackTrace();
			throw new Exception("Unrecognized properties found or missing in the selected settings file.");
		} 
		catch(IOException e2) {
			e2.printStackTrace();
			throw new Exception("Failed to open, or close, the desired settings file." + e2);
		}
		catch(InaccessibleObjectException e3) {
			throw new Exception("Tried to load in accessible object from settings... " + e3);
		}
		catch (Exception e4) {
			e4.printStackTrace();
			throw new Exception("Something went wrong while trying to load the selected settings file."); 
		}
	}
	
	public static Path findPropertiesFile() throws Exception{
		String DATA_DIR = System.getenv("APPDATA") + "\\LockBox";
		String DATA_FILE = "/AccountStorage.xml";
		
		//Find directory or create it if it doesn't exist.
		Path dir = Paths.get(DATA_DIR);
		if (!Files.exists(dir)) {Files.createDirectory(dir);}
		
		//find file or create it if it doesn't exist.
		Path file = Paths.get(DATA_DIR + DATA_FILE);
		if (!Files.exists(file)) {Files.createFile(file);}

		return file;
	}
	
}
