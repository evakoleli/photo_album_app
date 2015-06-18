<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Photo</title>
</head>
<body>
	<h1>New Photo</h1>
	<form action="/photo_album_app/Photos" method="post" enctype="multipart/form-data">
		<input type="text" name="title" />
		<input type="file" name=path />
		<input type="submit" value="Upload File" />
	</form>
</body>
</html>