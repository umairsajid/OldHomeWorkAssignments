package cs636.music.presentation.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.domain.Invoice;
import cs636.music.service.AdminServiceAPI;
import cs636.music.service.ServiceException;

public class ProcessInvoiceController implements Controller {
	private AdminServiceAPI adminService;
	private String view;

	public ProcessInvoiceController(AdminServiceAPI adminService, String view) {
		this.adminService = adminService;
		this.view = view;
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<Invoice> invoices = new ArrayList<Invoice>();
		String invoiceIdToProcessString = (String) request.getParameter("invoiceIdToProcess");
		Integer invoiceIdToProcess = null;
		if (invoiceIdToProcessString!= null)
			invoiceIdToProcess = Integer.parseInt(invoiceIdToProcessString);
		try {
			if (invoiceIdToProcess != null)
				adminService.processInvoice(invoiceIdToProcess);
			invoices = new ArrayList<Invoice>(adminService.getListofUnprocessedInvoices());
		} catch (ServiceException e) {
			System.out.println("ProcessInvoiceController: " + e);
			throw new ServletException(e);
		}
		request.setAttribute("invoices", invoices);
		return view;
	}
}
