package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import photo_album_app.DatabaseConnector;

/**
 * Servlet implementation class Users
 */
@WebServlet("/Users")
public class Users extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String re_password = request.getParameter("re_password");
		System.out.println(password + " " + re_password);
		Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			String sign_up_failure = "The email you provided is not valid. Try once more ...";
			request.setAttribute("sign_up_failure", sign_up_failure);
			request.getRequestDispatcher("/users/sign_up.jsp").include(request,
					response);
		} else if (!password.equals(re_password)) {
			String sign_up_failure = "The passwords you provided do not match. Try once more ...";
			request.setAttribute("sign_up_failure", sign_up_failure);
			request.getRequestDispatcher("/users/sign_up.jsp").include(request,
					response);
		} else {
			DatabaseConnector db_conn = new DatabaseConnector();
			String encrypted_password = BCrypt.hashpw(
					password, BCrypt.gensalt());

			try {
				String query = "insert into users (email, encrypted_password) values (?,?)";
				db_conn.connectToDB("localhost", "photo_album_app");
				PreparedStatement prep = db_conn.prepareStatement(query);
				prep.setString(1, email);
				prep.setString(2, encrypted_password);
				prep.executeUpdate();
				prep.close();
				response.sendRedirect("/photo_album_app");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				db_conn.close();
			}
		}
	}

}
