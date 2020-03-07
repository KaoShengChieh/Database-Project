import java.util.List;

public class Response implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	public QueryType type;
	public boolean isSuccessful;
	public String errorMessage;
	int ADcode;
	public OrderInfo orderInfo;	
	public List<OrderInfo> orderInfoList;	// historyList ReservationList
	public List<HotelInfo> hotelInfoList;	// Hotel
	
	public void setErrorMessage(String errorMessage) {
		isSuccessful = false;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setOrder(OrderInfo orderInfo) {
		isSuccessful = true;
		this.orderInfo = orderInfo.clone();
	}
	
	public OrderInfo getOrder() {
		return orderInfo.clone();
	}
	
	public void setOrderList(List<OrderInfo> orderInfoList) {
		isSuccessful = true;
		this.orderInfoList = orderInfoList;
	}
	
	public void setHotelInfoList(List<HotelInfo> hotelInfoList) {
		isSuccessful = true;
		this.hotelInfoList = hotelInfoList;
	}
}
