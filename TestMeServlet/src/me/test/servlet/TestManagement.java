package me.test.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jmtemplate.Template;

import me.test.Main;
import me.test.entity.user.User;
import me.test.servlet.viewmodel.TestManagementVM;
import me.test.template.TemplateLoader;
import me.test.usecase.test.activate.ActivateTestRequestData;
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
		Map<String, String> descriptions = new HashMap<>();
		Set<String> tests = getActiveTests(swp);
		
		tests.stream().forEach(t -> descriptions.put(t, getDescription(swp, t)));
				
		Main.INSTANCE.getActivateTestUC().setActiveTests(new ActivateTestRequestImpl(tests, descriptions));
		
		doGetLoggedIn(request, response, user);
	}
	
	@Override
	void doPostLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}
	
	private Set<String> getActiveTests(Map<String, String[]> swp) {
		return swp.keySet().stream().filter(k -> k.endsWith("_testkey")).map(v -> v.substring(0, v.length() - "_testkey".length())).collect(Collectors.toSet());
	}
	
	private String getDescription(Map<String, String[]> swp, String test) {
		String key = swp.keySet().stream().filter(k -> k.equals(test + "_desckey")).findFirst().orElse(null);
		
		if (key != null) {
			String [] ret = swp.get(key);
			if (ret.length > 0) {
				return ret[0];
			}
			return "";
		}
		
		return "";
	}
	
	private class ActivateTestRequestImpl implements ActivateTestRequestData {
		
		private Set<String> testCasesName;
		private Map<String, String> description;
		
		public ActivateTestRequestImpl(Set<String> testCasesName, Map<String, String> description) {
			this.testCasesName = testCasesName;
			this.description = description;
		}

		@Override
		public Set<String> getTestCasesName() {
			return testCasesName;
		}

		@Override
		public String getDescription(String testName) {
			String ret = description.get(testName);
			if (ret == null) {
				ret = "";
			}
			return ret;
		}
	}

}
