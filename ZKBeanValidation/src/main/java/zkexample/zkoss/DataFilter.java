package zkexample.zkoss;

public class DataFilter {

	private String code = "";
	private String firstName = "";
	private String lastName = "";
	private String email = "";
	private String loginID= "";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? "" : code.trim();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName == null ? "" : firstName.trim();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName == null ? "" : lastName.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? "" : email.trim();
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID == null ? "" : loginID.trim();
	}
 
}
