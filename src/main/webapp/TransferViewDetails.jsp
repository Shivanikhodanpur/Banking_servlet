

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"

    pageEncoding="ISO-8859-1"%>

    <%@ page import="java.io.*,java.util.*,java.sql.*"%>

<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<!DOCTYPE html>

<html>

<head>

<meta charset="ISO-8859-1">

<title>Insert title here</title>

</head>

<body>

<%

    Connection con;

    ResultSet resultSet;

    PreparedStatement pstmt;

     int aacno=(int)session.getAttribute("aacno");

    try {

        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/projectbank";

        String user = "root";

        String pwd = "Shivsai@5";

        con = DriverManager.getConnection(url, user, pwd);

        String sql = "select * from transfer_status where sender_accno=? or rec_accno=?";

        pstmt=con.prepareStatement(sql);

        pstmt.setInt(1, aacno);

        pstmt.setInt(2, aacno);

        resultSet=pstmt.executeQuery();

    %>

    <table border="1">

        <tr>

            <th>Customer ID</th>

            <th>Bank name</th>

            <th>ifsc</th>

            <th>sender accno</th>

            <th>rec ifsc</th>

            <th>Receiver acc</th>

            <th>amount</th>

            <th>tid</th>

        </tr>

        <%

        while (resultSet.next()) {

        %>

        <tr>

            <td><%=resultSet.getInt(1)%></td>

            <td><%=resultSet.getString(2)%></td>

            <td><%=resultSet.getString(3)%></td>

            <td><%=resultSet.getInt(4)%></td>

            <td><%=resultSet.getString(5)%></td>

            <td><%=resultSet.getInt(6)%></td>

            <td><%=resultSet.getInt(7)%></td>

            <td><%=resultSet.getInt(8)%></td>

        </tr>

        <%

        }

        } catch (Exception e) {

        out.println(e);

        }  

        %>

    </table>

    <br>

    <a href="home.jsp"> Go to Home Page</a>

</body>

</html>

has context menu