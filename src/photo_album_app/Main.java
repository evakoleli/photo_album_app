package photo_album_app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) throws SQLException, IOException {
		DatabaseConnector db_conn = new DatabaseConnector();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("host", "localhost");
		params.put("database", "photo_album_app");
		db_conn.connectToDB(params.get("host"), params.get("database"));
		db_conn.close();
	}

}
