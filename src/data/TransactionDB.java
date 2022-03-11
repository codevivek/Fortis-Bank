package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bus.Customer;
import bus.EnumAccountType;
import bus.EnumTransactionType;
import bus.RaiseException;
import bus.Transaction;

public class TransactionDB {

	static private Connection myConnection = null;
	static private String mySQLCommand = null;
//	static private String mySQLQuery = null;
	static private PreparedStatement myStatement = null;
	static private ResultSet myResultSet = null;
	
	public static boolean insert(Transaction transaction) throws SQLException {

		myConnection = DBConnection.getConnection();

		mySQLCommand = "INSERT INTO transaction (transactionNumber, description, transDate, transType, transAmount, accountType, customerId) VALUES(?, ?, ?, ?, ?, ?, ?)";

		try {

			myStatement = myConnection.prepareStatement(mySQLCommand);

			myStatement.setLong(1, transaction.getTransactionNumber());
			myStatement.setString(2, transaction.getDescription());
			myStatement.setDate(3, new Date(transaction.getTransDate().getTime()));
			myStatement.setString(4, transaction.getTransType().toString());
			myStatement.setDouble(5, transaction.getTransAmount());
			myStatement.setString(6, transaction.getAccType().toString());
			myStatement.setInt(7, transaction.getCustomer().getCustomerId());

			int rowAffected = myStatement.executeUpdate();

			myConnection.commit();

			if (rowAffected > 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean delete(int id) throws SQLException {

		myConnection = DBConnection.getConnection();
		mySQLCommand = "DELETE FROM transaction WHERE transactionNumber = ?";

		try {

			myStatement = myConnection.prepareStatement(mySQLCommand);

			myStatement.setInt(1, id);

			int rowAffected = myStatement.executeUpdate();

			myConnection.commit();

			if (rowAffected > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Transaction search(long transNum) throws SQLException, SQLException, RaiseException {

		myConnection = DBConnection.getConnection();

		mySQLCommand = "SELECT * FROM transaction WHERE transactionNumber = ?";

		myStatement = myConnection.prepareStatement(mySQLCommand);

		myStatement.setLong(1, transNum);

		myResultSet = myStatement.executeQuery();

		Transaction transaction = null;

		if (myResultSet.next()) {

			long transactionNumber = myResultSet.getLong(1);
			String description = myResultSet.getString(2);
			Date transDate = myResultSet.getDate(3);
			String transType = myResultSet.getString(4);
			Double transAmount = myResultSet.getDouble(5);
			String accType = myResultSet.getString(6);
			int cid = myResultSet.getInt(7);

			EnumAccountType type;

			if (accType.equals(EnumAccountType.Credit.toString()))
				type = EnumAccountType.Credit;

			else if (accType.equals(EnumAccountType.Checking.toString()))
				type = EnumAccountType.Checking;

			else if (accType.equals(EnumAccountType.Saving.toString()))
				type = EnumAccountType.Saving;

			else if (accType.equals(EnumAccountType.Currency.toString()))
				type = EnumAccountType.Currency;

			else
				return null;

			EnumTransactionType transactionType;

			if (transType.equals(EnumTransactionType.Deposit.toString()))
				transactionType = EnumTransactionType.Deposit;

			else if (transType.equals(EnumTransactionType.Withdraw.toString()))
				transactionType = EnumTransactionType.Withdraw;
			else
				return null;

			Customer customer = Customer.findCustomer(cid);

			transaction = new Transaction(transactionNumber, description, transDate, transactionType, transAmount, type,
					customer);

		}

		return transaction;
	}

	public static ArrayList<Transaction> getAllTransactionByCustIdType(int id, EnumAccountType accType)
			throws SQLException, NumberFormatException, RaiseException {

		myConnection = DBConnection.getConnection();

		mySQLCommand = "SELECT * FROM transaction WHERE customerId = ? and accountType = ?";

		myStatement = myConnection.prepareStatement(mySQLCommand);

		myStatement.setInt(1, id);
		myStatement.setString(2, accType.toString());

		myResultSet = myStatement.executeQuery();

		ArrayList<Transaction> myList = new ArrayList<>();

//		Account account = null;

		Transaction transaction;

		while (myResultSet.next()) {

			long transactionNumber = myResultSet.getLong(1);
			String description = myResultSet.getString(2);
			Date transDate = myResultSet.getDate(3);
			String transType = myResultSet.getString(4);
			Double transAmount = myResultSet.getDouble(5);
			String accTypeDb = myResultSet.getString(6);
			int cid = myResultSet.getInt(7);

			EnumAccountType type;

			if (accTypeDb.equals(EnumAccountType.Credit.toString()))
				type = EnumAccountType.Credit;

			else if (accTypeDb.equals(EnumAccountType.Checking.toString()))
				type = EnumAccountType.Checking;

			else if (accTypeDb.equals(EnumAccountType.Saving.toString()))
				type = EnumAccountType.Saving;

			else if (accTypeDb.equals(EnumAccountType.Currency.toString()))
				type = EnumAccountType.Currency;

			else
				return null;

			EnumTransactionType transactionType;

			if (transType.equals(EnumTransactionType.Deposit.toString()))
				transactionType = EnumTransactionType.Deposit;

			else if (transType.equals(EnumTransactionType.Withdraw.toString()))
				transactionType = EnumTransactionType.Withdraw;
			else
				return null;

			Customer customer = Customer.findCustomer(cid);

			transaction = new Transaction(transactionNumber, description, transDate, transactionType, transAmount, type,
					customer);

			myList.add(transaction);
		}

		return myList;
	}

	public static long getLastNumber() {

		myConnection = DBConnection.getConnection();

		mySQLCommand = "SELECT transactionNumber FROM transaction ORDER BY transactionNumber desc";

		try {

			myStatement = myConnection.prepareStatement(mySQLCommand);

			myResultSet = myStatement.executeQuery();
			
			if (myResultSet.next()) {
				
				return myResultSet.getLong(1);
			}
			
		} catch (Exception ex) {

		}

		return 0;
	}

}
