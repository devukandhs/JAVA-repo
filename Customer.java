package model;

import java.math.BigDecimal;
import java.sql.*;

public class Customer extends Employee {

	
		private int id;
		private String account_type;
		private String account_number;
		//private String account_number_to;
	    private double transaction_amount;
	    private double current_balance;
	    private String transaction_mode;
	    private Timestamp transaction_time;
	    private BigDecimal amount;
		private String account_number_to;
		private String bank_name;
	    
	    public Customer() {
	    	//default constructor
	    }
	
		
		public Customer(String firstname, String lastname, String username, String password, String permanant_address,String residential_address,String gender,String dob, String contact, String email) {
	        super(firstname, lastname, username, password, permanant_address,residential_address, gender, dob, contact,email);
	        
	        
		}
		public Customer(int id,String account_type,String account_number,BigDecimal amount) {
			this.id=id;
			this.account_type=account_type;
			this.account_number=account_number;
			this.amount=amount;
			
			
		}
		/*public Customer(String account_number,BigDecimal amount,String account_number_to,String bank_name) {
			
			this.account_number=account_number;
			this.amount=amount;
			this.account_number_to=account_number_to;
			this.bank_name=bank_name;		
		}*/
		
		public Customer(String account_number,String transaction_mode,double transaction_amount,double current_balance,Timestamp transaction_time,String account_number_to) {
			
			// TODO Auto-generated constructor stub

			this.account_number=account_number;
			this.current_balance=current_balance;
			this.transaction_amount=transaction_amount;
			this.current_balance=current_balance;
			this.transaction_time=transaction_time;
			this.account_number_to=account_number_to;
		}


		public BigDecimal getAmount() {
			return amount;
		}


		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}


		public void setAccount_number(String account_number) {
			this.account_number = account_number;
		}


		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		

		public Timestamp getTransaction_time() {
			return transaction_time;
		}


		public void setTransaction_time(Timestamp timestamp) {
			this.transaction_time = timestamp;
		}

		public String getAccount_number_to() {
			return account_number_to;
		}

		public void setAccount_number_to(String account_number_to) {
			this.account_number_to = account_number_to;
		}


		public String getAccount_type() {
			return account_type;
		}


		public void setAccount_type(String account_type) {
			this.account_type = account_type;
		}


		

		public String getAccount_number() {
			return account_number;
		}


		

		public double getTransaction_amount() {
			return transaction_amount;
		}


		public void setTransaction_amount(double transaction_amount) {
			this.transaction_amount = transaction_amount;
		}


		public double getCurrent_balance() {
			return current_balance;
		}


		public void setCurrent_balance(double current_balance) {
			this.current_balance = current_balance;
		}


		public String getTransaction_mode() {
			return transaction_mode;
		}


		public void setTransaction_mode(String transaction_mode) {
			this.transaction_mode = transaction_mode;
		}




	
}
