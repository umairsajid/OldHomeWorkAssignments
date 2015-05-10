package cs636.pizza.dao;

// Example JUnit4 test 
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cs636.pizza.config.PizzaSystemConfig;
import cs636.pizza.domain.PizzaOrder;
import cs636.pizza.domain.PizzaSize;
import cs636.pizza.domain.Topping;

public class PizzaOrderDAOTest1 {
	private DbDAO dbDAO;
	private PizzaOrderDAO pizzaOrderDAO;
	private static EntityManagerFactory emf;
	
	@BeforeClass
	public static void setUpClass() {
		// we usually use HSQLDB as a db for testing, but 
		// this can run for other DBs too
		// Note: need to load it first and "ant config-hsqldb"
		// to get the persistence.xml file onto the classpath
		// Do this part once for this whole class--takes some time
		emf = PizzaSystemConfig.configureJPA();
	}

	@Before
	// each test runs in its own transaction, on same db setup
	public void setup() {
		dbDAO = new DbDAO(emf);
		dbDAO.startTransaction();
		dbDAO.initializeDb(); // no orders, toppings, sizes
		dbDAO.commitTransaction();
		pizzaOrderDAO = new PizzaOrderDAO(dbDAO);
	}

	@After
	public void tearDown() {
		// This executes even after an exception
		// so we need to rollback here in case of exception
		// (If the transaction was successful, it's already
		// committed, and this won't hurt.)
		dbDAO.rollbackAfterException();
	}
	@AfterClass
	public static void tearDownClass() throws Exception {
		PizzaSystemConfig.shutdownServices();
	}
	
	@Test
	public void testMakeOrder() throws SQLException
	{
		dbDAO.startTransaction();
		// the size and toppings need to be in the DB at the time of insertOrder--
		pizzaOrderDAO.createPizzaSize("small");
		PizzaSize size = pizzaOrderDAO.findPizzaSizes().iterator().next();
		pizzaOrderDAO.createTopping("pepperoni");
		Set<Topping> tops = pizzaOrderDAO.findToppings();;
		
		PizzaOrder order = new PizzaOrder(size, 5, 1, 1, tops);
		pizzaOrderDAO.insertOrder(order);
		dbDAO.commitTransaction();
	}
	
	// case of expected (subclass of) RuntimeException (JPA exception) 
	@Test(expected=RuntimeException.class)
	public void testMakeOrderMissingSizeRow() throws SQLException
	{
		dbDAO.startTransaction();
		// the size and toppings need to be in the DB at the time of insertOrder--
		// but here we skip a step and just create a size object, not in DB
		PizzaSize size = new PizzaSize("smallish"); 
		pizzaOrderDAO.createTopping("pepperoni");
		Set<Topping> tops = pizzaOrderDAO.findToppings();;
		// This will throw SQLException
		PizzaOrder order = new PizzaOrder(size, 5, 1, 1, tops);
		pizzaOrderDAO.insertOrder(order);
		dbDAO.commitTransaction();
	}
	
	
	
	@Test
	public void testCreateTopping() throws SQLException
	{
		dbDAO.startTransaction();
		pizzaOrderDAO.createTopping("anchovies");	
		int count = pizzaOrderDAO.findToppings().size();
		dbDAO.commitTransaction();
		assertTrue("first topping not created", count == 1);
	}
	
	// duplicate should just do nothing
	@Test
	public void testDuplicateTopping()
	{
		dbDAO.startTransaction();
		pizzaOrderDAO.createTopping("anchovies");	
		pizzaOrderDAO.createTopping("anchovies");	
		dbDAO.commitTransaction();
	}
	// no orders yet, so findFirstOrder should fail
	// with JPA's standard exception, (subclass of) RuntimeException
	@Test(expected = RuntimeException.class)
	public void noFirstOrderYet() {
		dbDAO.startTransaction();
		pizzaOrderDAO.findFirstOrder(1);
		dbDAO.commitTransaction();
	}
}
