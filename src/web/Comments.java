package web;

import java.io.IOException;
import java.sql.PreparedStatement;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseConnector db_conn = new DatabaseConnector();		
	    String text = request.getParameter("text");
	    int photo_id = Integer.parseInt(request.getParameter("photo_id"));

		String query = "insert into comments (text, photo_id) values (?,?)";
		try {
			db_conn.connectToDB("localhost", "photo_album_app");
			PreparedStatement prep = db_conn.prepareStatement(query);
			prep.setString(1, text);
			prep.setInt(2, photo_id);
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db_conn.close();
		}
		response.sendRedirect("/photo_album_app/Photos?photo_id="+photo_id);
	}

}