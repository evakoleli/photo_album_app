<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="../resources/css/styles.css">
<link rel="stylesheet" type="text/css" href="resources/css/styles.css">
<title>New Photo</title>
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
	%>
	<div id="header">
		<h1>Photo Album Application</h1>
	</div>
	<div id="navigation">
		<h4>Menu buttons</h4>
		<% if (userEmail != null) {%>
			<form action="/photo_album_app/photos/index.jsp" method="get">
				<input type="submit" value="View all photos" />
			</form>
			<form action="/photo_album_app/photos/new.jsp">
				<input type="submit" value="Upload a new photo" />
			</form>
			<form action="/photo_album_app/SignOut" method="post">
				<input type="submit" value="Logout">
			</form>
		<% } else {%>
			<a href="/photo_album_app/users/sign_in.jsp">Sing in</a>
			<br>
			<a href="/photo_album_app/users/sign_up.jsp">Sing up</a>
		<% } %>
	</div>
	<h1>New Photo</h1>
	<form action="/photo_album_app/Photos" method="post" enctype="multipart/form-data">
		<input type="text" name="title" />
		<input type="file" name=path />
		<input type="submit" value="Upload File" />
	</form>
		<div id="footer">
			<p>Evangelia Koleli</p>
		</div>
	</div>
</body>
</html>