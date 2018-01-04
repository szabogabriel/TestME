package me.test.servlet;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jmtemplate.Template;

import me.test.Main;
import me.test.entity.test.Test;
import me.test.entity.user.User;
import me.test.servlet.viewmodel.TestVM;
import me.test.template.TemplateLoader;
import me.test.tools.QueryString;
import me.test.usecase.test.list.QueryTestsRequestData;

@WebServlet("/test")
public class TestServlet extends BasicServlet {
	private static final long serialVersionUID = -4692692000699899245L;
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.loadTemplate("test.template");

	public TestServlet() {
        super();
    }

	private Test getTest(QueryString queryString) {
		Test ret = null;
		if (queryString.isKnownKeyAndHaveValue("title")) {
			String testName = queryString.getValue("title");
			
			List<Test> tests = Main.INSTANCE.getQueryTestsUC().getTests(new QueryTestsRequestData() {
				@Override
				public Boolean getActiveOnly() {
					return Boolean.TRUE;
				}

				@Override
				public Predicate<Test> getFilter() {
					return T -> T.getName().equals(testName);
				}
			}).getTests();
			
			if (tests.size() > 0) {
				ret = tests.get(0);
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
