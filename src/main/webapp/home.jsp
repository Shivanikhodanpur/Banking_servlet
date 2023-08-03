<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
	<link rel="stylesheet" href="style.css">

</head>
<body>
    		<section class="container-jsp">
    		
    
	<h1 align="center">Welcome to bank</h1>
	<%
	session=request.getSession();
	String s1=(String)session.getAttribute("cust_name");
	out.println(s1+" welcome to your account.please select opertaion to perform\n");
	%>
	<br>
	<br>
	<a href="CheckBalance">1.check balance</a><br><br>
	<a href="Changepsd.html">2.Change Password</a><br><br>
		<a href="Loan.jsp">3.Apply For Loan</a><br><br>
		<a href="transfer.html">4.Transfer amount</a><br><br>
		<a href="TransferViewDetails.jsp">5.View Transaction Details</a><br>
		<a href="Logout">4.Logout</a>
	    </section>
	    



</body>
</html>