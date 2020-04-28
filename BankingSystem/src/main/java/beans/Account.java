package beans;

import java.io.Serializable;

public class Account implements Serializable {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1897445578791776637L;
	
	
	private String username; //must be unique
	private String password; //one password for each user
	private int accountNumber; //unique only to the user, account number n is nth account created by given user
	private float balance; //account balance
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public Account(String username) {
		this.username = username;
	}
	
	
	public Account(String username, String password, int accountNumber, float balance) {
		this.username = username;
		this.password = password;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "username=" + username + ", password=" + password + ", accountNumber=" + accountNumber
				+ ", balance=" + balance;
	}

	


}
