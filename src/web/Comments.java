package web;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import photo_album_app.DatabaseConnector;

/**
 * Servlet implementation class Comments
 */
@WebServlet("/Comments")
public class Comments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comments() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Create comment
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseConnector db_conn = new DatabaseConnector();		
	    String text = request.getParameter("text");
	    int photo_id = Integer.parseInt(request.getParameter("photo_id"));
	    String user_email = request.getParameter("user_email");

		try {
			db_conn.connectToDB("localhost", "photo_album_app");
			
			// find user id
			String query = "select id from users where email = ?";
			PreparedStatement prep = db_conn.prepareStatement(query);
			prep.setString(1, user_email);
			ResultSet rs = prep.executeQuery();
			if (!rs.next()) {
				// I should have redirected in case user does not exist
			} else {
				int user_id = rs.getInt("id");
				// Create comment
				query = "insert into comments (text, photo_id, user_id) values (?,?,?)";
				prep = db_conn.prepareStatement(query);
				prep.setString(1, text);
				prep.setInt(2, photo_id);
				prep.setInt(3, user_id);
				prep.executeUpdate();
				prep.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db_conn.close();
		}
		response.sendRedirect("/photo_album_app/Photos?photo_id="+photo_id);
	}

}
