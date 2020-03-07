public class CreditCardInfo implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	public int CS_ID;
	public String number;
	public String expirationDate;
	public String securityCode;
	public String ownerName;
	
	public CreditCardInfo clone() {
		try {
			return (CreditCardInfo)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
