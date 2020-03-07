public interface CashSystemDB {
	public boolean checkCreditInfo(String cardNumber, String expirationDate, int secureCode, String name) throws CashSystemException;
		
	public void pay(String bookID, int bankID, String creditcardID, String expirationDate, String securityCode, int amount, int hotelID) throws CashSystemException;

	public String getBankName(int bankID) throws CashSystemException;
}
