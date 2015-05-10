package hw6_v1;
  

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student Jadd;
		Jadd = new Student(10000,"Jadd",-1);
		
		System.out.println(Jadd.getName() + " pays " + Jadd.getTuition() + " for tuition ");
		
		Jadd = new IntlStudent(20000,"Jadd",111);
		
		System.out.println(Jadd.getName() + " pays " + Jadd.getTuition() + " for tuition ");
	}

}
