<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="photo_album_app.DatabaseConnector"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="../resources/css/styles.css">
<link rel="stylesheet" type="text/css" href="resources/css/styles.css">
<title>${title}</title>
</head>
<body>
	<div id="container">
		<%
			String userEmail = null;
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : request.getCookies()) {
					if (cookie.getName().equals("user"))
						userEmail = cookie.getValue();
				}
			}
			if (userEmail == null) {
				response.sendRedirect("/photo_album_app/users/sign_in.jsp");
			}
		%>
		<div id="header">
			<h1>Photo Album Application</h1>
		</div>
		<div id="navigation">
			<h4>Menu buttons</h4>
			<%
				if (userEmail != null) {
			%>
			<form action="/photo_album_app/photos/index.jsp" method="get">
				<input type="submit" value="View all photos" />
			</form>
			<form action="photos/new.jsp">
				<input type="submit" value="Upload a new photo" />
			</form>
			<form action="/photo_album_app/SignOut" method="post">
				<input type="submit" value="Logout">
			</form>
			<%
				} else {
			%>
			<a href="/photo_album_app/users/sign_in.jsp">Sing in</a> <br> <a
				href="/photo_album_app/users/sign_up.jsp">Sing up</a>
			<%
				}
			%>
		</div>
		<div id="content">
			<h1>${title}</h1>
			<p>
				<img src="/photo_album_app/Photos?photo=${path}" alt="${path}%>">
			</p>
			<%
				DatabaseConnector db_conn = new DatabaseConnector();
			%>
			<%
				db_conn.connectToDB("localhost", "photo_album_app");
			%>
			<%
				PreparedStatement prep = db_conn
						.prepareStatement("select text from comments where photo_id="
								+ request.getAttribute("photo_id"));
			%>
			<%
				ResultSet rs = prep.executeQuery();
			%>
			<table>
				<tbody>
					<%
						while (rs.next()) {
					%>
					<tr>
						<td><%=rs.getString("text")%></td>
					</tr>
					<%
						}
					%>
					<%
						prep.close();
					%>
					<%
						db_conn.close();
					%>
				</tbody>
			</table>
			<p>Create a comment:</p>
			<form action="/photo_album_app/Comments" method="post"
				id="comment_form">
					<textarea name="text" form="comment_form"
							placeholder="Write your comment here..." rows="4" cols="50" />
					<input type="hidden" name="photo_id" value="${photo_id}" />
					<input type="hidden" name="user_email"
						value="${userEmail}" />
					<input type="submit" value="Create Comment" />
			</form>
		</div>
		<div id="footer">
			<p>Evangelia Koleli</p>
		</div>
	</div>
</body>
</html>