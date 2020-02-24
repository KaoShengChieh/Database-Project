import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HotelDB
{
	private static final String protocol = "jdbc:sqlite:";
	private static final String filepath = "./data/";
	private static final String name = "hotel";
	private static final String fields = "(HotelID INTEGER PRIMARY KEY, HotelStar INTEGER, " +
		"City TEXT, Address TEXT, PriceSingle INTEGER, NumSingle INTEGER, " + 
		"PriceDouble INTEGER, NumDouble INTEGER, PriceQuad INTEGER, NumQuad INTEGER)";
	private Connection conn;
	private ReservationDB reservationDB; 
	
	public HotelDB() {
		connectHotelDB();
		System.out.println("Hotel database is ready.");
	}
	
	public void connectOtherDB(ReservationDB reservationDB) {
		this.reservationDB = reservationDB;
	}
	
	public int[] availableRooms(int hotelID, String checkin, String checkout) throws Exception {
		ResultSet resultSet = selectHotel(hotelID);
		resultSet.next();
		
		int[] availableRoomNum = {resultSet.getInt("NumSingle"),
			resultSet.getInt("NumDouble"), resultSet.getInt("NumQuad")};
			
		resultSet.close();
			
		resultSet = reservationDB.selectReservation("HotelID = " + hotelID +
			" AND ((Checkin >= '" + checkin + "' AND Checkout <= '" + checkin +
			"') OR (Checkin >= '" + checkout + "' AND Checkout <= '" + checkout + "'))");

		while (resultSet.next()) {
			availableRoomNum[0] -= resultSet.getInt("Single");
			availableRoomNum[1] -= resultSet.getInt("Double");
			availableRoomNum[2] -= resultSet.getInt("Quad");
		}

		return availableRoomNum;
	}
	
	public int calculatePrice(int hotelID, int[] roomNum, String checkin, String checkout) throws Exception {
		ResultSet resultSet = selectHotel(hotelID);
		resultSet.next();
		
		return (roomNum[0] * resultSet.getInt("PriceSingle") + 
			roomNum[1] * resultSet.getInt("PriceDouble") +
			roomNum[2] * resultSet.getInt("PriceQuad")) *
			countDays(checkin, checkout);
	}
	
	public List<HotelInfo> hotelList(int[] roomNum, String checkin, String checkout, String conditions) throws Exception {
		List<HotelInfo> hotelInfoList = new ArrayList<>();
		ResultSet resultSet = null;
		
		try {
			resultSet = conn.createStatement().executeQuery(
				"SELECT * FROM " + name + " WHERE " + conditions);
		} catch (Exception e) {
			throw new SQLException("Incorrect SQL format");
		}
		
		while (resultSet.next()) {
			if (roomNum[0] <= resultSet.getInt("NumSingle")
				&& roomNum[1] <= resultSet.getInt("NumDouble")
				&& roomNum[2] <= resultSet.getInt("NumQuad")) {
				hotelInfoList.add(new HotelInfo(resultSet.getInt("HotelID"), resultSet.getInt("HotelStar"),
					resultSet.getString("City"), resultSet.getString("Address"),
					(roomNum[0] * resultSet.getInt("PriceSingle") + roomNum[1] * resultSet.getInt("PriceDouble") +
					roomNum[2] * resultSet.getInt("PriceQuad")) * countDays(checkin, checkout)));
			}
		}
		
		return hotelInfoList;
	}
	
	private void connectHotelDB() {
		String hotelData = "hotel.json";
		
		try {
			conn = DriverManager.getConnection(protocol + filepath + name);
		} catch (SQLException e) {
			System.err.println("Fail to connect to hotel database: " + e.getMessage());
			System.exit(0);
		}
		
		try {
			if (conn.getMetaData().getTables(null, null, name, null).next())
				return;

			Statement s = conn.createStatement();
			s.execute("CREATE TABLE " + name + fields);
				
			System.out.println("Created hotel table.");
			System.out.println("Inserting data...");
			
			JSONParser parser = new JSONParser();
			
			FileInputStream in = new FileInputStream(filepath + hotelData);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			
			JSONArray a = (JSONArray)parser.parse(reader);
			
			String insertStatement = null;
			
			for (Object o : a) {
				JSONObject hotel = (JSONObject)o;
				
				insertStatement = "INSERT INTO " + name + " VALUES (";
				insertStatement += Integer.valueOf(String.valueOf(hotel.get("HotelID")));
				insertStatement += ", " + Integer.valueOf(String.valueOf(hotel.get("HotelStar")));
				insertStatement += ", '" + (String)hotel.get("Locality") + "'";
				insertStatement += ", '" + (String)hotel.get("Street-Address") + "'";

				JSONArray Rooms = (JSONArray)hotel.get("Rooms");
				
				for (Object c : Rooms) {
					JSONObject roomobject = (JSONObject)c;
					
					insertStatement += ", " + Integer.valueOf(String.valueOf(roomobject.get("RoomPrice")));
					insertStatement += ", " + Integer.valueOf(String.valueOf(roomobject.get("Number")));
				}
				
				insertStatement += ")";
				s.execute(insertStatement);
			}
			
			System.out.println("Data inserted.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(0);
		}
	}
	
	private ResultSet selectHotel(int hotelID) throws Exception {
		return conn.createStatement().executeQuery(
			"SELECT * FROM " + name + " WHERE HotelID = " + hotelID);
	}
	
	private int countDays(String D1, String D2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate firstDate = LocalDate.parse(D1, formatter);
		LocalDate secondDate = LocalDate.parse(D2, formatter);
		
		return (int)ChronoUnit.DAYS.between(firstDate, secondDate);
	}
}
