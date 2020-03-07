import java.util.List;

public interface Manager {

	/* Sign up */
	void signUp(String userName, String password, String realName, String email, String membership)
			throws ManagerException;

	/**
	 * @param userName
	 * @param password
	 * @return memberID
	 * @throws ManagerException
	 */
	MemberInfo logIn(String userName, String password) throws ManagerException;

	List<HotelInfo> listHotel(String city, String starCondition, int[] roomNum, String checkIn, String checkOut)
			throws HotelException;

	OrderInfo book(int memberID, int hotelID, int[] roomNum, String checkIn, String checkOut)
			throws ManagerException, HotelException;

	/* OrderInfo.additionalInfo = expirationDate; */
	List<OrderInfo> listReservation(int memberID) throws ManagerException, HotelException;

	void cancel(String bookID) throws ManagerException, HotelException;

	OrderInfo modify(String bookID, int[] roomNum, String checkIn, String checkOut)
			throws ManagerException, HotelException;

	OrderInfo pay(String bookID, int bankID, String creditcardID, String expirationDate, String securityCode)
			throws ManagerException, CashSystemException, HotelException;

	/* OrderInfo.additionalInfo = bankName; */
	List<OrderInfo> listOrder(int memberID) throws HotelException, CashSystemException;
}
