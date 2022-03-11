package bus;

import java.sql.SQLException;
import java.util.ArrayList;

import data.CheckingAccountDB;

public class CheckingAccount  extends Account{

	private static final long serialVersionUID = -7016269768357019400L;

	private int freeLimitPerMonth;
	
	public CheckingAccount(long accNumber,Customer customer) throws RaiseException, SQLException {
		super(accNumber, customer);
	}

	public CheckingAccount() {
		super();
	}

	public int getFreeLimitPerMonth() {
		return freeLimitPerMonth;
	}

	public void setFreeLimitPerMonth(int freeLimitPerMonth) {
		this.freeLimitPerMonth = freeLimitPerMonth;
	}

	@Override
	public String getInitials() {
		return "CK";
	}

	@Override
	public EnumAccountType getAccountType() {
		return EnumAccountType.Checking;
	}
	
	
//	public static services (CRUD operations)
	
	public static boolean add(CheckingAccount account) throws SQLException, RaiseException {
		
		return CheckingAccountDB.insert(account);
	}

	
	public static boolean update(CheckingAccount account) throws SQLException, RaiseException {
		
		return CheckingAccountDB.update(account);
	}


	public static boolean delete(long accNum) throws SQLException, RaiseException {
		
		return CheckingAccountDB.delete(accNum);
	}
	
	public static Account findAccount(long accNum) throws SQLException, RaiseException {
		
		return CheckingAccountDB.search(accNum);
	}
	
	public static ArrayList<Account> getAll() throws NumberFormatException, SQLException, RaiseException{
		
		return CheckingAccountDB.select();
	}
}
