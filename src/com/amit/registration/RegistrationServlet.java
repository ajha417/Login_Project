package com.amit.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegistrationServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String uname = req.getParameter("name");
		String uemail = req.getParameter("email");
		String upwd = req.getParameter("pass");
		String phoneNo = req.getParameter("contact");
		RequestDispatcher dispatcher = null;
		Connection conn  = null;
		try {
			String url = "jdbc:mysql://localhost:3306/logindb?useSSL=false";
			String username = "root";
			String password = "";
			String query = "insert into users(uname,upwd,uemail,umobile) values(?,?,?,?)";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,username,password);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);
			pst.setString(4, phoneNo);
			
			int rowCount = pst.executeUpdate();
			dispatcher = req.getRequestDispatcher("registration.jsp");
			if(rowCount > 0)
			{
				req.setAttribute("status", "success");
			}
			else {
				req.setAttribute("status", "failed");
			}
			dispatcher.forward(req, resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
