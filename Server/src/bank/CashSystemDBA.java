import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class CashSystemDBA extends DBGate implements CashSystemDB {
	public static final String DBNAME = "CashSystem";
	
	public CashSystemDBA() throws ClassNotFoundException {
		dbsource = new BasicDBPool(DBNAME);
		try {
			createTable("CashSystem");
			createTable("AccountNatural");
			createTable("CreditCard");
			createTable("AccountLegal");
			createTable("Fees");
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
			String ddl = Files.readString(Paths.get("data/CashSystem/"+first2low(tableName)+".txt"), StandardCharsets.UTF_8);
			stmt.execute(ddl);
			stmt.close();
			System.out.println("Created "+tableName+" table.");
		}
		dbsource.closeConnection(conn);
	}

	private void insertData(){
		//TODO
	}

	public boolean checkCreditInfo(String cardID, String expired, int secureCode, String name) throws CashSystemException {
		boolean re = false;
		Connection conn = null;

		try {
			conn = dbsource.getConnection();
			ResultSet result = conn.createStatement().executeQuery("SELECT * FROM CreditCard WHERE Card_ID = " + cardID);
			if (result.next()) {
				String expDate = result.getString("ExpirationDate");
				int scode = result.getInt("SecureCode");
				if (expDate.equals(expired) && (scode == secureCode)) {
					re = true;
				} else {
					throw new CashSystemException("Wrong Credit Card Information");
				}
			} else {
				throw new CashSystemException("Wrong Credit Card Information");
			}
			result.close();
			dbsource.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return re;
	}
	
	public void pay(String bookID, int bankID, String creditcardID, String expirationDate, String securityCode, int amount, int hotelID) throws CashSystemException {
		Connection conn = null;
		try {
			int lpID = getLegalID(hotelID);
			conn = dbsource.getConnection();
			conn.setAutoCommit(false);
			this.insertFees(bookID, bankID, creditcardID, expirationDate, securityCode, amount, hotelID);
			int npID = this.confrimFees();
			this.trade(amount, npID, lpID);
			conn.commit();
			conn.setAutoCommit(true);
			dbsource.closeConnection(conn);
		} catch(SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
				conn.setAutoCommit(true);
				dbsource.closeConnection(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new CashSystemException(e.getMessage());
		} catch(CashSystemException e) {
			e.printStackTrace();
			try {
				conn.rollback();
				conn.setAutoCommit(true);
				dbsource.closeConnection(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new CashSystemException(e);
		}
		
	}
	
	private void insertFees(String bookID, int bankID, String creditcardID, String expirationDate, String securityCode, int amount, int hotelID) 
			throws CashSystemException, SQLException {
		
		Connection conn = dbsource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Fees VALUES(?,?,?,?,?,?,?)");
		pstmt.setString(1, bookID);
		pstmt.setInt(2, bankID);
		pstmt.setString(3, creditcardID);
		pstmt.setString(4, expirationDate);
		pstmt.setString(5, securityCode);
		pstmt.setInt(6, amount);
		pstmt.setInt(7, hotelID);
		pstmt.execute();
		pstmt.close();
		dbsource.closeConnection(conn);
	}
	
	/**
	 * @return the natural person id 
	 * @throws CashSystemException
	 */
	private int confrimFees() throws CashSystemException, SQLException {
		int id = 0;
		
		Connection conn = dbsource.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"SELECT CreditCard.NP_ID AS NP_ID "
				+ "FROM Fees, CreditCard "
				+ "WHERE Fees.CardNumber = CreditCard.CardNumber "
				+ "AND Fees.ExpirationDate = CreditCard.ExpirationDate "
				+ "AND Fees.SecurityCode = CreditCard.SecurityCode");
		if (rs.next() == false) {
			throw new CashSystemException("Wrong Credit Card Information");
		} else {
			id = rs.getInt(1);
		}
		rs.close();
		stmt.close();
		dbsource.closeConnection(conn);
		return id;
	}

	/**
	 * Complete a trade between the credit card and the given hotel.
	 * @param payer credit card's id
	 * @param payee hotel id
	 * @param price price of the trade
	 * @return if the trade is successfully completed
	 */
	private void trade(int price, int npID, int lpID) throws CashSystemException, SQLException {
			Connection conn = dbsource.getConnection();
			Statement stmt1 = conn.createStatement();
			stmt1.execute(
					"UPDATE AccountNatural " +
					"Set Balance = Balance - " + price + " " +
					"WHERE NP_ID = " + npID);
			stmt1.close();
			Statement stmt2 = conn.createStatement();
			stmt2.execute(
					"UPDATE AccountLegal " +
					"Set Balance = Balance + " + price + " " +
					"WHERE LP_ID = " + lpID);
			stmt2.close();
			dbsource.closeConnection(conn);
	}
	
	private int getLegalID(int hotelID) throws CashSystemException {
		int id = 0;
		try {
			Connection conn = dbsource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery(
					"SELECT LP_ID FROM AccountLegal WHERE Hotel_ID = " + hotelID);
			if (rs.next()) {
				id = rs.getInt("LP_ID");
			} else {
				throw new CashSystemException("Hotel :" + hotelID+ " is not in cashSystem.");
			}
			rs.close();
			dbsource.closeConnection(conn);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public String getBankName(int bankID) throws CashSystemException {
		String bankName = null;
		try {
			Connection conn = dbsource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery("SELECT Name FROM CashSystem WHERE CS_ID = " + bankID);
			if (rs.next()) {
				bankName = rs.getString("Name");
			} else {
				throw new CashSystemException("Cash System ID: "+bankID+" does not exist.");
			}
			rs.close();
			dbsource.closeConnection(conn);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return bankName;
	}
}
