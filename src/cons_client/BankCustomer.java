package cons_client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import bus.Account;
import bus.EnumAccountType;
import bus.Customer;
import bus.CustomerManager;
import bus.Manager;
import bus.RaiseException;
import bus.Transaction;
import bus.EnumTransactionType;
import bus.InsufficientAmountException;

public class BankCustomer {

	public static void main(String[] args) throws IOException, RaiseException, SQLException {

		Scanner scan = new Scanner(System.in);
		CustomerManager cm = new CustomerManager();
		Manager manager = Manager.getInstance();

		Customer c = manager.createCustomer("Arjun", "Patel", 123);

		BankManager.displayCustomerDetails(c);

		System.out.println("pin " + c.getPin());

		int choice = 0;
		int cid;
		int pin;

		System.out.print("\nEnter customer id: ");
		cid = Integer.parseInt(scan.nextLine());

		System.out.print("\nEnter your pin: ");
		pin = Integer.parseInt(scan.nextLine());

		Customer customer = new Customer(cid, pin);

		Customer verifiedCustomer = manager.verifyCustomer(customer);

		if (verifiedCustomer != null) {
			System.out.println("\nWelcome, " + verifiedCustomer.getCustomerFName());

			BankManager.displayCustomerDetails(verifiedCustomer);

			while (choice <= 3) {

				System.out.println("\n1. Do Transaction");
				System.out.println("\n2. Display all Transaction");
				System.out.println("\n3. Exit");

				System.out.print("\nPlease select choice from above: ");
				choice = Integer.parseInt(scan.nextLine());

				switch (choice) {

					case 1:

						try {
							doTransaction(verifiedCustomer, cm, scan);
						} catch (SQLException | RaiseException | InsufficientAmountException e) {
//							e.printStackTrace();
							
							System.out.println(e.getMessage());
							
						}

						break;

					case 2:
						displayAllTransaction(verifiedCustomer, cm);
						break;

					case 3:
						choice = 100;
						break;
						
					default:
						choice = 0;
						break;
				}
			}
			
			Manager.saveToFile();

		} else {
			System.out.println("Invalid customer id and password");
		}

	}

	private static void displayAllTransaction(Customer customer, CustomerManager cm) {

		ArrayList<Transaction> transactions = cm.getAllTransaction(customer);

		System.out.println("->>>>>>>>>>>" + transactions.size());
		
		for (Transaction transaction : transactions) {

			System.out.print("\t" + transaction.getTransactionNumber());
			System.out.print("\t" + transaction.getAccType());
			System.out.print("\t" + transaction.getTransDate());
			System.out.print("\t" + transaction.getTransType());
			System.out.print("\t" + transaction.getTransAmount());

			System.out.println();
		}

	}

	private static void doTransaction(Customer customer, CustomerManager cm, Scanner scan) throws SQLException, RaiseException, InsufficientAmountException {

		String choice;
		int trsChoice;

		for (Account account : customer.getAccounts()) {

			System.out.print("\n");
			
			System.out.println(account.getInitials());
			
			System.out.print("\t" + account.getAccountType());
		}

		System.out.print("\nSelect choice: ");
		choice = scan.nextLine().toUpperCase();

		EnumAccountType type;

		switch (choice) {
			case "CK":

				type = EnumAccountType.Checking;
				break;

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
				type = EnumAccountType.Checking;
				break;
		}

		Transaction transaction = new Transaction();

		EnumTransactionType trsType;

		System.out.println("\n1. Deposit");
		System.out.println("\n2. Withdraw");

		System.out.print("\nSelect transaction type: ");
		trsChoice = Integer.parseInt(scan.nextLine());

		if (trsChoice == 1) {
			trsType = EnumTransactionType.Deposit;
		} else {
			trsType = EnumTransactionType.Withdraw;
		}

		System.out.println("\nEnter amount: ");
		double amount = Double.parseDouble(scan.nextLine());

		transaction.setCustomer(customer);
		transaction.setTransAmount(amount);
		transaction.setTransType(trsType);
		transaction.setAccType(type);

		boolean status = cm.doTransaction(transaction);

		if (status) {

		}

	}
}
