package cs636.music.presentation.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.config.MusicSystemConfig;
import cs636.music.presentation.SystemTest;

// Quick way to run web app without JSPs

public class SysTestServlet extends HttpServlet {

	private static final long serialVersionUID = 3971217231726348088L;
	// Initialization of servlet: runs before any request is
	// handled in the web app. 
	@Override
	public void init() throws ServletException {
	    // This servlet is optional to the system, and
		// unlike DispatcherServlet, is configured in web.xml to 
		// load when needed, i.e, sometime after DispatcherServlet, 
		// which should have called configureServices already, but let's check...
		System.out.println("starting SysTestServlet");
		if (MusicSystemConfig.getAdminService() == null) {
			System.out.println(" SysTestServlet: found that configureServices has not been run yet");
			MusicSystemConfig.configureServices();
		}
	}
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String result;

		String contextPath = context.getContextPath();
		try {
			// in top-level directory deployed
			String path = context.getRealPath("test.dat");
			SystemTest test = new SystemTest(path);
			test.run();
			result = "Success";
		} catch (Exception e) {
			result = "Error in run: " + e + "Cause: " + e.getCause();
			System.out.println(result); // for tomcat's log
		}

		System.out.println(contextPath);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		System.out.println("in doGet");
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet running SystemTest</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println(" <h2> SystemTest Result </h2>");
		out.println("<p> "+ result + "</p>");
		out.println("<p> for more info, see tomcat log </p>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.close();
	}

}
