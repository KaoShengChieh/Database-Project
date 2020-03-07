import java.util.List;
import java.util.Random;

public class HotelManager
{
	private Manager manager;
	
	public HotelManager() {
		manager = new ManagerA();
	}
	
	public Response signUp(Query query) {
		Response response = new Response();
		
		try {
			if (query.userInfo.userName.equals("GUEST")) {
				response.setErrorMessage("\"GUEST\" is not a valid username");
			} else {
				manager.signUp(query.userInfo.userName, query.userInfo.password, query.userInfo.realName, query.userInfo.email, query.userInfo.membership);
				response.isSuccessful = true;
			}
		} catch (ManagerException e) {
			response.setErrorMessage(e.getMessage());
		}
		
		return response;
	}
	
	public Response logIn(Query query, MemberInfo rs) {
		Response response = new Response();
		MemberInfo memberInfo = null;
		
		try {
			if (query.userInfo.userName.equals("GUEST")) {
				rs.membership = Membership.GUEST;
			} else {
				memberInfo = manager.logIn(query.userInfo.userName, query.userInfo.password);
				rs.memberID = memberInfo.memberID;
				rs.membership = memberInfo.membership;
			}
			response.isSuccessful = true;
		} catch (ManagerException e) {
			response.setErrorMessage(e.getMessage());
		}
		
		return response;
	}
	
	public Response handleQuery(MemberInfo memberInfo, Query query) {
		Service service = new Service();
		Response response = new Response();
		
		try {
			switch (memberInfo.membership) {
				case VIP:
					service.doService(new VIP(memberInfo.memberID), query, response);
					break;
				case MEMBER:
					service.doService(new Member(memberInfo.memberID), query, response);
					break;
				case GUEST:
					service.doService(query, response);
					break;
				default:
					response.setErrorMessage("Unknown membership");
			}
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
			viewAD(query, response);

			List<HotelInfo>	hotelList = null;		
			
			try {
				hotelList = manager.listHotel(query.orderInfo.city, query.orderInfo.additionalInfo, query.orderInfo.roomNum, query.orderInfo.checkin, query.orderInfo.checkout);
				response.setHotelInfoList(hotelList);
			} catch (HotelException e) {	
				throw new CustomerActionFailException(e.getMessage());
			}
		}
		
		public void viewAD(Query query, Response response) {
			//TODO
			Random random = new Random();
			response.ADcode = random.nextInt(16);
		}
		
		public void pay(Query query, Response response) throws CustomerActionFailException {
			payForVIP(query, response);
		}
		
		public void payForVIP(Query query, Response response) {
			//TODO
			/* If success, should require client re-login */
		}
		
		public void accept(Visitor visitor) {}
	}
	
	private class CustomerActionFailException extends Exception {
		private static final long serialVersionUID = 1L;

		public CustomerActionFailException(String message) {
			super(message);
		}
	}

	private class Member extends Customer {
		private int memberID;
		
		public Member(int memberID) {
			super();
			this.memberID = memberID;
		}
		
		public void doMember() {
		}
		
		public void makeReservation(Query query, Response response) throws CustomerActionFailException {
			OrderInfo reservertionInfo = null;
			
			try {
				reservertionInfo = manager.book(memberID, query.orderInfo.hotelID, query.orderInfo.roomNum, query.orderInfo.checkin, query.orderInfo.checkout);
				response.setOrder(reservertionInfo);
			} catch (ManagerException e) {
				throw new CustomerActionFailException(e.getMessage());
			} catch (HotelException e) {
				throw new CustomerActionFailException(e.getMessage());
			}
		}
		
		public void listReservation(Query query, Response response) throws CustomerActionFailException {
			List<OrderInfo> reservationList = null;

			try {
				reservationList = manager.listReservation(memberID);
				response.setOrderList(reservationList);
			} catch (ManagerException e) {
				throw new CustomerActionFailException(e.getMessage());
			} catch (HotelException e) {
				throw new CustomerActionFailException(e.getMessage());
			}
		}

		public void modifyReservation(Query query, Response response) throws CustomerActionFailException {
			throw new CustomerActionFailException("Permission denied. VIP only.");
		}

		public void cancelReservation(Query query, Response response) throws CustomerActionFailException {
			try {
				manager.cancel(query.additionalInfo);
				response.isSuccessful = true;
			} catch (ManagerException e) {
				throw new CustomerActionFailException(e.getMessage());
			} catch (HotelException e) {
				throw new CustomerActionFailException(e.getMessage());
			}
		}
		
		public void pay(Query query, Response response) throws CustomerActionFailException {
			boolean isPayForVIP = false;
			if (isPayForVIP) {
				payForVIP(query, response);
				return;
			}
			
			OrderInfo orderInfo = null;
			
			try {
				orderInfo = manager.pay(query.additionalInfo, query.creditCardInfo.CS_ID, query.creditCardInfo.number, query.creditCardInfo.expirationDate, query.creditCardInfo.securityCode);
				response.setOrder(orderInfo);
			} catch (ManagerException e) {
				throw new CustomerActionFailException(e.getMessage());
			} catch (HotelException e) {
				throw new CustomerActionFailException(e.getMessage());
			} catch (CashSystemException e) {
				throw new CustomerActionFailException(e.getMessage());
			}
		}
		
		public void listOrder(Query query, Response response) throws CustomerActionFailException {
			List<OrderInfo> orderList = null;

			try {
				orderList = manager.listOrder(memberID);
				response.setOrderList(orderList);
			} catch (HotelException e) {
				throw new CustomerActionFailException(e.getMessage());
			} catch (CashSystemException e) {
				throw new CustomerActionFailException(e.getMessage());
			}
		}
		
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}   
	}

	private class VIP extends Member {
		public VIP(int memberID) {
			super(memberID);
		}
		
		public void doVIP() {
		}

		public void modifyReservation(Query query, Response response) throws CustomerActionFailException {
			try {
				manager.modify(query.orderInfo.bookID, query.orderInfo.roomNum, query.orderInfo.checkin, query.orderInfo.checkout);
				response.isSuccessful = true;
			} catch (ManagerException e) {
				throw new CustomerActionFailException(e.getMessage());
			} catch (HotelException e) {
				throw new CustomerActionFailException(e.getMessage());
			}
		}
		
		public void viewAD(Query query, Response response) {
			response.ADcode = -1;
		}
		
		public void pay(Query query, Response response) throws CustomerActionFailException {
			OrderInfo orderInfo = null;
			
			try {
				orderInfo = manager.pay(query.additionalInfo, query.creditCardInfo.CS_ID, query.creditCardInfo.number, query.creditCardInfo.expirationDate, query.creditCardInfo.securityCode);
				response.setOrder(orderInfo);
			} catch (ManagerException e) {
				throw new CustomerActionFailException(e.getMessage());
			} catch (HotelException e) {
				throw new CustomerActionFailException(e.getMessage());
			} catch (CashSystemException e) {
				throw new CustomerActionFailException(e.getMessage());
			}
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

		public void doService(Query query, Response response)
			throws CustomerActionFailException {
			Customer customer = new Customer();
			((Visitable)customer).accept(visitor);
			
			switch (query.type) {
				case LIST_HOTEL:
					customer.listHotel(query, response);
					break;
				default:
					throw new CustomerActionFailException("Unknown query");
			}
		}
		
		public void doService(Member member, Query query, Response response)
			throws CustomerActionFailException {
			((Visitable)member).accept(visitor);
			
			switch (query.type) {
				case LIST_HOTEL:
					member.listHotel(query, response);
					break;
				case BOOK:
					member.makeReservation(query, response);
					break;
				case LIST_RESERVATION:	
					member.listReservation(query, response);
					break;
				case MODIFY:
					member.modifyReservation(query, response);
					break;
				case CANCEL:
					member.cancelReservation(query, response);
					break;
				case PAY:
					member.pay(query, response);
					break;
				case LIST_ORDER:
					member.listOrder(query, response);
					break;
				default:
					throw new CustomerActionFailException("Unknown query");
			}
		}
	}
}
