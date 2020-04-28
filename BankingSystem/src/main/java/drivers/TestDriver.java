package drivers;

import beans.Admin;
import beans.Banking;
import beans.Employee;

public class TestDriver {

	public static void main(String[] args) {
		Employee e = new Employee();
		//e.approveApplication("killbill", "3");
		//e.denyApplication("hey", "2");
		Banking b = new Banking();
		//b.registration();
		//e.showApplications();
		//b.newAccount("killbill");
		//e.showActiveAccounts();
		//Employee.showApplications();
		//b.deposit("killbill", "321");
		//b.showAllAccounts("nathangeo");
		//b.login();
		//b.withdrawl("nathangeo", "23123");
		//b.registration();
		//b.transferFunds("hey", "1234");
		//b.transferFunds("killbill", "321");
		Admin a = new Admin();
		a.deleteAccount();
	}

}
