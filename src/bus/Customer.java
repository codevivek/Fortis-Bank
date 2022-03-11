package bus;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.CustomerDB;

public class Customer implements Serializable {

	private static final long serialVersionUID = 7548778313303709192L;

	private int customerId;
	private String customerFName;
	private String customerLName;
	private int pin;

	private List<Account> accounts = new ArrayList<Account>();

	public Customer(int customerId, String customerFName, String customerLName, int pin) throws RaiseException {
		super();
//		this.customerId = customerId;
		setCustomerId(customerId);

//		this.customerFName = customerFName;
		setCustomerFName(customerFName);

//		this.customerLName = customerLName;
		setCustomerLName(customerLName);

//		this.pin = pin;
		setPin(pin);
	}

	public Customer(int customerId, int pin) {
		this.customerId = customerId;
		this.pin = pin;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) throws RaiseException {

		if (Validation.isInRange(Integer.toString(customerId), 4, 6))
			this.customerId = customerId;
	}

	public String getCustomerFName() {
		return customerFName;
	}

	public void setCustomerFName(String customerFName) throws RaiseException {
		
		if (Validation.isChar(customerFName))
			this.customerFName = customerFName;
	}

	public String getCustomerLName() {
		return customerLName;
	}

	public void setCustomerLName(String customerLName) throws RaiseException {

		if (Validation.isChar(customerLName))
			this.customerLName = customerLName;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {

		this.pin = pin;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccount(Account account) {
		accounts.add(account);
	}



	public static Customer findCustomer(int cid) throws SQLException, RaiseException {

		Customer c = CustomerDB.search(cid);

		return c;
	}

	public static ArrayList<Customer> getAll() throws RaiseException {

		ArrayList<Customer> customers = null;

		try {
			customers = CustomerDB.getAllCustomers();

			for (Customer customer : customers) {

				ArrayList<Account> accounts = Account.getAllAccountsByCustomer(customer);

				for (Account account : accounts) {

					ArrayList<Transaction> transactions = Transaction
							.getAllTransactionByCustIdType(customer.getCustomerId(), account.getAccountType());

					for (Transaction transaction : transactions) {
						account.setTransactions(transaction);
					}
					customer.setAccount(account);
				}

			}

		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			
			customers = new ArrayList<>();
		}

		DataCollection.setList(customers);
		return customers;
	}

	public static boolean add(Customer customer) throws RaiseException, SQLException {

		boolean status = CustomerDB.insert(customer);

		if (status)
			for (Account account : customer.accounts)
				Account.add(account);

		return true;

	}

	public boolean updateToDb() {

		boolean status = false;

		try {
			status = CustomerDB.update(this);

			return status;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean remove(Customer customer) throws SQLException {

		return CustomerDB.delete(customer.customerId);
	}

	public static Customer getDetails(int id) throws SQLException, RaiseException {
		
		Customer customer = findCustomer(id);
		
		for (Account account : Account.getAllAccountsByCustomer(customer)) {

			ArrayList<Transaction> transactions = Transaction
					.getAllTransactionByCustIdType(customer.getCustomerId(), account.getAccountType());

			for (Transaction transaction : transactions) {
				account.setTransactions(transaction);
			}
			customer.setAccount(account);
		}
		
		return customer;
	}

}
