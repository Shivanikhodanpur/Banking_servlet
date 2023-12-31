package com.bankingapplication;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;

@WebServlet("/login")

public class login extends HttpServlet {
	public Connection con;

	public static PreparedStatement pstmt;

	public static ResultSet resultSet;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int cust_id = Integer.parseInt(req.getParameter("cust_id"));
		int pin = Integer.parseInt(req.getParameter("pin"));
		String url = "jdbc:mysql://localhost:3306/projectbank";
		String user = "root";
		String pwd = "Shivsai@5";
		HttpSession session = req.getSession(true);

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement("select * from bankapp where cust_id=? and pin=?");
			pstmt.setInt(1, cust_id);
			pstmt.setInt(2, pin);
			resultSet = pstmt.executeQuery();
			if (resultSet.next() == true) {
				session.setAttribute("aacno", resultSet.getInt("aacno"));
				session.setAttribute("cust_name", resultSet.getString("cust_name"));
				session.setAttribute("pin", resultSet.getInt("pin"));
                resp.sendRedirect("/BankingApplication/home.jsp");

			} else {
				resp.sendRedirect("/BankingApplication/loginfail.html");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
