import java.util.List;

public class Response implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	public boolean isSuccess;
	public String errorMessage;
	public Query result;
	public List<Query> orderList;
	public List<HotelInfo> hotelInfoList;
	
	public void setErrorMessage(String errorMessage) {
		isSuccess = false;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setResult(Query result) {
		isSuccess = true;
		this.result = result.clone();
	}
	
	public Query getResult() {
		return result.clone();
	}
	
	public void setOrderList(List<Query> orderList) {
		isSuccess = true;
		this.orderList = orderList;
	}
	
	public void setHotelInfoList(List<HotelInfo> hotelInfoList) {
		isSuccess = true;
		this.hotelInfoList = hotelInfoList;
	}
}
