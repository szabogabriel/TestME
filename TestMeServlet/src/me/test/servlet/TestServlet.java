package me.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jmtemplate.Template;

import me.test.Main;
import me.test.servlet.viewmodel.TestVM;
import me.test.template.TemplateLoader;
import me.test.test.Test;
import me.test.tools.QueryString;
import me.test.user.User;

@WebServlet("/test")
public class TestServlet extends BasicServlet {
	private static final long serialVersionUID = -4692692000699899245L;
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.loadTemplate("test.template");

	public TestServlet() {
        super();
    }

	private Test getTest(QueryString queryString) {
		Test ret = null;
		if (queryString.knownKey("title")) {
			String testName = queryString.getValue("title");
			if (Main.INSTANCE.getTestsHolder().isKnownTest(testName) && Main.INSTANCE.getTestsHolder().isActiveTest(testName)) {
				ret = Main.INSTANCE.getTestsHolder().getTest(testName);
			}
		}
		return ret;
	}

	@Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		doGetLoggedOut(request, response);
	}

	@Override
	protected void doGetLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TestVM viewModel = new TestVM();
		
		Test test = getTest(new QueryString(request.getQueryString()));
		
		if (test != null) {
			viewModel.setTest(test);
		}
		
		response.getWriter().append(TEMPLATE.render(viewModel.provideData()));		
	}

	@Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		doPostLoggedOut(request, response);
	}

	@Override
	protected void doPostLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO - handle the post of the test.
	}

}
