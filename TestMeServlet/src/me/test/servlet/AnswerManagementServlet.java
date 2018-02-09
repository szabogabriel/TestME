package me.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jmtemplate.Template;

import me.test.Main;
import me.test.entity.user.User;
import me.test.servlet.viewmodel.ResponseManagementVM;
import me.test.template.TemplateLoader;

@WebServlet("/answersManagement")
public class AnswerManagementServlet extends BasicServlet {

	private static final long serialVersionUID = 5176087527841589434L;
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.loadTemplate("responseManagement.template");

	@Override
	void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, User user)	throws ServletException, IOException {
		// TODO Auto-generated method stub
		ResponseManagementVM vm = new ResponseManagementVM();
		
		Main.INSTANCE.getListAnswerUC().getAnswers(() -> null);
		
		response.getWriter().print(TEMPLATE.render(vm.provideData()));
	}

	@Override
	void doGetLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}

	@Override
	void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		// TODO Handle request of the answers.
		response.sendRedirect("index");
	}

	@Override
	void doPostLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index");
	}

}
