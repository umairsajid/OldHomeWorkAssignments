package cs636.pizza.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the PIZZA_ORDERS database table.
 * 
 */
@Entity
@Table(name="PIZZA_ORDERS")
public class PizzaOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="OrderNumberGen",
			table = "PIZZA_ID_GEN",
			pkColumnName = "GEN_NAME",
			valueColumnName = "GEN_VAL",
			pkColumnValue = "Ordno_Gen")
	@GeneratedValue(generator="OrderNumberGen")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private int day;

	@Column(name="room_number", nullable=false)
	private int roomNumber;

	@Column(nullable=false)
	private int status;
	
	// Added to generated class: pizza order status values--
 	public static final int PREPARING = 1;
 	public static final int BAKED = 2;
 	public static final int FINISHED = 3;
 	public static final int NO_SUCH_ORDER = 0;
 	private static final String[] STATUS_NAME = { "NO_SUCH_ORDER", 
         "PREPARING", 
         "BAKED",
         "FINISHED"
         };  

	//uni-directional many-to-many association to Topping
    @ManyToMany
	@JoinTable(
		name="ORDER_TOPPING"
		, joinColumns={
			@JoinColumn(name="ORDER_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="TOPPING_ID", nullable=false)
			}
		)
	private Set<Topping> toppings;

	//uni-directional many-to-one association to PizzaSize
    @ManyToOne
	@JoinColumn(name="SIZE_ID", nullable=false)
	private PizzaSize pizzaSize;

    public PizzaOrder() {
    }
    
 	/** full constructor, except id: leave it null, JPA provider fills it in */
    public PizzaOrder(PizzaSize pizzaSize, int roomNumber, int day, int status, Set<Topping> toppings) {
       this.pizzaSize = pizzaSize;
       this.roomNumber = roomNumber;
       this.day = day;
       this.status = status;
       this.toppings = toppings;
    }
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDay() {
		return this.day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getRoomNumber() {
		return this.roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<Topping> getToppings() {
		return this.toppings;
	}

	public void setToppings(Set<Topping> toppings) {
		this.toppings = toppings;
	}
	
	public PizzaSize getPizzaSize() {
		return this.pizzaSize;
	}

	public void setPizzaSize(PizzaSize pizzaSize) {
		this.pizzaSize = pizzaSize;
	}
	
	// string equivalent of status code
	public String getStatusString()
	{
		return STATUS_NAME[status];
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ORDER ID: " + getId() + "\n");
		buffer.append("ORDER DAY: " + getDay() + "\n");
		buffer.append("SIZE: " + (getPizzaSize() != null?getPizzaSize().getSizeName():"not available") + "\n");
		buffer.append("ROOM NUMBER: " + getRoomNumber() + "\n");
		buffer.append("STATUS: " + getStatus());
		return buffer.toString();
	}
	// Note: no compareTo, so can't use TreeSet<PizzaOrder>, just HashSet<PizzaOrder>
	// or List<PizzaOrder>. Here equals and hashCode are not overridden, 
	// so simple object equality is in use, based on object addresses.
	// This works fine for apps like this one that do not need to
	// compare detached objects with each other or with managed
	// or new (not yet persisted) entity objects. In a persistence context, 
	// JPA ensures that there is only one managed entity object for each id value.
	// Any new entities have different addresses.
	// Thus a HashSet<PizzaOrder> may have both new and managed PizzaOrders
	// in it without problems (just not detached ones).
}