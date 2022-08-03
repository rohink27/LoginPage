<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
${error }
<br/>
<form action="registers" method = post>
Enter username: <input type="text" name = "username"><br/>
Enter Password: <input type="password" name = "password"><br/>
Confirm Password: <input type="password" name = "password2"><br/>
Enter email: <input type="text" name = "email"><br/>
<input type = "submit">

</body>
</html>