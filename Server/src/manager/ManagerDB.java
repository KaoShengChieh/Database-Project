import java.util.List;

public interface ManagerDB {

	public void addAccount(String realName, String username, String password,
			String email, String membership) throws ManagerException;
	
	public int verifyAccount(String username, String password) throws ManagerException;
	
	public Membership checkMembership(int memberID) throws ManagerException;
	
	
	/**
	 * @param bookID
	 * @param bankID
	 * @param creditcardID
	 * @param expirationDate
	 * @param securityCode
	 * @return the price of the fee
	 * @throws ManagerException
	 */
	public int addFee(String bookID, int bankID, String creditcardID, String expirationDate, String securityCode) throws ManagerException;

	public int getHotelID(String bookID) throws ManagerException;

	public void cancelReservation(String bookID) throws ManagerException;

	public String addReservation(int memberID, int hotelID, int[] roomNum, String checkIn, String checkOut, String expDate, int price) throws ManagerException;
	
	public String getRealName (int memberID) throws ManagerException;
	
	public List<OrderInfo> listOrder(int memberID);
	
	public List<OrderInfo> listReservation(int memberID) throws ManagerException;
	
	public OrderInfo getReservation(String bookID) throws ManagerException;

	public void modifyReservation(String bookID, int[] roomNum, String checkIn, String checkOut) throws ManagerException;

	public void insertOrder(OrderInfo order, int memberID, int bankID);

	public int getMemIDFromRes(String bookID);
}
