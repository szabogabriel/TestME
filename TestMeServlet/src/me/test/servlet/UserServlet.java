package me.test.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jmtemplate.Template;

import me.test.Main;
import me.test.entity.user.User;
import me.test.servlet.viewmodel.UserVM;
import me.test.template.TemplateLoader;
import me.test.usecase.user.create.CreateUserRequestData;
import me.test.usecase.user.forcepassword.ForcePasswordRequestData;

@WebServlet("/user")
public class UserServlet extends BasicServlet {

	private static final long serialVersionUID = -7447773278058130772L;
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.loadTemplate("user.template");

	@Override
	void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		UserVM data = new UserVM();
		
		data.setActiveUser(user.getUsername());
		data.addUser(getUsers().stream().map(u -> u.getUsername()).collect(Collectors.toList()));
		
		response.getWriter().print(TEMPLATE.render(data.provideData()));
	}

	@Override
	void doGetLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}

	@Override
	void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		RequestData requestData = new RequestData(request);
		switch(requestData.operation) {
		case ADD:
			performAddUser(requestData);
			break;
		case UPDATE:
			performUpdateUser(requestData);
			break;
		default:
			break;
		}
		doGetLoggedIn(request, response, user);
	}

	@Override
	void doPostLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}
	
	private List<User> getUsers() {
		return Main.INSTANCE.getQueryUserUC().getUser(() -> null).getUser();
	}
	
	private void performAddUser(RequestData request) {
		if (request.isParametersCorrect()) {
			Main.INSTANCE.getCreateUserUC().createUser(new CreateUserRequestData() {
				@Override public String getUsername() { return request.getUsername(); }
				@Override public String getPassword() {	return request.getPassword(); }
			});
		}
	}
	
	private void performUpdateUser(RequestData request) {
		if (request.isParametersCorrect()) {
			Main.INSTANCE.getForcePasswordUC().setPassword(new ForcePasswordRequestData() {
				@Override public String getUsername() { return request.getUsername(); }
				@Override public String getPassword() { return request.getPassword(); }
			});
		}
	}
	

	
	private class RequestData {
		
		private String username = null;
		private String password1 = null;
		private String password2 = null;
		private Operations operation = Operations.UNKNOWN;
		
		public RequestData(HttpServletRequest request) {
			username = getParameter(request, "username");
			password1 = getParameter(request, "psswd1");
			password2 = getParameter(request, "psswd2");
			operation = Operations.create(getParameter(request, "operation"));
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getPassword() {
			return password1;
		}
		
		public boolean isParametersCorrect() {
			return username != null && password1 != null && password1.equals(password2) && password1.trim().length() > 0;
		}
		
		private String getParameter(HttpServletRequest request, String paramName) {
			String ret = null;
			
			ret = request.getParameter(paramName);
			
			return ret;
		}
		
	}
	
	private enum Operations {
		ADD("add"),
		UPDATE("update"),
		UNKNOWN("");
		
		private final String operation;
		
		private Operations(String operation) {
			this.operation = operation;
		}
		
		public static Operations create(String value) {
			Operations ret = UNKNOWN;
			for (Operations o : values()) {
				if (o.operation.equalsIgnoreCase(value)) {
					ret = o;
				}
			}
			return ret;
		}
	}
	
}
