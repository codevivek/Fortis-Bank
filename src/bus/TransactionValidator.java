package bus;

public class TransactionValidator {

	private Transaction transaction;

	TransactionValidator(Transaction transaction) {

		this.transaction = transaction;
	}

	public boolean validateTransactionLimit() {

		EnumAccountType accType = this.transaction.getAccType();

		Customer c = this.transaction.getCustomer();

		Account acc = Account.findAccount(c, accType);

		switch (accType) {
			case Checking:
				if (((CheckingAccount)acc).getFreeLimitPerMonth() < Constants.MAX_FREE_TRANS_CHECKING)
					return true;
				else
					return false;

			case Saving:

				return false;

			default:
				break;
		}

		return true;
	}

}
