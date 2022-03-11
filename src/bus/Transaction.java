package bus;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import data.TransactionDB;

public class Transaction implements Serializable {

	private static final long serialVersionUID = -6570909025984014036L;

	private long transactionNumber;
	private String description;
	private double transAmount;
	private Date transDate;
	private EnumTransactionType transType;
	private EnumAccountType accType;

	private Customer customer;

	public Transaction() {
	}

	public Transaction(long transactionNumber, String description, Date transDate, EnumTransactionType transType,
			double transAmount, EnumAccountType accType, Customer customer) {
		super();
		this.transactionNumber = transactionNumber;
		this.description = description;
		this.transDate = transDate;
		this.transType = transType;
		this.transAmount = transAmount;
		this.accType = accType;
		this.customer = customer;
	}

	public long getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(long transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public EnumTransactionType getTransType() {
		return transType;
	}

	public void setTransType(EnumTransactionType transType) {
		this.transType = transType;
	}

	public double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}

	public EnumAccountType getAccType() {
		return accType;
	}

	public void setAccType(EnumAccountType accType) {
		this.accType = accType;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
//	public static services (crud operation)
	
	public static ArrayList<Transaction> getAllTransactionByCustIdType(int id, EnumAccountType accType) throws RaiseException {

		ArrayList<Transaction> transactions = null;

		try {
			
			transactions = TransactionDB.getAllTransactionByCustIdType(id, accType);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public boolean saveToDb() {
		
		boolean status = false;
		
		try {
			status = TransactionDB.insert(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}

	public static long getNextTransactionNumberFromDb() {

		long transNo = TransactionDB.getLastNumber();
		
		return transNo + 1;
	}

}
