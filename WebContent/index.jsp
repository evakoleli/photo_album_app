<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Photo Album Application</title>
</head>
<body>
	<h1>Welcome to Photo Album Application</h1>
	<form action="/photo_album_app/Photos" method="get">
		<input type="submit"value="View all photos"/>
	</form>
	<form action="photos/new.jsp">
		<input type="submit"value="Upload a new photo"/>
	</form>
</body>
</html>