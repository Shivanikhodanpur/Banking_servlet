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

@WebServlet("/ChangePassword")
public class ChangePwd extends HttpServlet{
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet resultSet;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/projectbank";
		String user = "root";
		String pwd = "Shivsai@5";
		HttpSession session =req.getSession();
		int aacno=(int) session.getAttribute("aacno");
		
//		int pin= Integer.parseInt(req.getParameter("pin"));
//		int new_pin= Integer.parseInt(req.getParameter("pin1"));
//		int confirm_pin= Integer.parseInt(req.getParameter("pin2"));
//		
		
		String pin=req.getParameter("pin");
		String new_pin=req.getParameter("pin1");
		String confirm_pin=req.getParameter("pin2");
		
		
		if(new_pin.equals(confirm_pin)) {
		//Database connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement("update bankapp set pin=? where aacno=? and pin=? ");
			pstmt.setString(1, new_pin);
			pstmt.setInt(2, aacno);
			pstmt.setString(3, pin);
			
			
			int resultSet=pstmt.executeUpdate();
			if(resultSet>0) {
				
				resp.sendRedirect("/BankingApplication/PasswordChangeSuccess.html");

			}
			else {
				resp.sendRedirect("/BankingApplication/PasswordChangeFail.html");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}}
		else {
			resp.sendRedirect("/BankingApplication/PasswordChangeFail.html");
		}

		// TODO Auto-generated method stub
		
		
		
		
		
	}}
	