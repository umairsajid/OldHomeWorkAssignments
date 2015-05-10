package cs636.pizza.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.*;


/**
 * The persistent class for the TOPPINGS database table.
 * * The Id generator follows the example in Keith & Schincariol, pg. 83-85
 * 
 */
@Entity
@Table(name="TOPPINGS",
	uniqueConstraints = @UniqueConstraint(columnNames="TOPPING_NAME"))
	
public class Topping implements Serializable, Comparable<Topping> {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="ToppingIdGen",
			table = "PIZZA_ID_GEN",
			pkColumnName = "GEN_NAME",
			valueColumnName = "GEN_VAL",
			pkColumnValue = "ToppingId_Gen")
				
	@GeneratedValue(generator="ToppingIdGen")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="T_STATUS", nullable=false)
	private int status;

	@Column(name="TOPPING_NAME", nullable=false, length=30)
	private String toppingName;

    public Topping() {
    }
    
	/** create non-deleted Topping, id is handled by JPA */
    public Topping(String toppingName) {
    	this.status = 1;
        this.toppingName = toppingName;
    }
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getToppingName() {
		return this.toppingName;
	}

	public void setToppingName(String toppingName) {
		this.toppingName = toppingName;
	}
	
	// so we can use TreeSet<Topping> or HashSet<Topping> any time we want--
    // "business key equality/comparison/hashCode" where business key is toppingName
    // see comments in PizzaSize
	public int compareTo(Topping x)
	{
		return getToppingName().compareTo(x.getToppingName());
	}
	@Override
	public boolean equals(Object x)
	{
		if (x == null || x.getClass()!= getClass())
			return false;
		return getToppingName().equals(((Topping)x).getToppingName());
	}
	@Override
	public int hashCode()
	{
		return getToppingName().hashCode();
	}
}