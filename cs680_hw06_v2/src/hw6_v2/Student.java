package hw6_v2;


public class Student {
	private float tuition = -1;
	private String name = null;
	int i20 = -1;
	Category cat;
	
	
	private Student(float tu, String nm, int i){
		setTuition(tu);
		setName(nm);
		i20 = i;
		cat= new Category();
	}

	public static Student CreateInStateStudent(float tu, String nm){
		Student s = new Student(tu,nm,-1);
		while(!s.setCategory().equals("InState"));
		
		return s;
			
	}

	public static Student CreateOutStateStudent(float tu, String nm){
		Student s = new Student(tu,nm,-1);
		while(!s.setCategory().equals("OutState"));
		
		return s;
			
	}

	public static Student CreateIntlStudent(float tu, String nm){
		Student s = new Student(tu,nm,-1);
		while(!s.setCategory().equals("International"));
		
		return s;
			
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
