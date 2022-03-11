package cons_client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import bus.Account;
import bus.EnumAccountType;
import bus.Customer;
import bus.Manager;
import bus.RaiseException;

public class BankManager {

	public static void main(String[] args) throws IOException {

		Scanner scan = new Scanner(System.in);

		System.out.println("-------------Manager Tester-------------");

//		create one customer to test

		Manager manager = Manager.getInstance();

//		Customer customer = manager.createCustomer("Arjun", "Patel", 000);

//		displayCustomerDetails(customer);

		int choice = 0;

		while (choice >= 0 && choice < 7) {

//			manager menu

			displayCustomerMenu();

			choice = getIntegerChoice(scan);

			switch (choice) {
				case 1:

					Customer c = createCustomer(scan, manager);

					if (c != null)
						displayCustomerDetails(c);

					break;

				case 2:

					openAccount(scan, manager);
					break;

				case 3:

					closeAccount(scan, manager);
					break;

				case 4:

					removeCustomer(scan, manager);
					break;

				case 5:

					displayCustomerById(scan, manager);
					break;

				case 6:

					displayAllCustomers(manager);
					break;

				case 7:

					choice = 100;
					break;

				case 0:

					System.out.println("\nInvalid Choice");

				default:
					System.out.println("Something went wrong!");
					System.exit(0);
			}
		}

		Manager.saveToFile();
	}

	private static int getIntegerChoice(Scanner scan) {

		try {
			return Integer.parseInt(scan.nextLine());

		} catch (Exception e) {
			return 0;
		}
	}

	private static void displayCustomerMenu() {

		System.out.println("\n1. Create a customer");
		System.out.println("2. Open an account for customer.");
		System.out.println("3. Close an account");
		System.out.println("4. Remove customer");
		System.out.println("5. Display customer By id");
		System.out.println("6. Display customer list");
		System.out.println("7. Exit");

		System.out.print("\nSelect option from above: ");

	}

//	this method displays customer data by id
	private static void displayCustomerById(Scanner scan, Manager manager) {

		int cid;

//		get customer id from user

		System.out.print("\nEnter customer id: ");
		try {

			cid = getIntegerChoice(scan);

			Customer c = manager.findCustomerById(cid);

			if (c != null)
				displayCustomerDetails(c);

			else
				System.out.println("Customer not found");

		} catch (Exception e) {
			System.out.println("\nSomething went wrong!:\t" + e.getMessage());
		}
	}

//	remove customer from bank

	private static void removeCustomer(Scanner scan, Manager manager) {

		int cid;

		System.out.print("\nEnter customer id: ");

		try {

			cid = Integer.parseInt(scan.nextLine());

			Customer c = manager.findCustomerById(cid);

			if (c != null) {

				manager.removeCustomer(c);

				System.out.println("Customer Removed : id" + cid);
			} else
				System.out.println("Customer not found");

		} catch (Exception e) {

			System.out.println("\nSomething went wrong!:\t" + e.getMessage());
		}
	}

//	close account by account type

	private static void closeAccount(Scanner scan, Manager manager) {

		int cid;

		System.out.print("\nEnter customer id: ");

		try {

			cid = getIntegerChoice(scan);

			Customer c = manager.findCustomerById(cid);

			if (c != null) {

				displayCustomerDetails(c);

				System.out.println("SA Saving Account");
				System.out.println("CR Credit Account");
				System.out.println("CU Currency Account");

//				get account type from user

				System.out.print("\nEnter account type: ");

				String accChoice = scan.nextLine().toUpperCase();

				EnumAccountType type;

				switch (accChoice) {
					case "SA":

						type = EnumAccountType.Saving;
						break;

					case "CR":

						type = EnumAccountType.Credit;
						break;

					case "CU":

						type = EnumAccountType.Currency;
						break;

					default:
						throw new RaiseException("Invalid choice for Account type");
				}

				manager.closeAccount(c, type);

				System.out.println("\n" + type.toString() + " account removed");
			}

		} catch (Exception e) {
			System.out.println("\nSomething went wrong!:\t" + e.getMessage());
		}
	}

//	open new account

	private static void openAccount(Scanner scan, Manager manager) {

		int cid;

		System.out.print("\nEnter customer id: ");

		try {

			cid = getIntegerChoice(scan);

			Customer c = manager.findCustomerById(cid);

			if (c != null) {

				displayCustomerDetails(c);

				System.out.println("\nSA Saving Account");
				System.out.println("CR Credit Account");
				System.out.println("CU Currency Account");

				System.out.print("\nEnter account type: ");

				String accChoice = scan.nextLine().toUpperCase();

				EnumAccountType type;

				switch (accChoice) {
					case "SA":

						type = EnumAccountType.Saving;
						break;

					case "CR":

						type = EnumAccountType.Credit;
						break;

					case "CU":

						type = EnumAccountType.Currency;
						break;

					default:
						throw new RaiseException("Invalid choice for Account type");
				}

				manager.openNewAccount(c, type);

				System.out.println("\n" + type.toString() + " account created");

				displayCustomerDetails(c);

			} else
				System.out.println("\nCustomer not found\n");

		} catch (Exception e) {
			System.out.println("\nSomething went wrong!:\t" + e.getMessage());
		}

	}

	public static void displayCustomerDetails(Customer customer) {
		System.out.println("\n\nCustomer Id: " + customer.getCustomerId());
		System.out.println("Fname: " + customer.getCustomerFName());
		System.out.println("Lname: " + customer.getCustomerLName());

		for (Account account : customer.getAccounts()) {

			System.out.println("\n" + account.getAccountType() + " account");

			System.out.println("\tAccount number: " + account.getAccountNumber());
			System.out.println("\tAccount balance: " + account.getBalance());
		}
	}

	private static Customer createCustomer(Scanner scan, Manager manager) {

		String fname;
		String lname;
		int pin;

		try {

			System.out.print("\nEnter Customer's First Name: ");
			fname = scan.nextLine();

			System.out.print("\nEnter Customer's Last Name: ");
			lname = scan.nextLine();

			System.out.print("\nEnter Customer's PIN: ");
			pin = Integer.parseInt(scan.nextLine());

			Customer customer = manager.createCustomer(fname, lname, pin);

			return customer;

		} catch (Exception e) {

			System.out.println("\nSomething went wrong!:\t" + e.getMessage());
			return null;
		}

	}

	public static void displayAllCustomers(Manager manager) {

		ArrayList<Customer> customers;
		try {
			customers = manager.getCustomerList();

			System.out.println("\nList of Customers:");

			for (Customer c : customers)
				displayCustomerDetails(c);

		} catch (RaiseException e) {
			System.out.println("\nError: " + e.getMessage());
		}
	}

}
