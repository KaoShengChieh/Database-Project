import java.util.Arrays;

public class OrderInfo implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	public String bookID;
	public int hotelID;
	public int star;
	public String city;
	public String address;
	public int[] roomNum;
	public String checkin;
	public String checkout;
	public int price;
	public String additionalInfo;
	/********* additionalInfo ********
	 * LIST_HOTEL: star condition
	 * LIST_RESERVATION: expirationDate
	 * LIST_ORDER: bankName
	 *********************************/
	
	public OrderInfo() {
		roomNum = new int[3];
	}
	
	public OrderInfo clone() {
		try {
			return (OrderInfo)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	public String toString() {
		
		String s = "bookID : " + bookID + "\n"+
				"hotelID : " + hotelID + "\n"+
				"star : " + star + "\n"+
				"city : " + city + "\n"+
				"address : " + address + "\n"+
				"roomNum : " + Arrays.toString(roomNum) + "\n"+
				"checkin : " + checkin + "\n"+
				"checkout : " + checkout + "\n"+
				"price : " + price + "\n"+
				"additionalInfo : " + additionalInfo + "\n";
		return s;
	}
}
