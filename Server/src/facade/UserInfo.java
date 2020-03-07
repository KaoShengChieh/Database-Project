public class UserInfo implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	public String userName;
	public String realName;
	public String password;
	public String email;
	public String membership;
	
	public UserInfo clone() {
		try {
			return (UserInfo)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
