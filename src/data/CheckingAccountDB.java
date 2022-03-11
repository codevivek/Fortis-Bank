package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bus.Account;
import bus.CheckingAccount;
import bus.RaiseException;
import bus.SingletonBank;

public class CheckingAccountDB {

	static private Connection myConnection = null;
	static private String mySQLQuery = null;
	static private PreparedStatement myStatement = null;
//	static private ResultSet myResultSet = null;
	static private int rowAffected;

	/**
	 * @param accountnumber primary key of account
	 * @return return true if insert successfully otherwise false
	 * @throws RaiseException 
	 */

	public static boolean insert(CheckingAccount account) throws SQLException, RaiseException {

		myConnection = DBConnection.getConnection();

//		mySQLQuery = "INSERT INTO account VALUES(" + account.getAccountNumber() + ", " + account.getBalance() + ", "
//				+ account.getOpenedDate() + ", " + "'" + account.getAccountType() + "', "
//				+ account.getCustomer().getCustomerId() + ")";

		if (AccountDB.insert(account)) {

			rowAffected = 0;

			mySQLQuery = "INSERT INTO checkingaccount VALUES(?, ?)";

			myStatement = myConnection.prepareStatement(mySQLQuery);

			myStatement.setLong(1, account.getAccountNumber());

			myStatement.setInt(2, account.getFreeLimitPerMonth());

			rowAffected = myStatement.executeUpdate();

			if (rowAffected > 0)
				myConnection.commit();
			else
				throw new RaiseException("Data not inserted");
		} else
			throw new RaiseException("Data not inserted");

		return true;
	}

	/**
	 * @param accnumber primary key of account
	 * @return return true if updated successfully otherwise false
	 * @throws SQLException
	 * @throws RaiseException 
	 */

	public static boolean update(CheckingAccount account) throws SQLException, RaiseException {

		myConnection = DBConnection.getConnection();

		if (AccountDB.update(account)) {

			mySQLQuery = "UPDATE checkingaccount SET freeLimitperMonth = ?" + "WHERE accountnumber = ?";

			myStatement = myConnection.prepareStatement(mySQLQuery);

			myStatement.setInt(1, account.getFreeLimitPerMonth());
			myStatement.setLong(2, account.getAccountNumber());

			rowAffected = 0;

			rowAffected = myStatement.executeUpdate();

			if (rowAffected > 0)
				myConnection.commit();
			else
				throw new RaiseException("Data not updated");
		} else
			throw new RaiseException("Data not updated");

		return true;
	}

	/**
	 * @param accnumber primary key of account
	 * @return return true if removed successfully otherwise false
	 * @throws SQLException
	 * @throws RaiseException 
	 */
	public static boolean delete(long accNum) throws SQLException, RaiseException {
		myConnection = DBConnection.getConnection();

		rowAffected = 0;

		mySQLQuery = "DELETE FROM checkingaccount WHERE accountnumber = ?";

		myStatement = myConnection.prepareStatement(mySQLQuery);

		myStatement.setLong(1, accNum);

		rowAffected = myStatement.executeUpdate();

		if (rowAffected > 0)
			if (AccountDB.delete(accNum))
				myConnection.commit();
			else
				throw new RaiseException("Data not deleted");
		else
			throw new RaiseException("Data not deleted");

		return true;
	}

	public static Account search(long accNum) throws SQLException, RaiseException{

		myConnection = DBConnection.getConnection();

		Account account = Account.search(accNum);

		if (account != null) {
			mySQLQuery = "SELECT * FROM checkingaccount WHERE accountnumber = ?";

			myStatement = myConnection.prepareStatement(mySQLQuery);

			myStatement.setLong(1, accNum);

			ResultSet myResultSet = myStatement.executeQuery();

			if (myResultSet.next())
				if (account instanceof CheckingAccount)
					((CheckingAccount) account).setFreeLimitPerMonth(myResultSet.getInt(2));

			myResultSet.close();
		}
	
		return account;
	}

	public static ArrayList<Account> select() throws SQLException, RaiseException {

		ArrayList<Account> accounList = new ArrayList<Account>();

		myConnection = DBConnection.getConnection();
		SingletonBank shmc = SingletonBank.getSingleInstance();

		Account.select();

		if (shmc.getAccountHashMap().size() > 0) {

			mySQLQuery = "SELECT * FROM checkingaccount";

			myStatement = myConnection.prepareStatement(mySQLQuery);
			ResultSet myResultSet = myStatement.executeQuery();

			Account account = null;

			while (myResultSet.next()) {

				account = shmc.search(myResultSet.getLong(1));

				if (account instanceof CheckingAccount)
					((CheckingAccount) account).setFreeLimitPerMonth(myResultSet.getInt(2));

				accounList.add(account);
				
				myResultSet.close();
			}
		}

		return accounList;
	}

}
