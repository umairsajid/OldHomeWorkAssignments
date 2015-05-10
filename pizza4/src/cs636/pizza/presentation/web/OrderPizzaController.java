package cs636.pizza.presentation.web;
// A validating form controller for the pizza order form
// If a chosen topping or pizza size disappears between form display
// and form processing, this code notices the problem and redisplays
// the form with the updated toppings and pizza sizes
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.pizza.domain.*;
import cs636.pizza.presentation.PresentationUtils;
import cs636.pizza.service.StudentService;
import cs636.pizza.service.ServiceException;

public class OrderPizzaController implements Controller {
	private StudentService studentService;
	// this controller forwards to two places, depending
	// on whether the form data is OK, allowing a pizza order,
	// or the form needs to be filled out again by the user:
	private String viewSuccess; // form OK and processed
	private String redoFormURL; // redo form (URL back to servlet)

	public OrderPizzaController(StudentService studentService, 
			String viewSuccess, String redoFormURL) {
		this.studentService = studentService;
		this.viewSuccess = viewSuccess;
		this.redoFormURL = redoFormURL;
	}

	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		StudentBean student = (StudentBean) session.getAttribute("student");
		System.out.println("in OrderPizzaController");
		String forwardURL;
		Set<PizzaSize> allSizes;
		Set<Topping> allToppings;
		String errorMessage = "";
		try {
			allSizes = studentService.getPizzaSizes();
			allToppings = studentService.getToppings();

			request.setAttribute("allSizes", allSizes);
			request.setAttribute("allToppings", allToppings);
			
			if (allSizes.size() == 0) 
				errorMessage += "No pizza sizes are now available, please try again later";
			else if (allToppings.size() == 0)
				errorMessage += "No toppings are now available, please try again later";
			
			if (errorMessage.length()>0) {
				request.setAttribute("errorMessage", errorMessage);
				forwardURL = redoFormURL; // show form again
				return forwardURL;
			}

			PizzaSize chosenSize = null;
			String sizeIdStr;
			if ((sizeIdStr = request.getParameter("sizeId")) == null) {
				errorMessage = "Please choose a pizza size. ";
			} else {
				int sizeId = Integer.parseInt(sizeIdStr);

				chosenSize = PresentationUtils.getSizeFromSizes(allSizes,
						sizeId);
				if (chosenSize == null) { 
					// size was there when we showed
					// the form, but disappeared since
					errorMessage = "Your chosen pizza size is no longer available, please choose again. ";
					// show the form again--with updated lists of sizes and
					// toppings
				}
			}
			String[] topIds = request.getParameterValues("toppings");
			Set<Topping> chosenToppings = null;
			if (topIds == null)
				errorMessage += "Please choose at least one topping. ";
			else {
				System.out.println("#toppings from form: " + topIds.length);
				chosenToppings = new TreeSet<Topping>();
				for (String i : topIds) {
					Topping topping = PresentationUtils.getToppingFromToppings(
							allToppings, Integer.parseInt(i));
					if (topping == null) {
						errorMessage += 
							"One of your chosen toppings is now gone, please choose again. ";
					} else
						chosenToppings.add(topping);
				}
			}
			if (errorMessage.length() > 0) {
				request.setAttribute("errorMessage", errorMessage);
				forwardURL = redoFormURL; // show form again
			} else {
				Integer roomNo = null;
				String roomNoStr;
				if ((roomNoStr = request.getParameter("room")) != null) {
					roomNo = Integer.parseInt(roomNoStr);
					student.setRoomNo(roomNo);
				}
				roomNo = student.getRoomNo(); // just set or from before
				if (roomNo < 0) {
					// bug: UI should have forced user to choose a good roomNo
					throw new ServletException(
							"OrderPizzaController: unexpected null roomNo");
				}
				// have toppings and size objects set up, and roomNo, call
				// BL to make order
				studentService.makeOrder(roomNo, chosenSize, chosenToppings);
				forwardURL = viewSuccess; // success
			}
		} catch (ServiceException e) {
			// almost certainly a bug, report to log--
			System.out.println("OrderPizzaController: order failed: "
					+ e + e.getCause());
			errorMessage = "Your order failed, please try again. " + e
					+ e.getCause();
			request.setAttribute("errorMessage", errorMessage);
			// let user have another chance at it (maybe useless)
			forwardURL = redoFormURL;
		}

		return forwardURL;
	}
}
