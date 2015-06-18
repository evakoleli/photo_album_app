<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="photo_album_app.DatabaseConnector" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Photos</title>
</head>
<body>
	<h1>Photos</h1>
	<%DatabaseConnector db_conn = new DatabaseConnector();%>
	<%db_conn.connectToDB("localhost", "photo_album_app");%>
	<%PreparedStatement prep = db_conn.prepareStatement("select title, path from photos");%>
	<%ResultSet rs = prep.executeQuery(); %>
<table>
    <tbody>
    <% while (rs.next()) {%>
      <tr>
        <td>
          <%=rs.getString("title")%>
        </td>
        <td>
          <img src="/photo_album_app/Photos?photo=<%=rs.getString("path") %>" alt="<%=rs.getString("path")%>" style="width:304px;height:228px;">
        </td>
      </tr>
    <%}%>
    <%prep.close();%>
    <%db_conn.close();%>
</tbody>
</table>
</body>
</html>