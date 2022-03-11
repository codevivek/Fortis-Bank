package bus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SingletonBank {

	private static SingletonBank singleInstance = null;

	private HashMap<Long, Account> hashMapOfAccounts = null;

	private SingletonBank() {
		hashMapOfAccounts = new HashMap<>();
	}

	public static SingletonBank getSingleInstance() {
		if (singleInstance == null) {
			singleInstance = new SingletonBank();
		}
		return singleInstance;
	}

	public HashMap<Long, Account> getAccountHashMap() {
		return hashMapOfAccounts;
	}

	public HashMap<Long, Account> getCheckingAccountHashMap() {

		HashMap<Long, Account> hashMapOfCheckingAccounts = new HashMap<>();

		for (Iterator<Account> iterator = hashMapOfAccounts.values().iterator(); iterator.hasNext();) {
			Account account = iterator.next();

			if (account instanceof CheckingAccount)
				hashMapOfCheckingAccounts.put(account.getAccountNumber(), account);
		}

		return hashMapOfCheckingAccounts;
	}

	public HashMap<Long, Account> getSavingAccountHashMap() {

		HashMap<Long, Account> hashMapOfSavingAccounts = new HashMap<>();

		for (Iterator<Account> iterator = hashMapOfAccounts.values().iterator(); iterator.hasNext();) {
			Account account = iterator.next();

			if (account instanceof SavingAccount)
				hashMapOfSavingAccounts.put(account.getAccountNumber(), account);
		}

		return hashMapOfSavingAccounts;
	}

	public void add(Account newAccount) {
		singleInstance.hashMapOfAccounts.put(newAccount.getAccountNumber(), newAccount);
	}

	public void remove(Account account) {

		singleInstance.hashMapOfAccounts.remove(account.getAccountNumber());
	}

//	public void sort (NumberPredicate) {
//		
//	}

//	public void sortByAccountlNumber() {
//
//		Collections.sort(hashMapOfAccounts, new NumberPredicate());
//	}

	public void serialize(HashMap<Long, Account> hashMapOfAccounts) throws IOException {

		FileManager.serialize(hashMapOfAccounts);
		
	}

	public HashMap<Long, Account> deSerialize() throws IOException, ClassNotFoundException {

		return FileManager.deSerialize();
	}

	public void search(Long accountNumber) {

	}

	public void setListOfAccounts(HashMap<Long, Account> listOfAccounts) {
		this.hashMapOfAccounts = listOfAccounts;
	}

	public void add(long accNum, Account newAccount) {

		singleInstance.hashMapOfAccounts.put(accNum, newAccount);
	}

	public void add(ArrayList<Account> accounts) {

		for (Account account : accounts) {
			singleInstance.hashMapOfAccounts.put(account.getAccountNumber(), account);
		}
	}

	public void clear() {
		singleInstance.hashMapOfAccounts.clear();
	}

	public Account search(long accNum) {

		Account account = null;

		if (singleInstance.hashMapOfAccounts.containsKey(accNum)) {
			account = singleInstance.hashMapOfAccounts.get(accNum);
		}

		return account;
	}
}