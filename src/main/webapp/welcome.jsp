<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import= "org.springframework.security.core.Authentication" %>
<%@ page import= "org.springframework.security.core.context.SecurityContextHolder" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
Hello<br/>
<%
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
String currentPrincipalName = authentication.getName();
out.println(currentPrincipalName);
%>
<form action="/logout" method= get>
<button type="submit">
Logout
</button>
</form>
<form action="/passwordchange" method= get>
<button type="submit">
Change password?
</button>
</form>
<form action="/emailchange" method= get>
<button type="submit">
Change email?
</button>
</form>
</body>
</html>