public class HotelException extends Exception {
	private static final long serialVersionUID = 1L;
	
	HotelException(){
		
	}
	HotelException(HotelException e){
		super(e);
	}
	HotelException(String msg){
		super(msg);
	}
}
