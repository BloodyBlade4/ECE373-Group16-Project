package storage;

//Storage object for account info. 
//Making accessing different fields in an account easy. 

public class AccountInfo {
	private String name = "", password = "";
	private String homeDirectory = "";
	
	//Desired level of security???
	
	
	//security questions
	private String secQ1 = "", secQ2 = "", secQ3 = "";
	private String secAns1 = "", secAns2 = "", secAns3 = "";
	
	public AccountInfo() {};
	
	public AccountInfo(String name, String password,
			String secQOne, String secAnsOne,
			String secQTwo, String secATwo,
			String secQThree, String secAThree, String homeDir) {
		this.name = name;
		this.password = password;
		this.secQ1 = secQOne;
		this.secAns1 = secAnsOne;
		this.secQ2 = secQTwo;
		this.secAns2 = secATwo;
		this.secQ3 = secQThree;
		this.secAns3 = secAThree;
		this.homeDirectory = homeDir;
	}
	
	/*
	public String InfoAsString() {
		return (name+","+password+","+secQ1+","+secAns1);
	}
	*/
	public void printInfo() {
		System.out.println("Account info is: " + 
				"\nName: " + this.name +
				"\npassword: " + this.password +
				"\nsecQ1: " + this.secQ1 +
				"\nsecA1: " + this.secAns1 +
				"\nsecQ2: " + this.secQ2 +
				"\nsecA2: " + this.secAns2 +
				"\nsecQ3: " + this.secQ3 +
				"\nsecA3: " + this.secAns3 +
				"\nHome Directory: " + this.homeDirectory);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHomeDirectory() {
		return homeDirectory;
	}

	public void setHomeDirectory(String homeDirectory) {
		this.homeDirectory = homeDirectory;
	}

	public String getSecQ1() {
		return secQ1;
	}

	public void setSecQ1(String secQ1) {
		this.secQ1 = secQ1;
	}

	public String getSecQ2() {
		return secQ2;
	}

	public void setSecQ2(String secQ2) {
		this.secQ2 = secQ2;
	}

	public String getSecQ3() {
		return secQ3;
	}

	public void setSecQ3(String secQ3) {
		this.secQ3 = secQ3;
	}

	public String getSecAns1() {
		return secAns1;
	}

	public void setSecAns1(String secAns1) {
		this.secAns1 = secAns1;
	}

	public String getSecAns2() {
		return secAns2;
	}

	public void setSecAns2(String secAns2) {
		this.secAns2 = secAns2;
	}

	public String getSecAns3() {
		return secAns3;
	}

	public void setSecAns3(String secAns3) {
		this.secAns3 = secAns3;
	}
	
}
