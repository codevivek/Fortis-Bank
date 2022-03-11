package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private DBConnection() {
	}

	private static Connection myConnection = null;

	public static Connection getConnection() {

		if (myConnection != null)
			return myConnection;

		String userName, password, service, url;

		userName = "c##fortisbank";
		password = "root";
		service = "localhost:1521:orcl";

		url = "jdbc:oracle:thin:";

		try {
			myConnection = DriverManager.getConnection(url + userName + "/" + password + "@" + service);
			System.out.println(" Connection successfull");

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println(" Connection failed  ");
		}

		return myConnection;
	}

}
