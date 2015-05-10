package hw6_v1;


public class Student {
	private float tuition = -1;
	private String name = null;
	int i20 = -1;
	Category cat;
	
	
	public Student(float tu, String nm, int i){
		setTuition(tu);
		setName(nm);
		i20 = i;
		cat= new Category();
	}

	public String setCategory(){
		if(cat.hasMoreElements())
			return cat.nextElement();
		else{
			cat = new Category();
			return cat.nextElement();
		}
	}
 

	private void setName(String nm) {
		// TODO Auto-generated method stub
		name=nm;
	}


	private void setTuition(float tu) {
		// TODO Auto-generated method stub
		tuition = tu;
	}




	public float getTuition() {
		return tuition;
	}

  
	String getName() {
		return name;
	}
 
}
