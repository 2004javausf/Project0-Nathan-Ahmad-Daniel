package test;

import org.junit.jupiter.api.Test;

import beans.Admin;

class AdminTesting {

	@Test
	void deleteAccountTest() {
		
		Admin a = new Admin();
		a.deleteAccount();
	}

	@Test
	void adminLoginTest() {
		Admin a = new Admin();
		a.adminLogin("admin", "1234");
	}
	
	
}
