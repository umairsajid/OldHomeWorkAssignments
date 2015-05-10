package cs636.pizza.dao;
/**
 *
 * Data access class for pizza order objects, including their sizes and toppings
 */

import java.util.Set;
import java.util.TreeSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import cs636.pizza.domain.PizzaOrder;
import cs636.pizza.domain.PizzaSize;
import cs636.pizza.domain.Topping;

//Note: these can throw various subclasses of RuntimeException, 
// as defined by JPA (compare to SQLException of JDBC, a checked exception)
public class PizzaOrderDAO {
	
    private DbDAO dbDAO;
    
	public PizzaOrderDAO(DbDAO db) {
		this.dbDAO = db;
	}
	
  	public void insertOrder(PizzaOrder order) 
	{
		dbDAO.getEM().persist(order);
	}
	
	// Get orders for a certain day and room number
  	// Callers can get toppings as needed by using o.getToppings() (by lazy loading)
  	// while the persistence context is still available (i.e., until commit)
	public List<PizzaOrder> findOrdersByRoom(int roomNumber, int day) 
	{
    	TypedQuery<PizzaOrder> query = dbDAO.getEM().createQuery("select o from PizzaOrder o where o.roomNumber = "
				+ roomNumber + " and o.day = " + day + " order by o.id", PizzaOrder.class);
		List<PizzaOrder> orders = query.getResultList();	
		return orders;
	}
	
	// find first order with specified status
	public int findFirstOrder(int status) 
	{
    	TypedQuery<PizzaOrder> query = dbDAO.getEM().createQuery("select o from PizzaOrder o where o.status = "
				+ status + " order by o.id", PizzaOrder.class);	
		List<PizzaOrder> orders = query.getResultList();
		return orders.get(0).getId();
	}
	
	public void updateOrderStatus(int ordNo, int newStatus)
	{
		EntityManager em = dbDAO.getEM();
		PizzaOrder order = (PizzaOrder)em.find(PizzaOrder.class, ordNo);
		order.setStatus(newStatus);
	}
	
	// get all orders between day1 and day2 (inclusive)
	public List<PizzaOrder> findOrdersByDays(int day1, int day2) {
    	TypedQuery<PizzaOrder> query = dbDAO.getEM().createQuery("select o from PizzaOrder o where o.day >= " 
				+ day1 + " and o.day <= " + day2 + " order by o.id", PizzaOrder.class);	
    	List<PizzaOrder> orders = query.getResultList();
		return orders;
	}
    
	public Topping findTopping(String toppingName) {
		EntityManager em = dbDAO.getEM();
		TypedQuery<Topping> query = em.createQuery(
				"select t from Topping t where t.toppingName = '" + toppingName
						+ "'", Topping.class);
		List<Topping> tops = query.getResultList();
		if (tops.size() == 0)
			return null;
		Topping top = tops.get(0);
		if (top.getStatus() == 0)
			return null; // topping has been deleted
		else
			return top;
	}
  
	public void  createTopping(String toppingName) {
		EntityManager em = dbDAO.getEM();
		TypedQuery<Topping> query = em.createQuery(
				"select t from Topping t where t.toppingName = '" + toppingName + "'", Topping.class);
		List<Topping> tops = query.getResultList();
		if (tops.size() > 0) {
			tops.get(0).setStatus(1); // make sure found topping is active (not deleted)
		} else { // create new topping
			em.persist(new Topping(toppingName));
		}
	}
 
	public PizzaSize findPizzaSize(String sizeName) {
		EntityManager em = dbDAO.getEM();
		System.out.println("in findPizzaSize");
		TypedQuery<PizzaSize> query = em.createQuery(
				"select s from PizzaSize s where s.sizeName = '" + sizeName
						+ "'", PizzaSize.class);
		List<PizzaSize> sizes = query.getResultList();
		if (sizes.size() == 0)
			return null;
		PizzaSize size = sizes.get(0);
		if (size.getStatus() == 0) // make sure found size is active (not
									// deleted)
			return null; // it's gone
		else
			return size;
	}

	public void createPizzaSize(String sizeName) {
		EntityManager em = dbDAO.getEM();
		System.out.println("in createPizzaSize");
		TypedQuery<PizzaSize> query = em.createQuery(
				"select s from PizzaSize s where s.sizeName = '" + sizeName + "'", PizzaSize.class);
		List<PizzaSize> sizes = query.getResultList();
		if (sizes.size() > 0) {
			sizes.get(0).setStatus(1); // make sure found size is active (not deleted)
		} else { // create new size
			em.persist( new PizzaSize(sizeName));
		}
	}
    
	public void deleteTopping(String toppingName) {
		EntityManager em = dbDAO.getEM();
		Topping top = (Topping) em.createQuery(
				"select t from Topping t where t.toppingName = '" + toppingName + "'")
				.getSingleResult();  // throws if no row
		top.setStatus(0); // mark deleted
	}

	public void deletePizzaSize(String sizeName) {
		EntityManager em = dbDAO.getEM();
		PizzaSize size = (PizzaSize) em.createQuery(
				"select s from PizzaSize s where s.sizeName = '" + sizeName + "'")
				.getSingleResult();  // throws if no row, as we want here
		size.setStatus(0);
	}
	
	public Set<PizzaSize> findPizzaSizes() {
		EntityManager em = dbDAO.getEM();
		TypedQuery<PizzaSize> query = em.createQuery(
				"select s from PizzaSize s where s.status = 1", PizzaSize.class);
		List<PizzaSize> sizes = query.getResultList();
		return new TreeSet<PizzaSize>(sizes);
	}

	public Set<Topping> findToppings() {
		EntityManager em = dbDAO.getEM();
		TypedQuery<Topping> query = em.createQuery(
				"select t from Topping t where t.status = 1", Topping.class);
		List<Topping> tops = query.getResultList();
		return new TreeSet<Topping>(tops);
	}

}
