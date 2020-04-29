package beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Banking {
	private static final String accFile = "Accounts.txt";  
	private static final String applicationFile = "Applications.txt";
	public static List<Account> accList = new ArrayList<Account>();
	File newAccFileTmp = new File("Applications.txt");
	
	//writes account object onto a file (either applications or accounts)
	//frequently called method, checks for file exceptions
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

	//prepares a file for reading, so that other methods may access information stored in files
	//frequently called method, checks for exception if file cannot be found
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
	

	//this method collects the relevant information to make a new account:
	//username (which also gives us the password) and number of current accounts
	public void newAccount(String username) {
		String password = null;
		int value = 0;
		readFile(accFile);
		List<Integer> values = new ArrayList<Integer>();       //this array keeps track of current accounts (per username)
		for(int i = 0; i < Banking.accList.size(); i++) {
			//iterates through each account object in the arrayList
			//per iteration, stores a instance of account object (with all its attributes)
			String tmp = Banking.accList.get(i).toString();
			//if username is in string, its uniqueness guarantees it is an already established account for
			//that particular user
			if(tmp.contains("username=" + username + ",")) {
				//this block collects the password and the account numbers for given username
				tmp = tmp.replace("=", " ");
				tmp = tmp.replace(",", "");
				String[] tmps = tmp.split(" ");
				//each tmps is a list which follows this general structure:
				// [id,username,id,password,id,accNum,id,balance]. password is at index 3, account number is at index 5
				password = tmps[3];
				value = Integer.parseInt(tmps[5]);
				values.add(value);				
			}
		} 
		  //after collecting all necessary information for making a new account
		  //delete members of accList (without deleting actual structure)

		for(int j = 0; j < Banking.accList.size(); j++) {
			Banking.accList.remove(j);
		}
		readFile(applicationFile);
		Account a = new Account(username, password, Collections.max(values,null) + 1, 0);
		accList.add(a);
		writeToFile(applicationFile, accList);

		//new account is now pending approval before it goes in AccFile
	}
		
	//before an account can be made a user must be registered
	//with a username and password
	//upon successful username and password input, account 1 is automatically applied for and appending approval

	public void registration() {
		Banking b = new Banking();
		System.out.println("Enter desired username (no '=' or ',')");
		Scanner s = new Scanner(System.in);
		String username = s.nextLine();
		if((username.contains("=")) || (username.contains(","))) {
			//these characters can make the username and password ambiguous 
			//for our use of substring searching in other methods
			System.out.println("Invalid input");
			registration();
		} else {

		//reading file to check if username is already existing

		readFile(accFile);
		String tmp = Banking.accList.toString(); //converts entire account list to a single string

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
			System.out.println("Account registered, you must wait for account to be approved before logging in.");
		}
	}
	}

	//a user may deposit money to any of its owned accounts
	public void deposit(String username, String password) {

		int accountNumber = 0;
		double deposit = 0;
		//displays user's accounts to choose from.
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
			deposit = s.nextDouble();
			if(deposit < 0) {
				System.out.println("Invalid input");
				return;
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		readFile(accFile);
		//searching for specific attributes in the collection is somewhat difficult
		//we need this information to verify the user and the account number
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp = Banking.accList.get(i).toString();
			if(tmp.contains("username=" + username + ", password=" + password + ", accountNumber=" + accountNumber)) {
				tmp = tmp.replace("=", " ");
				tmp = tmp.replace(",", "");
				String[] tmps = tmp.split(" ");
				System.out.println("Old balance: " + tmps[7]); //tmps[7] holds the account balance
				Double balance = Double.parseDouble(tmps[7]);
				accList.remove(i); //removes account temporarily
				balance = balance + deposit;
				DecimalFormat df2 = new DecimalFormat("#.##");
				String tmp2 = df2.format(balance);
				Double tmpp = Double.parseDouble(tmp2);
				System.out.println("New balance: " + tmpp);
				Account a = new Account(username, password, accountNumber, tmpp); //re adds account with modified balance
				accList.add(a);
				writeToFile(accFile, accList); //this bank account has already been approved - we can write directly to accFile
				showAllAccounts(username);
				return;
			} else {
				
			}
		}
		System.out.println("Invalid input");
	}

	//very similar to withdrawl method
	public void withdrawl(String username, String password) {
		
		int accountNumber = 0;
		double withdrawal = 0;
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
			withdrawal = s.nextDouble();
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
				double balance = Double.parseDouble(tmps[7]);
				accList.remove(i);
				balance = balance - withdrawal;
				if(balance < withdrawal) {
					System.out.println("Withdrawal too large.");
					return;
				}
				DecimalFormat df2 = new DecimalFormat("#.##");
				String tmpp = df2.format(balance);
				Double tmpp2 = Double.parseDouble(tmpp);
				System.out.println("Old balance: " + tmps[7]);
				System.out.println("New balance: " + tmpp2);
				Account a = new Account(username, password, accountNumber, tmpp2);
				accList.add(a);
				writeToFile(accFile, accList);
				showAllAccounts(username);
				return;
			} else {
				
			}
		}
		System.out.println("Invalid input");	
	}
	
	//for a given username, show all their accounts for the user
	public void showAllAccounts(String username) {
		
		readFile(accFile);
		for(int i = 0; i < Banking.accList.size(); i++) {
			String tmp = Banking.accList.get(i).toString();
			if(tmp.contains("username=" + username + ",")) {
				System.out.println(tmp);
			}
		}	
	}

	//before a user makes an additional account, transfers, withdraws, or deposits, they must login
	public void login(String username, String password) {
		
		readFile(accFile);
		String tmp3 = "hereweare";
		for(int j = 0; j < Banking.accList.size(); j++) {
			String tp = Banking.accList.get(j).toString();
			//first checks to see if username exists
			if(tp.contains("username=" + username + ",")) {
				tmp3 = tp;
				break;
			}
		}
		//then checks to see if the password matches that of the account
		if(tmp3.contains("username=" + username + ", password=" + password)) {
			System.out.println("login success");
			//s.close();
		} else {
			System.out.println("Incorrect login info, please try again");
			System.exit(0);
			
		}	
	}
	
	//username and password are checked
	//but for 2 different accounts, so we need an additional check
	//compared to deposit and withdrawl
	public void transferFunds(String username, String password) {
		Account c = null;
		Account d = null;
		DecimalFormat df2 = new DecimalFormat("#.##");
		String value1 = null;
		String value2 = null;
		String tmp2 = null;
		double amount = 0;
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
				Banking.accList.set(i, c);             //dummy account1
			} else if (tmp2.contains("username=" + username + ", password=" + password + ", accountNumber=" + accNum2)) {
				value2 = tmp2;
				d = new Account("s", "s", 1001, 3);    //dummy account2 (for testing purposes)
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
		Double v1 = Double.parseDouble(values1[7]);
		Double v2 = Double.parseDouble(values2[7]);
		System.out.println("Enter transfer amount");
		try {
			amount = s.nextDouble();
			df2.format(amount);
		if(amount > v1) {
			System.out.println("Insufficient funds for transfer amount");
			return;
		}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		String tmp = df2.format(v1 - amount);
		String tmp3 = df2.format(v2 + amount);
		Double tmpp = Double.parseDouble(tmp);
		Double tmpp2 = Double.parseDouble(tmp3);
		
		Account a = new Account(username, password, accNum1, tmpp);
		Account b = new Account(username, password, accNum2, tmpp2);
		accList.remove(c);
		accList.remove(d);
		accList.add(a);
		accList.add(b);
		writeToFile(accFile, accList);
		System.out.println("Transfer Successful:");
		showAllAccounts(username);
	
	
	}



}
