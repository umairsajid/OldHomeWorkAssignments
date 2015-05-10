package hw11;

import java.util.ArrayList;
import java.util.Date;

public class Directory extends FSElement {
	
	ArrayList<FSElement> files;
	public Directory(Directory par, Date cr, String nm) {
		super(par, cr, "Directory");
		rename(nm);
		files = new ArrayList<FSElement>();
		if(par!= null && !nm.equals("..")) appendChild(new Directory(par.getParent(),par.getLastModified(),".."));
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
		if(nm.equals("..")) return this.getParent();
		for(int k=0;k<files.size();k++){
			if(files.get(k).getName().equals(nm))
			{
				return files.get(k);
			}
			
		}
		return null;
	}
	public FSElement findByNameRecursive(String nm){
		if(nm.equals("..")) return this.getParent();
		for(int k=0;k<files.size();k++){
			if(files.get(k).getName().equals(nm))
			{
				return files.get(k);
			}
			if(files.get(k).getKind().equals("Directory")){
					FSElement temp;
					temp = ((Directory) files.get(k)).findByNameRecursive(nm);
					if (temp != null){
						if(temp.getName().equals(nm))
								return temp;
					}
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
	public String nonRecursive_getInfo(){
		String ret =  getName() + ": " + getKind() + ":"+ getSize()+ " bytes: Owner: " + getOwner();
		FSElement temp;
			for(int k=0;k<files.size();k++){
				temp =  new FSElement((FSElement) files.get(k));
				ret= ret + "\n\t" +  temp.getInfo();
			}
		return ret;
	}
	public String nonRecursive_getName(){
		String ret =  getName() ;
		FSElement temp;
			for(int k=0;k<files.size();k++){
				temp =  new FSElement((FSElement) files.get(k));
				ret= ret + "\n\t" +  temp.getName();
			}
		return ret;
	}
	
}
