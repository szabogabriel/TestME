package me.test.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.test.Main;
import me.test.entity.user.User;
import me.test.session.SessionUtils;

public abstract class BasicServlet extends HttpServlet {

	private static final long serialVersionUID = 5358878151251275032L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		User user = setLoggedIn(request.getSession());
		if (user != null) {
			doGetLoggedIn(request, response, user);
		} else {
			doGetLoggedOut(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		User user = setLoggedIn(request.getSession());
		if (user != null) {
			doPostLoggedIn(request, response, user);
		} else {
			doPostLoggedOut(request, response);
		}
	}
	
	private User setLoggedIn(HttpSession session) {
		Map<String, Object> userData = SessionUtils.handleSession(session);
		User user = null;
		
		if (SessionUtils.isLoggedIn(userData)) {
			user = Main.INSTANCE.getQueryUserUC().getUser(() -> userData.get("username").toString()).getUser().get(0);
		}
		return user;
	}
	
	abstract void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException ;
	abstract void doGetLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException ;
	
	abstract void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException ;
	abstract void doPostLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException ;

}
