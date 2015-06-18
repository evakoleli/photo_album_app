package web;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import photo_album_app.DatabaseConnector;

/**
 * Servlet implementation class Photos
 */
@MultipartConfig
@WebServlet("/Photos")
public class Photos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String home_folder = "C:\\Users\\Evangelia Koleli\\Desktop\\";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Photos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseConnector db_conn = new DatabaseConnector();
		try {
			System.out.println("edw");
			db_conn.connectToDB("localhost", "photo_album_app");

			PreparedStatement prep = db_conn
					.prepareStatement("select title, path from photos");
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("title"));
				System.out.println(rs.getString("path"));
			}
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db_conn.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseConnector db_conn = new DatabaseConnector();
		
		Part photoPart = request.getPart("path");
		String path = photoPart.getSubmittedFileName();
	    //InputStream imageInputStream = photoPart.getInputStream();
	    photoPart.write(home_folder+path);
	    
	    //Part titlePart = request.getPart("title");
	    String title = request.getParameter("title");

		String query = "insert into photos (title, path) values (?,?)";
		try {
			db_conn.connectToDB("localhost", "photo_album_app");
			PreparedStatement prep = db_conn.prepareStatement(query);
			prep.setString(1, title);
			prep.setString(2, path);
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db_conn.close();
		}
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
