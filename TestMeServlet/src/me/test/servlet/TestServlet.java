package me.test.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import me.test.usecase.answer.create.TestAnswerRequestData;
import me.test.usecase.answer.create.TestAnswerResponseData;

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
			
			List<Test> tests = Main.INSTANCE.getQueryTestsUC().getTests(() -> (T -> T.getName().equals(testName) && T.isActive())).getTests();
			
			if (tests.size() > 0) {
				ret = tests.get(0);
			}
		}
		return ret;
	}

	@Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		doGetCommon(request, response, true, user.getUsername());
	}

	@Override
	protected void doGetLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGetCommon(request, response, false, null);		
	}
	
	private void doGetCommon(HttpServletRequest request, HttpServletResponse response, boolean loggedIn, String username) throws ServletException, IOException {
		TestVM viewModel = new TestVM();
		
		if (loggedIn && username != null) {
			viewModel.setLoggedIn(true);
			viewModel.setUsername(username);
		}
		
		QueryString qs = new QueryString(request.getQueryString());
		
		viewModel.setResponserAge(Integer.parseInt(getResponderAge(qs)));
		viewModel.setRespName(getResponderName(qs));
		
		Test test = getTest(qs);
		
		if (test != null) {
			viewModel.setTest(test);
		}
		
		response.getWriter().append(TEMPLATE.render(viewModel.provideData()));
	}
	
	private String getResponderAge(QueryString qs) throws UnsupportedEncodingException {
		return URLDecoder.decode(qs.getValue("msg"), "UTF-8");
	}
	
	private String getResponderName(QueryString qs) throws UnsupportedEncodingException {
		return URLDecoder.decode(qs.getValue("email"), "UTF-8");
	}
	
	@Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		doPostLoggedOut(request, response);
	}

	@Override
	protected void doPostLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> params = request.getParameterMap();
		
		TestAnswerResponseData resp = Main.INSTANCE.getTestAnswerUC().publishAnswer(new TestAnswerRequestDataImpl(params));
		
		response.sendRedirect("index");
		
	}
	
	private class TestAnswerRequestDataImpl implements TestAnswerRequestData {
		
		private static final String KEY_RESP_NAME = "username";
		private static final String KEY_RESP_AGE = "age";
		private static final String KEY_TEST_NAME = "test";
		private static final String KEY_PREFIX_ANSWER = "answer_";
		
		private String username = null;
		private String age = null;
		private String test = null;
		private Map<Integer, String> answers = new HashMap<>();
		
		private TestAnswerRequestDataImpl(Map<String, String[]> postedData) {
			username = postedData.get(KEY_RESP_NAME)[0];
			age = postedData.get(KEY_RESP_AGE)[0];
			test = postedData.get(KEY_TEST_NAME)[0];
			
			for (String it : postedData.keySet()) {
				if (it.startsWith(KEY_PREFIX_ANSWER)) {
					Integer key = Integer.parseInt(it.split("_")[1]);
					answers.put(key, postedData.get(it)[0]);
				}
			}
		}

		@Override
		public String getAnswer(Integer questionNumber) {
			return answers.get(questionNumber);
		}

		@Override
		public String getResponderName() {
			return username;
		}

		@Override
		public int getResponderAge() {
			int ret;
			try {
				ret = Integer.parseInt(age);
			} catch (Exception e) {
				ret = -1;
			}
			return ret;
		}

		@Override
		public String getTestName() {
			return test;
		}
		
	}

}
