package drivers;

import beans.Banking;
import beans.Employee;

public class TestDriver {

	public static void main(String[] args) {
		Employee e = new Employee();
		//e.approveApplication("nathangeo", "3");
		//e.denyApplication("nathangeo", "2");
		Banking b = new Banking();
		//b.registration();
		//e.showApplications();
		//b.newAccount("nathangeo");
		//e.showActiveAccounts();
		//Employee.showApplications();
		//b.deposit("nathangeo", "23123");
		//b.showAllAccounts("nathangeo");
		//b.login();
		b.withdrawl("nathangeo", "23123");
		//b.registration();
	}

}
