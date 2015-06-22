package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import photo_album_app.DatabaseConnector;

/**
 * Servlet implementation class SignIn
 */
@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Sign in
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseConnector db_conn = new DatabaseConnector();
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String query = "select encrypted_password from  users where email = ?";
		try {
			db_conn.connectToDB("localhost", "photo_album_app");
			PreparedStatement prep = db_conn.prepareStatement(query);
			prep.setString(1, email);
			prep.executeQuery();
			ResultSet rs = prep.executeQuery();
			boolean user_exists = true;			
			if (rs.next()) {
				String encrypted_password = rs.getString("encrypted_password");
				if (BCrypt.checkpw(password, encrypted_password)) {
					Cookie signinCookie = new Cookie("user", email);
					signinCookie.setMaxAge(1200);
					response.addCookie(signinCookie);
					response.sendRedirect("/photo_album_app");
				} else {
					user_exists = false;					
				}
			} else {
				user_exists = false;					
			}			
			if (!user_exists) {
				String sign_in_failure = "Either user name or password is wrong. Try once more ...";
				request.setAttribute("sign_in_failure", sign_in_failure);
				request.getRequestDispatcher("/users/sign_in.jsp").include(request, response);
			}
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db_conn.close();
		}
	}

}
