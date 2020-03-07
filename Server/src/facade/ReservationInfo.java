
public class ReservationInfo {
	private String bookID;
	private int hotelID;
	private int[] roomNum;
	private String checkIn;
	private String checkOut;
	private String name;
	
	public ReservationInfo() {
		
	}
	
	public ReservationInfo(String bookID, int hotelID, int[] roomNum, String checkIn, String checkOut, String name) {
		this.bookID = bookID;
		this.hotelID = hotelID;
		this.roomNum = new int[roomNum.length];
		System.arraycopy(roomNum, 0, this.roomNum, 0, roomNum.length );
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.name = name;
	}
	public String getBookID() {
		return bookID;
	}
	public int getHotelID() {
		return hotelID;
	}
	public int[] getRoomNum() {
		int[] re = new int[roomNum.length];
		System.arraycopy(roomNum, 0, re, 0, roomNum.length);
		return re;
	}
	public String getCheckIn() {
		return checkIn;
	}
	public String getCheckOut() {
		return checkOut;
	}
	public String getName() {
		return name;
	}
}
