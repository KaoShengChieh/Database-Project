import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.StringTokenizer;

public class HotelManager
{
	private HotelDB hotelDB;
	private MemberDB memberDB;
	private ReservationDB reservationDB;
	
	public HotelManager() {
		hotelDB = new HotelDB();
		memberDB = new MemberDB();
		reservationDB = new ReservationDB();
		connectDBs();
	}
	
	public Response signUp(Query query) {
		Response response = new Response();
		String userName = null;
		String password = null;
		String membership = null;
		String email = null;
		StringTokenizer tokens = new StringTokenizer(query.userName, "/");
		
		if (tokens.countTokens() != 4) {
			response.setErrorMessage("Unknown sign up format");
			return response;
		}
		
		userName = tokens.nextToken();
		password = tokens.nextToken();
		membership = tokens.nextToken();
		email = tokens.nextToken();
		try {
			if (memberDB.isMember(userName)) {
				response.setErrorMessage("Username has been used");
				return response;
			}

			if (membership.equals("MEMBER") || membership.equals("VIP")) {
				memberDB.newAccount(userName, password, membership, email);
			} else {
				response.setErrorMessage("Unknown membership");
				return response;
			}
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			return response;
		}
		
		response.isSuccess = true;
		return response;
	}
	
	public Response logIn(Query query, ThreadInfo myThreadInfo) {
		Response response = new Response();
		String userName = null;
		String password = null;
		StringTokenizer tokens = new StringTokenizer(query.userName, "/");
		
		switch (tokens.countTokens()) {
			case 1:
				userName = tokens.nextToken();
				if (memberDB.isGuest(userName)) {
					response.isSuccess = true;
					return response;
				}
				response.setErrorMessage("Unknown log in format");
				break;
			case 2:
				userName = tokens.nextToken();
				password = tokens.nextToken();
				try {
					if (memberDB.isMember(userName)) {
						if (memberDB.verifyIdentity(userName, password) == true) {
							response.isSuccess = true;
							myThreadInfo.userName = userName;
							return response;
						}
						response.setErrorMessage("Incorrect password");
						break;
					}
					response.setErrorMessage("Username not found");
				} catch (Exception e) {
					response.setErrorMessage(e.getMessage());
				}
				break;
			default:
				response.setErrorMessage("Unknown log in format");
		}
		
		return response;
	}
	
	public Response handleQuery(Query query) {
		Service service = new Service();
		Response response = new Response();
		
		try {
			if (memberDB.isVIP(query.userName))
				service.doService(new VIP(), query, response);
			else if (memberDB.isMember(query.userName))
				service.doService(new Member(), query, response);
			else if (memberDB.isGuest(query.userName))
				service.doService(new Customer(), query, response);
			else
				response.setErrorMessage("Unknown username");
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
		}
		
		return response;
	}
	
	interface Visitable {
		void accept(Visitor visitor);
	}

	interface Visitor {
		void visit(Member member);
		void visit(VIP vip);
	}

	private class Customer implements Visitable {
		public void listHotel(Query query, Response response) throws CustomerActionFailException {
			List<HotelInfo> hotelInfoList = null;
			
			viewAD(query, response);
			
			if (query.roomNum[0] + query.roomNum[1] + query.roomNum[2] <= 0
				|| query.roomNum[0] < 0 || query.roomNum[1] < 0 || query.roomNum[2] < 0) {
				throw new CustomerActionFailException("Invalid number of rooms");
			}

			if (countDays(query.checkin, query.checkout) <= 0) {
				throw new CustomerActionFailException("Check out can't be before check in");
			}
			
			try {
				hotelInfoList = hotelDB.hotelList(query.roomNum, query.checkin, query.checkout, query.bookID);
			} catch (Exception e) {
				throw new CustomerActionFailException(e.getMessage());
			}
			
			response.setHotelInfoList(hotelInfoList);
		}
		
		public void makeOrder(Query query, Response response) throws CustomerActionFailException {
			if (query.roomNum[0] + query.roomNum[1] + query.roomNum[2] <= 0
				|| query.roomNum[0] < 0 || query.roomNum[1] < 0 || query.roomNum[2] < 0) {
				throw new CustomerActionFailException("Invalid number of rooms");
			}

			if (countDays(query.checkin, query.checkout) <= 0) {
				throw new CustomerActionFailException("Check out can't be before check in");
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			String today = formatter.format(new java.util.Date());
			if (countDays(today, query.checkin) <= 0) {
				throw new CustomerActionFailException("Can only book reservation after " + today);
			}
			
			try {
				query.bookID = memberDB.generateBookID(query.userName);
				reservationDB.insertReservation(query.userName, query.bookID,
					query.hotelID, query.roomNum, query.checkin, query.checkout);
				query.price = hotelDB.calculatePrice(query.hotelID, query.roomNum, query.checkin, query.checkout);
				reservationDB.updatePrice(query.userName, query.bookID, query.price);
			} catch (Exception e) {
				throw new CustomerActionFailException(e.getMessage());
			}

			response.setResult(query);
		}
		
		public void listOrder(Query query, Response response) throws CustomerActionFailException {
			List<Query> queryList = null;
			
			try {
				queryList = reservationDB.reservationInfoList(query.userName);
			} catch (Exception e) {
				throw new CustomerActionFailException(e.getMessage());
			}
			
			response.setOrderList(queryList);
		}

		public void cancelOrder(Query query, Response response) throws CustomerActionFailException {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			String today = formatter.format(new java.util.Date());
			
			try {
				if (countDays(today, reservationDB.reservationInfo(query.userName, query.bookID).checkin) <= 0) {
					throw new CustomerActionFailException("Can't cancel reservation before " + today);
				}
				reservationDB.removeReservation(query.userName, query.bookID);
			} catch (Exception e) {
				throw new CustomerActionFailException(e.getMessage());
			}
			
			response.isSuccess = true;
		}

		public void modifyOrder(Query query, Response response) throws CustomerActionFailException {
			if (query.roomNum[0] + query.roomNum[1] + query.roomNum[2] <= 0
				|| query.roomNum[0] < 0 || query.roomNum[1] < 0 || query.roomNum[2] < 0) {
				throw new CustomerActionFailException("Invalid number of rooms");
			}

			if (countDays(query.checkin, query.checkout) <= 0) {
				throw new CustomerActionFailException("Check out can't be before check in");
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			String today = formatter.format(new java.util.Date());
			
			try {
				if (countDays(today, reservationDB.reservationInfo(query.userName, query.bookID).checkin) <= 0) {
					throw new CustomerActionFailException("Can't modify reservation before " + today);
				}
				reservationDB.updateReservation(query.userName, query.bookID,
					query.roomNum, query.checkin, query.checkout);
				Query reservation = reservationDB.reservationInfo(query.userName, query.bookID);
				reservation.price = hotelDB.calculatePrice(
					reservation.hotelID, reservation.roomNum, reservation.checkin, reservation.checkout);
				reservationDB.updatePrice(reservation.userName, reservation.bookID, reservation.price);
			} catch (Exception e) {
				throw new CustomerActionFailException(e.getMessage());
			}
			
			response.isSuccess = true;
		}
		
		public void listReservation(Query query, Response response) throws CustomerActionFailException {
			//TODO
		}
		
		public void pay(Query query, Response response) {
			FeesSubmissionNotice notice = new FeesSubmissionNotice("Citi Bank");
			notice.setReceiver("Pipi Lai", "b06705031@ntu.edu.tw");
			notice.setFees(3000);
			notice.send();
			System.out.println("Send Email4");
			response.isSuccess = true;
			return;
		}
		
		public void viewAD(Query query, Response response) {
			//TODO
		}
		
		public void accept(Visitor visitor) {}
	}
	
	private class CustomerActionFailException extends Exception {
		private static final long serialVersionUID = 1L;
		
		public CustomerActionFailException() {
			super("Customer Action Fail Exception");
		}

		public CustomerActionFailException(String message) {
			super(message);
		}
	}

	private class Member extends Customer {
		public void doMember() {
		}
		
		public void pay(Query query, Response response) {
			//TODO
			boolean isPayForVIP = false;
			System.out.println("Send Email1");
			if (!isPayForVIP) {
				super.pay(query, response);
				System.out.println("Send Email2");
			} else {
				payForVIP(query, response);
			}
			System.out.println("Send Email3");
		}
		
		public void payForVIP(Query query, Response response) {
			//TODO
			/* If success, should require client re-login */
			System.out.println("Send Email5");
		}
		
		public void modifyOrder(Query query, Response response) throws CustomerActionFailException {
			throw new CustomerActionFailException("Permission denied. VIP only.");
		}
		
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}   
	}

	private class VIP extends Customer {
		public void doVIP() {
		}

		public void viewAD(Query query, Response response) {
			// do nothing
		}

		public void accept(Visitor visitor) {
			visitor.visit(this);
		}    
	}

	private class VisitorImpl implements Visitor {
		public void visit(Member member) {
			member.doMember();
		}

		public void visit(VIP vip) {
			vip.doVIP();
		}
	}

	private class Service {
		private Visitor visitor = new VisitorImpl();

		public void doService(Customer customer, Query query, Response response)
			throws CustomerActionFailException {
			((Visitable)customer).accept(visitor);
			
			switch (query.type) {
				case LISTHOTEL:
					customer.listHotel(query, response);
					break;
				case BOOK:
					customer.makeOrder(query, response);
					break;
				case LISTRESERVATION:	
					customer.listReservation(query, response);
					break;
				case CANCEL:
					customer.cancelOrder(query, response);
					break;
				case MODIFY:
					customer.modifyOrder(query, response);
					break;
				case PAY:
					customer.pay(query, response);
					break;
				case LISTORDER:
					customer.listOrder(query, response);
					break;
				default:
					throw new CustomerActionFailException("Unknown query");
			}
		}
	}
	
	private void connectDBs() {
		hotelDB.connectOtherDB(reservationDB);
		reservationDB.connectOtherDB(hotelDB);
	}
	
	private int countDays(String D1, String D2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate firstDate = LocalDate.parse(D1, formatter);
		LocalDate secondDate = LocalDate.parse(D2, formatter);
		
		return (int)ChronoUnit.DAYS.between(firstDate, secondDate);
	}
}
