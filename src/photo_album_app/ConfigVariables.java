package photo_album_app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigVariables {
	
	private String username;
	private String password;

	public ConfigVariables() {
		this.username = "";
		this.password = "";
	}

	public void readVariables() throws IOException {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		BufferedReader br = new BufferedReader(new FileReader(
				"config_variables"));
		String line = br.readLine();
		while (line != null) {
			if (line.startsWith("db_username:")) {
				this.username = line.replaceFirst("db_username:", "");
			} else if (line.startsWith("db_password:")) {
				this.password = line.replaceFirst("db_password:", "");
			}
			line = br.readLine();
		}
		br.close();
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
}
