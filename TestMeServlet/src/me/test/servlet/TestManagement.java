package me.test.servlet;

import java.io.IOException;
import java.util.Map;

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
import me.test.usecase.test.list.QueryTestsUC;

@WebServlet("/testsManagement")
public class TestManagement extends BasicServlet {
	private static final long serialVersionUID = -1761456224629755668L;
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.loadTemplate("testsManagement.template");

	@Override
	void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, User user)	throws ServletException, IOException {
		TestManagementVM data = new TestManagementVM();
		
		QueryTestsUC uc = Main.INSTANCE.getQueryTestsUC();
		
		data.add(uc.getTests(() -> null).getTests());
		
		data.activate(uc.getTests(() -> (t -> t.isActive())).getTests());
		
		data.setUsername(user.getUsername());
		
		response.getWriter().write(TEMPLATE.render(data.provideData()));
	}

	@Override
	void doGetLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}

	@Override
	void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		Map<String, String[]> swp = request.getParameterMap();
		
		Main.INSTANCE.getActivateTestUC().setActiveTests(() -> (swp.keySet()));
		
//		swp.keySet().stream().map(tst -> getTest(tst)).collect(Collectors.toSet());
		
		doGetLoggedIn(request, response, user);
	}
	
	@Override
	void doPostLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}
	
	private Test getTest(String name) {
		return Main.INSTANCE.getQueryTestsUC().getTests(() -> (t -> t.getName().equals(name))).getTests().get(0);
	}

}
