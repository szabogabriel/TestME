package me.test.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitServlet implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
