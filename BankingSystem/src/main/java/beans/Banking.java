package beans;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Banking {
	private static final String accFile = "Accounts.txt";  //empty until account objects from applications
	private static final String applicationFile = "Applications.txt";
	public static List<Account> accList = new ArrayList<Account>();
	File newAccFileTmp = new File("Applications.txt");
	
	public void writeToFile(String FileName, Object acc) {
		
		try {
			ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(FileName));
			objectOut.writeObject(acc);
			objectOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	public void readFile(String FileName) {
		try {
			ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(FileName));
			accList = (ArrayList<Account>)objectIn.readObject();
			objectIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void newAccount(String username) {
		String password = null;
		int value = 0;
		readFile(accFile);
		List<Integer> values = new ArrayList<Integer>();
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp = Banking.accList.get(i).toString();
			if(tmp.contains("username=" + username + ",")) {
				tmp = tmp.replace("=", " ");
				tmp = tmp.replace(",", "");
				String[] tmps = tmp.split(" ");
				password = tmps[3];
				value = Integer.parseInt(tmps[5]);
				values.add(value);				
			}
		}
		for(int j = 0; j < Banking.accList.size(); j++) {
			Banking.accList.remove(j);
		}
		readFile(applicationFile);
		Account a = new Account(username, password, Collections.max(values,null) + 1, 0);
		accList.add(a);
		writeToFile(applicationFile, accList);
	}
		
	
	public void registration() {
		Banking b = new Banking();
		System.out.println("Enter desired username (no '=' or ',')");
		Scanner s = new Scanner(System.in);
		String username = s.nextLine();
		if((username.contains("=")) || (username.contains(","))) {
			System.out.println("Invalid input");
			registration();
		}
		readFile(accFile);
		String tmp = Banking.accList.toString();
		if(tmp.contains(username)) {
			System.out.println("Username already in use. Please try another.");
			registration();
		} else {
			System.out.println("Enter desired password (no '=' or ',')");
			String password = s.nextLine();
			if((password.contains("=")) || (password.contains(","))) {
				System.out.println("Invalid input");
				registration();
			}
			s.close();
			for(int j = 0; j < Banking.accList.size(); j++) {
				Banking.accList.remove(j);
			}
			readFile(applicationFile);
			Account a = new Account(username, password, 1, 0);
			accList.add(a);
			writeToFile(applicationFile, accList);
		}
	
		System.out.println("Account registered, you must wait for account to be approved before logging in.");
	}



	public void deposit(String username, String password) {

		int accountNumber = 0;
		float deposit = 0;
		showAllAccounts(username);
		Scanner s = new Scanner(System.in);
		System.out.println("Enter account number.");
		try {
			accountNumber = s.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		System.out.println("Enter deposit amount");
		try {
			deposit = s.nextFloat();
			if(deposit < 0) {
				System.out.println("Invalid input");
				return;
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		readFile(accFile);
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp = Banking.accList.get(i).toString();
			if(tmp.contains("username=" + username + ", password=" + password + ", accountNumber=" + accountNumber)) {
				tmp = tmp.replace("=", " ");
				tmp = tmp.replace(",", "");
				String[] tmps = tmp.split(" ");
				System.out.println("Old balance: " + tmps[7]);
				Float balance = Float.parseFloat(tmps[7]);
				accList.remove(i);
				balance = balance + deposit;
				System.out.println("New balance: " + balance);
				Account a = new Account(username, password, accountNumber, balance);
				accList.add(a);
				writeToFile(accFile, accList);
				showAllAccounts(username);
				return;
			} else {
				
			}
		}
		System.out.println("Invalid input");
	}

	
	public void withdrawl(String username, String password) {
		
		int accountNumber = 0;
		float withdrawal = 0;
		showAllAccounts(username);
		Scanner s = new Scanner(System.in);
		System.out.println("Enter account number.");
		try {
			accountNumber = s.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		System.out.println("Enter withdrawal amount");
		try {
			withdrawal = s.nextFloat();
			if(withdrawal < 0) {
				System.out.println("Invalid input");
				return;
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		readFile(accFile);
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp = Banking.accList.get(i).toString();
			if(tmp.contains("username=" + username + ", password=" + password + ", accountNumber=" + accountNumber)) {
				tmp = tmp.replace("=", " ");
				tmp = tmp.replace(",", "");
				String[] tmps = tmp.split(" ");
				Float balance = Float.parseFloat(tmps[7]);
				accList.remove(i);
				balance = balance - withdrawal;
				if(balance < withdrawal) {
					System.out.println("Withdrawal too large.");
					return;
				}
				System.out.println("Old balance: " + tmps[7]);
				System.out.println("New balance: " + balance);
				Account a = new Account(username, password, accountNumber, balance);
				accList.add(a);
				writeToFile(accFile, accList);
				showAllAccounts(username);
				return;
			} else {
				
			}
		}
		System.out.println("Invalid input");	
	}

	public void showAllAccounts(String username) {
		
		readFile(accFile);
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp = Banking.accList.get(i).toString();
			if(tmp.contains("username=" + username + ",")) {
				System.out.println(tmp);
			}
		}	
	}


	public void login(String username, String password) {
		
//		Scanner s = new Scanner(System.in);
//		System.out.println("Enter username.");
//		String username = s.nextLine();
//		System.out.println("Enter password");
//		String password = s.nextLine();
		readFile(accFile);
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
			login(username, password);
		}	
	}
	
	public void transferFunds(String username, String password) {
		Account c = null;
		Account d = null;
		String value1 = null;
		String value2 = null;
		String tmp2 = null;
		float amount = 0;
		int accNum1 = 0;
		int accNum2 = 0;
		Scanner s = new Scanner(System.in);
		showAllAccounts(username);
		System.out.println("Enter account number which you would like to pull funds from");
		try {
		accNum1 = s.nextInt();
		if(accNum1 < 1) {
			System.out.println("Invalid input");
			return;
		}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		System.out.println("Enter account number which you woul like to transfer funds to");
		try {
		accNum2 = s.nextInt();
		if(accNum2 < 1) {
			System.out.println("Invalid input");
			return;
		}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		readFile(accFile);
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp = Banking.accList.get(i).toString();
			tmp2 = tmp;
			if(tmp2.contains("username=" + username + ", password=" + password + ", accountNumber=" + accNum1)) {
				value1 = tmp2;
				c = new Account("s", "s", 1000, 3);
				Banking.accList.set(i, c);
			} else if (tmp2.contains("username=" + username + ", password=" + password + ", accountNumber=" + accNum2)) {
				value2 = tmp2;
				d = new Account("s", "s", 1001, 3);
				Banking.accList.set(i, d);
				
			}
		}

		System.out.println(value1);
		System.out.println(value2);
		value1 = value1.replace("=", " ");
		value1 = value1.replace(",", "");
		String[] values1 = value1.split(" ");
		value2 = value2.replace("=", " ");
		value2 = value2.replace(",", "");
		String[] values2 = value2.split(" ");
		Float v1 = Float.parseFloat(values1[7]);
		Float v2 = Float.parseFloat(values2[7]);
		System.out.println("Enter transfer amount");
		try {
		amount = s.nextFloat();
		if(amount > v1) {
			System.out.println("Insufficient funds for transfer amount");
			return;
		}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		Account a = new Account(username, password, accNum1, v1 - amount);
		Account b = new Account(username, password, accNum2, v2 + amount);
		accList.remove(c);
		accList.remove(d);
		accList.add(a);
		accList.add(b);
		writeToFile(accFile, accList);
		System.out.println("Transfer Successful:");
		showAllAccounts(username);
	
	
	}



}
