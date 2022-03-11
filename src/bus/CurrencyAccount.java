package bus;

import java.sql.SQLException;

public class CurrencyAccount extends Account{

	private static final long serialVersionUID = -7150250415520503049L;

	public CurrencyAccount(long accountNumber, Customer customer) throws RaiseException, SQLException {
		super(accountNumber, customer);
	}

	@Override
	public String getInitials() {
		return "CU";
	}

	@Override
	public EnumAccountType getAccountType() {
		return EnumAccountType.Currency;
	}

//	public static method 
	
	public static boolean add(CurrencyAccount account) {

		return false;
	}

	public static Account findAccount(Long accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean delete(Long accountNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
