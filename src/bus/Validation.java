package bus;

import java.sql.SQLException;

public class Validation {

	public static boolean isInRange(String value, int i, int j) throws RaiseException {

		if(value.length() < i && value.length() > j)
			throw new RaiseException("Value not in range");
		
		return true;

	}

	public static boolean isChar(String value) throws RaiseException {

		for (int i = 0; i < value.length(); i++)
			if (!Character.isLetter(value.charAt(i)))
				throw new RaiseException("Enter value is not a String");

		return true;
	}

	public static boolean isDigit(String value) throws RaiseException {

		for (int i = 0; i < value.length(); i++)
			if (Character.isLetter(value.charAt(i)))
				throw new RaiseException("Enter value is not a digit");

		return true;
	}

	public static boolean isDouble(String value) throws RaiseException {

		try {
			Double.parseDouble(value);
		} catch (Exception e) {
			throw new RaiseException("Enter value is not a double value");
		}

		return true;
	}

	public static boolean isValidCustomer(Customer customer) throws RaiseException, SQLException {
		
		
		if(Customer.findCustomer(customer.getCustomerId()) != null)
			return true;
		else 
			throw new RaiseException("Invalid Customer!");
		
	}
}