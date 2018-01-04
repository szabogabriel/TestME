package me.test.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.function.Predicate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jmtemplate.Template;

import me.test.Main;
import me.test.entity.test.Test;
import me.test.entity.user.User;
import me.test.servlet.viewmodel.TestManagementVM;
import me.test.template.TemplateLoader;
import me.test.usecase.test.activate.ActivateTestRequestData;
import me.test.usecase.test.list.QueryTestsRequestData;
import me.test.usecase.test.list.QueryTestsUC;

@WebServlet("/testsManagement")
public class TestManagement extends BasicServlet {
	private static final long serialVersionUID = -1761456224629755668L;
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.loadTemplate("testsManagement.template");

	@Override
	void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, User user)	throws ServletException, IOException {
		TestManagementVM data = new TestManagementVM();
		
		QueryTestsUC uc = Main.INSTANCE.getQueryTestsUC();
		
		data.add(
			uc.getTests(new QueryTestsRequestData() {
				@Override public Predicate<Test> getFilter() { return t -> true; }
				@Override public Boolean getActiveOnly() { return Boolean.FALSE; }
			}).getTests());
		
		data.activate(
			uc.getTests(new QueryTestsRequestData() {
				@Override public Predicate<Test> getFilter() { return t -> true; }
				@Override public Boolean getActiveOnly() { return Boolean.TRUE;	}
			}).getTests());
		
		response.getWriter().write(TEMPLATE.render(data.provideData()));
	}

	@Override
	void doGetLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}

	@Override
	void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		Map<String, String[]> swp = request.getParameterMap();
		
		swp.keySet().stream().forEach(t -> Main.INSTANCE.getActivateTestUC().activate(new Request(t)));
		
		doGetLoggedIn(request, response, user);
	}

	@Override
	void doPostLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}
	
	private class Request implements ActivateTestRequestData {
		private String data;
		
		public Request(String data) {
			this.data = data;
		}
		
		@Override
		public String getTestCaseName() { 
			return data;
		}
	}

}
