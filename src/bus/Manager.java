package bus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Manager {

	private static Manager manager;

	private Manager() {
	}

	static {
//		ArrayList<Customer> customerList = FileManager.readCustomerList();

		ArrayList<Customer> customerList;
		try {
			customerList = Customer.getAll();
		} catch (RaiseException e) {
			e.printStackTrace();

			customerList = new ArrayList<>();
		}

		DataCollection.setList(customerList);
		
	}

	public static Manager getInstance() {
		System.out.println(DataCollection.getAll().size());

		if (manager == null)
			return new Manager();
		else
			return manager;
	}

	public Customer createCustomer(String fname, String lname, int pin) throws RaiseException, SQLException {

		int customerId = getNewCustomerId();

		Customer customer = new Customer(customerId, fname, lname, pin);

		if (Customer.add(customer)) {

			openNewAccount(customer, EnumAccountType.Checking);

			DataCollection.add(customer);
			return customer;
		} else
			return null;
	}

	public ArrayList<Customer> getCustomerList() throws RaiseException {

//		return DataCollection.getAll();

		return Customer.getAll();
	}

	public static ArrayList<Account> getAccountListByType(EnumAccountType type) {

		ArrayList<Account> accounts = new ArrayList<>();

		for (Customer customer : DataCollection.getAll()) {

			for (Account account : customer.getAccounts()) {

				switch (type) {
					case Checking:
						if (account instanceof CheckingAccount)
							accounts.add(account);
						break;

					case Saving:

						if (account instanceof SavingAccount)
							accounts.add(account);

						break;
					case Credit:

						if (account instanceof CreditAccount)
							accounts.add(account);

						break;
					case Currency:
						if (account instanceof CurrencyAccount)
							accounts.add(account);

						break;

					default:
						break;
				}

			}

		}

		return accounts;
	}

	private static int getNewCustomerId() {

		int newAccNumber;

		ArrayList<Integer> ids = new ArrayList<>();

		for (Customer c : DataCollection.getAll())
			ids.add(c.getCustomerId());

		do {

			newAccNumber = 80000000 + (int) (Math.random() * (90000000 - 80000000));

		} while (ids.contains(newAccNumber));

		return newAccNumber;
	}

	private static long getNewAccountNumberByType(EnumAccountType type) {

		long newAccNumber;
		int upperLimit = 0;
		int lowerLimit = 0;

		ArrayList<Account> accounts = getAccountListByType(type);

		ArrayList<Long> accNumberList = new ArrayList<Long>();

		for (Account a : accounts)
			accNumberList.add(a.getAccountNumber());

		switch (type) {

			case Checking:

				upperLimit = Constants.ACC_UPPER_lIMIT_CHECKING;
				lowerLimit = Constants.ACC_LOWER_lIMIT_CHECKING;

				break;

			case Saving:

				upperLimit = Constants.ACC_UPPER_lIMIT_SAVING;
				lowerLimit = Constants.ACC_LOWER_lIMIT_SAVING;

				break;
			case Currency:

				upperLimit = Constants.ACC_UPPER_lIMIT_CURRENCY;
				lowerLimit = Constants.ACC_LOWER_lIMIT_CURRENCY;

				break;

			case Credit:

				upperLimit = Constants.ACC_UPPER_lIMIT_CREDIT;
				lowerLimit = Constants.ACC_LOWER_lIMIT_CREDIT;

				break;

			default:
				break;
		}

		do {
			newAccNumber = lowerLimit + (long) (Math.random() * (upperLimit - lowerLimit));

		} while (accNumberList.contains(newAccNumber));

		return newAccNumber;
	}

	public Customer verifyCustomer(Customer customer) throws SQLException, RaiseException {

		Customer c = this.findCustomerById(customer.getCustomerId());

		if (c == null)
			return null;

		if (customer.getPin() == c.getPin())
			return c;

		return null;
	}

	public Customer findCustomerById(int id) throws SQLException, RaiseException {

//		for (Customer c : DataCollection.getAll())
//			if (c.getCustomerId() == id)
//
//				return c;

		return Customer.getDetails(id);
	}

	public Account openCheckingAccount(Customer customer) throws RaiseException, SQLException {

		long accNumber = getNewAccountNumberByType(EnumAccountType.Checking);

		return new CheckingAccount(accNumber, customer);

	}

	public Account openCurrencyAccount(Customer customer) throws RaiseException, SQLException {

		long accNumber = getNewAccountNumberByType(EnumAccountType.Currency);

		return new CurrencyAccount(accNumber, customer);
	}

	public Account openSavingAccount(Customer customer) throws RaiseException, SQLException {

		long accNumber = getNewAccountNumberByType(EnumAccountType.Saving);

		return new SavingAccount(accNumber, customer);
	}

	public Account openCreditAccount(Customer customer) throws RaiseException, SQLException {

		long accNumber = getNewAccountNumberByType(EnumAccountType.Credit);

		return new CreditAccount(accNumber, customer);
	}

	public boolean openNewAccount(Customer c, EnumAccountType type) throws SQLException, RaiseException {

//		Account.getAllAccountsByCustomer(c);

		Account account = null;
		
		account = Account.findAccount(c, type);
		
		System.out.println(account);
		
		if (account != null) {
			System.out.println("2");
			throw new RaiseException(type.toString() + "account is already exist");
		}		

		System.out.println(account);
		
		switch (type) {
			case Checking:

				account = openCheckingAccount(c);
				break;

			case Saving:

				account = openSavingAccount(c);
				break;

			case Credit:

				account = openCreditAccount(c);
				break;

			case Currency:

				account = openCurrencyAccount(c);
				break;

			default:
				throw new RaiseException("Invalid account type");
		}

		try {

			if (account != null) {

				if (Account.add(account)) {

					c.setAccount(account);

					saveToFile();

					return true;
				}
			}

			throw new RaiseException("Account not created");

		} catch (IOException e) {
			e.printStackTrace();

			return false;
		}

	}

	public boolean closeAccount(Customer customer, EnumAccountType type) throws RaiseException, SQLException {

		if (Account.findAccount(customer, type) == null)
			throw new RaiseException(type.toString() + " account not found");

//		if (Customer.remove(customer)) 
		{

			for (Customer c : DataCollection.getAll())
				if (customer.getCustomerId() == c.getCustomerId()) {

					Account a = Account.findAccount(c, type);

					if (a != null) {
						Account.delete(a);
						
						c.getAccounts().remove(a);
					}
					else
						return false;
				}
		}

		try {
			saveToFile();

			return true;
		} catch (IOException e) {
			e.printStackTrace();

			return false;
		}
	}

	public boolean removeCustomer(Customer c) throws RaiseException, SQLException {

		if (findCustomerById(c.getCustomerId()) != null) {
			DataCollection.remove(c);

			return Customer.remove(c);

		} else {
			throw new RaiseException("Customer not found");
		}
	}

	public static void saveToFile() throws IOException {
		FileManager.writeToSerializedFile(DataCollection.getAll());
	}

	public boolean updateCustomer(Customer updateCustomer) throws RaiseException, SQLException {

		Customer customer = (findCustomerById(updateCustomer.getCustomerId()));

		if (customer != null) {
			customer.setCustomerFName(updateCustomer.getCustomerFName());
			customer.setCustomerLName(updateCustomer.getCustomerLName());

			boolean status = customer.updateToDb();

			return status;
		}

		return false;
	}

}
