import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberDB
{
	private static final String protocol = "jdbc:sqlite:";
	private static final String filepath = "./data/";
	private static final String name = "member";
	private static final String fields = "(UserName TEXT PRIMARY KEY, HashedPassword TEXT, Membership TEXT, Email TEXT, OrderMade INTEGER)";
	private Connection conn;
	
	public MemberDB() {
		connectMemberDB();
		System.out.println("Member database is ready.");
	}
	
	public void newAccount(String userName, String hashedPassword, String membership, String email) throws Exception {
		conn.createStatement().execute(
			"INSERT INTO " + name + " VALUES ('" +
			userName +  "', '" + hashedPassword + "', '" + membership + "', '"+ email + "', 0)");
	}
	
	public boolean isGuest(String userName) {
		return userName.equals("GUEST");
	}
	
	public boolean isMember(String userName) throws Exception {
		return selectMember(userName).next();
	}
	
	public boolean isVIP(String userName) throws Exception {
		if (isMember(userName) == false) {
			return false;
		}
		ResultSet resultSet = selectMember(userName);
		resultSet.next();
		return "VIP".equals(resultSet.getString("Membership"));
	}
	
	public boolean verifyIdentity(String userName, String checksum) throws Exception {
		return conn.createStatement().executeQuery(
			"SELECT * FROM " + name + " WHERE UserName = '" + userName +
			"' AND HashedPassword = '" + checksum + "'").next();
	}
	
	public String generateBookID(String userName) throws Exception {
		ResultSet resultSet = selectMember(userName);
		resultSet.next();
		
		int orderMade = resultSet.getInt("OrderMade");
		
		MessageDigest md = MessageDigest.getInstance("MD5"); 
				
		byte[] messageDigest = md.digest((userName + orderMade).getBytes()); 
				
		BigInteger no = new BigInteger(1, messageDigest); 
				
		String encryptText = no.toString(16); 
		
		String bookID = encryptText.substring(encryptText.length()-8, encryptText.length());
		
		conn.createStatement().execute(
			"UPDATE " + name + " SET OrderMade = " + (orderMade + 1) +
			" WHERE UserName = '" + userName + "'");
		
		return bookID;
	}
	
	private void connectMemberDB() {
		try {
			conn = DriverManager.getConnection(protocol + filepath + name);
		} catch (SQLException e) {
			System.err.println("Fail to connect to member database : " + e.getMessage());
			System.exit(0);
		}
		
		try {
			if (conn.getMetaData().getTables(null, null, name, null).next())
				return;
				
			Statement s = conn.createStatement();
			s.execute("CREATE TABLE " + name + fields);
			s.execute("INSERT INTO " + name + " VALUES ('GUEST', 'GUEST', 'GUEST', '', 0)");
			System.out.println("Created member table.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(0);
		}
	}
	
	private ResultSet selectMember(String userName) throws Exception {
		return conn.createStatement().executeQuery(
			"SELECT * FROM " + name + " WHERE UserName = '" + userName + "'");
	}
}
