package cs636.music.presentation.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.domain.Download;
import cs636.music.domain.Invoice;
import cs636.music.service.AdminServiceAPI;
import cs636.music.service.ServiceException;

public class ReportController implements Controller {
	private AdminServiceAPI adminService;
	private String view;

	public ReportController(AdminServiceAPI adminService, String view) {
		this.adminService = adminService;
		this.view = view;
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<Invoice> invoices = new ArrayList<Invoice>();
		List<Download> downloads = new ArrayList<Download>();
		try {
			invoices.addAll(adminService.getListofInvoices());
			downloads.addAll(adminService.getListofDownloads());
		} catch (ServiceException e) {
			System.out.println("ReportController: " + e);
			throw new ServletException(e);
		}
		request.setAttribute("invoices", invoices);
		request.setAttribute("downloads", downloads);
		return view;
	}
}
