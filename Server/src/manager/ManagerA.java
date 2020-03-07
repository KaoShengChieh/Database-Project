import java.util.Calendar;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class ManagerA implements Manager {
	HotelDB hotelDB; // interface
	CashSystemDB cashSystemDB; // interface
	ManagerDB managerDB; // class reference
	
	ManagerA(){
		try {
			hotelDB = new HotelDBA();
			cashSystemDB = new CashSystemDBA();
			managerDB = new ManagerDBA();
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find database driver.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void signUp(String userName, String password, String realName, String email, String membership)
			throws ManagerException {
		managerDB.addAccount(realName, userName, password, email, membership);
	}

	@Override
	public MemberInfo logIn(String userName, String password) throws ManagerException {
		int memberID = managerDB.verifyAccount(userName, password);
		Membership membership = managerDB.checkMembership(memberID);
		return new MemberInfo(memberID, membership);
	}

	@Override
	public List<HotelInfo> listHotel(String city, String starCondition, int[] roomNum, String checkIn, String checkOut)
			throws HotelException {	
		return hotelDB.listHotel(city, starCondition, roomNum, checkIn, checkOut);
	}

	@Override
	public OrderInfo book(int memberID, int hotelID, int[] roomNum, String checkIn, String checkOut)
			throws ManagerException, HotelException {
		OrderInfo order = new OrderInfo();
		HotelInfo hotel = hotelDB.getHotelInfo(hotelID);
		order.hotelID = hotelID;
		order.star = hotel.star;
		order.city = hotel.city;
		order.address = hotel.address;
		order.roomNum = roomNum;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		String today = formatter.format(new java.util.Date());
		Calendar calendar = Calendar.getInstance();
		try {calendar.setTime(formatter.parse(today));} catch (ParseException e) {}
		calendar.add(Calendar.DATE, 2);
		String expDate = formatter.format(calendar.getTime());
		order.additionalInfo = expDate.toString();
		order.checkin = checkIn;
		order.checkout = checkOut;
		order.price = hotelDB.calculatePrice(hotelID, roomNum, checkIn, checkOut);		
		order.bookID = managerDB.addReservation(memberID, hotelID, roomNum, checkIn, checkOut, expDate, order.price);
		String realName = managerDB.getRealName(memberID);
		hotelDB.addReservation(memberID, hotelID, roomNum, checkIn, checkOut, order.bookID, realName);
		//TODO add email function below
		return order;
	}
	
	@Override
	public List<OrderInfo> listReservation(int memberID) throws ManagerException, HotelException {
		List<OrderInfo> ls = managerDB.listReservation(memberID);
		for(int i = 0; i < ls.size(); i += 1) {
			OrderInfo order = ls.get(i);
			HotelInfo hotel = hotelDB.getHotelInfo(order.hotelID);
			order.star = hotel.star;
			order.city = hotel.city;
			order.address = hotel.address;
			ls.set(i, order);
		}	
		return ls;
	}

	@Override
	public void cancel(String bookID) throws ManagerException, HotelException {
		managerDB.cancelReservation(bookID);
		hotelDB.cancelReservation(bookID);
	}

	@Override
	public OrderInfo modify(String bookID, int[] roomNum, String checkIn, String checkOut)
			throws ManagerException, HotelException {
		managerDB.modifyReservation(bookID, roomNum, checkIn, checkOut);
		hotelDB.modifyReservation(bookID, roomNum, checkIn, checkOut);
		return null;
	}

	@Override
	public OrderInfo pay(String bookID, int bankID, String creditcardID, String expirationDate, String securityCode)
			throws ManagerException, CashSystemException, HotelException {
		int memberID = managerDB.getMemIDFromRes(bookID);
		OrderInfo order = new OrderInfo();
		order.bookID = bookID;
		order.hotelID = managerDB.getHotelID(bookID);
		order.price = managerDB.addFee(bookID, bankID, creditcardID, expirationDate, securityCode);
		HotelInfo hotel = hotelDB.getHotelInfo(order.hotelID);
		order.star = hotel.star;
		order.city = hotel.city;
		order.address = hotel.address;
		OrderInfo reservationOrder = managerDB.getReservation(bookID);
		order.roomNum[0] = reservationOrder.roomNum[0];
		order.roomNum[1] = reservationOrder.roomNum[1];
		order.roomNum[2] = reservationOrder.roomNum[2];
		order.checkin = reservationOrder.checkin;
		order.checkout = reservationOrder.checkout;
		order.price = reservationOrder.price;
		cashSystemDB.pay(bookID, bankID, creditcardID, expirationDate, securityCode, order.price, order.hotelID);
		managerDB.insertOrder(order, memberID, bankID);
		return order;
	}

	@Override
	public List<OrderInfo> listOrder(int memberID) throws HotelException, CashSystemException {
		List<OrderInfo> ls = managerDB.listOrder(memberID);
		for (int i = 0; i < ls.size(); i += 1) {
			OrderInfo order = ls.get(i);
			HotelInfo hotel = hotelDB.getHotelInfo(order.hotelID);
			order.star = hotel.star;
			order.city = hotel.city;
			order.address = hotel.address;
			order.additionalInfo = cashSystemDB.getBankName(Integer.valueOf(order.additionalInfo));
			ls.set(i, order);
		}
		return ls;
	}

}
