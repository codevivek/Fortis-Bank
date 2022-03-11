package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bus.Customer;
import bus.RaiseException;

public class CustomerDB {

	static private Connection myConnection = null;
	static private String mySQLCommand = null;
	static private String mySQLQuery = null;
	static private PreparedStatement myStatement = null;
//	static private ResultSet myResultSet = null;
	static private int rowAffected;

	/*
	 * return 1 if added successfully otherwise 0
	 */
	public static boolean insert(Customer customer) throws SQLException, RaiseException {

		myConnection = DBConnection.getConnection();

		rowAffected = 0;

		mySQLCommand = "INSERT INTO customer (customerId, fname, lname, pin) VALUES(?, ?, ?, ?)";

		myStatement = myConnection.prepareStatement(mySQLCommand);

		myStatement.setInt(1, customer.getCustomerId());
		myStatement.setString(2, customer.getCustomerFName());
		myStatement.setString(3, customer.getCustomerLName());
		myStatement.setInt(4, customer.getPin());

		int rowAffected = myStatement.executeUpdate();

		if (rowAffected > 0) {
			myConnection.commit();
			return true;
		} else
			throw new RaiseException("Customer not created");
	}

	public static boolean update(Customer customer) throws SQLException {

		myConnection = DBConnection.getConnection();
		rowAffected = 0;

		mySQLCommand = "UPDATE customer SET fname = ?, lname = ?, pin = ? WHERE customerId = ?";

		try {

			myStatement = myConnection.prepareStatement(mySQLCommand);

			myStatement.setString(1, customer.getCustomerFName());
			myStatement.setString(2, customer.getCustomerLName());
			myStatement.setInt(3, customer.getPin());
			myStatement.setInt(4, customer.getCustomerId());

			rowAffected = myStatement.executeUpdate();

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

	public static boolean delete(int id) throws SQLException {

		myConnection = DBConnection.getConnection();
		rowAffected = 0;

		
		

		try {

			mySQLCommand = "DELETE FROM savingaccount WHERE customerId = ?";

			myStatement = myConnection.prepareStatement(mySQLCommand);

			myStatement.setInt(1, id);

			mySQLCommand = "DELETE FROM account WHERE customerId = ?";

			myStatement = myConnection.prepareStatement(mySQLCommand);

			myStatement.setInt(1, id);

			
			mySQLCommand = "DELETE FROM checkingaccount WHERE customerId = ?";

			myStatement = myConnection.prepareStatement(mySQLCommand);

			myStatement.setInt(1, id);
			
			mySQLCommand = "DELETE FROM customer WHERE customerId = ?";

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

	public static Customer search(int id) throws SQLException, RaiseException {

		myConnection = DBConnection.getConnection();

		mySQLCommand = "SELECT * FROM customer WHERE customerId = ?";

		myStatement = myConnection.prepareStatement(mySQLCommand);

		myStatement.setInt(1, id);

		ResultSet myResultSet = myStatement.executeQuery();

		Customer customer = null;

		if (myResultSet.next()) {

			customer = new Customer(myResultSet.getInt(1), myResultSet.getString(2), myResultSet.getString(3),
					myResultSet.getInt(4));
		}

		myResultSet.close();
		
		return customer;
	}

	public static ArrayList<Customer> getAllCustomers() throws SQLException, NumberFormatException, RaiseException {

		myConnection = DBConnection.getConnection();

		mySQLQuery = "SELECT * FROM customer";

		myStatement = myConnection.prepareStatement(mySQLQuery);

		ResultSet myResultSet = myStatement.executeQuery();

		ArrayList<Customer> myList = new ArrayList<>();

		Customer customer = null;

		while (myResultSet.next()) {

			customer = new Customer(myResultSet.getInt(1), myResultSet.getString(2), myResultSet.getString(3),
					myResultSet.getInt(4));

			myList.add(customer);
		}

		myResultSet.close();
		
		return myList;
	}

}
