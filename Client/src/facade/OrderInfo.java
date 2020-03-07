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
	 * LIST_RESERVATION: expirationDxate
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
}
