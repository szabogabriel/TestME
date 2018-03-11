package me.test.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jmtemplate.Template;

import me.test.Main;
import me.test.entity.answer.Answer;
import me.test.entity.test.Test;
import me.test.entity.user.User;
import me.test.servlet.viewmodel.ResponseManagementVM;
import me.test.template.TemplateLoader;

@WebServlet("/answersManagement")
public class AnswerManagementServlet extends BasicServlet {

	private static final long serialVersionUID = 5176087527841589434L;
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.loadTemplate("responseManagement.template");

	@Override
	void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, User user)	throws ServletException, IOException {
		ResponseManagementVM vm = new ResponseManagementVM();
		
		Map<Test, List<Answer>> data = Main.INSTANCE.getListAnswerUC().getAnswers(() -> null).getAnswers();
		vm.add(data);
		
		response.getWriter().print(TEMPLATE.render(vm.provideData()));
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

}
