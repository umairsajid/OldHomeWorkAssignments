package cs636.pizza.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PIZZA_SIZE database table.
 * The Id generator follows the example in Keith & Schincariol, pg. 83-85
 * 
 */
@Entity(name = "PizzaSize")
@Table(name="PIZZA_SIZE", 
	uniqueConstraints = @UniqueConstraint(columnNames="SIZE_NAME"))
public class PizzaSize implements Serializable, Comparable<PizzaSize> {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="SizeIdGen",
			table = "PIZZA_ID_GEN",
			pkColumnName = "GEN_NAME",
			valueColumnName = "GEN_VAL",
			pkColumnValue = "SizeId_Gen")				
	@GeneratedValue(generator="SizeIdGen")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="S_STATUS", nullable=false)
	private int status;

	@Column(name="SIZE_NAME", nullable=false, length=30)
	private String sizeName;

    public PizzaSize() {
    }
    
	/** create non-deleted PizzaSize, id is handled by JPA */
    public PizzaSize(String sizeName) {
    	this.status = 1;
    	this.sizeName = sizeName;
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

	public String getSizeName() {
		return this.sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	// Implement compareTo and equals so we can use TreeSet<PizzaSize>,
    // and hashCode too so we can use HashSet<PizzaSize>
    // If we used id comparison here, we would be restricted to sets of 
    // Toppings with actual ids, and ids are assigned sometime between
	// the persist inside createTopping and the commit that follows.
	// To allow sets of new and old Toppings, and since
    // we have a UNIQUE column constraint on toppingname, we
    // can use it for equality checking and comparison.
    // Here toppingName is a "business key".
    // See Bauer and King, pg. 397 for discussion of this issue.
    // Also, use getters here (for object x), in case these methods
	// are executed for a proxy in a lazy to-one relationship 
	// (not in use in the pizza project, but just in case you use 
	// this code as a model for another project that does use lazy
	// to-one relationships.) 
    @Override
	public int compareTo(PizzaSize x)
	{
		return getSizeName().compareTo(x.getSizeName());
	}
	@Override
	public boolean equals(Object x)
	{
		if (x == null || x.getClass()!= getClass())
			return false;
		return getSizeName().equals(((PizzaSize)x).getSizeName());
	}
	@Override
	public int hashCode()
	{
		return getSizeName().hashCode();
	}
}