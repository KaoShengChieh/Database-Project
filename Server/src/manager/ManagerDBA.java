import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ManagerDBA extends DBGate implements ManagerDB {
	public static final String DBNAME = "Manager";
	
	public ManagerDBA() throws ClassNotFoundException {
		dbsource = new BasicDBPool(DBNAME);
		try {
			createTable("Advertiser");
			createTable("Ad");
			createTable("Member");
			createTable("MemberViewAd");
			createTable("Reservation");
			createTable("Fees");
			createTable("OrderHistory");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createTable(String tableName) throws SQLException, IOException{
		Connection conn = dbsource.getConnection();
		if (!conn.getMetaData().getTables(DBNAME, null, tableName, null).next()) {
			Statement stmt = conn.createStatement();
			String ddl = Files.readString(Paths.get("data/Manager/"+first2low(tableName)+".txt"), StandardCharsets.UTF_8);
			stmt.execute(ddl);
			stmt.close();
			System.out.println("Created "+tableName+" table.");
		}
		dbsource.closeConnection(conn);
	}

	
	@Override
	public OrderInfo getReservation(String bookID) throws ManagerException{
		OrderInfo order = new OrderInfo();
		try {
			Connection conn = dbsource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Reservation WHERE Book_ID = '" + bookID + "'");
			if (rs.next()) {
				order.bookID = bookID;
				order.hotelID = rs.getInt("Hotel_ID");
				order.roomNum[0] = rs.getInt("Single");
				order.roomNum[1] = rs.getInt("Double");
				order.roomNum[2] = rs.getInt("Quad");
				order.checkin = rs.getString("checkIn");
				order.checkout = rs.getString("checkOut");
				order.price = rs.getInt("Price");
				order.additionalInfo = rs.getString("ExpirationDate");
			} else {
				throw new ManagerException("No book ID:" + bookID + " does not exist in Reservation");
			}
			rs.close();
			dbsource.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}
	
	@Override
	public List<OrderInfo> listReservation(int memberID) throws ManagerException {
		List<OrderInfo> ls = new LinkedList<OrderInfo>();
		try {
			Connection conn = dbsource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery(
					"SELECT * FROM Reservation WHERE Member_ID = " + memberID);
			if (rs.next()) {
				do {
					OrderInfo order = new OrderInfo();
					order.bookID = rs.getString("Book_ID");
					order.hotelID = rs.getInt("Hotel_ID");
					order.roomNum[0] = rs.getInt("Single");
					order.roomNum[1] = rs.getInt("Double");
					order.roomNum[2] = rs.getInt("Quad");
					order.checkin = rs.getString("checkIn");
					order.checkout = rs.getString("checkOut");
					order.additionalInfo = rs.getString("ExpirationDate");
					order.price = rs.getInt("Price");
					ls.add(order);
				} while(rs.next());
			} else {
				throw new ManagerException("MemberID :"+memberID+" does not exist.");
			}
			rs.close();
			dbsource.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return ls;
	}
	
	@Override
	public List<OrderInfo> listOrder(int memberID) {
		List<OrderInfo> ls = new LinkedList<OrderInfo>();
		try {
			Connection conn = dbsource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery(
					"SELECT * FROM OrderHistory WHERE Member_ID = " + memberID);
			dbsource.closeConnection(conn);
			while (rs.next()) {
				OrderInfo order = new OrderInfo();
				order.bookID = rs.getString("Order_ID");
				order.hotelID = rs.getInt("Hotel_ID");
				order.roomNum[0] = rs.getInt("Single");
				order.roomNum[1] = rs.getInt("Double");
				order.roomNum[2] = rs.getInt("Quad");
				order.checkin = rs.getString("checkIn");
				order.checkout = rs.getString("checkOut");
				order.price = rs.getInt("Price");
				order.additionalInfo = String.valueOf(rs.getInt("CS_ID"));
				ls.add(order);
			}
			rs.close();
			dbsource.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public void addAccount(String realName, String username, String password,
			String email, String membership) throws ManagerException {
		try {
			Connection conn = dbsource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO Member (RealName, UserName, Password, Membership, Email) "
					+ "VALUES(?,?,?,?,?)");
			pstmt.setString(1, realName);
			pstmt.setString(2, username);
			pstmt.setString(3, password);
			pstmt.setString(4, membership);
			pstmt.setString(5, email);
			pstmt.execute();
			pstmt.close();
			dbsource.closeConnection(conn);
		} catch (SQLException e) {
			String[] msg = e.getMessage().toString().split(" ");
			if (msg[0].contentEquals("Duplicate")) {
				//duField is duplicated field
				String duField = msg[msg.length - 1].replace("'", "");
				duField = duField.split("_UNIQUE")[0];
				throw new ManagerException(duField + " already exists.");
			}
			e.printStackTrace();
		}
	}
	
	@Override
	public int verifyAccount(String username, String password) throws ManagerException {
		int re = 0;
		try {
			Connection conn = dbsource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery(
					"SELECT Password, Member_ID "+
					"FROM Member "+
					"WHERE UserName = '" + username + "'");
			if(rs.next()) {
				String pw = rs.getString("Password");
				if (!pw.equals(password)) {
					throw new ManagerException("Incorrect password.");
				}
				re = rs.getInt("Member_ID");
			} else {
				throw new ManagerException("Username not exist.");
			}
			rs.close();
			dbsource.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return re;
	}
	
	public Membership checkMembership(int memberID) throws ManagerException {
		Membership re = null;
		try {
			Connection conn = dbsource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery(
					"SELECT Membership "+
					"FROM Member "+
					"WHERE Member_ID = " + memberID);
			if(rs.next()) {
				switch (rs.getString("Membership")) {
					case "GUEST":
						re = Membership.GUEST;
						break;
					case "MEMBER":
						re = Membership.MEMBER;
						break;
					case "VIP":
						re = Membership.VIP;
						break;
					default:
						throw new ManagerException("Unknown membership.");
				}
			} else {
				throw new ManagerException("Unexpected error while checking membership.");
			}
			rs.close();
			dbsource.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return re;
	}

	@Override
	public int addFee(String bookID, int bankID, String creditcardID, String expirationDate, String securityCode) throws ManagerException {
		int amount = 0;
		try {
			Connection conn = dbsource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery(
					"SELECT * FROM Reservation WHERE Book_ID = '" + bookID + "'");
			if (rs.next()) {
				amount = rs.getInt("Price");
			} else {
				throw new ManagerException("Book ID does not exist.");
			}
			rs.close();
			
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Fees VALUES(?,?,?,?,?)");
			pstmt.setString(1, bookID);
			pstmt.setInt(2, bankID);
			pstmt.setString(3, creditcardID);
			pstmt.setString(4, expirationDate);
			pstmt.setString(5, securityCode);
			pstmt.execute();
			
			pstmt.close();
			dbsource.closeConnection(conn);
		} catch(SQLException e){
			e.printStackTrace();
		}
		return amount;
	}
	
	public int getHotelID(String bookID) throws ManagerException {
		int hotelID = 0;
		try {
			Connection conn = dbsource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery(
					"SELECT Hotel_ID FROM Reservation WHERE Book_ID = '" + bookID + "'");
			if (rs.next()) {
				hotelID = rs.getInt(1);
			} else {
				throw new ManagerException("No bookID exist in Reservation");
			}
			rs.close();
			dbsource.closeConnection(conn);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return hotelID;
	}

	@Override
	public void cancelReservation(String bookID) throws ManagerException {
		try {
			if (this.isEleExist(bookID, "Book_ID", "Reservation")) {
				Connection conn = dbsource.getConnection();
				conn.createStatement().execute(
						"DELETE FROM Reservation WHERE Book_ID = '" + bookID + "'");
				dbsource.closeConnection(conn);
			} else {
				throw new ManagerException("Book ID: "+bookID+" does not exist.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized String addReservation(int memberID, int hotelID, int[] roomNum, String checkIn, String checkOut, String expDate, int price) throws ManagerException {
		String bookID = getBookID(memberID);
		try {
			Connection conn = dbsource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO Reservation "
					+ "(Book_ID, Member_ID, Hotel_ID, Single, Double, Quad, CheckIn, CheckOut, Price, ExpirationDate) "
					+ "VALUES(?, ?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, bookID);
			pstmt.setInt(2, memberID);
			pstmt.setInt(3, hotelID);
			pstmt.setInt(4, roomNum[0]);
			pstmt.setInt(5, roomNum[1]);
			pstmt.setInt(6, roomNum[2]);
			pstmt.setString(7, checkIn);
			pstmt.setString(8, checkOut);
			pstmt.setInt(9, price);
			pstmt.setString(10, expDate);
			pstmt.execute();
			
			pstmt.close();
			dbsource.closeConnection(conn);
		} catch(SQLException e){
			e.printStackTrace();
		}
		return bookID;
	}

	public String getRealName(int memberID) throws ManagerException {
		String re = null;
		try {
			Connection conn = dbsource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery(
					"SELECT RealName "+
					"FROM Member "+
					"WHERE Member_ID = " + memberID);
			if(rs.next()) {
				re = rs.getString("RealName");
			} else {
				throw new ManagerException("Unexpected error while getting your real name.");
			}
			rs.close();
			dbsource.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return re;
	}

	@Override
	public void modifyReservation(String bookID, int[] roomNum, String checkIn, String checkOut) throws ManagerException {
		OrderInfo order = this.getReservation(bookID);
		int[] originRoomNum = order.roomNum;
		
		for (int i = 0; i < 3; i++) {
			if (roomNum[i] > originRoomNum[i])
				throw new ManagerException("Can only reduce the number of rooms");
		}
		
		if (countDays(checkIn, order.checkin) > 0 || countDays(checkOut, order.checkout) < 0) {
			throw new ManagerException("Can only shorten the reservation");
		}
		
		try {
			Connection conn = dbsource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE Reservation "
					+ "SET Single = ?, `Double` = ?, Quad = ?, Checkin = ?, "
					+ "Checkout = ? WHERE Book_ID = ?");
			pstmt.setInt(1, roomNum[0]);
			pstmt.setInt(2, roomNum[1]);
			pstmt.setInt(3, roomNum[2]);
			pstmt.setString(4, checkIn);
			pstmt.setString(5, checkOut);
			pstmt.setString(6, bookID);
			pstmt.execute();

			pstmt.close();
			dbsource.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertOrder(OrderInfo order, int memberID, int bankID) {
		try {
			Connection conn = dbsource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO OrderHistory VALUES(?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, order.bookID);
			pstmt.setInt(2, memberID);
			pstmt.setInt(3, order.hotelID);
			pstmt.setInt(4, order.roomNum[0]);
			pstmt.setInt(5, order.roomNum[1]);
			pstmt.setInt(6, order.roomNum[2]);
			pstmt.setString(7, order.checkin);
			pstmt.setString(8, order.checkout);
			pstmt.setInt(9, order.price);
			pstmt.setInt(10, bankID);
			pstmt.execute();
			
			pstmt.close();
			dbsource.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getMemIDFromRes(String bookID) {
		int memberID = 0;
		try {
			Connection conn = dbsource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery("SELECT Member_ID FROM Reservation WHERE Book_ID = '" + bookID + "'");
			rs.next();
			memberID = rs.getInt("Member_ID");
			rs.close();
			dbsource.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberID;
	}
	
	private int countDays(String D1, String D2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate firstDate = LocalDate.parse(D1, formatter);
		LocalDate secondDate = LocalDate.parse(D2, formatter);

		return (int) ChronoUnit.DAYS.between(firstDate, secondDate);
	}
	
	private String getBookID(int memberID) {
		String encryptedText = null;
		try {
			Connection conn = dbsource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) AS OrderNum From Reservation WHERE Member_ID = " + memberID);
			int orderNum = rs.getInt("OrderNum");
			rs.close();
			
			boolean exist;
			do {
				encryptedText = encrypt("" + memberID + orderNum++);
				rs = conn.createStatement().executeQuery("SELECT EXISTS (SELECT 1 FROM Reservation WHERE Book_ID = '" + encryptedText + "') AS Result");
				rs.next();
				exist = rs.getInt("Result") == 1 ? true : false;
				rs.close();
			} while (exist);
			dbsource.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return encryptedText;
	}
	
	private String encrypt(String strToEncrypt) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] messageDigest = md.digest(strToEncrypt.getBytes());

			BigInteger no = new BigInteger(1, messageDigest);

			String encryptText = no.toString(16);

			return encryptText.substring(encryptText.length()-8);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
