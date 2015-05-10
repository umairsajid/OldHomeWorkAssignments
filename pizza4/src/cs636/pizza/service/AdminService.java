package cs636.pizza.service;

import cs636.pizza.dao.DbDAO;
import cs636.pizza.dao.AdminDAO;
import cs636.pizza.dao.PizzaOrderDAO;

import cs636.pizza.domain.PizzaOrder;
import cs636.pizza.domain.Topping;

import java.util.List;

/**
 * This class captures the business logic for admin-related interactions,
 * the actions that ordinary users (students) don't need.
 * 
 * Only one instance of this class is instantiated, i.e.,
 * its object is a singleton object, and this singleton receives 
 * references to the singleton DAO objects at its own creation time.
 */

// Note all the similar code for each service call. This can be eliminated by
// using container-managed transactions (not available in Tomcat, though).
// Note that each call catches DAO/JPA PersistenceExceptions and throws its own
// exception, after rolling back the transaction. The new exception, with
// a useful message, then gets caught in the presentation layer.

public class AdminService {

	private DbDAO dbDAO;
	private AdminDAO adminDAO;
	private PizzaOrderDAO pizzaOrderDAO;

	public AdminService(DbDAO db, AdminDAO admDAO, PizzaOrderDAO poDAO) {
		dbDAO = db;
		adminDAO = admDAO;
		pizzaOrderDAO = poDAO;
	}

	public void initializeDb() throws ServiceException {
		try {
			dbDAO.startTransaction();
			dbDAO.initializeDb();
			dbDAO.commitTransaction();
		} catch (Exception e) { // any exception
			// the following doesn't itself throw, but it handles the case that
			// rollback throws, discarding that exception object
			dbDAO.rollbackAfterException();
			throw new ServiceException(
					"Can't initialize DB: (probably need to load DB)", e);
		}
	}

	public void addTopping(String name) throws ServiceException {
		try {
			dbDAO.startTransaction();
			pizzaOrderDAO.createTopping(name);
			dbDAO.commitTransaction();
		} catch (Exception e) {
			dbDAO.rollbackAfterException();
			throw new ServiceException("Topping was not added successfully: ",
					e);
		}
	}

	public void markNextOrderReady() throws ServiceException {
		try {
			dbDAO.startTransaction();
			int ordNo = pizzaOrderDAO.findFirstOrder(PizzaOrder.PREPARING);
			pizzaOrderDAO.updateOrderStatus(ordNo, PizzaOrder.BAKED);
			dbDAO.commitTransaction();
		} catch (Exception e) {
			dbDAO.rollbackAfterException();
			throw new ServiceException("Error in marking the next order ready",
					e);
		}
	}

	public void advanceDay() throws ServiceException {
		try {
			dbDAO.startTransaction();
			List<PizzaOrder> pizzaOrders = getTodaysOrders();
			// day is done, so mark today's pizzas as "finished"
			for (PizzaOrder order : pizzaOrders) {
				pizzaOrderDAO.updateOrderStatus(order.getId(),
						PizzaOrder.FINISHED);
			}
			adminDAO.advanceDay();
			dbDAO.commitTransaction();
		} catch (Exception e) {
			dbDAO.rollbackAfterException();
			throw new ServiceException("Unsuccessful advance day", e);
		}
	}

	public void addPizzaSize(String name) throws ServiceException {
		try {
			dbDAO.startTransaction();
			pizzaOrderDAO.createPizzaSize(name);
			dbDAO.commitTransaction();
		} catch (Exception e) {
			dbDAO.rollbackAfterException();
			throw new ServiceException("Pizza size was not added successfully",
					e);
		}
	}

	public void removeTopping(String topping) throws ServiceException {
		try {
			dbDAO.startTransaction();
			pizzaOrderDAO.deleteTopping(topping);
			dbDAO.commitTransaction();
		} catch (Exception e) {
			dbDAO.rollbackAfterException();
			throw new ServiceException("Error while removing topping ", e);
		}
	}

	public List<PizzaOrder> getDailyReport() throws ServiceException {
		try {
			dbDAO.startTransaction();
			List<PizzaOrder> orders = getTodaysOrders();
			// materialize details during transaction
			// (they won't be accessed until the after the transaction is
			// committed, when it's too late to go back to the database)
			for (PizzaOrder o : orders) {
				o.getPizzaSize().getSizeName();
				for (Topping t : o.getToppings())
					t.getToppingName();
			}

			dbDAO.commitTransaction();
			return orders;
		} catch (Exception e) {
			dbDAO.rollbackAfterException();
			throw new ServiceException("Error while getting daily report ", e);
		}
	}

	// helper method to getDailyReport and advanceDay
	// executes inside the current transaction
	private List<PizzaOrder> getTodaysOrders() {
		int today = adminDAO.findCurrentDay();
		List<PizzaOrder> orders = pizzaOrderDAO.findOrdersByDays(today, today);
		return orders;
	}

	public void removeSize(String size) throws ServiceException {
		try {
			dbDAO.startTransaction();
			pizzaOrderDAO.deletePizzaSize(size);
			dbDAO.commitTransaction();
		} catch (Exception e) {
			dbDAO.rollbackAfterException();
			throw new ServiceException("Error while removing topping", e);
		}
	}

	public List<PizzaOrder> getAdminReport() throws ServiceException {
		List<PizzaOrder> report;
		try {
			dbDAO.startTransaction();
			int prevLastReportDay = adminDAO.findLastReportDay();
			int today = adminDAO.findCurrentDay();
			report = pizzaOrderDAO.findOrdersByDays(prevLastReportDay + 1,
					today);
			// materialize details during transaction
			for (PizzaOrder o : report) {
				o.getPizzaSize().getSizeName();
				for (Topping t : o.getToppings())
					t.getToppingName();
			}

			if (today > prevLastReportDay) {
				adminDAO.updateLastReportDate(today); // advance past reported
														// days
			}
			dbDAO.commitTransaction();
		} catch (Exception e) {
			dbDAO.rollbackAfterException();
			throw new ServiceException("Error in admin report", e);
		}
		return report;
	}

	public int getCurrentDay() throws ServiceException {
		int day;
		try {
			dbDAO.startTransaction(); // read-only
			day = adminDAO.findCurrentDay();
			dbDAO.commitTransaction();
		} catch (Exception e) {
			dbDAO.rollbackAfterException();
			throw new ServiceException("Can't access date in db: ", e);
		}
		return day;
	}

}
