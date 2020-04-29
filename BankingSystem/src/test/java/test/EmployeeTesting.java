package test;

import org.junit.jupiter.api.Test;

import beans.Employee;

class EmployeeTesting {


	@Test void approveApplicationTest() {
		
		Employee e = new Employee();
		e.approveApplication("kidx");
	}
	
	@Test void denyApplicationTest() {
		Employee e = new Employee();
		e.denyApplication("nathanx");
	}
	
	@Test void showActiveAccountsTest() {
		
		Employee.showActiveAccounts();
	}
	
	@Test void showApplicationsTest() {
		
		Employee.showApplications();
	}

	@Test void employeeLoginTest() {
		Employee e = new Employee();
		e.employeeLogin("employee", "1234");
	}

}
