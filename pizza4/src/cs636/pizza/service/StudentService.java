package cs636.pizza.service;

import cs636.pizza.dao.DbDAO;
import cs636.pizza.dao.AdminDAO;
import cs636.pizza.dao.PizzaOrderDAO;

import cs636.pizza.domain.PizzaOrder;
import cs636.pizza.domain.PizzaSize;
import cs636.pizza.domain.Topping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * This class captures the business logic for student interactions. 
 *
 * Only one instance of this class is instantiated, i.e.,
 * its object is a singleton object, and this singleton receives 
 * references to the singleton DAO objects at its own creation time.
 */


public class StudentService {

	private PizzaOrderDAO pizzaOrderDAO;

	private AdminDAO adminDAO;

	private DbDAO dbDAO;

	public StudentService(DbDAO dbDAO, PizzaOrderDAO pizzaDAO,
			AdminDAO admDAO) {
		this.dbDAO = dbDAO;
		pizzaOrderDAO = pizzaDAO;
		adminDAO = admDAO;
	}
	public Set<PizzaSize> getPizzaSizes()throws ServiceException
	{
		Set<PizzaSize> sizes = null;
		try {
            dbDAO.startTransaction();  
			sizes = pizzaOrderDAO.findPizzaSizes();
            dbDAO.commitTransaction();
		} catch (Exception e) {
			dbDAO.rollbackAfterException(); 
			throw new ServiceException("Can't access pizza sizes in db: ", e);
		}
	   return sizes;	
	}

	public Set<Topping> getToppings()throws ServiceException
	{
		Set<Topping> toppings = null;
		try {
            dbDAO.startTransaction();  
			toppings = pizzaOrderDAO.findToppings();
            dbDAO.commitTransaction();
		} catch (Exception e) {
			dbDAO.rollbackAfterException(); 
			throw new ServiceException("Can't access toppings in db: ", e);
		}
		return toppings;
	}

	// Transaction to make an order.
	// Note that the size and toppings are coming in from the presentation
	// layer, so they are "detached entities", not part of the Persistence
	// Context started up at the startTransaction(). We reload them in
	// as a first step. The code will work without the reloads, but will
	// not qualify as a fully serializable transaction. In particular the action
	// would succeed in ordering a pizza with a recently-deleted size or
	// topping. Another way to make detached entities rejoin a persistence
	// context is with merge, but it would do the wrong thing in the case
	// of a recently-deleted size or topping, since it determines differences
	// between detached and database state and applies the detached state,
	// assumed to be the more current.
	public void makeOrder(int roomNum, PizzaSize size, Set<Topping> toppings) 
				throws ServiceException {
		try {	
			dbDAO.startTransaction(); 
			// reload the objects coming in from previous persistence contexts
			// size or topping could have been deleted since this user chose it
			PizzaSize orderSize;
			if ((orderSize = pizzaOrderDAO.findPizzaSize(size.getSizeName())) == null) 
				throw new ServiceException(
						"Order cannot be placed because specified size " + size.getSizeName() + " is unavailable");
			Set<Topping> orderToppings = new HashSet<Topping>();
			Topping t1;
			for (Topping t:toppings)
				if ((t1 = pizzaOrderDAO.findTopping(t.getToppingName())) == null)
					throw new ServiceException(
							"Order cannot be placed because specified topping " + t.getToppingName() + " is unavailable");
				else
					orderToppings.add(t1);  // it's OK, collect it up
			// Now orderSize and orderToppings are proper persistent entities, read in this
			// transaction from the database.
			PizzaOrder order = new PizzaOrder(orderSize, roomNum, 
					adminDAO.findCurrentDay(), PizzaOrder.PREPARING, orderToppings);
			pizzaOrderDAO.insertOrder(order);
			dbDAO.commitTransaction();
		} catch (Exception e) {
			dbDAO.rollbackAfterException();
			throw new ServiceException("Order can not be placed ", e);
		}
	}

	// return all orders for this room
	// mark such orders FINISHED (student has been notified)
	public List<PizzaOrder> getOrderStatus(int roomNumber)
			throws ServiceException {
		List<PizzaOrder> pizzaOrders = null;
		try {
			dbDAO.startTransaction(); 																				
			pizzaOrders = pizzaOrderDAO.findOrdersByRoom(roomNumber, adminDAO
					.findCurrentDay());

			for (PizzaOrder order : pizzaOrders) {
				if (order.getStatus() == PizzaOrder.BAKED)
					// a student has seen it ready, so mark this pizza "finished"
					// Note there is no need to call the DAO for the following update
					// because the JPA runtime system is tracking changes and 
					// will apply them at commit
					order.setStatus(PizzaOrder.FINISHED); 

				// JPA by default eagerly fetches the PizzaSize object to go with 
				// each PizzaOrder because of the to-one relationship, but is lazy 
				// about the toppings because they have a to-many relationship.
				// Thus we need to access the Toppings here (to get them loaded
				// during the transaction) to make sure they are available when 
				// the presentation code needs them, i.e., after the commit.
				// This can be done automatically by additional JPA configuration
				for (Topping t: order.getToppings())
				    t.getToppingName();
			}
			dbDAO.commitTransaction();
		} catch (Exception e) {
			dbDAO.rollbackAfterException();
			throw new ServiceException("Error in getting status ", e);
		}
		return pizzaOrders;
	}
}
