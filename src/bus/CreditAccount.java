
package bus;

import java.sql.SQLException;

public class CreditAccount extends Account{

	private static final long serialVersionUID = -1442509662474553325L;

	public CreditAccount( long accountNumber, Customer customer) throws RaiseException, SQLException {
		super(accountNumber, customer);
	}

	public String getInitials() {
		return "CR";
	}

	@Override
	public EnumAccountType getAccountType() {
		return EnumAccountType.Credit;
	}
	
//	static method crud operation

	public static boolean add(CreditAccount account) {
		// TODO Auto-generated method stub
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
