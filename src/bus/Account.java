package bus;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import data.AccountDB;

public abstract class Account implements Serializable, IOperation {

	private static final long serialVersionUID = -3482441930727611147L;

	private Long accountNumber;
	private Double balance;
	private Date openedDate;
	private ArrayList<Transaction> transactions;

	private Customer customer;

	protected Account(Long accountNumber, Customer customer) throws RaiseException, SQLException {
		super();

//		this.accountNumber = accountNumber;
		setAccountNumber(accountNumber);

//		this.openedDate = new Date();
		setOpenedDate(openedDate);

//		this.customer = customer;
		setCustomer(customer);

		setBalance(0.0);

		setOpenedDate(new Date());

		this.transactions = new ArrayList<>();
	}

	public Account() {
	}

	public abstract String getInitials();

	public abstract EnumAccountType getAccountType();

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) throws RaiseException {

		if (Validation.isDouble(balance.toString()))
			this.balance = balance;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Transaction transaction) {

		this.transactions.add(transaction);
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setOpenedDate(Date openedDate) {
		this.openedDate = openedDate;
	}

	public Date getOpenedDate() {
		return openedDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setAccountNumber(Long accountNumber) {

		this.accountNumber = accountNumber;
	}

	public void setCustomer(Customer customer) throws RaiseException, SQLException {

//		if (Validation.isValidCustomer(customer))
		this.customer = customer;
	}

	public static Account findAccount(Customer c, EnumAccountType type) {

		for (Account account : c.getAccounts())
			switch (type) {
				case Checking:
					if (account instanceof CheckingAccount)
						return account;

					break;
				case Saving:
					if (account instanceof SavingAccount)
						return account;

					break;
				case Credit:
					if (account instanceof CreditAccount)
						return account;

					break;
				case Currency:
					if (account instanceof CurrencyAccount)
						return account;

					break;

				default:
					return null;
			}

		return null;
	}

	public static Account getAccountInstance(long accNum, String accType, Customer customer)
			throws RaiseException, SQLException {

		Account account;

		if (accType.equals(EnumAccountType.Credit.toString()))
			account = new CreditAccount(accNum, customer);

		else if (accType.equals(EnumAccountType.Checking.toString()))
			account = new CheckingAccount(accNum, customer);

		else if (accType.equals(EnumAccountType.Saving.toString()))
			account = new SavingAccount(accNum, customer);

		else if (accType.equals(EnumAccountType.Currency.toString()))
			account = new CurrencyAccount(accNum, customer);
		else
			return null;

		return account;
	}

//	CRUD operations

	public static ArrayList<Account> getAllAccountsByCustomer(Customer customer) throws RaiseException {

		ArrayList<Account> accountList = new ArrayList<Account>();

		try {
			accountList = AccountDB.getAllAccountsByCustomer(customer);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountList;
	}

	public static boolean update(Account account) throws SQLException, RaiseException {

		if (account instanceof CheckingAccount)
			return CheckingAccount.update((CheckingAccount) account);

		else if (account instanceof SavingAccount)
			return SavingAccount.update((CheckingAccount) account);

		else if (account instanceof CreditAccount)
			return CreditAccount.update((CheckingAccount) account);

		else if (account instanceof CurrencyAccount)
			return CurrencyAccount.update((CheckingAccount) account);

		else
			throw new RaiseException("Something went wrong");
	}

	public static boolean add(Account account) throws SQLException, RaiseException {

		if (account instanceof CheckingAccount)
			return CheckingAccount.add((CheckingAccount) account);

		else if (account instanceof SavingAccount)
			return SavingAccount.add((SavingAccount) account);

		else if (account instanceof CreditAccount)
			return CreditAccount.add((CreditAccount) account);

		else if (account instanceof CurrencyAccount)
			return CurrencyAccount.add((CurrencyAccount) account);

		else
			throw new RaiseException("Something went wrong");
	}

	public static boolean delete(Account account) throws SQLException, RaiseException {

//		return AccountDB.delete(accNum);
		if (account instanceof CheckingAccount)
			return CheckingAccount.delete(account.accountNumber);

		else if (account instanceof SavingAccount)
			return SavingAccount.delete(account.accountNumber);

		else if (account instanceof CreditAccount)
			return CreditAccount.delete(account.accountNumber);

		else if (account instanceof CurrencyAccount)
			return CurrencyAccount.delete(account.accountNumber);

		else
			throw new RaiseException("Something went wrong");
		
	}

	public static Account search(long accNum) throws SQLException, RaiseException {

		return AccountDB.search(accNum);
	}

	public static void select() throws SQLException, RaiseException {

		AccountDB.select();
	}

	public static Account getSubTableData(Account account) throws RaiseException, SQLException {

		if (account instanceof CheckingAccount)
			return CheckingAccount.findAccount(account.accountNumber);

		else if (account instanceof SavingAccount)
			return SavingAccount.findAccount(account.accountNumber);

		else if (account instanceof CreditAccount)
			return CreditAccount.findAccount(account.accountNumber);

		else if (account instanceof CurrencyAccount)
			return CurrencyAccount.findAccount(account.accountNumber);

		else
			throw new RaiseException("Something went wrong");
	}
}
