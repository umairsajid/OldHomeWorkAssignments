package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.config.MusicSystemConfig;
import cs636.music.service.AdminServiceAPI;
import cs636.music.service.UserServiceAPI;

// like Spring MVC DispatcherServlet and its config file, but simpler.
// This servlet is handling the pages of the music project,
// calling on various controllers, one for each page.
// Note that all the jsp filenames (for views) are in this file, not
// in the controllers themselves.  Each controller is set up
// here and given its forward-to URLs in its constructor.

public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 3971217231726348088L;
	private static UserServiceAPI userService;
	private static AdminServiceAPI adminService;

	// The controllers, one per user page
	private static Controller userWelcomeController;
	private static Controller cartController;
	private static Controller catalogController;
	private static Controller invoiceController;
	private static Controller listenController;
	private static Controller downloadController;
	private static Controller productController;
	private static Controller registrationController;
	
	private static Controller initializeDatabaseController;
	private static Controller processInvoiceController;
	private static Controller reportController;
	
	// String constants for URL's
	private static final String WELCOME_URL = "/WEB-INF/jsp/welcome.jsp";
	private static final String USER_WELCOME_URL = "/WEB-INF/jsp/userWelcome.jsp";
	private static final String CART_URL = "/WEB-INF/jsp/cart.jsp";
	private static final String CATALOG_URL = "/WEB-INF/jsp/catalog.jsp";
	private static final String INVOICE_URL = "/WEB-INF/jsp/invoice.jsp";
	private static final String SOUND_URL = "/WEB-INF/jsp/sound.jsp";
	private static final String PRODUCT_URL = "/WEB-INF/jsp/product.jsp";
	private static final String REGISTER_URL = "/WEB-INF/jsp/register.jsp";
	// Admin pages (incomplete: see music3 for full admin pages)
	private static final String INIT_DB_URL = "/WEB-INF/jsp/initdb.jsp";
	private static final String PROCESS_INVOICES_URL = "/WEB-INF/jsp/processInvoices.jsp";
	private static final String REPORT_URL = "/WEB-INF/jsp/reports.jsp";
	
	// Initialization of servlet: runs before any request is
	// handled in the web app. It does MusicSystemConfig initialization
	// then sets up all the controllers
	@Override
	public void init() throws ServletException {
		System.out.println("Starting dispatcher servlet initialization");
		// If configureServices fails, it logs errors to the tomcat log, 
		// then throws (not caught here), notifying tomcat of its failure,
		// so tomcat won't allow any requests to be processed
		MusicSystemConfig.configureServices();
		userService = MusicSystemConfig.getUserService();
		adminService = MusicSystemConfig.getAdminService();
		// create all the controllers and their forward URLs
		userWelcomeController = new UserWelcomeController(USER_WELCOME_URL);
		cartController = new CartController(userService, CART_URL);
		catalogController = new CatalogController(userService, CATALOG_URL);
		invoiceController = new InvoiceController(userService, INVOICE_URL);
		listenController = new ListenController(userService, SOUND_URL);
		downloadController = new DownloadController(userService);  // computes redirect URL
		productController = new ProductController(userService, PRODUCT_URL);
		registrationController = new RegistrationController(REGISTER_URL);
		
		initializeDatabaseController = new InitializeDatabaseController(adminService, INIT_DB_URL);
		processInvoiceController = new ProcessInvoiceController(adminService, PROCESS_INVOICES_URL);
		reportController = new ReportController(adminService, REPORT_URL);
	}
	
	// Called when app server is shutting this servlet down
	// because it is shutting the app down.
	// Since this servlet is in charge of this app, it is
	// the one to respond by shutting down the BL+DAO
	// (the SysTestServlet ignores the shutdown)
	@Override
	public void destroy() {
		System.out.println("DispatcherServlet: shutting down");
		MusicSystemConfig.shutdownServices();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestURL = request.getServletPath(); 
		System.out.println("DispatcherServlet: requestURL = " + requestURL);
		// At userWelcome, the user gets a UserBean.
		// If it's not there for subsequent pages, hand the request to
		// studentWelcome. Having the bean is like being "logged in".
		boolean hasBean = (request.getSession().getAttribute("user") != null);

		// Dispatch to the appropriate Controller, which will determine
		// the next URL to use as well as do its own actions.
		// The URL returned by handleRequest will be a servlet-relative URL, 
		// like /WEB-INF/jsp/foo.jsp (a view) 
		// or /something.html (for a controller).
		// Note that although resources below /WEB-INF are inaccessible
		// to direct HTTP requests, they are accessible to forwards
		String forwardURL = null; 
		if (requestURL.equals("/welcome.html"))
			forwardURL = WELCOME_URL; // no controller needed
		else if (requestURL.equals("/adminWelcome.html"))
			forwardURL = "/WEB-INF/jsp/adminWelcome.jsp"; // no controller needed
		else if (requestURL.equals("/initdb.html"))
			forwardURL = initializeDatabaseController.handleRequest(request, response);
		else if (requestURL.equals("/processInvoices.html"))
			forwardURL = processInvoiceController.handleRequest(request, response);
		else if (requestURL.equals("/reports.html"))
			forwardURL = reportController.handleRequest(request, response);
        // For non-admin pages, test for bean, and if not there, send user to user welcome page
		else if (requestURL.equals("/userWelcome.html") || !hasBean)  
			forwardURL = userWelcomeController.handleRequest(request, response);
		else if (requestURL.equals("/catalog.html"))
			forwardURL = catalogController.handleRequest(request, response);
		else if (requestURL.equals("/cart.html"))
			forwardURL = cartController.handleRequest(request, response);
		else if (requestURL.equals("/product.html"))
			forwardURL = productController.handleRequest(request, response);
		else if (requestURL.equals("/invoice.html")) 
			forwardURL = invoiceController.handleRequest(request, response);
		else if (requestURL.equals("/listen.html")) 
			forwardURL = listenController.handleRequest(request, response);
		else if (requestURL.equals("/download.do"))   // download.html didn't work
			forwardURL = downloadController.handleRequest(request, response);
		else if (requestURL.equals("/register.html")) 
			forwardURL = registrationController.handleRequest(request, response); 
		else 
			throw new ServletException("DispatcherServlet: Unknown servlet path: "
					+ requestURL);

		System.out.println("DispatcherServlet: forwarding to "+ forwardURL);
	
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(forwardURL);
		dispatcher.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
