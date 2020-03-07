import java.util.List;

public interface HotelDB {

	public void cancelReservation(String bookID) throws HotelException;
	
	public int calculatePrice(int hotelID, int[] roomNum, String checkin, String checkout) throws HotelException;

	public void addReservation(int memberID, int hotelID, int[] roomNum, String checkIn, String checkOut, String bookID, String realName) throws HotelException;
	
	public HotelInfo getHotelInfo(int hotelID) throws HotelException;
	
	public int[] availabelRooms(int hotelID, String checkIn, String checkOut) throws HotelException;

	public List<HotelInfo> listHotel(String city, String starCondition, int[] roomNum, String checkIn, String checkOut) throws HotelException;

	public void modifyReservation(String bookID, int[] roomNum, String checkIn, String checkOut) throws HotelException;

}
