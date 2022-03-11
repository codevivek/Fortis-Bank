package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import bus.Account;
import bus.Customer;
import bus.RaiseException;
import bus.SingletonBank;

public class AccountDB {

	private static  Connection myConnection = null;
	private static  String mySQLQuery = null;
	private static  PreparedStatement myStatement = null;
//	private static  ResultSet myResultSet = null;
	private static  int rowAffected;

	public static boolean insert(Account account) throws SQLException {

		myConnection = DBConnection.getConnection();

		rowAffected = 0;
		
		mySQLQuery = "INSERT INTO account VALUES (?, ?, ?, ?, ?)";

		myStatement = myConnection.prepareStatement(mySQLQuery);
		
		myStatement.setLong(1, account.getAccountNumber());
		myStatement.setDouble(2, account.getBalance());
		myStatement.setDate(3, new java.sql.Date(account.getOpenedDate().getTime()));
		myStatement.setString(4, account.getAccountType().toString());
		myStatement.setInt(5, account.getCustomer().getCustomerId());

		int rowAffected = myStatement.executeUpdate();

		if (rowAffected > 0)
//		myConnection.commit();
			return true;
		else
			throw new SQLException();
	}

	public static boolean delete(long accNo) throws SQLException, RaiseException {

		myConnection = DBConnection.getConnection();

		rowAffected = 0;

		mySQLQuery = "DELETE FROM account WHERE accountNumber = ?";

		myStatement = myConnection.prepareStatement(mySQLQuery);

		myStatement.setLong(1, accNo);

		rowAffected = myStatement.executeUpdate();

		if (rowAffected > 0)
			return true;
//			myConnection.commit();
		else
			throw new RaiseException("Data not deleted");
	}

	public static Account search(long accNo) throws SQLException, RaiseException {

		myConnection = DBConnection.getConnection();

		mySQLQuery = "SELECT * FROM account WHERE accountNumber = ?";

		myStatement = myConnection.prepareStatement(mySQLQuery);

		myStatement.setLong(1, accNo);

		ResultSet myResultSet = myStatement.executeQuery();

		Account account = null;

		if (myResultSet.next()) {

			long accNumDb = myResultSet.getLong(1);
			Double balance = myResultSet.getDouble(2);
			Date openedDate = myResultSet.getDate(3);
			String accType = myResultSet.getString(4);
			int customerId = myResultSet.getInt(5);

			Customer customer = Customer.findCustomer(customerId);

			account = Account.getAccountInstance(accNumDb, accType, customer);
			account.setOpenedDate(openedDate);
			account.setBalance(balance);
			account.setCustomer(Customer.findCustomer(customerId));
		}

		myResultSet.close();
		
		return account;
	}

	public static ArrayList<Account> getAllAccountsByCustomer(Customer customer) throws SQLException, RaiseException {

		myConnection = DBConnection.getConnection();

		mySQLQuery = "SELECT * FROM account WHERE customerId = ?";

		myStatement = myConnection.prepareStatement(mySQLQuery);

		myStatement.setInt(1, customer.getCustomerId());

		ResultSet myResultSet = myStatement.executeQuery();
		
		ArrayList<Account> myList = new ArrayList<>();

		Account account = null;
		
		while (myResultSet.next()) {

			long accNumDb = myResultSet.getLong(1);
			Double balance = myResultSet.getDouble(2);
			Date openedDate = myResultSet.getDate(3);
			String accType = myResultSet.getString(4);

			account = Account.getAccountInstance(accNumDb, accType, customer);

			account.setOpenedDate(openedDate);
			account.setBalance(balance);

			account = Account.getSubTableData(account);

			myList.add(account);
		}
		
		myResultSet.close();
		
		return myList;
	}

	public static boolean update(Account account) throws SQLException {

		myConnection = DBConnection.getConnection();

		rowAffected = 0;

		mySQLQuery = "UPDATE account SET balance = ? WHERE accountnumber = ?";

		myStatement = myConnection.prepareStatement(mySQLQuery);

		myStatement.setDouble(1, account.getBalance());
		myStatement.setLong(2, account.getAccountNumber());

		rowAffected = myStatement.executeUpdate();

		myConnection.commit();

		if (rowAffected > 0)
			return true;
		else
			throw new SQLException();
	}

	public static void select() throws SQLException, RaiseException {

		myConnection = DBConnection.getConnection();

		mySQLQuery = "SELECT * FROM account";

		myStatement = myConnection.prepareStatement(mySQLQuery);

		ResultSet myResultSet = myStatement.executeQuery();

		SingletonBank shmc = SingletonBank.getSingleInstance();

		Account account = null;

		while (myResultSet.next()) {

			long accNumDb = myResultSet.getLong(1);
			Double balance = myResultSet.getDouble(2);
			Date openedDate = myResultSet.getDate(3);
			String accType = myResultSet.getString(4);
			int customerId = myResultSet.getInt(5);

			Customer customer = Customer.findCustomer(customerId);

			account = Account.getAccountInstance(accNumDb, accType, customer);
			account.setOpenedDate(openedDate);
			account.setBalance(balance);
			account.setCustomer(Customer.findCustomer(customerId));

			shmc.add(accNumDb, account);
		}
	}

}
