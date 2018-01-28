package me.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.test.entity.user.User;

@WebServlet("/answersManagement")
public class AnswerManagementServlet extends BasicServlet {

	private static final long serialVersionUID = 5176087527841589434L;

	@Override
	void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, User user)	throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	void doGetLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}

	@Override
	void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		// TODO Handle request of the answers.
		response.sendRedirect("index");
	}

	@Override
	void doPostLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}

}
