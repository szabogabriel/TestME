package me.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jmtemplate.Template;

import me.test.Main;
import me.test.servlet.viewmodel.MainVM;
import me.test.template.TemplateLoader;
import me.test.user.User;

@WebServlet("/index")
public class MainServlet extends BasicServlet {
	private static final long serialVersionUID = 4531522939350045396L;
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.loadTemplate("index.template");

	public MainServlet() {
        super();
    }

	@Override
	void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException  {
		//TODO - control view of the logged in user.
		response.sendRedirect("index");
	}

	@Override
	void doGetLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		MainVM viewModel = new MainVM();
		
		viewModel.setTests(Main.INSTANCE.getTestsHolder().getActiveTests());
		
		response.getWriter().append(TEMPLATE.render(viewModel.provideData()));
	}

	@Override
	void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException  {
		doGetLoggedIn(request, response, user);
	}

	@Override
	void doPostLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		doGetLoggedOut(request, response);
	}

}
