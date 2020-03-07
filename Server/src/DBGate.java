import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DBGate {
	protected DBSource dbsource;

	public DBGate() throws ClassNotFoundException {
    }
    
    public DBGate(String dbName) throws ClassNotFoundException {
        dbsource = new BasicDBPool(dbName);
    }
    
    protected String first2low(String s) {
		char c[] = s.toCharArray();
		c[0] = Character.toLowerCase(c[0]);
		return new String(c);
	}
	
	protected boolean isEleExist(String element, String column, String table) {
		boolean re = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbsource.getConnection();
			pstmt = conn.prepareStatement("SELECT COUNT(1) FROM "+table+" WHERE ?=?");
			pstmt.setString(1, column);
			pstmt.setString(2, element);

			ResultSet result = pstmt.executeQuery();
			if (result.next()) {
				re = true;
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
                    e.printStackTrace();
                    return false;
				}
			}
			if (conn != null) {
				try {
					dbsource.closeConnection(conn);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
                    e.printStackTrace();
                    return false;
				}
			}
		}
		return re;
	}
}
