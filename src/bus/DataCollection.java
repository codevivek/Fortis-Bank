package bus;

import java.util.ArrayList;

public class DataCollection {

	private static ArrayList<Customer> customerList = new ArrayList<>();

	public static void add(Customer customer) {

		customerList.add(customer);
	}

	public static ArrayList<Customer> getAll() {
		return customerList;
	}

	public static void remove(Customer c) {

		customerList.remove(c);
	}

	public static void setList(ArrayList<Customer> list) {

		customerList = list;
	}

}
