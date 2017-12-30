package me.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jmtemplate.Template;

import me.test.Main;
import me.test.entity.user.User;
import me.test.servlet.viewmodel.LoginVM;
import me.test.template.TemplateLoader;
import me.test.usecase.user.credentials.AuthenticateUserRequestData;

@WebServlet("/login")
public class Login extends BasicServlet {
	private static final long serialVersionUID = 5239806670146848466L;
	
	private static final Template TEMPLATE_GET = TemplateLoader.INSTANCE.loadTemplate("login.template");
	private static final Template TEMPLATE_LOGIN_SUCCESS = TemplateLoader.INSTANCE.loadTemplate("login.success.template");
	private static final Template TEMPLATE_LOGIN_ERROR = TemplateLoader.INSTANCE.loadTemplate("login.error.template");

	public Login() {
        super();
    }
	
	@Override
	void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException  {
		response.sendRedirect("index");
	}

	@Override
	void doGetLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		LoginVM vm = new LoginVM();
		response.getWriter().print(TEMPLATE_GET.render(vm.provideData()));
	}

	@Override
	void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException  {
		doGetLoggedIn(request, response, user);
	}

	@Override
	void doPostLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		LoginVM vm = new LoginVM();
		String username = getUsername(request);
		String password = getPassword(request);
		
		HttpSession session = null;
		
		if (checkCredentials(username, password)) {
			session = request.getSession();
			session.setAttribute("username", username);
			if (!request.getParameterMap().containsKey("rememberme")) {
				session.setMaxInactiveInterval(30 * 60);
			}
		}
		
		if (session == null) {
			response.getWriter().print(TEMPLATE_LOGIN_ERROR.render(vm.provideData()));
		} else {
			vm.setUsername(username);
			vm.setSuccessful(true);
			response.getWriter().print(TEMPLATE_LOGIN_SUCCESS.render(vm.provideData()));
		}
	}
	
	private boolean checkCredentials(String username, String password) {
		return Main.INSTANCE.getAuthenticateUserUC().isCredentialsCorrect(new AuthenticateUserRequestData() {
			@Override
			public String getUserName() {
				return username;
			}
			@Override
			public String getPassword() {
				return password;
			}
		}).result();
	}
	
	private String getUsername(HttpServletRequest request) {
		String ret = request.getParameter("username");
		if (ret == null) {
			ret = "";
		}
		return ret;
	}
	
	private String getPassword(HttpServletRequest request) {
		String ret = request.getParameter("password");
		if (ret == null) {
			ret = "";
		}
		return ret;
	}

}
