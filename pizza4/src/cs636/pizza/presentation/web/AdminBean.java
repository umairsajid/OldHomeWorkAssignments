package cs636.pizza.presentation.web;

import cs636.pizza.presentation.PresentationUtils;
import cs636.pizza.service.AdminService;
import cs636.pizza.service.StudentService;
import cs636.pizza.config.PizzaSystemConfig;
import cs636.pizza.service.ServiceException;

import java.util.List;
import java.util.Set;

import cs636.pizza.domain.PizzaOrder;
import cs636.pizza.domain.PizzaSize;
import cs636.pizza.domain.Topping;

public class AdminBean {

	private AdminService adminService;
	private StudentService studentService;
	// Note that we have no real session variables here

	// Properties used from JSP--only used for in-request communication
	// i.e., not real session variables holding state between requests
	private String command; // add or delete

	private String item; // topping or size ID

	// get service object ref at start of session for current database
	public AdminBean() {
		adminService = PizzaSystemConfig.getAdminService();
		studentService = PizzaSystemConfig.getStudentService();
	}
	
	//Mutators, called from scriptlets, to avoid using getters as mutators
	public String initDB() {
		String info = null;
		try {
			adminService.initializeDb();			
			info = "OK";
		} catch (ServiceException e) {
			// for development, put full error report in page
			info = "failed: " + PizzaSystemConfig.exceptionReport(e);		}
		return info;
	}

	public String makeNextOrderReady() {
		String info = null;
		try {
			adminService.markNextOrderReady();
			info = "success";
		} catch (ServiceException e) {
			info = "failed: " + PizzaSystemConfig.exceptionReport(e);
		}
		return info;
	}

	public String advanceDay() {
		String info = null;
		try {
			adminService.advanceDay();
			info = "success";
		} catch (ServiceException e) {
			info = "failed: " + PizzaSystemConfig.exceptionReport(e);
		}
		return info;
	}


	public String changeSize() {
		String info = "";
		if (item != null) {
			// null command means textbox entry with <CR> submitted by browser 
			if (command == null || command.equalsIgnoreCase("add")) {
				info = addPizzaSize(item);  // item is name
			} else if (command.equalsIgnoreCase("remove")) {
				info = removeSize(item);  // item is id
			} else {
				info = "Failed because of bad request parameter: "+ command;
				System.out.println("changeTopping failed because of bad request parameter: "+ command);
			}
		}
		reset();
		return info;
	}

	public String changeTopping() {
		String info = "";
		if (item != null) {
			// null command means textbox entry with <CR> submitted by browser 
			if (command == null || command.equalsIgnoreCase("add")) {
				info = addTopping(item); // item is name
			} else if (command.equalsIgnoreCase("remove")) {
				info = removeTopping(item); // item is id
			} else {
				info = "Failed because of bad request parameter: "+ command;
				System.out.println("changeTopping failed because of bad request parameter: "+ command);
			}
		}
		reset();
		return info;
	}

	private String addPizzaSize(String sizeName) {
		String result;
		try {
			adminService.addPizzaSize(sizeName);
			result = "Success";
		} catch (ServiceException e) {
			result = "failed: " + PizzaSystemConfig.exceptionReport(e);
		}
		return result;
	}

	private String addTopping(String topping) {
		String result;
		try {
			adminService.addTopping(topping);
			result = "Success";
		} catch (ServiceException e) {
			result = "failed: " + PizzaSystemConfig.exceptionReport(e);
		}
		return result;
	}

	private String removeSize(String sizeId) {
		String result;
		try {
			// convert id to name for removeSize
			int id = Integer.parseInt(sizeId);
			Set<PizzaSize> allSizes = studentService.getPizzaSizes();	
			PizzaSize chosenSize = PresentationUtils.getSizeFromSizes(allSizes,
					id);			
			adminService.removeSize(chosenSize.getSizeName());
			result = "Success";
		} catch (ServiceException e) {
			result = "failed: " + PizzaSystemConfig.exceptionReport(e);
		}
		return result;
	}

	private String removeTopping(String toppingId) {
		String result;
		try {
			// convert id to name for removeTopping
			int id = Integer.parseInt(toppingId);
			Set<Topping> allToppings = studentService.getToppings();
			Topping chosenTopping = 
					PresentationUtils.getToppingFromToppings(allToppings, id);
			adminService.removeTopping(chosenTopping.getToppingName());
			result = "Success";
		} catch (ServiceException e) {
			result = "failed: " + PizzaSystemConfig.exceptionReport(e);
		}
		return result;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	// The next two methods are duplicated in StudentBean
	public Set<PizzaSize> getAllSizes() {
		Set<PizzaSize> allSizes = null;
		try {
			allSizes = studentService.getPizzaSizes();
		} catch (ServiceException e) {
			PizzaSystemConfig.exceptionReport(e);
		}
		return allSizes;
	}

	public Set<Topping> getAllToppings() {
		Set<Topping> allToppings = null;
		try {
			allToppings = studentService.getToppings();
		} catch (ServiceException e) {
			PizzaSystemConfig.exceptionReport(e);
		}
		return allToppings;
	}

	public 	List<PizzaOrder> getDailyReport()
	{
		try {
			return adminService.getDailyReport();
		} 
		catch (ServiceException e)
		{
			PizzaSystemConfig.exceptionReport(e);
			return null;
		}
	}
	public 	List<PizzaOrder> getAdminReport()
	{
		try {
			return adminService.getAdminReport();
		} catch (ServiceException e)
		{			
			return null;
		}
	}

	private void reset() {
		command = null;
		item = null;
	}
}
