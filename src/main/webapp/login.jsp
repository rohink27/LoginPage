<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
${SPRING_SECURITY_LAST_EXCEPTION.message}
<br/>
<form action="/login" method= post>
Username: <input type="text" name="username"><br/>
Password: <input type="password" name= "password"><br/>
Remember Me:<input type="checkbox" name = "remember-me"><br/>
<input type=submit>
</form>

<form action="/register" method= get>
<button type="submit">
Register Here!!
</button>
</form>

<form action="/forgot" method= get>
<button type="submit">
Forgot Password
</button>
</form>
</body>
</html>