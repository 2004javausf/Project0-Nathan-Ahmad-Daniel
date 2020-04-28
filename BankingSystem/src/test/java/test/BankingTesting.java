package test;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import beans.Banking;

class BankingTesting {

	@Test // TESTED AND WORKS
	void loginTest() {
		Banking b = new Banking();
		b.login();
		
		
	}

	@Test //TESTED AND WORKS
	void registrationTest() {
		Banking b = new Banking();
		b.registration();
	}



}
