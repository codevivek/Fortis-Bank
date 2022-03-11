package bus;

import java.sql.SQLException;
import java.util.ArrayList;

import data.SavingAccountDB;

public class SavingAccount extends Account{

	private static final long serialVersionUID = 5004049609963888392L;

	private Double interestRate;
	private Double annualGain;
	private Double extraFees;
	
	public SavingAccount(long accountNumber, Customer customer) throws RaiseException, SQLException {
		super(accountNumber, customer);
		
		this.annualGain = 0.0;
		this.interestRate = 0.0;
		this.extraFees = 0.0;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Double getAnnualGain() {
		return annualGain;
	}

	public void setAnnualGain(Double annualGain) {
		this.annualGain = annualGain;
	}

	public Double getExtraFees() {
		return extraFees;
	}

	public void setExtraFees(Double extraFees) {
		this.extraFees = extraFees;
	}

	@Override
	public String getInitials() {
		return "SA";
	}

	@Override
	public EnumAccountType getAccountType() {
		return EnumAccountType.Saving;
	}
	

//	public static services (CRUD operations)
	
	public static boolean add(SavingAccount account) throws SQLException, RaiseException {
		
		return SavingAccountDB.insert(account);
	}
	
	public static boolean update(SavingAccount account) throws SQLException, RaiseException {
		
		return SavingAccountDB.update(account);
	}

	public static boolean delete(long accNum) throws SQLException, RaiseException {
		
		return SavingAccountDB.delete(accNum);
	}
	
	public static Account findAccount(long accNum) throws SQLException, RaiseException {
		
		return SavingAccountDB.search(accNum);
	}
	
	public static ArrayList<Account> getAll() throws NumberFormatException, SQLException, RaiseException{
		
		return SavingAccountDB.select();
	}

	
	
}
