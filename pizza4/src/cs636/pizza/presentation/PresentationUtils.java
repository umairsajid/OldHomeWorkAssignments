package cs636.pizza.presentation;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import cs636.pizza.domain.PizzaOrder;
import cs636.pizza.domain.PizzaSize;
import cs636.pizza.domain.Topping;

import cs636.pizza.service.ServiceException; 


/**
 * Presentation Utility methods: none of these call the service layer
 * themselves. Instead, as proper "utilities", they just work with their 
 * argued data structures.
 *
 */
public class PresentationUtils {
	// for getMenuEntry menu handler
	public static final int NUM_OF_ATTEMPTS = 3;
	public static final int NO_MORE = -2;
	public static final String QUIT_KEY = "q";
	public static final int ERROR = -1;
	
	public static void printOrderStatus(List<PizzaOrder> report, PrintStream out)
	{
		for (PizzaOrder order: report) {
			out.println("----------Order Status--------------");
			out.println(order.getStatusString());
			out.print("Order's Toppings: ");
			for (Topping t: order.getToppings()) {
				out.print(t.getToppingName()+ " ");
			}
			out.print("\nOrder's Size: ");
			out.println(order.getPizzaSize().getSizeName());
			out.println("-------------------------------------");
			}
	}
	// find topping object for given name
	public static Topping getToppingFromToppings(Set<Topping> toppings, String name)
	{
		for (Topping t: toppings)
			if (t.getToppingName().equals(name))
				return t;
		return null;
	}
	// find topping object for given id
	public static Topping getToppingFromToppings(Set<Topping> toppings, int id)
	{
		for (Topping t: toppings)
			if (t.getId() == id)
				return t;
		return null;
	}
	// find PizzaSize object for given name
	public static PizzaSize getSizeFromSizes(Set<PizzaSize> sizes, String name)
	{
		for (PizzaSize s: sizes)
			if (s.getSizeName().equals(name))
				return s;
		return null;
	}
	
	// find PizzaSize object for given id
	public static PizzaSize getSizeFromSizes(Set<PizzaSize> sizes, int id)
	{
		for (PizzaSize s: sizes)
			if (s.getId() == id)
				return s;
		return null;
	}

	// we shouldn't create a domain object in the presentation layer, but
	// we can reuse one we got from the service layer--find the right one
	// in allToppings and put it in chosenToppings
	
	public static void addToppingToChosenSet(String name, Set<Topping> chosenToppings, 
			Set<Topping> allToppings)
	{
		Topping chosenTopping = 
			getToppingFromToppings(allToppings, name);
		chosenToppings.add(chosenTopping);
	}
	public static void printReport(List<PizzaOrder> report, PrintStream out) throws ServiceException 
	{
		for (PizzaOrder order: report) {
			out.println(order);
			out.println("---------------------");
		}
	}
	// a primitive menu handler: displays choices, gets choice # from user
	// (a non-negative number or QUIT_KEY)
	// Returns choice number (Map key value), or NO_MORE, or ERROR
	public static int getMenuEntry(String promptMsg, Map<Integer, String> validEntries,
			Scanner in) throws IOException {
		int loop = 0;
		while (loop < NUM_OF_ATTEMPTS) {
			for (int id : validEntries.keySet()) {
				System.out.println("" + id + "  " + validEntries.get(id));
			}
			String entryLine = PresentationUtils.readEntry(in, promptMsg);
			if (entryLine.equalsIgnoreCase(QUIT_KEY))
				return NO_MORE;
			loop++;
			int num = -1;
			try {
				num = new Integer(entryLine);  // user's choice number
			} catch (NumberFormatException e) {
				System.out.println("Please enter a whole number");
				continue; // let user try again
			}
			if (validEntries.get(num) != null)  
				return num;
			else {
				System.out.println("Not a valid choice, try again");
				continue;
			}
		}
		System.out.println("Invalid Entry after " + NUM_OF_ATTEMPTS
				+ " attempts");
		return ERROR;
	}
	// super-simple prompted input from user
	// changed print to println, so prompts show under ant execution
	public static String readEntry(Scanner in, String prompt) throws IOException {
		System.out.println(prompt + ":");
		return in.nextLine().trim();
	}
	
}
