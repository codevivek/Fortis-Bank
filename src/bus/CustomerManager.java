package bus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class CustomerManager {

	private static long transactionNumber = 999999L;

	static {
		transactionNumber = Transaction.getNextTransactionNumberFromDb();
	}

	private static ArrayList<Long> transactionNumberList = new ArrayList<Long>();

	public boolean doTransaction(Transaction transaction) throws SQLException, RaiseException, InsufficientAmountException {

		Customer c = transaction.getCustomer();

		TransactionValidator ctv = new TransactionValidator(transaction);

		boolean result = ctv.validateTransactionLimit();

		if (result) {

		}
		Account acc = Account.findAccount(c, transaction.getAccType());

		Double newBalance;

		switch (transaction.getTransType()) {
			case Deposit:

				newBalance = acc.getBalance() + transaction.getTransAmount();

				acc.setBalance(newBalance);

//						System.out.println("---->>>" + newBalance);
				break;

			case Withdraw:

				if (acc.getBalance() < transaction.getTransAmount())
					throw new InsufficientAmountException("Not enough fund");

				newBalance = acc.getBalance() - transaction.getTransAmount();

				acc.setBalance(newBalance);
//						System.out.println("---->>>" + newBalance);

				break;

			default:
				break;
		}

		transaction.setTransactionNumber(getNewTreansactionNumber());

		transaction.setTransDate(new Date());

		acc.setTransactions(transaction);

		boolean status = transaction.saveToDb();

		if (status)
			Account.update(acc);

		return true;

	}

	private static long getNewTreansactionNumber() {

		transactionNumber++;

		transactionNumberList.add(transactionNumber);

		return transactionNumber;
	}

	public ArrayList<Transaction> getAllTransaction(Customer c) {

		ArrayList<Transaction> transactions = new ArrayList<Transaction>();

		for (Account account : c.getAccounts())
			transactions.addAll(account.getTransactions());

		return transactions;
	}

}
