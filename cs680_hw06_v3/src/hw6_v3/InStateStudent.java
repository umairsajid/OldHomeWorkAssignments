package hw6_v3;
 
public class InStateStudent implements Student {

	@SuppressWarnings("unused")
	private float tuition = 500;
	private String name = null;
	int i20 = -1;
	Category cat;
	
		InStateStudent(float tu, String nm, int i){
			setTuition(tu);
			setName(nm);
			i20 = i;
			cat= new Category();
			while(!setCategory().equals("InState"));
		}

		public String setCategory(){
			if(cat.hasMoreElements())
				return cat.nextElement();
			else{
				cat = new Category();
				return cat.nextElement();
			}
		}
	 

		public void setName(String nm) {
			// TODO Auto-generated method stub
			name=nm;
		}


 		public float getTuition() {
			return 500;
		}

	  
		public String getName() {
			return name;
		}
 
		public void setTuition(float tu) {
			tuition=tu;
			
		}
	 
	}



