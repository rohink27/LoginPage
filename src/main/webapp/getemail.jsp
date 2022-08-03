<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
${emailerror }
<form action="/email" method= post>
Email: <input type="text" name="email"><br/>
<input type=submit>
</form>
</body>
</html>