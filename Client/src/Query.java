public class Query implements java.io.Serializable, Cloneable
{
	private static final long serialVersionUID = 1L;
	
	public QueryType type;
	public String userName;
	public String bookID;
	public int hotelID;
	public int[] roomNum;
	public String checkin;
	public String checkout;
	/**
	 * 12/15
	 */
//	public String hotelAddress; 
	public int price;
	
	public Query() {
		roomNum = new int[3];
	}
	
	public Query clone() {
		try {
			return (Query)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	public boolean quit() {
		return type == QueryType.QUIT;
	}
}
