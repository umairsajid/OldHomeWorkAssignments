package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.service.AdminServiceAPI;
import cs636.music.service.ServiceException;

public class InitializeDatabaseController implements Controller {
	private AdminServiceAPI adminService;
	private String view;

	public InitializeDatabaseController(AdminServiceAPI adminService, String view) {
		this.adminService = adminService;
		this.view = view;
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		try {
			adminService.initializeDb();
		} catch (ServiceException e) {
			System.out.println("InitializeDatabaseController: " + e);
			throw new ServletException(e);
		}
		return view;
	}
}
