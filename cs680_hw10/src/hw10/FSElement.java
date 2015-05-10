package hw10;

import java.util.Date;

public class FSElement {
	
	private String name = "Unnamed";
	private String kind;
	private String owner = "Admin";
	Date lastModified;
	Directory parent;
	FSElement child;
	public int size =0;
	@SuppressWarnings("unused")
	private Date created; 
	
	public FSElement(Directory par, Date cr, String kn){
		created =cr;
		lastModified = cr;
		parent = par;
		kind = kn;
	 
	}
	public Directory getParent(){
		return parent;
	}
	public boolean isLeaf(){
		if(child==null) return true;
		else return false;
	}
	public String getInfo(){
		return  getName() + ": " + getKind() + ": "+ getSize()+ " bytes: Owner: " + getOwner().toString();
	}
	
	public int getSize(){
		
		return size;
	}
	void rename(String name) {
		 this.name = name;
		 lastModified = new Date();
 	}
	public String getName() {
		return name;
	}
	void setOwner(String owner) {
		this.owner = owner;
	}
	String getOwner() {
		return owner;
	}
	void setSize(int size) {
		this.size = size;
	}
	String getKind() {
		return kind;
	}

}
