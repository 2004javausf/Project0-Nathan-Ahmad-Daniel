package beans;

import java.util.Scanner;

//employees have access to the Account ArrayList data structure in the Banking class
//as well as the text files
//and uses some of its methods, including readFile, writeToFile

public class Employee extends Banking {

	
	//for a given username, this method searches the applications in the arrayList
	//if an application matches a username, it is approved and removed from the Application file
	//and written to Account file
	public void approveApplication(String username) {
		
		Banking b = new Banking();
		b.readFile("Applications.txt");
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp2 = Banking.accList.get(i).toString();
			if(tmp2.contains("username=" + username + ",")) {
				Account tmp3 = Banking.accList.get(i);
				Banking.accList.remove(i);
				b.writeToFile("Applications.txt", Banking.accList);
				//System.out.println(tmp3.toString());
				for(int j = 0; j < Banking.accList.size(); j++) {
					Banking.accList.remove(j);
				}
				b.readFile("Accounts.txt");
				Banking.accList.add(tmp3);
				b.writeToFile("Accounts.txt", Banking.accList);
				break;
			}		
		}	
		
	}
	
	//similar in structure to approveApplication
	//it simply removes the pending application from the applications file
	//without writing it to the account file

	public void denyApplication(String username) {
		Banking b = new Banking();
		b.readFile("Applications.txt");
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp2 = Banking.accList.get(i).toString();
			if(tmp2.contains("username=" + username + ",")) {
				Account tmp3 = Banking.accList.get(i);
				Banking.accList.remove(i);
				b.writeToFile("Applications.txt", Banking.accList);
				break;
			}
		}
	}

	//shows all approved accounts
	public static void showActiveAccounts() {
		Banking b = new Banking();
		b.readFile("Accounts.txt");
		int flag=0;
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp2 = Banking.accList.get(i).toString();
			System.out.println(tmp2);
		}
	}
	
	//shows all applications
	public static void showApplications() {

		Banking b = new Banking();
		b.readFile("Applications.txt");
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp2 = Banking.accList.get(i).toString();
			System.out.println(tmp2);

		}
	}
	
	//all employees login with the same username and password: employee, 1234
	//each employee has the same rights

	public void employeeLogin(String username, String password) {

		readFile("Employees.txt");
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
