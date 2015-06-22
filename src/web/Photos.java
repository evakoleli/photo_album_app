package web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import photo_album_app.Comment;
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
	 * photo show page
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String photo = request.getParameter("photo");
		String photo_id = request.getParameter("photo_id");
		
		// if the request has parameter "photo"
		if (photo != null) {
			response.setContentType("image/jpeg");
			ServletOutputStream out = response.getOutputStream();
			FileInputStream fin = new FileInputStream(home_folder + photo);
			BufferedInputStream bin = new BufferedInputStream(fin);
			BufferedOutputStream bout = new BufferedOutputStream(out);
			int ch = 0;
			while ((ch = bin.read()) != -1) {
				bout.write(ch);
			}

			bin.close();
			fin.close();
			bout.close();
			out.close();
		}
		// if the request has parameter "photo_id"
		else if (photo_id != null) {
			DatabaseConnector db_conn = new DatabaseConnector();
			try {
				db_conn.connectToDB("localhost", "photo_album_app");
				// Get photo
				PreparedStatement prep = db_conn
						.prepareStatement("select title, path, created_at from photos where id = ?");
				prep.setInt(1, Integer.parseInt(photo_id));
				ResultSet rs = prep.executeQuery();
				if (!rs.next()) {
					// I should have implemented a redirect
				} else {
					String title = rs.getString("title");
					String path = rs.getString("path");
					String created_at = rs.getString("created_at");
					
					// Get comments of current photo
					prep = db_conn.prepareStatement("select text, email, created_at from comments inner join users on user_id = users.id where photo_id = ? order by created_at");
					prep.setInt(1, Integer.parseInt(photo_id));
					rs = prep.executeQuery();
					ArrayList<Comment> comments = new ArrayList<Comment>();
					while (rs.next()) {
						comments.add(new Comment(Integer.parseInt(photo_id), rs.getString("email"), rs.getString("text"), rs.getString("created_at")));
					}
					request.setAttribute("title", title);
					request.setAttribute("path", path);
					request.setAttribute("photo_id", photo_id);
					request.setAttribute("created_at", created_at);
					request.setAttribute("comments", comments);
				}
				prep.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				db_conn.close();
			}
			request.getRequestDispatcher("/photos/show.jsp").include(request, response);
		}
	}

	/**
	 * Create photo
	 * @throws IOException 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseConnector db_conn = new DatabaseConnector();
		String path = "";
		try {
			Part photoPart = request.getPart("path");
			path = Photos.getFileName(photoPart);
		    photoPart.write(home_folder+path);
		} catch (IOException e) {
			String path_failure = "File not found. Try again...";
			request.setAttribute("failure", path_failure);
			request.getRequestDispatcher("/photos/new.jsp").include(request, response);
			e.printStackTrace();
			return;
		}
	    
	    String title = request.getParameter("title");
	    if (title.isEmpty()) {
			String path_failure = "Title cannot be empty. Try again...";
			request.setAttribute("failure", path_failure);
			request.getRequestDispatcher("/photos/new.jsp").include(request, response);
	    } else {
			String query = "insert into photos (title, path) values (?,?)";
			try {
				db_conn.connectToDB("localhost", "photo_album_app");
				PreparedStatement prep = db_conn.prepareStatement(query);
				prep.setString(1, title);
				prep.setString(2, path);
				prep.executeUpdate();
				prep.close();
				response.sendRedirect("photos/index.jsp");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				db_conn.close();
			}
	    }
	}

	private static String getFileName(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
}
