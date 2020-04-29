package test;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import beans.Banking;

class BankingTesting {

	@Test // TESTED AND WORKS
	void loginTest() {
		Banking b = new Banking();
		b.login("nathangeo", "1234");
		
		
	}

	@Test //TESTED AND WORKS
	void registrationTest() {
		Banking b = new Banking();
		b.registration();
	}

	@Test
	void newAccountTest() {
		Banking b = new Banking();
		b.newAccount("nathangeo");
	}

	@Test
	void depositTest() {
		Banking b = new Banking();
		b.deposit("nathangeo", "1234");
	}
	
	@Test
	void withdrawlTest() {
		Banking b = new Banking();
		b.withdrawl("nathangeo", "1234");
	}
	
	@Test
	void showAllAccountsTest() {
		Banking b = new Banking();
		b.showAllAccounts("nathangeo");
	}
	
	@Test
	void transferFundsTest() {
		Banking b = new Banking();
		b.transferFunds("nathangeo", "1234");
	}
	
	
	
	
	
}
