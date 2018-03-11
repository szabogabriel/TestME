package me.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.test.Main;
import me.test.entity.answer.Answer;
import me.test.entity.test.Test;
import me.test.entity.user.User;
import me.test.tools.QueryString;

@WebServlet("/exportTest")
public class ListServlet extends BasicServlet {

	private static final long serialVersionUID = -6768181980702670687L;
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
	
	@Override
	void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		
		Test tst = getTestByName(getTestName(request));
		
		String desc = getAnswerDescription(request);
		
		if (tst != null) {
			sb.append(createCSVHeader(tst)).append("\n");
			
			List<Answer> answers = getAnswers(tst, desc);
			for (Answer it : answers) {
				String toAdd = createAnswerData(it);
				if (toAdd != null) {
					sb.append(toAdd).append("\n");
				}
			}
		}
		
		StringBuilder fileName = new StringBuilder();
		fileName
			.append((tst != null) ? tst.getName() : "unknown")
			.append((desc != null) ? desc : "")
			.append("_")
			.append(DATE_FORMAT.format(new Date(System.currentTimeMillis())))
			.append(".csv")
			;
		
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName.toString());
		
		PrintWriter pw = response.getWriter();
		pw.println(sb.toString());
	}

	@Override
	void doGetLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}

	@Override
	void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		response.sendRedirect("index");
	}

	@Override
	void doPostLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}
	
	private Test getTestByName(String testName) {
		return Main.INSTANCE.getQueryTestsUC()
				.getTests(() -> (t -> t.getName().equals(testName)))
				.getTests()
				.stream()
				.findFirst()
				.orElse(null);
	}
	
	private List<Answer> getAnswers(Test test, String desc) {
		List<Answer> answers = null;
		
		Map<Test, List<Answer>> tmp = Main.INSTANCE.getListAnswerUC()
				.getAnswers(() -> (t -> t.equals(test)))
				.getAnswers(); 
		
		if (tmp.containsKey(test)) {
			answers = tmp.get(test);
		}
		
		if (answers == null) {
			answers = new ArrayList<>();
		}
		
		if (desc != null) {
			return answers.stream().filter(a -> a.getDescription().equals(desc)).collect(Collectors.toList());
		} else {
			return answers;
		}
	}
	
	private String createAnswerData(Answer answer) {
		StringBuilder sb = new StringBuilder();
		try {
			sb
				.append(answer.getAge()).append(",")
				.append(answer.getUser()).append(",")
				.append(answer.getGender().toString()).append(",")
				.append(DATE_FORMAT.format(new Date(answer.getTimestamp())));
			
			for (int i = 0; i < answer.getTest().getQuestions().length; i++) {
				sb.append(",").append(answer.getAnswerValue(answer.getTest().getQuestions()[i]) + "");
			}
		} catch (Exception e) {
			return null;
		}
		
		return sb.toString();
	}
	
	private String createCSVHeader(Test tst) {
		StringBuilder sb = new StringBuilder();
		sb.append("age,username,gender,timestamp");
		for (int i = 0; i < countQuestions(tst); i++) {
			sb.append(",question." + (i + 1));
		}
		return sb.toString();
	}
	
	private int countQuestions(Test test) {
		int ret = 0;
		
		if (test != null) {
			ret = test.getQuestions().length;
		}
		
		return ret;
	}
	
	private String getTestName(HttpServletRequest request) {
		QueryString qs = new QueryString(request.getQueryString());
		return qs.getValue("test");
	}
	
	private String getAnswerDescription(HttpServletRequest request) {
		QueryString qs = new QueryString(request.getQueryString());
		return qs.getValue("desc");
	}

}
