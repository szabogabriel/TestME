package me.test.servlet;

import java.io.IOException;
import java.util.function.Predicate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jmtemplate.Template;

import me.test.Main;
import me.test.entity.test.Test;
import me.test.entity.user.User;
import me.test.servlet.viewmodel.MainVM;
import me.test.template.TemplateLoader;
import me.test.usecase.test.list.QueryTestsRequestData;

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
		MainVM viewModel = new MainVM();
		
		viewModel.setTests(getActiveTests());
		viewModel.setLoggedIn(true);
		
		response.getWriter().append(TEMPLATE.render(viewModel.provideData()));
	}

	@Override
	void doGetLoggedOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		MainVM viewModel = new MainVM();
		
		viewModel.setTests(getActiveTests());
		
		response.getWriter().append(TEMPLATE.render(viewModel.provideData()));
	}
	
	private Test[] getActiveTests() {
		return Main.INSTANCE.getQueryTestsUC().getTests(new QueryTestsRequestData() {
			
			@Override
			public Predicate<Test> getFilter() {
				return t -> true;
			}
			
			@Override
			public Boolean getActiveOnly() {
				return true;
			}
		}).getTests().stream().toArray(Test[]::new);
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
