package bus;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class FileManager {

	public static void serialize(HashMap<Long, Account> hashMapOfAccounts) throws IOException {

		FileOutputStream fos = new FileOutputStream("src//data//bank.ser.ser");

		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(hashMapOfAccounts);

		fos.close();
	}

	@SuppressWarnings("unchecked")
	public static HashMap<Long, Account> deSerialize() throws IOException, ClassNotFoundException {

		HashMap<Long, Account> listOfCustomersFromFile = new HashMap<>();

		FileInputStream fis = new FileInputStream("src//data//bank.ser.ser");

		ObjectInputStream ois = new ObjectInputStream(fis);

		listOfCustomersFromFile = (HashMap<Long, Account>) ois.readObject();
		fis.close();

		return listOfCustomersFromFile;
		
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Customer> readCustomerList() throws IOException, ClassNotFoundException {

		ArrayList<Customer> listOfCustomersFromFile = new ArrayList<>();

		FileInputStream fis = new FileInputStream("src//data//bank.ser.ser");

		ObjectInputStream ois = new ObjectInputStream(fis);

		listOfCustomersFromFile = (ArrayList<Customer>) ois.readObject();
		fis.close();

		return listOfCustomersFromFile;
	}

	public static void writeToSerializedFile(ArrayList<Customer> customers) throws IOException {

		FileOutputStream fos = new FileOutputStream("src//data//bank.ser.ser");

		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(customers);

		fos.close();
	}
}
