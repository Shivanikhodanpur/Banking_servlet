<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
	<link rel="stylesheet" href="style.css">

</head>
<body>
		<section class="balance-fail">
		<section class="a">

	<h2>
		<% 
session=request.getSession();
out.println("sorry cannot load balance.please try later");
		%>
	</h2>
	<a href="home.jsp"> click here to redirect</a>

</body>
</html>