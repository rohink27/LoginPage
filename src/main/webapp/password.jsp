<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
${passerror}
<form action="/password" method= post>
New Password: <input type="password" name="password"><br/>
Confirm Password: <input type="password" name="password2"><br/>
<input type=submit>
</form>
</body>
</html>