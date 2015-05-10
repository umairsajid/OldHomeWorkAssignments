package cs636.pizza.service;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs636.pizza.config.PizzaSystemConfig;
import cs636.pizza.domain.PizzaSize;
import cs636.pizza.domain.Topping;

public class TwoUsersTest {
	private AdminService adminService;
	private StudentService studentService;
	
	@Before
	public void setUp() throws Exception {
		PizzaSystemConfig.configureServices();
		adminService = PizzaSystemConfig.getAdminService();
		studentService = PizzaSystemConfig.getStudentService();
		adminService.initializeDb(); // bring system to known state
		adminService.addTopping("xxx"); // set up a topping and size
		adminService.addPizzaSize("yyy");
	}

	@After
	public void tearDown() throws Exception {
		// This executes even after an exception
		// do full shutdown to make sure that
		// even if a transaction is still going
		// it gets cleaned up
		PizzaSystemConfig.shutdownServices();	
	}
	
	
	// force a version mismatch on Topping
	// one user selects a topping, another deletes it
	// then first user orders with it
	// Note: with fancier code, we could check that the
	// exception message has "Topping" in it, as expected
	@Test(expected=ServiceException.class)
	public void testDropToppingMakeOrder() throws ServiceException  {
		// user 1 action
		Set<Topping> tops = studentService.getToppings();
		// user 2 action
		adminService.removeTopping("xxx");
		// user1 actions
		PizzaSize size = studentService.getPizzaSizes().iterator().next();
		studentService.makeOrder(1, size, tops);
	}

}
