package hw6_v2;
  

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student Jadd;
		Jadd = Student.CreateInStateStudent(10000, "JaddInState");
		
		System.out.println(Jadd.getName() + " pays " + Jadd.getTuition() + " for tuition ");
		
		Jadd = Student.CreateIntlStudent(20000, "JaddInternational");
		
		System.out.println(Jadd.getName() + " pays " + Jadd.getTuition() + " for tuition ");
	}

}
