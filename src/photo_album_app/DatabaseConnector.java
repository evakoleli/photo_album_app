package photo_album_app;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnector implements Closeable {

	private static Connection conn;
	private java.sql.Statement statement;

	public void connectToDB(String host, String database) throws SQLException,
			IOException {
		if (conn == null || conn.isClosed()) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException cnfe) {
				System.out.println("Could not find the JDBC driver!");
			}
			ConfigVariables env = new ConfigVariables();
			env.readVariables();
			conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:postgresql://" + host
						+ "/" + database + ":5432", env.getUsername(),
						env.getPassword());
			} catch (SQLException sqle) {
				System.out.println("Could not connect");
				System.out.println(sqle.getMessage());
				for (StackTraceElement el : sqle.getStackTrace())
					System.out.println(el.toString());
			}
		} else {
			return;
		}
	}
	
	public java.sql.Statement getStatement() {
		return statement;
	}
	
	public void createStatement() throws SQLException {
		try {
			statement = conn.createStatement();
		} catch(SQLException e) {			
			System.out.println("Couldn't create statement ... ");
			System.out.println(e.getMessage());
			for (StackTraceElement el : e.getStackTrace())
				System.out.println(el.toString());
		}
	}

	public PreparedStatement prepareStatement(String q) throws SQLException {
		return conn.prepareStatement(q);
	}
	
	public void closeStatement() throws SQLException {
		try {
			if (statement != null)
				statement.close();		
		} catch(SQLException sqle) {
			System.out.println("Statement not closed...");
			System.out.println(sqle.getMessage());
			for (StackTraceElement el : sqle.getStackTrace())
				System.out.println(el.toString());
		}
	}
	
	public void close() throws IOException {
		try {
			conn.close();
		} catch (SQLException psqle) {
			System.out.println("Connection not closed...");
			System.out.println(psqle.getMessage());
			for (StackTraceElement el : psqle.getStackTrace())
				System.out.println(el.toString());
		}
	}
}
