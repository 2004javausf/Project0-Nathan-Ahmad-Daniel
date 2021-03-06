package beans;

import java.util.Scanner;

//admin inherits from employee, as it has all the privileges employees have
//along with the additional privileges of withdrawing, depositing, transferring, viewing
//Since Employee inherits from Banking, Admin also inherits the privileges customers have

public class Admin extends Employee {
	
	//to delete account, we need an existing username, respective password, and account number
	//if username and password does not match an existing account, nothing happens
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
	
	//each admin signs in with same username and password: admin, 1234
	//each admin has the same privileges
	public void adminLogin(String username, String password) {
//		Scanner s = new Scanner(System.in);
//		System.out.println("Enter admin username.");
//		String username = s.nextLine();
//		System.out.println("Enter admin password");
//		String password = s.nextLine();
		readFile("Admins.txt");
		String tmp3 = "hereweare";
		for(int j = 0; j < Banking.accList.size(); j++) {
			String tp = Banking.accList.get(j).toString();
			if(tp.contains("username=" + username + ",")) {
				tmp3 = tp;
				break;
			}
		}
		
		if(tmp3.contains("username=" + username + ", password=" + password)) {
			System.out.println("login success");
			//s.close();
		} else {
			System.out.println("Incorrect login info, please try again");
			System.exit(0);
		}
	}
	
}
