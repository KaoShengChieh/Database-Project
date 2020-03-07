import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasicDBPool implements DBSource {
    private static final String protocol = "jdbc:sqlite:";
	private static final String filepath = "data/";
	private static final int max = 10;
	private String dbName;
    private List<Connection> connections;

    public BasicDBPool(String dbName) {
        this.dbName = dbName;
        connections = new ArrayList<Connection>();
        
        try {
        	for (int i = 0; i < max; i++) {
        		connections.add(DriverManager.getConnection(protocol + filepath + dbName + "/" + dbName));
        	}
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connections.size() == 0) {
            return DriverManager.getConnection(protocol + filepath + dbName + "/" + dbName);
        } else {
            int lastIndex = connections.size() - 1;
            return connections.remove(lastIndex);
        }
    }

    public synchronized void closeConnection(Connection conn) throws SQLException {
        if (connections.size() >= max) {
            conn.close();
        } else {
            connections.add(conn);
        }
    }
}
