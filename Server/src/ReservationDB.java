import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReservationDB
{
	private static final String protocol = "jdbc:sqlite:";
	private static final String filepath = "./data/";
	private static final String name = "reservation";
	private static final String fields = "(UserName TEXT, BookID TEXT, HotelID INTEGER, " +
		"Single INTEGER, Double INTEGER, Quad INTEGER, Checkin DATETIME, Checkout DATETIME, " +
		"Price INTEGER, PRIMARY KEY (UserName, BookID))";
	private Connection conn;
	private HotelDB hotelDB;
	
	public ReservationDB() {
		connectReservationDB();
		System.out.println("Reservation database is ready.");
	}
	
	public void connectOtherDB(HotelDB hotelDB) {
		this.hotelDB = hotelDB;
	}
	
	public synchronized void insertReservation(String userName, String bookID, int hotelID, int[] roomNum, String checkin, String checkout) throws Exception {
		System.err.println("check In : " + checkin);
		System.err.println("check Out : " + checkout);
		int[] availableRoomNum = hotelDB.availableRooms(hotelID, checkin, checkout);
		String errMessage = "";
		
		if (roomNum[0] > availableRoomNum[0])
			errMessage += availableRoomNum[0] + " single room(s) left\n";
		if (roomNum[1] > availableRoomNum[1])
			errMessage += availableRoomNum[1] + " double room(s) left\n";
		if (roomNum[2] > availableRoomNum[2])
			errMessage += availableRoomNum[2] + " quad room(s) left\n";
		if (errMessage.equals("") == false)
			throw new Exception("Reservation is not available:\n" + 
				errMessage.substring(0, errMessage.length() - 1));
		conn.createStatement().execute(
			"INSERT INTO " + name + " VALUES ('" +
			userName +  "', '" + bookID + "', " + hotelID + ", " +
			roomNum[0] + ", "+ roomNum[1] + ", "+ roomNum[2] + ", '"
			+ checkin + "', '"+ checkout + "', 0)");
	}
	
	public void updatePrice(String userName, String bookID, int price) throws Exception {
		conn.createStatement().execute(
			"UPDATE " + name + " SET Price = " + price +
			" WHERE UserName = '" + userName + "' AND BookID = '" + bookID + "'");
	}
	
	public ResultSet selectReservation(String conditions) throws Exception {
		return conn.createStatement().executeQuery(
			"SELECT * FROM " + name + " WHERE " + conditions);
	}
	
	public Query reservationInfo(String userName, String bookID) throws Exception {
		ResultSet resultSet = selectReservation(userName, bookID);
		resultSet.next();
		
		Query info = new Query();
		info.userName = userName;
		info.bookID = bookID;
		info.hotelID = resultSet.getInt("HotelID");
		info.roomNum[0] = resultSet.getInt("Single");
		info.roomNum[1] = resultSet.getInt("Double");
		info.roomNum[2] = resultSet.getInt("Quad");
		info.checkin = resultSet.getString("Checkin");
		info.checkout = resultSet.getString("Checkout");
		info.price = resultSet.getInt("Price");
		
		return info;
	}
	
	public List<Query> reservationInfoList(String userName) throws Exception {
		List<Query> queryList = new ArrayList<>();
		ResultSet resultSet = selectReservation("UserName = '" + userName + "' ORDER BY Checkin");
		
		while (resultSet.next()) {
			Query entry = new Query();
			entry.userName = userName;
			entry.bookID = resultSet.getString("BookID");
			entry.hotelID = resultSet.getInt("HotelID");
			entry.roomNum[0] = resultSet.getInt("Single");
			entry.roomNum[1] = resultSet.getInt("Double");
			entry.roomNum[2] = resultSet.getInt("Quad");
			entry.checkin = resultSet.getString("Checkin");
			entry.checkout = resultSet.getString("Checkout");
			entry.price = resultSet.getInt("Price");
			queryList.add(entry);
		}
		
		return queryList;
	}
	
	public void removeReservation(String userName, String bookID) throws Exception {
		conn.createStatement().execute(
			"DELETE FROM " + name + " WHERE UserName = '" + userName + "' AND BookID = '" + bookID + "'");
	}
	
	public void updateReservation(String userName, String bookID, int[] roomNum, String checkin, String checkout) throws Exception {
		ResultSet resultSet = selectReservation(userName, bookID);
		resultSet.next();
		int[] originRoomNum = {resultSet.getInt("Single"), resultSet.getInt("Double"), resultSet.getInt("Quad")};
		
		for (int i = 0; i < 3; i++) {
			if (roomNum[i] > originRoomNum[i])
				throw new Exception("Can only reduce the number of rooms");
		}
		
		if (countDays(resultSet.getString("Checkin"), checkin) < 0 || countDays(resultSet.getString("Checkout"), checkout) > 0) {
			throw new Exception("Can only shorten the reservation");
		}

		conn.createStatement().execute(
			"UPDATE " + name + " SET Single = " + roomNum[0] +
			", Double = " + roomNum[1] + ", Quad = " + roomNum[2] +
			", Checkin = '" + checkin + "', Checkout = '" + checkout + 
			"' WHERE UserName = '" + userName + "' AND BookID = '" + bookID + "'");
	}
	
	private int countDays(String D1, String D2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate firstDate = LocalDate.parse(D1, formatter);
		LocalDate secondDate = LocalDate.parse(D2, formatter);
		
		return (int)ChronoUnit.DAYS.between(firstDate, secondDate);
	}
	
	private void connectReservationDB() {
		try {
			conn = DriverManager.getConnection(protocol + filepath + name);
		} catch (SQLException e) {
			System.err.println("Fail to connect to reservation database : " + e.getMessage());
			System.exit(0);
		}
		
		try {
			if (conn.getMetaData().getTables(null, null, name, null).next())
				return;
			
			Statement s = conn.createStatement();
			s.execute("CREATE TABLE " + name + fields);
				
			System.out.println("Created reservation table.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(0);
		}
	}
	
	private ResultSet selectReservation(String userName, String bookID) throws Exception {
		return conn.createStatement().executeQuery(
			"SELECT * FROM " + name + " WHERE UserName = '" + userName + "' AND BookID = '" + bookID + "'");
	}
}
