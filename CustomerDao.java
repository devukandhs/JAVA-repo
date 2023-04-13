package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Admin;
import model.Customer;
import model.Employee;

public class CustomerDao {
	private String jdbc_url="jdbc:mysql://localhost:3306/bankingsystem";
	private String jdbc_username="root";
	private String jdbc_password="Murugan1234567";
	private Connection jdbcConnection;
	
	public CustomerDao(String jdbc_url,String jdbc_username,String jdbcPassword) {
		this.jdbc_url=jdbc_url;
		this.jdbc_username=jdbc_username;
		this.jdbc_password=jdbcPassword;
	}
public CustomerDao() {
		
	}
	
Employee employee=new Employee();
	
	protected void connect() throws SQLException {
		if(jdbcConnection==null||jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdbcConnection=DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
		}
	}
	
	protected void diconnect() throws SQLException {
		if(jdbcConnection==null||jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
		
	private static final String LIST_CusUSERS_SQL="select * from customer_details";
	private static final String DELETE_CusUSERS_SQL="delete from customer_details where id=?";
	
	
/*	private static final String TRANSFER_MONEY_Cus_SQL="insert into transaction_history(account_number,transaction_mode,transaction_amount,current_balance) values(?,?,?,?)";
	private static final String WITHDRAW_Cus_SQL="insert into transaction_history(account_number,transaction_mode,transaction_amount,current_balance) values(?,?,?,?)";
	private static final String DEPOSIT_Cus_SQL="insert into transaction_history(account_number,transaction_mode,transaction_amount,current_balance) values(?,?,?,?)";
	private static final String UPDATE_AMOUNT_AFTER_WITH_DEP_Cus_SQL="update customer_details set current_balance=? where account_number?";
	private static final String Transaction_History_CusUSERS_SQL="select * from transaction_history where account_number=?";
	
*/
	   private static final String GET_CURRENT_BALANCE_SQL = "SELECT amount FROM customer_details WHERE account_number=?";
	   private static final String UPDATE_AMOUNT_AFTER_DEPOSIT_SQL = "UPDATE customer_details SET amount=? WHERE account_number=?";
	   private static final String UPDATE_AMOUNT_AFTER_WITHDRAWAL_SQL = "UPDATE customer_details SET amount=? WHERE account_number=?";

	
	protected Connection getConnection() {
		Connection connection=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
		}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return connection;
		
	}
	
	
	
	
public Customer register(String firstname,String lastname,String username,String password,String permanant_address,String residential_address,String gender,String dob,String contact,String email,String usertype) throws ClassNotFoundException, SQLException {
	String REGISTER_SQL=null;
	Customer user=null;

		REGISTER_SQL = "INSERT INTO "+usertype+" (firstname, lastname, username, password, permanant_address,residential_address,gender,dob, contact, email) VALUES (?,?,?,AES_ENCRYPT(?,'encryption_key'),?,?,?,?,?,?);";
	
		
		System.out.println(REGISTER_SQL);
	
		try(Connection connection=getConnection();
    		   PreparedStatement preparedStatement=connection.prepareStatement(REGISTER_SQL)){
        
      
			int affectedRows = 0; // Use an int variable to store the number of affected rows
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, permanant_address);
            preparedStatement.setString(6, residential_address);
            preparedStatement.setString(7, gender);
            preparedStatement.setString(8, dob);
            preparedStatement.setString(9, contact);
            preparedStatement.setString(10, email);
           

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                user = new Customer(firstname, lastname, username, password, permanant_address, residential_address,gender,dob,contact,email);
            }
        }
        return user;
    }

//login Customer

public Admin login(String username,String password,String usertype)throws ClassNotFoundException, SQLException  {
	Admin user=null;
	String LOGIN_SQL="select * from "+usertype+" where username=? and password=AES_ENCRYPT(?,'encryption_key')";
	String LOGIN_SQL_admin="select * from "+usertype+" where username=? and password=?";
	System.out.println(LOGIN_SQL);
	System.out.println(usertype);
	//boolean status=false;
	if(usertype.equals("admin")) {
		try(Connection connection=getConnection();
				   PreparedStatement preparedStatement=connection.prepareStatement(LOGIN_SQL_admin)){
		    


		        // Step 2:Create a statement using connection object
				//PreparedStatement preparedStatement = connection.prepareStatement(EMP_LOGIN_SQL)){
		    	preparedStatement.setString(1, username);//this is get from the getparameter method in servlet (request)
		        preparedStatement.setString(2, password);
		    	ResultSet result = preparedStatement.executeQuery();
		    	if (result.next()) {
		            user = new Admin(result.getString("username"), result.getString("password"), usertype);
		        }
		    }

	}else {
	try(Connection connection=getConnection();
		   PreparedStatement preparedStatement=connection.prepareStatement(LOGIN_SQL)){
    


        // Step 2:Create a statement using connection object
		//PreparedStatement preparedStatement = connection.prepareStatement(EMP_LOGIN_SQL)){
    	preparedStatement.setString(1, username);//this is get from the getparameter method in servlet (request)
        preparedStatement.setString(2, password);
    	ResultSet result = preparedStatement.executeQuery();
    	if (result.next()) {
            user = new Admin(result.getString("username"), result.getString("password"), usertype);
        }
    }
	}
    return user;
	} 
   
public List<Customer> listUsersjoin(String usertype) throws ClassNotFoundException, SQLException {
    List<Customer> users = new ArrayList<>();
    String query = "";
    
    if(usertype.equals("inner_join")) {
    	query="select employee_details.firstname,employee_details.lastname,customer_details.username,customer_details.account_number,customer_details.amount from employee_details inner join customer_details on employee_details.username=customer_details.username order by employee_details.username";
    }
    else if(usertype.equals("left_outerjoin")) {
    	query="select employee_details.firstname,employee_details.lastname,customer_details.username,customer_details.account_number,customer_details.amount from employee_details left outer join customer_details on employee_details.username=customer_details.username order by employee_details.username";
    }
    else if(usertype.equals("right_outerjoin")) {
    	query="select employee_details.firstname,employee_details.lastname,customer_details.username,customer_details.account_number,customer_details.amount from employee_details right outer join customer_details on employee_details.username=customer_details.username order by employee_details.username";
    }
    else if(usertype.equals("full_outerjoin")) {
    	query = "SELECT employee_details.firstname, employee_details.lastname, customer_details.username, customer_details.account_number, customer_details.amount " +
                "FROM employee_details " +
                "LEFT OUTER JOIN customer_details ON employee_details.username = customer_details.username " +
                "UNION " +
                "SELECT employee_details.firstname, employee_details.lastname, customer_details.username, customer_details.account_number, customer_details.amount " +
                "FROM employee_details " +
                "RIGHT OUTER JOIN customer_details ON employee_details.username = customer_details.username " +
                "WHERE employee_details.username IS NULL";
    }

    
    Connection con = getConnection();
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    
    System.out.println("I executed the query");
    
    while (rs.next()) {
      
        Customer customer=new Customer();
        customer.setFirstname(rs.getString("firstname"));
        customer.setLastname(rs.getString("lastname"));
        customer.setUsername(rs.getString("username"));
        customer.setAccount_number(rs.getString("account_number"));
        customer.setAmount(rs.getBigDecimal("amount"));
      
        users.add(customer);
    }
    System.out.println("i am going to return evrything"+ users.size());
    for(Customer user:users) {
    	System.out.println("User: "+ user.getFirstname() + " " + user.getLastname()+" "+user.getUsername()+" "+user.getAccount_number()+" "+user.getAmount());
    } 

    
	return users;
}
	

public List<Employee> listUsers(String usertype) throws ClassNotFoundException, SQLException {
    List<Employee> users = new ArrayList<>();

    String query = "";
   
		    if (usertype.equals("customer_details")) {
		        query = "SELECT * FROM customer_details";
		    } else if (usertype.equals("employee_details")) {
		        query = "SELECT * FROM employee_details";
		    }
		
		    Connection con = getConnection();
		    Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    
		    System.out.println("I executed the query");
		    
		    while (rs.next()) {
		        Employee employee= new Employee();
		       
		
		        employee.setId(rs.getInt("id"));
		        employee.setFirstname(rs.getString("firstname"));
		        employee.setLastname(rs.getString("lastname"));
		        employee.setUsername(rs.getString("username"));
		        employee.setPassword(rs.getString("password"));
		        employee.setPermanant_address(rs.getString("permanant_address"));
		        employee.setResidential_address(rs.getString("residential_address"));
		        employee.setGender(rs.getString("gender"));
		        employee.setDob(rs.getString("dob"));
		        employee.setContact(rs.getString("contact"));
		        employee.setEmail(rs.getString("email"));
		
		        users.add(employee);
		    }
		    System.out.println("i am going to return evrything"+ users.size());
		    for(Employee user:users) {
		    	System.out.println("User: " + user.getId() + " " + user.getFirstname() + " " + user.getLastname());
		    } 
    
    return users;
}


//update

public boolean update_Cus(int id, String residential_address, String contact, String email, String usertype) throws SQLException {
	
	String UPDATE_SQL="update "+usertype+" set residential_address=?,contact=?,email=? where id=?";
	boolean row_upadated;
	try(Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_SQL);){
		System.out.println(preparedStatement);
		preparedStatement.setString(1, residential_address);
		preparedStatement.setString(2, contact);
		preparedStatement.setString(3, email);
		preparedStatement.setInt(4, id);
		System.out.println(contact);
		row_upadated=preparedStatement.executeUpdate()>0;
		
	}
	
	
	return row_upadated;
	
}


//transfer_money to other account

public void updateCurrentBalanceAfterTransfer(String account_number,BigDecimal amount, String account_number_to,String bank_name,BigDecimal newBalance) throws SQLException {

	String debitQuery = "UPDATE customer_details SET amount = ? WHERE account_number = ?";
	try(Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(debitQuery);){
		preparedStatement.setBigDecimal(1, newBalance);
		preparedStatement.setString(2, account_number);
		preparedStatement.executeUpdate();
	}
		
	insertTransactionHistory_transfer(account_number, "transfer_debit", amount, newBalance,account_number_to);
	
	if(bank_name.equals("iob")) {
			BigDecimal currentBalance = getcurrentbalance(account_number_to);	    
			BigDecimal newBalance_c = currentBalance.add(amount);
		
		String creditQuery = "UPDATE customer_details SET amount = amount + ? WHERE account_number = ?";
		try(Connection connection=getConnection();
				PreparedStatement preparedStatement=connection.prepareStatement(creditQuery);){
			preparedStatement.setBigDecimal(1, amount);
			preparedStatement.setString(2, account_number_to);
			preparedStatement.executeUpdate();
		}
				
		insertTransactionHistory_transfer(account_number_to, "transfer_credit", amount, newBalance_c,account_number);
	}else {
		String creditQuery_sbi = "UPDATE sbibank SET current_balance = current_balance + ? WHERE account_number = ?";
		try(Connection connection=getConnection();
				PreparedStatement preparedStatement=connection.prepareStatement(creditQuery_sbi);){
			preparedStatement.setBigDecimal(1, amount);
			preparedStatement.setString(2, account_number_to);
			preparedStatement.executeUpdate();
		}

	}
	
}


private void insertTransactionHistory_transfer(String account_number, String transaction_mode, BigDecimal amount,
		BigDecimal newBalance_c, String account_number_to) throws SQLException {
	// TODO Auto-generated method stub
	 String sql = "INSERT INTO transaction_history(account_number, transaction_mode, transaction_amount, current_balance,account_number_to) VALUES (?, ?, ?, ?,?)";
	   	try(Connection connection=getConnection();
	    		   PreparedStatement preparedStatement=connection.prepareStatement(sql)){
	        preparedStatement.setString(1, account_number);
	        preparedStatement.setString(2, transaction_mode);
	        preparedStatement.setBigDecimal(3, amount);
	        preparedStatement.setBigDecimal(4, newBalance_c);
	        preparedStatement.setString(5, account_number_to);
	        preparedStatement.executeUpdate();
	   	}
}
//delete

public boolean delete_Cus(int id,String usertype) throws SQLException {
	String DELETE_SQL="delete from "+usertype+" where id=?";
	boolean row_deleted;
	try(Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(DELETE_SQL);){
		preparedStatement.setInt(1, id);
		System.out.println("i am going to execute query");
		row_deleted=preparedStatement.executeUpdate()>0;
		
	}
	return row_deleted;
	
}


public BigDecimal getcurrentbalance(String account_number) throws SQLException{
	
	BigDecimal current_balance = null;
	try(Connection connection=getConnection();
    		   PreparedStatement preparedStatement=connection.prepareStatement(GET_CURRENT_BALANCE_SQL)){
		preparedStatement.setString(1, account_number);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            current_balance = rs.getBigDecimal("amount");
        }
    }

	return current_balance;
	
}

//to check the username is already in the database
public boolean check_username(String username,String usertype) {
	System.out.println(usertype);
	String CHECK_USERNAME="SELECT username from "+usertype+" WHERE username=?";
	boolean row_checked=false;
	try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USERNAME);) {
			preparedStatement.setString(1, username);
			System.out.println("I am checking username");
	        ResultSet row_check = preparedStatement.executeQuery();
	        if (row_check.next()) {
	            row_checked = true;
	        }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println(row_checked);
	return row_checked;
	
}

//to check whether the account already created or not

public boolean check_acc(int id) throws SQLException {
    String CHECK_SQL = "SELECT account_number FROM customer_details WHERE id=? AND account_number IS NOT NULL";
    boolean row_checked = false;
    try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_SQL);) {
        preparedStatement.setInt(1, id);
        ResultSet row_check = preparedStatement.executeQuery();
        if (row_check.next()) {
            row_checked = true;
        }
    }
    return row_checked;
}

// to check account number is available or not
public boolean check_accountnum(String account_number) {
	
	   String CHECK_ACCOUNT_NUMBER = "SELECT account_number FROM customer_details WHERE account_number = ?";
	    boolean accountNumberExists = false;
	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(CHECK_ACCOUNT_NUMBER);) {
	        preparedStatement.setString(1, account_number);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            accountNumberExists = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return accountNumberExists;
	
}

//to create an account for registered customer

public boolean acc_create(int id, String account_type,String account_number,BigDecimal amount) throws SQLException {
	
	String ACC_CREATE_SQL="update customer_details set account_type=?,account_number=?,amount=? where id=?";
	boolean row_upadated;
	BigDecimal newBalance=amount;
	try(Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(ACC_CREATE_SQL);){
		System.out.println(preparedStatement);
		preparedStatement.setString(1, account_type);
		preparedStatement.setString(2, account_number);
		preparedStatement.setBigDecimal(3, amount);
		preparedStatement.setInt(4, id);
		System.out.println(ACC_CREATE_SQL);
		row_upadated=preparedStatement.executeUpdate()>0;
		
	}
	insertTransactionHistory(account_number, "Deposit", amount, newBalance);
	
	return row_upadated;
	
}


public void updateCurrentBalanceAfterDeposit(String account_number, BigDecimal amount, BigDecimal newBalance) throws SQLException {
    
	try(Connection connection=getConnection();
    		   PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_AMOUNT_AFTER_DEPOSIT_SQL)){

        preparedStatement.setBigDecimal(1, newBalance);
        preparedStatement.setString(2, account_number);
        preparedStatement.executeUpdate();
    }
    insertTransactionHistory(account_number, "Deposit", amount, newBalance);
}

public void updateCurrentBalanceAfterWithdrawal(String accountNumber, BigDecimal amount, BigDecimal newBalance) throws SQLException {
	try(Connection connection=getConnection();
    		   PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_AMOUNT_AFTER_WITHDRAWAL_SQL)){

	
        preparedStatement.setBigDecimal(1, newBalance);
        preparedStatement.setString(2, accountNumber);
        preparedStatement.executeUpdate();
    }
    insertTransactionHistory(accountNumber, "Withdrawal", amount, newBalance);
}

public void insertTransactionHistory(String account_number, String transaction_mode, BigDecimal transaction_amount, BigDecimal current_balance) throws SQLException {
    String sql = "INSERT INTO transaction_history(account_number, transaction_mode, transaction_amount, current_balance) VALUES (?, ?, ?, ?)";
   	try(Connection connection=getConnection();
    		   PreparedStatement preparedStatement=connection.prepareStatement(sql)){
        preparedStatement.setString(1, account_number);
        preparedStatement.setString(2, transaction_mode);
        preparedStatement.setBigDecimal(3, transaction_amount);
        preparedStatement.setBigDecimal(4, current_balance);
        preparedStatement.executeUpdate();
    }
}


public List<Customer> listTransactionHistory(String account_number) throws ClassNotFoundException, SQLException {
    List<Customer> list = new ArrayList<>();

    String query = "SELECT * FROM transaction_history WHERE account_number=?";
    
    Connection con = getConnection();
    PreparedStatement pstmt = con.prepareStatement(query);
    pstmt.setString(1, account_number);
    ResultSet rs = pstmt.executeQuery();
    System.out.println("I executed the query");
    
    while (rs.next()) {
       Customer customer=new Customer();
       

        customer.setAccount_number(rs.getString("account_number"));
        customer.setTransaction_mode(rs.getString("transaction_mode"));
        customer.setTransaction_amount(rs.getDouble("transaction_amount"));
        customer.setCurrent_balance(rs.getDouble("current_balance"));
        customer.setTransaction_time(rs.getTimestamp("transaction_time"));
        customer.setAccount_number_to(rs.getString("account_number_to"));
       
        list.add(customer);
    }
    System.out.println("i am going to return evrything"+ list.size());
    for(Customer user:list) {
    	System.out.println("User: " + user.getAccount_number() + " " + user.getCurrent_balance() + " " + user.getTransaction_time());
    }  
    return list;
}




/*
public ResultSet listTransactionHistory(String accountNumber) throws SQLException {
    String sql = "SELECT * FROM transaction_history WHERE account_number=?";
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, accountNumber);
        ResultSet rs = preparedStatement.executeQuery();
        System.out.println("yes i execute the query");
        if (rs != null && rs.next()) {
            return rs;
        } else {
            throw new SQLException("No transaction history found for account number " + accountNumber);
        }
    } catch (SQLException e) {
        // Handle any SQL exceptions here
        e.printStackTrace();
        throw e;
    }
}

*/


private void printSQLException(SQLException ex) {
    for (Throwable e: ex) {
        if (e instanceof SQLException) {
            e.printStackTrace(System.err);
            System.err.println("SQLState: " + ((SQLException) e).getSQLState());
            System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
            System.err.println("Message: " + e.getMessage());
            Throwable t = ex.getCause();
            while (t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
        }
    }
}


}
