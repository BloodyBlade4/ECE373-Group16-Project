package storage;

//Storage object for account info. 
//Making accessing different fields in an account easy. 

public class AccountInfo {
	private String name, password;
	private String homeDirectory;
	
	//Desired level of security???
	
	
	//security questions
	private String secQ1, secQ2, secQ3;
	private String secAns1, secAns2, secAns3;
	
	public AccountInfo(String name, String password,
			String secQOne, String secAnsOne) {
		this.name = name;
		this.password = password;
		this.secQ1 = secQOne;
		this.secAns1 = secAnsOne;
	}
	
	public String getInfoAsString() {
		return (name+","+password+","+secQ1+","+secAns1);
	}
	
}
