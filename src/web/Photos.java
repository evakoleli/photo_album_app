package web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
		String photo = request.getParameter("photo");
		String photo_id = request.getParameter("photo_id");
		if (photo != null) {
			response.setContentType("image/jpeg");
			ServletOutputStream out = response.getOutputStream();
			FileInputStream fin = new FileInputStream("C:\\Users\\Evangelia Koleli\\Desktop\\" + photo);
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
		} else if (photo_id != null) {
			DatabaseConnector db_conn = new DatabaseConnector();
			try {
				db_conn.connectToDB("localhost", "photo_album_app");
				PreparedStatement prep = db_conn
						.prepareStatement("select title, path from photos where id = ?");
				prep.setInt(1, Integer.parseInt(photo_id));
				ResultSet rs = prep.executeQuery();
				rs.next();
				String title = rs.getString("title");
				String path = rs.getString("path");

				request.setAttribute("title", title);
				request.setAttribute("path", path);
				request.setAttribute("photo_id", photo_id);
				prep.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("/photos/show.jsp").include(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseConnector db_conn = new DatabaseConnector();
		Part photoPart = request.getPart("path");
		String path = Photos.getFileName(photoPart);
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
