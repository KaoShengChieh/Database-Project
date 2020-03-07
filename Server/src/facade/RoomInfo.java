
public class RoomInfo {
	private String type;
	private int hotelID;
	private int price;
	private int number;
	
	RoomInfo(){
		
	}
	
	RoomInfo(int hotelID, String type, int number, int price){
		this.hotelID = hotelID;
		this.type = type;
		this.number = number;
		this.price = price;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getHotelID() {
		return hotelID;
	}

	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
