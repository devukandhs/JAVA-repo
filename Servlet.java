package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import dao.CustomerDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AESKeyGenerator;
import model.Admin;
import model.Customer;
import model.Employee;

/**
 * Servlet implementation class Servlet
 */
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	//private AdminDao admin_dao=new AdminDao();
	private CustomerDao customer_dao=new CustomerDao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=request.getParameter("action1");
		
		System.out.println("Switch value"+action);
		try {
			switch(action) {
			
			case "register":
				
				registerform(request,response);
				break;
			case "login":
				
				loginform(request,response);
				break;
			case "list":
				System.out.println("i entered into list");
				listUsers(request,response);
				break;
			case "update":
				updateform(request,response);
				break;
			case "delete":
				System.out.println("i entered into delete");
				deleteform(request,response);
				break;
			case "createaccount":
				createaccount(request,response);
				break;
			case "deposit":
				depositform(request,response);
				break;
			case "withdraw":
				withdrawform(request,response);
				break;
			case "transfer":
				transfer_money(request,response);
				break;
			case "trans_list":
				trans_list_form(request,response);
				break;
			
			}
		}catch( ClassNotFoundException | SQLException e) {
            e.printStackTrace();}
	}
	




	private void transfer_money(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		String account_number=request.getParameter("account_number");
		BigDecimal amount=new BigDecimal(request.getParameter("amount"));
		String account_number_to=request.getParameter("account_number_to");
		String bank_name=request.getParameter("bank_name");
		if(bank_name.equals("iob")) {
			boolean availability_acc=customer_dao.check_accountnum(account_number);
			boolean availability_acc_to=customer_dao.check_accountnum(account_number_to);
			if(availability_acc && availability_acc_to) {
						BigDecimal currentBalance = customer_dao.getcurrentbalance(account_number);
					    BigDecimal newBalance;
						currentBalance = customer_dao.getcurrentbalance(account_number);
						PrintWriter out=response.getWriter();
			         if (currentBalance.compareTo(amount) >= 0) {
			             newBalance = currentBalance.subtract(amount);
			             customer_dao.updateCurrentBalanceAfterTransfer(account_number, amount, account_number_to,bank_name,newBalance);
			             //response.sendRedirect("admin.jsp?message=transfer successful");
			             out.println("<font color=green size=18>Successfully create account and credited!!<br>");
			 			out.println("<a href=customer_workspace.jsp>Click here to your workspace!!</a>");
			 	        
			         } else {
			         	// response.sendRedirect("admin.jsp?message=Insufficient balance");
			        	 out.println("<font color=red size=18> Transfer Failed!! / Insufficient Balance or Recevier account number not available <br>");
			 			out.println("<a href=customer_workspace.jsp>Try Again!!</a>");
			         }

			}else {
				PrintWriter out=response.getWriter();
				 out.println("<font color=red size=18>account number not available <br>");
		 			out.println("<a href=customer_workspace.jsp>Try Again!!</a>");
			}
		}else if(bank_name.equals("sbi")) {
			boolean availability_acc=customer_dao.check_accountnum(account_number);
			
					if(availability_acc ) {
						BigDecimal currentBalance = customer_dao.getcurrentbalance(account_number);
					    BigDecimal newBalance;
						currentBalance = customer_dao.getcurrentbalance(account_number);
						PrintWriter out=response.getWriter();
			         if (currentBalance.compareTo(amount) >= 0) {
			             newBalance = currentBalance.subtract(amount);
			             customer_dao.updateCurrentBalanceAfterTransfer(account_number, amount, account_number_to,bank_name,newBalance);
			             //response.sendRedirect("admin.jsp?message=transfer successful");
			            out.println("<font color=green size=18>Successfully create account and credited!!<br>");
			 			out.println("<a href=customer_workspace.jsp>Click here to your workspace!!</a>");
			 	        
			         	} else {
			         	// response.sendRedirect("admin.jsp?message=Insufficient balance");
			        	out.println("<font color=red size=18> Transfer Failed!! / Insufficient Balance or Recevier account number not available <br>");
			 			out.println("<a href=customer_workspace.jsp>Try Again!!</a>");
			         }
		
					}else {
						PrintWriter out=response.getWriter();
						out.println("<font color=red size=18>account number not available <br>");
						out.println("<a href=customer_workspace.jsp>Try Again!!</a>");
					}

			}
	}
		
/*		 BigDecimal currentBalance = customer_dao.getcurrentbalance(account_number);
		    BigDecimal newBalance;
			currentBalance = customer_dao.getcurrentbalance(account_number);
			PrintWriter out=response.getWriter();
         if (currentBalance.compareTo(amount) >= 0) {
             newBalance = currentBalance.subtract(amount);
             customer_dao.updateCurrentBalanceAfterTransfer(account_number, amount, account_number_to,bank_name,newBalance);
             //response.sendRedirect("admin.jsp?message=transfer successful");
             out.println("<font color=green size=18>Successfully create account and credited!!<br>");
 			out.println("<a href=customer_workspace.jsp>Click here to your workspace!!</a>");
 	        
         } else {
         	// response.sendRedirect("admin.jsp?message=Insufficient balance");
        	 out.println("<font color=red size=18> Transfer Failed!! / Insufficient Balance or Recevier account number not available <br>");
 			out.println("<a href=customer_workspace.jsp>Try Again!!</a>");
         }

*/		
	


   
	private void createaccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		int id=Integer.parseInt( request.getParameter("id"));
		String account_type=request.getParameter("account_type");
		String account_number=request.getParameter("account_number");
		BigDecimal amount=new BigDecimal(request.getParameter("amount"));
		System.out.println(account_type);
		PrintWriter out=response.getWriter();
		if(customer_dao.check_acc(id)) {
			out.println("<font color=red size=18>You already create an account and credited!!<br>");
			out.println("<a href=employee_workspace.jsp>Click here to your workspace!!</a>");
			
		}else {
		if(customer_dao.acc_create(id, account_type,account_number,amount)) {
			//response.sendRedirect("admin_workspace.jsp");
			out.println("<font color=green size=18>Successfully create account and credited!!<br>");
			out.println("<a href=employee_workspace.jsp>Click here to your workspace!!</a>");
			//out.println("<a href=exit.jsp>Exit!!</a>");
		}
		else {
			
			out.println("<font color=red size=18> updation Failed!! / No Such id is there <br>");
			out.println("<a href=employee_workspace.jsp>Try Again!!</a>");
			
		}

		}
	}

	
	private void withdrawform(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
			String pageuser=request.getParameter("pageuser");
			String accountNumber = request.getParameter("account_number");
		    BigDecimal amount = new BigDecimal(request.getParameter("amount"));
		    BigDecimal currentBalance = customer_dao.getcurrentbalance(accountNumber);
		    BigDecimal newBalance;
		    if(customer_dao.check_accountnum(accountNumber)) {
			currentBalance = customer_dao.getcurrentbalance(accountNumber);
			
            if (currentBalance.compareTo(amount) >= 0) {
                newBalance = currentBalance.subtract(amount);
                customer_dao.updateCurrentBalanceAfterWithdrawal(accountNumber, amount, newBalance);
                if(pageuser.equals("emp_page")) {
                response.sendRedirect("employee_workspace.jsp?message=Withdrawal successfull");
                }else {
                	response.sendRedirect("customer_workspace.jsp?message=Withdrawal successfull");
                }
    	        
            } else {
            	if(pageuser.equals("emp_page")) {
            	 response.sendRedirect("employee_workspace.jsp?message=Insufficient balance");
            	}else {
            		response.sendRedirect("customer_workspace.jsp?message=Insufficient balance");
            	}
    	       
            }
		 }else {
			 if(pageuser.equals("emp_page")) {
            	 response.sendRedirect("employee_workspace.jsp?message=Account Number not available");
            	}else {
            		response.sendRedirect("customer_workspace.jsp?message=Account Number not available");
            	}
		 }
			
	}
	
	private void trans_list_form(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
			String accountNumber = request.getParameter("account_number");
			 List<Customer> rs = customer_dao.listTransactionHistory(accountNumber);
			 System.out.println("yes i got list to set attribute to jsp");
             request.setAttribute("transactions", rs);
             System.out.println("yes i am going to print jsp");
             request.getRequestDispatcher("transaction_history.jsp").forward(request, response);
	}

	
	private void registerform(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		String pageuser=request.getParameter("pageuser");
		String usertype=request.getParameter("usertype");
		String firstname=request.getParameter("firstname");//within double quotes is attributes name in database table
		String lastname=request.getParameter("lastname");
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String permanant_address=request.getParameter("permanant_address");
		
		String residential_address=request.getParameter("residential_address");
		String gender=request.getParameter("gender");
		String dob=request.getParameter("dob");
		String contact=request.getParameter("contact");
		String email=request.getParameter("email");
		
		System.out.println("I am get all the parameter");
		PrintWriter out=response.getWriter();
		
	if(contact.length()!=10|| firstname==null||lastname==null||username==null||password==null||permanant_address==null||residential_address==null||gender==null||dob==null||contact==null||email==null) {
		out.println("<font color=red size=18> Register Failed!! / your contact number may be wrong/All Fields are Required <br>");
		if(pageuser.equals("admin_page")) {
		out.println("<a href=admin_workspace.jsp>Try Again!!</a>");
		}else if(pageuser.equals("emp_page")) {
			out.println("<a href=employee_workspace.jsp>Try Again!!</a>");
		}else if(pageuser.equals("emp_page_in")) {
			out.println("<a href=login_reg_emp.jsp>Try Again!!</a>");
		}else if(pageuser.equals("cus_page_in")) {
			out.println("<a href=login_reg_cus.jsp>Try Again!!</a>");
		}else {
			out.println("<a href=customer_workspace.jsp>Try Again!!</a>");}
	}else if(!customer_dao.check_username(username, usertype)) {
		System.out.println("i am going to complete register");
		Customer user=customer_dao.register(firstname, lastname, username, password, permanant_address, residential_address,gender,dob,contact,email,usertype);
		System.out.println("i complete register");
		StringBuilder html = new StringBuilder();
	    if (user != null) {
	        html.append("<font color=green size=18>Successfully registered!!<br>");
	        if(pageuser.equals("admin_page")) {
	    		html.append("<a href=admin_workspace.jsp>Click here to Further operations!!</a>");
	    		}else if(pageuser.equals("emp_page")) {
	    			html.append("<a href=employee_workspace.jsp>Click here to Further operations!!</a>");
	    		}else if(pageuser.equals("emp_page_in")) {
	    			html.append("<a href=login_reg_emp.jsp>Click here to Further operations!!</a>");
	    		}else if(pageuser.equals("cus_page_in")) {
	    			html.append("<a href=login_reg_cus.jsp>Click here to Further operations!!</a>");
	    		}else {
	    			html.append("<a href=customer_workspace.jsp>Click here to Further operations!!</a>");
	    			}

	       // html.append("<a href=admin.jsp>Click here to Further operations!!</a>");
	       // html.append("<a href=exit.jsp>Exit!!</a>");
	    } else {
	        html.append("<font color=red size=18>Registration failed !!/ your entry values may be wrong<br>");
	        if(pageuser.equals("admin_page")) {
	    		html.append("<a href=admin_workspace.jsp>Try Again!!</a>");
	    		}else if(pageuser.equals("emp_page")) {
	    			html.append("<a href=employee_workspace.jsp>Try Again!!</a>");
	    		}else if(pageuser.equals("emp_page_in")) {
	    			html.append("<a href=login_reg_emp.jsp>Try Again!!</a>");
	    		}else if(pageuser.equals("cus_page_in")) {
	    			html.append("<a href=login_reg_cus.jsp>Try Again!!</a>");
	    		}else {
	    			html.append("<a href=customer_workspace.jsp>Try Again!!</a>");
	    			}
	       // html.append("<a href=admin.jsp>Try Again!!</a>");
	       // html.append("<a href=exit.jsp>Exit!!</a>");
	    }
	
	   // PrintWriter out = response.getWriter();
	    out.print(html.toString());
	}
	else {
		StringBuilder html = new StringBuilder();
		html.append("<font color=red size=18>Registration failed !!/ Username is already available<br>");
        if(pageuser.equals("admin_page")) {
    		html.append("<a href=admin_workspace.jsp>Try Again!!</a>");
    		}else if(pageuser.equals("emp_page")) {
    			html.append("<a href=employee_workspace.jsp>Try Again!!</a>");
    		}else if(pageuser.equals("emp_page_in")) {
    			html.append("<a href=login_reg_emp.jsp>Try Again!!</a>");
    		}else if(pageuser.equals("cus_page_in")) {
    			html.append("<a href=login_reg_cus.jsp>Try Again!!</a>");
    		}else {
    			html.append("<a href=customer_workspace.jsp>Try Again!!</a>");
    			}
        
        out.print(html.toString());
	}
	
	}

	
	private void loginform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String usertype=request.getParameter("usertype");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		try {
		Admin user=customer_dao.login(username,password,usertype);
		System.out.println(usertype);
		 if (user != null) {
	            request.getSession().setAttribute("user", user);
	            // Redirect to the appropriate workspace page based on the user type
	            if (usertype.equals("customer_details")) {
	                response.sendRedirect("customer_workspace.jsp");
	            } else if (usertype.equals("employee_details")) {
	                response.sendRedirect("employee_workspace.jsp");
	            }
	            else if (usertype.equals("admin")) {
	                response.sendRedirect("admin_workspace.jsp");
	            }
	        } else {
	            PrintWriter out = response.getWriter();
	            out.println("<font color=red size=18> Login Failed!!<br>");
	            out.println("<a href=admin.jsp>Try Again!!</a>");
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	
	}
	
	
	
	private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		String usertype = request.getParameter("usertype");
		System.out.println("i am inside the method");
	    
	    if(usertype.equals("customer_details")||usertype.equals("employee_details")) {
		    	List<Employee> users = customer_dao.listUsers(usertype);
			    request.setAttribute("users", users);
			    request.setAttribute("daoObject", customer_dao);
			    System.out.println("I set attributes all attrributes");
			    RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
			    dispatcher.forward(request, response);
			    System.out.println(request.getAttribute("users"));
	    }
	    else {
		    	List<Customer> users = customer_dao.listUsersjoin(usertype);
			    request.setAttribute("users", users);
			    request.setAttribute("daoObject", customer_dao);
			    System.out.println("I set attributes all attrributes");
		    	RequestDispatcher dispatcher = request.getRequestDispatcher("listjoin.jsp");
			    dispatcher.forward(request, response);
	    }
	   
	
	}
	
	
	
	
	private void updateform(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		String pageuser=request.getParameter("pageuser");
		int id=Integer.parseInt(request.getParameter("id"));
		String residential_address=request.getParameter("residential_address");
		String contact=request.getParameter("contact");
		String email=request.getParameter("email");
		String usertype=request.getParameter("usertype");
		PrintWriter out=response.getWriter();
		//Employee employee=new Employee(id, residential_address, contact, email,usertype);
		if(contact.length()!=10|| residential_address==null||contact==null||email==null) {
			
			if(pageuser.equals("admin_page")) {
				out.println("<font color=red size=18> Register Failed!! / your contact number may be wrong/All Fields are Required <br>");
	    		out.println("<a href=admin_workspace.jsp>Try Again!!</a>");
	    		}else if(pageuser.equals("emp_page")) {
	    			out.println("<font color=red size=18> Register Failed!! / your contact number may be wrong/All Fields are Required <br>");
	    			out.println("<a href=employee_workspace.jsp>Try Again!!</a>");
	    		}else {
	    			out.println("<font color=red size=18> Register Failed!! / your contact number may be wrong/All Fields are Required <br>");
	    			out.println("<a href=customer_workspace.jsp>Try Again!!</a>");
	    			}

			
			//out.println("<a href=admin.jsp>Try Again!!</a>");
			}else {
		
		
		if(customer_dao.update_Cus(id, residential_address, contact, email,usertype)) {
			//response.sendRedirect("admin_workspace.jsp");
			
			 if(pageuser.equals("admin_page")) {
				 	out.println("<font color=green size=18>Successfully updated!!<br>");
		    		out.println("<a href=admin_workspace.jsp>Click here to Further operations!!</a>");
		    		}else if(pageuser.equals("emp_page")) {
		    			out.println("<font color=green size=18>Successfully updated!!<br>");
		    			out.println("<a href=employee_workspace.jsp>Click here to Further operations!!</a>");
		    		}else {
		    			out.println("<font color=green size=18>Successfully updated!!<br>");
		    			out.println("<a href=customer_workspace.jsp>Click here to Further operations!!</a>");
		    			}
			
			//out.println("<a href=admin_workspace.jsp>Click here to your workspace!!</a>");
			//out.println("<a href=exit.jsp>Exit!!</a>");
		}
		else {			
			if(pageuser.equals("admin_page")) {
				out.println("<font color=red size=18> updation Failed!! / No Such id is there <br>");
	    		out.println("<a href=admin_workspace.jsp>Try Again!!</a>");
	    		}else if(pageuser.equals("emp_page")) {
	    			out.println("<font color=red size=18> updation Failed!! / No Such id is there <br>");
	    			out.println("<a href=employee_workspace.jsp>Try Again!!</a>");
	    		}else {
	    			out.println("<font color=red size=18> updation Failed!! / No Such id is there <br>");
	    			out.println("<a href=customer_workspace.jsp>Try Again!!</a>");
	    			}

			//out.println("<a href=admin_workspace.jsp>Try Again!!</a>");
			
		}

		
	}
}


	private void deleteform(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		// TODO Auto-generated method stub
		int id=Integer.parseInt(request.getParameter("id"));
		String usertype=request.getParameter("usertype");
		String pageuser=request.getParameter("pageuser");
		System.out.println("yes i am enter into the delete form");
		PrintWriter out=response.getWriter();
		if(customer_dao.delete_Cus(id,usertype)) {
			//response.sendRedirect("admin_workspace.jsp");
			
			
			if(pageuser.equals("admin_page")) {
				out.println("<font color=green size=18>Successfully Deleted!!<br>");
	    		out.println("<a href=admin_workspace.jsp>Click here to Further operations!!</a>");
	    		}else if(pageuser.equals("emp_page")) {
	    			out.println("<font color=green size=18>Successfully Deleted!!<br>");
	    			out.println("<a href=employee_workspace.jsp>Click here to Further operations!!</a>");
	    		}

			
			//out.println("<a href=admin_workspace.jsp>Click here to your workspace!!</a>");
			//out.println("<a href=.jsp>Exit!!</a>");
		}
		else {			
			if(pageuser.equals("admin_page")) {
				out.println("<font color=red size=18> Deletion Failed!! / No Such id is there <br>");
	    		out.println("<a href=admin_workspace.jsp>Try Again!!</a>");
	    		}else if(pageuser.equals("emp_page")) {
	    			out.println("<font color=red size=18> Deletion Failed!! / No Such id is there <br>");
	    			out.println("<a href=employee_workspace.jsp>Try Again!!</a>");
	    		}

			//out.println("<a href=admin_workspace.jsp>Try Again!!</a>");
			
		}
	}
	

	
	private void depositform(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
			String pageuser=request.getParameter("pageuser");
			String account_number = request.getParameter("account_number");
		    BigDecimal amount = new BigDecimal(request.getParameter("amount"));
		    PrintWriter out = response.getWriter();
    		if( account_number==null||amount==null) {
    			
    			if(pageuser.equals("emp_page")) {
    					out.println("<font color=red size=18> All Fields are Required <br>");
    	    			out.println("<a href=employee_workspace.jsp>Try Again!!</a>");
    	    		}else {
    	    			out.println("<font color=red size=18> All Fields are Required <br>");
    	    			out.println("<a href=customer_workspace.jsp>Try Again!!</a>");
    	    			}

    			
    			//out.println("<a href=admin.jsp>Try Again!!</a>");
    			}else {
    				if(customer_dao.check_accountnum(account_number)) {
					BigDecimal currentBalance = customer_dao.getcurrentbalance(account_number);
		            BigDecimal newBalance = currentBalance.add(amount);
		            customer_dao.updateCurrentBalanceAfterDeposit(account_number, amount, newBalance);
		            //response.sendRedirect("admin.jsp?message=Deposit successful");
		            
            
			            if(pageuser.equals("emp_page")) {
			            	response.setContentType("text/html");
			                out.println("<script type=\"text/javascript\">");
			                out.println("alert('Deposit successful');");
			            	out.println("location='employee_workspace.jsp';");
			            	out.println("</script>");
			            }else {
			            	response.setContentType("text/html");     
			                out.println("<script type=\"text/javascript\">");
			                out.println("alert('Deposit successful');");
			            	out.println("location='customer_workspace.jsp';");
			            	out.println("</script>");
			            }
			            //out.println("location='admin.jsp';");
            
			
    			}else {
    				if(pageuser.equals("emp_page")) {
    					out.println("<font color=red size=18> Account Number Not Available <br>");
    	    			out.println("<a href=employee_workspace.jsp>Try Again!!</a>");
    	    		}else {
    	    			out.println("<font color=red size=18> Account Number Not Available <br>");
    	    			out.println("<a href=customer_workspace.jsp>Try Again!!</a>");
    	    			}

    			}
    				
    		}
	}
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
