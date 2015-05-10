package hw11;

import java.util.Date;


public class Link extends FSElement{
	
	private FSElement target;
	public Link(Directory par, Date cr, String nm, FSElement fe) {
			super(par, cr, "Link");
			// TODO Auto-generated constructor stub
			rename(nm);
			setSize(1);
			setTarget(fe);
			
		}
	void setTarget(FSElement target) {
		this.target = target;
		rename(this.getName() + " ("+target.getName()+":"+ target.getKind() +")");
	}
	FSElement getTarget() {
		return target;
	}
	
}
 
