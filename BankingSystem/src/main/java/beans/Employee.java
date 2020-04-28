package beans;

import java.io.EOFException;

public class Employee extends Banking {

	public void approveApplication(String username, String accountNumber) {
		
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

	public void denyApplication(String username, String accountNumber) {
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

	public static void showActiveAccounts() {
		Banking b = new Banking();
		b.readFile("Accounts.txt");
		int flag=0;
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp2 = Banking.accList.get(i).toString();
			System.out.println(tmp2);
		}
	}

	public static void showApplications() {

		Banking b = new Banking();
		b.readFile("Applications.txt");
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp2 = Banking.accList.get(i).toString();
			System.out.println(tmp2);

		}
	}
		
}
