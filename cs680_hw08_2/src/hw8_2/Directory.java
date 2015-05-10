package hw8_2;

import java.util.ArrayList;
import java.util.Date;

public class Directory extends FSElement {
	
	ArrayList<FSElement> files;
	public Directory(Directory par, Date cr, String nm) {
		super(par, cr, "Directory");
		rename(nm);
		files = new ArrayList<FSElement>();
		// TODO Auto-generated constructor stub
	}	
	public int getSize(){
		int sz=size;
		for(int k=0;k<files.size();k++){
			sz = sz + files.get(k).getSize(); 
		}
		return sz;
	}
	public ArrayList<FSElement> getChildren(){
		return files;
	}
	
	public void appendChild(FSElement a){
		files.add(a);
		
	}
	void setSize(int size) {
		this.size = 0;
	}
	public FSElement findByName(String nm){
		for(int k=0;k<files.size();k++){
			if(files.get(k).getName().equals(nm))
			{
				return files.get(k);
			}
			
		}
		return null;
	}
	public String getInfo(){
		String ret =  getName() + ": " + getKind() + ":"+ getSize()+ " bytes: Owner: " + getOwner();
			for(int k=0;k<files.size();k++){
				ret= ret + "\n\t" + files.get(k).getInfo();
			}
		return ret;
	}
	
}
