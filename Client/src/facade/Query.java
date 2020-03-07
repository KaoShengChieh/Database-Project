public class Query implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	public QueryType type;
	public UserInfo userInfo;
	public OrderInfo orderInfo;
	public CreditCardInfo creditCardInfo;
	public String additionalInfo;
	/********* additionalInfo ********
	 * CANCEL and PAY: bookID
	 *********************************/
	
	public Query(QueryType queryType) {
		this.type = queryType;
		
		switch (queryType) {
			case SIGN_UP: case LOG_IN:
				userInfo = new UserInfo();
				return;
			case LIST_HOTEL: case BOOK: case MODIFY:
				orderInfo = new OrderInfo();
				return;
			case PAY:
				creditCardInfo = new CreditCardInfo();
				return;
			default:
				return;
		}
	}
	
	public Query clone() {
		try {
			Query query = (Query)super.clone();
			if (userInfo != null)
				query.userInfo = userInfo.clone();
			if (orderInfo != null)
				query.orderInfo = orderInfo.clone();
			if (creditCardInfo != null)
				query.creditCardInfo = creditCardInfo.clone();
			return query;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	public boolean quit() {
		return type == QueryType.QUIT;
	}
}
