package model;

public class Employee {
	private int id;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String permanant_address;
	private String residential_address;
	private String gender;
	private String dob;
	private String contact;
	private String email;
	private String usertype;
	
	
	
	public Employee() {
		
	}
	public Employee(int id,String usertype) {
		this.id=id;
		this.usertype=usertype;
		
	}
	public Employee(String firstname,String lastname,String username,String password,String permanant_address,String residential_address, String gender,String dob,String contact,String email) {
		this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.permanant_address = permanant_address;
        this.residential_address=residential_address;
        this.gender=gender;
        this.dob=dob;
        this.contact = contact;
        this.email=email;
       
	}
	
	public Employee(String firstname,String lastname,String username,String password,String permanant_address,String residential_address, String gender,String dob,String contact,String email, String usertype) {
		this.usertype=usertype;
	}
		
	public Employee(int id,String firstname,String lastname,String username,String password,String permanant_address,String residential_address, String gender,String dob,String contact,String email) {
		
		
		this.id=id;
		this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.permanant_address = permanant_address;
        this.residential_address=residential_address;
        this.gender=gender;
        this.dob=dob;
        this.contact = contact;
        this.email=email;
	}
	
	public Employee(int id,String residential_address,String contact,String email,String usertype) {
		this.id=id;
		this.residential_address = residential_address;
        this.contact = contact;
        this.email=email;
        this.usertype=usertype;
		
	}
	
	// add this constructor to match the fields in the Customer class
    public Employee(int id, String firstname, String lastname, String username, String password,
            String permanant_address,String residential_address, String gender, String dob, String contact, String email,
            String account_type, int account_number, double current_balance) {
    	

        
    }

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getResidential_address() {
		return residential_address;
	}

	public void setResidential_address(String residential_address) {
		this.residential_address = residential_address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPermanant_address() {
		return permanant_address;
	}

	public void setPermanant_address(String permanant_address) {
		this.permanant_address = permanant_address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
