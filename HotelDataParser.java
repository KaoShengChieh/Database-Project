import java.util.*;
import java.io.*;
import java.sql.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class HotelDataParser
{
	private static final String protocol = "jdbc:sqlite:";
	private static final String name = "hotel";
	private static final String fields = "(HotelID INTEGER PRIMARY KEY, HotelStar INTEGER, " +
		"City TEXT, Address TEXT, PriceSingle INTEGER, NumSingle INTEGER, " + 
		"PriceDouble INTEGER, NumDouble INTEGER, PriceQuad INTEGER, NumQuad INTEGER)";
	private String filepath;
	private String hotelData;
	
	public static void main(String[] args) {
		HotelDataParser hotelDataParser = new HotelDataParser(args);
		hotelDataParser.buildDB();
		System.out.println("Hotel database is ready.");
	}
	
	public HotelDataParser(String[] args) {
		if (args.length != 2) {
			System.err.println("[data_directory] or [data_filename] not found");
			System.exit(0);
		}
		filepath = args[0]; // "./data/"
		hotelData = args[1]; // "hotel.json"
	}
	
	private void buildDB() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(protocol + filepath + name);
		} catch (SQLException e) {
			System.err.println("Fail to build to hotel database: " + e.getMessage());
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
			System.err.println("Probably invalid [data_directory] or [data_filename]");
			System.err.println(e.getMessage());
			System.exit(0);
		}
	}
}
