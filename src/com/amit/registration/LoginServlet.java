package com.amit.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String uemail = req.getParameter("username");
		String upwd = req.getParameter("password");
		Connection conn = null;
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		RequestDispatcher dispatcher = null;
		try
		{
			
//			out.println("1");
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/logindb?useSSL=false";
			String username = "root";
			String password = "";
			String query = "select * from users where uemail=? and upwd=?";
 			conn = DriverManager.getConnection(url,username,password);
 			PreparedStatement statement = conn.prepareStatement(query);
 			statement.setString(1, uemail);
 			statement.setString(2, upwd);
 			
 			ResultSet rs = statement.executeQuery();
 			if(rs.next())
 			{
// 				out.println("2");
 				session.setAttribute("name", rs.getString("uname"));
 				dispatcher = req.getRequestDispatcher("index.jsp");
 			}
 			else
 			{
// 				out.println("3");
				req.setAttribute("status", "failed");
				dispatcher = req.getRequestDispatcher("login.jsp");
			}
 			dispatcher.forward(req, resp);
		} 
		catch (Exception e)
		{
//			out.println("4");
			e.printStackTrace();
		}
		finally
		{
//			out.println("5");
			try 
			{
//				out.println("6");
				conn.close();
			}
			catch (SQLException e) 
			{
//				out.println("7");
				e.printStackTrace();
			}
		}
	}
}
