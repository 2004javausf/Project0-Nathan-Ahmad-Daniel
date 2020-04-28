package beans;

import java.util.Scanner;

public class Admin extends Employee {

	public void deleteAccount() {
		Scanner s = new Scanner(System.in);
		readFile("Accounts.txt");
		showActiveAccounts();
		System.out.println("Enter username to remove");
		String username = s.nextLine();
		System.out.println("Enter password for this account");
		String password = s.nextLine();
		System.out.println("Enter account number to remove");
		String accNum = s.nextLine();
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp = Banking.accList.get(i).toString();
			if(tmp.contains("username=" + username + ", password=" + password + ", accountNumber=" + accNum)) {
				accList.remove(i);
				writeToFile("Accounts.txt", accList);
				System.out.println("Account removed:");
				break;
			}
		}
		
		
		showActiveAccounts();
		
	}
	
}
