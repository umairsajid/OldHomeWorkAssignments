package cs636.pizza.presentation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import cs636.pizza.config.PizzaSystemConfig;

import cs636.pizza.service.AdminService;
import cs636.pizza.service.ServiceException;
import cs636.pizza.service.StudentService;

import cs636.pizza.domain.PizzaOrder;
import cs636.pizza.domain.PizzaSize;
import cs636.pizza.domain.Topping;

/**
 * @author Saaid Baraty
 * 
 *         This class tests the whole system.
 */
public class SystemTest {

	private AdminService adminService;
	private StudentService studentService;
	private String inFile;

	public SystemTest(String inFile) {
		this.inFile = inFile;
		// configureServices is no longer being called by a
		// static initializer in PizzaSystemConfig
		// Instead, we call it explicitly here--
		PizzaSystemConfig.configureServices();
		adminService = PizzaSystemConfig.getAdminService();
		studentService = PizzaSystemConfig.getStudentService();
	}
	// for client-server case only
	public static void main(String[] args) {
		String inFile = null;
		if (args.length == 1) {
			inFile = args[0];
		} else  {
			System.out.println("usage:java <inputFile> ");
			System.exit(1);
		}
		SystemTest test = null;
		try {
				test = new SystemTest(inFile);
				test.run();
				System.out.println("Run complete, exiting");
		} catch (Exception e) {
			// System.err.println("Error in run: StackTrace for it: ");
			// e.printStackTrace();
			System.err.println("Error in run of SystemTest: " + PizzaSystemConfig.exceptionReport(e));
		}
	}
	
	// run tests: for client-server or web cases
	public void run() throws IOException, ServiceException {
		String command = null;
		Scanner in = new Scanner(new File(inFile));
		while ((command = getNextCommand(in)) != null) {
			System.out.println("\n\n*************" + command
					+ "***************\n");
			if (command.equalsIgnoreCase("ai")) { // admin init db
				adminService.initializeDb(); // create new tables, etc.
				adminService.addPizzaSize("small");
				adminService.addTopping("Pepperoni");
			} else if (command.equalsIgnoreCase("anr")) // admin next ready
				adminService.markNextOrderReady();
			else if (command.equalsIgnoreCase("aad")) // admin advance day
				adminService.advanceDay();
			else if (command.equalsIgnoreCase("acr")) { // admin college report
				List<PizzaOrder> report = adminService.getAdminReport();
				PresentationUtils.printReport(report, System.out);
			} else if (command.equalsIgnoreCase("adr")) { // admin daily report
				List<PizzaOrder> report = adminService.getDailyReport();
				PresentationUtils.printReport(report, System.out);
			} else if (command.startsWith("ss")) // student status
				handleOrderStatus(command);
			else if (command.startsWith("so")) // student order
				handleStudentOrder(command);
			else
				System.out.println("Invalid Command: " + command);
			System.out.println("----OK");
		}
		in.close();
	}

	private void handleOrderStatus(String command) throws ServiceException {
		String[] tokens = getTokens(command);
		int roomNumber = Integer.parseInt(tokens[1]);
		List<PizzaOrder> report = studentService.getOrderStatus(roomNumber);
		PresentationUtils.printOrderStatus(report, System.out);
	}

	private void handleStudentOrder(String command) throws ServiceException {
		String[] tokens = getTokens(command);
		int roomNumber = Integer.parseInt(tokens[1]);
		Set<Topping> allToppings = studentService.getToppings();
		Set<PizzaSize> allPizzaSizes = studentService.getPizzaSizes();
		// get a particular PizzaSize
		PizzaSize chosenPizzaSize = allPizzaSizes.iterator().next();
		studentService.makeOrder(roomNumber, chosenPizzaSize, allToppings);
	}
	
	// Return line or null if at end of file
	public String getNextCommand(Scanner in) throws IOException {
		String line = null;
		try {
			line = in.nextLine();
		} catch (NoSuchElementException e) { } // leave line null
		return (line != null) ? line.trim() : line;
	}

	// use powerful but somewhat mysterious split method of String
	private String[] getTokens(String command) {
		return command.split("\\s+"); // white space
	}
}
