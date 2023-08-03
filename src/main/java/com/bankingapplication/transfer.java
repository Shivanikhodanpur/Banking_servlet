package com.bankingapplication;

import java.io.IOException;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.Random;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

@WebServlet("/transfer")

public class transfer extends HttpServlet {

	private Connection con;

	private PreparedStatement prep;

	private ResultSet resultset2;

	private ResultSet r2;

	private PreparedStatement prep1;

	@Override

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int cust_id = Integer.parseInt(req.getParameter("cust_id"));

		String bank_name = req.getParameter("bank_name");

		int sender_accno = Integer.parseInt(req.getParameter("sender_accno"));

		String ifsc = req.getParameter("ifsc");

		String rec_accno = req.getParameter("rec_accno");

		String rec_ifsc = req.getParameter("rec_ifsc");

		int amount = Integer.parseInt(req.getParameter("amount"));

		int pin = Integer.parseInt(req.getParameter("pin"));

		HttpSession session = req.getSession();

		session.setAttribute("error", "Transaction Failed");

		session.setAttribute("amount", amount);

		Random r = new Random();

		String tid = 100000 + r.nextInt(90000) + "";

		session.setAttribute("tid", tid);

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/projectbank";

			String user = "root";

			String pass = "Shivsai@5";

			con = DriverManager.getConnection(url, user, pass);

			prep = con.prepareStatement("Select * from bankapp where cust_id=? and ifsc_code=? and aacno=? and pin=?");

			prep.setInt(1, cust_id);

			prep.setString(2, ifsc);

			prep.setInt(3, sender_accno);

			prep.setInt(4, pin);

			ResultSet r1 = prep.executeQuery();

			// validating sender id , ifsc , accno , pin

			if (r1.next() == true) {

				prep = con.prepareStatement("select * from bankapp where ifsc_code=? and aacno=?");

				prep.setString(1, rec_ifsc);

				prep.setString(2, rec_accno);

				resultset2 = prep.executeQuery();

				// validating the receiver acc

				if (resultset2.next() == true) {

					prep = con.prepareStatement("select balance from bankapp where pin=?");

					prep.setInt(1, pin);

					r2 = prep.executeQuery();

					if (r2.next()) {

						// validating the balance of sender

						if (r2.getInt("balance") >= amount) {

							// debit from sender

							prep = con.prepareStatement("update bankapp set balance =balance-? where pin=?");

							prep.setInt(1, amount);

							prep.setInt(2, pin);

							int x = prep.executeUpdate();

							if (x > 0) {

								// crediting to reciver account

								prep = con.prepareStatement("update bankapp set balance =balance+? where aacno=?");

								prep.setInt(1, amount);

								prep.setString(2, rec_accno);

								int x2 = prep.executeUpdate();

								if (x2 > 0) {

									// storing the details inside the DB

									prep = con.prepareStatement(
											"Insert into transfer_status(cust_id, bank_name, ifsc, sender_accno, rec_ifsc, rec_accno, amount,tid)  values(?,?,?,?,?,?,?,?)");
									prep.setInt(1, cust_id);

									prep.setString(2, bank_name);

									prep.setString(3, ifsc);

									prep.setInt(4, sender_accno);

									prep.setString(5, rec_ifsc);

									prep.setString(6, rec_accno);

									prep.setInt(7, amount);
									prep.setInt(8,session.getAttribute(tid));

									int x3 = prep.executeUpdate();

									if (x3 > 0) {
										resp.sendRedirect("/BankingApplication/TransferSuccess.jsp");

									}

									else {

										resp.sendRedirect("/BankingApplication/TransferFail.jsp");

									}

								} else {

									resp.sendRedirect("/BankingApplication/TransferFail.jsp");

								}

							} else {

								resp.sendRedirect("/BankingApplication/TransferFail.jsp");

							}

						} else {

							resp.sendRedirect("/BankingApplication/TransferFail.jsp");

						}

					} else {

						resp.sendRedirect("/BankingApplication/TransferFail.jsp");

					}

				}

			}

			else {

				resp.sendRedirect("/BankingApplication/TransferFail.jsp");

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}