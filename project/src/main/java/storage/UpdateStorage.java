package storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/*
 * Used to interact with encrypted files/user information. A kind of service for the in-between stages
 * 
 * ALL exceptions bubbled upwards.
 */

public class UpdateStorage {
	
	//bubble any exceptions back to serializer. 
	public static void writeAccount(AccountInfo encryptedAccount) throws Exception {
		//Get file
		Path p;
		try {
			p = findPropertiesFile();
		} catch (Exception e) {
			//TODO: Warn the user there is an issue finding the properties file.. 
			//This could be due to different platforms such as mac/linux. Addapt for these.
			e.printStackTrace();
			throw new Exception("Error finding or creating file! " + e);
		}
		ObjectMapper mapper = new XmlMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		//Get the current list of accounts and add the new one to it.
		ArrayList<AccountInfo> accounts = readAccountStorage(p);
		accounts.add(encryptedAccount);
		
		//rewrite the file with the new account added.
		try {
			mapper.writeValue(Files.newOutputStream(p), accounts);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error writing to the file. " + e);
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
