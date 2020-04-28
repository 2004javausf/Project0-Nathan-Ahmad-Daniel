package drivers;

import java.util.Scanner;

import beans.Banking;
import beans.Employee;

public class Driver {

	public static void main(String[] args) {
		boolean quit = false;
		Scanner s = new Scanner(System.in);
		System.out.println("Welcome, please identify as a 1: User, 2: Employee, 3: Admin");
		int choice = s.nextInt();
		switch(choice) {
		case 1:
			Banking b = new Banking();
			System.out.println("Are you a new user to our bank? (1: YES, 2: NO)");
			choice = s.nextInt();
			switch(choice) {
			case 1:
				b.registration();
				break;
			
			case 2:
				
				Scanner ss = new Scanner(System.in);
				Banking c = new Banking();
				System.out.println("Enter username");
				String username = ss.nextLine();
				System.out.println("Enter password");
				String password = ss.nextLine();
				c.login(username, password);
				
				do {
				System.out.println("Please select a banking option:");
				System.out.println("1: New account");
				System.out.println("2: View all account info");
				System.out.println("3: Transfer funds between accounts");
				System.out.println("4: Deposit funds into account");
				System.out.println("5: Withdrawal funds from account");
				System.out.println("0: Quit");
				choice = s.nextInt();
				switch(choice) {
				case 1:
					c.newAccount(username);
					System.out.println("Account will be available after it is accepted");
					System.out.println("THANKS");
					break;
				
				case 2:
					c.showAllAccounts(username);
					break;
				
				case 3:
					c.transferFunds(username, password);
					break;
				case 4:
					c.deposit(username, password);
					break;
				case 5:
					c.withdrawl(username, password);
					break;
				case 0:
					quit = true;
					break;
				default:
					System.out.println("Invalid input");
					break;
				} 
				} while (!quit);
				System.out.println("TANKS");
				System.exit(0);
				
			default:
				System.out.println("Invalid input");
				break;
				
			}
		
		case 2:
			Employee e = new Employee();
			Scanner ss = new Scanner(System.in);
			System.out.println("Enter employee username.");
			String username = ss.nextLine();
			System.out.println("Enter employee password");
			String password = ss.nextLine();
			e.employeeLogin(username, password);
			
			do {
				System.out.println("Please enter employee option:");
				System.out.println("1: Approve/Deny Application");
				System.out.println("2: View applications");
				System.out.println("3: View all active accounts");
				System.out.println("0: Quit");
				choice = ss.nextInt();
				switch(choice) {
				case 1:
					Employee.showApplications();
					System.out.println("Approve(1) or deny(2)?");
					Scanner sss = new Scanner(System.in);
					String input = sss.nextLine();
					if(input.equals("1")) {
						System.out.println("Enter username of account to approve");
						username = sss.nextLine();
						e.approveApplication(username);
						System.out.println("Account approved");
						break;
					} else if(input.equals("2")) {
						System.out.println("Enter username of accout to deny");
						username = sss.nextLine();
						e.denyApplication(username);
						System.out.println("Account denied and removed");
						break;
					}
				
				case 2:
					Employee.showApplications();
					break;
				case 3:
					Employee.showActiveAccounts();
					break;
				case 0:
					quit = true;
					break;
				default:
					System.out.println("Invalid input");
					break;
				}
			} while(!quit);
			System.out.println("Thank you");
			System.exit(0);
			
		
		case 3:
			//do login method for admin
		
		default:
			System.out.println("Invalid input");
		}

	}

}
