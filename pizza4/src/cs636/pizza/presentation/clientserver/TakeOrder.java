package cs636.pizza.presentation.clientserver;

import cs636.pizza.presentation.PresentationUtils;
import cs636.pizza.service.StudentService;
import cs636.pizza.service.ServiceException;
import cs636.pizza.domain.PizzaOrder;
import cs636.pizza.domain.PizzaSize;
import cs636.pizza.domain.Topping;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;

import cs636.pizza.config.PizzaSystemConfig;
//use static import to simplify use of constants--
import static cs636.pizza.config.PizzaSystemConfig.NUM_OF_ROOMS;

// Student's user interface for order and status report.

public class TakeOrder {
	
	private StudentService studentService;
	private static Scanner in = new Scanner(System.in); // the user
	
	public TakeOrder() {
		// In pizza4, there is no static initializer in PizzaSystemConfig,
		// so we call configureServices() here--
		PizzaSystemConfig.configureServices();
		studentService = PizzaSystemConfig.getStudentService();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TakeOrder orderTaker;
		try {
			orderTaker = new TakeOrder();
			// loop until Q command
			while (orderTaker.executeCommand())			
				;
			System.out.println("Thanks for visiting the pizza shop.");
		} catch (Exception e) {
			System.err.println("Error in run: StackTrace for it: ");
			e.printStackTrace();
			System.err.println("Error in run, shorter report: " + PizzaSystemConfig.exceptionReport(e));
		}
	}
	
	// execute one student command: O, S, or Q
	public boolean executeCommand() throws IOException {
		
		System.out.println("Possible Commands");
		System.out.println("O: Order");
		System.out.println("S: Status Report");
		System.out.println("Q: Quit");
		String command = PresentationUtils.readEntry(in, "Please Enter the Command");
		if (command.equalsIgnoreCase("O"))
			try {
				getTheOrder();
			} catch (ServiceException e) {
				System.out.println("Sorry, problem with inserting order: " + e);
			}
		else if (command.equalsIgnoreCase("S")) {
			String room = PresentationUtils.readEntry(in, "Enter the room number");
			try {
				List<PizzaOrder> report = 
					studentService.getOrderStatus(Integer.parseInt(room));
				PresentationUtils.printOrderStatus(report, System.out);
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input!");
			} catch (ServiceException e) {
				System.out.println("Sorry, problem with getting order status, cause =: "
						+ e);
			}
		} else if (command.equalsIgnoreCase("Q")) {
			return false;
		} else
			System.out.println("Invalid Command!");
		return true; // continue
	}
	
	public void getTheOrder() throws IOException, ServiceException {
		String roomNumStr = PresentationUtils.readEntry(in, 
			"Please Enter the room Number");
		int roomNum = 0;
		if ((roomNum = checkNumInput(roomNumStr, NUM_OF_ROOMS)) < 0) {
			System.out.println("Invalid Room Number");
			return;
		}
		listMenu();
		System.out.println("Available pizza sizes to choose from:");
		Set<PizzaSize> allSizes = studentService.getPizzaSizes();
		if (allSizes.size() == 0) {
			System.out.println("Sorry, no pizza sizes available (admin needs to add them)");
			return;
		}
			
		// set up map of choice numbers to name for menu
		Map<Integer,String> sizeTokens = new TreeMap<Integer, String>();
		int choiceNum = 1;
		for (PizzaSize s : allSizes) {
			sizeTokens.put(choiceNum++, s.getSizeName());
		}
	
		int sizeChoice = 
			PresentationUtils.getMenuEntry("Enter the size #", sizeTokens, in);
		if (sizeChoice < 0) {
			System.out.println("No size specified, please try again");
			return;
		}
		String chosenSizeName = sizeTokens.get(sizeChoice);
		// we shouldn't *create* a domain object in the presentation layer, but
		// we can reuse one we got from the service layer--find the right one--
		PizzaSize chosenSize = PresentationUtils.getSizeFromSizes(allSizes,
				chosenSizeName);

		System.out.println("Available pizza toppings to choose from:");
		Set<Topping> allToppings = studentService.getToppings();

		Map<Integer, String> toppingTokens = new TreeMap<Integer, String>();
		int toppingChoice = 1;
		for (Topping t : allToppings) {
			toppingTokens.put(toppingChoice++, t.getToppingName());
		}
		Set<Topping> chosenToppings = new TreeSet<Topping>();
		while (true) {
			int currToppingNum = 
				PresentationUtils.getMenuEntry("Enter Topping number, or " + 
						PresentationUtils.QUIT_KEY
					+ " for no more Toppings", toppingTokens, in);
			if (currToppingNum == PresentationUtils.NO_MORE)
				break;
			String currToppingName = toppingTokens.get(currToppingNum);
			// we shouldn't *create* a domain object in the presentation layer,
			// but we can reuse one we got from the service layer--find the
			// right one in allToppings and put it in chosenToppings
			PresentationUtils.addToppingToChosenSet(currToppingName,
					chosenToppings, allToppings);
		}
		studentService.makeOrder(roomNum, chosenSize, chosenToppings);
		System.out.println("Thank you for your order");
	}


	private int checkNumInput(String numStr, int maxBound) {
		int num = 0;
		try {
			num = Integer.parseInt(numStr);
			if (num > 0 && num <= maxBound)
				return num;
		} catch (NumberFormatException e) {
		}
		return -1;
	}

	private void listMenu() throws ServiceException {
		System.out.println("Basic Pizza: tomato sauce and cheese ");
		System.out.println("Additional toppings:");
		Set<Topping> toppings = studentService.getToppings();
		for (Topping t: toppings)
			System.out.println("  " + t.getToppingName());
		System.out.println("Sizes:");

		Set<PizzaSize> sizes = studentService.getPizzaSizes();
		for (PizzaSize s: sizes)
			System.out.println("  " + s.getSizeName());
	}
	
}
