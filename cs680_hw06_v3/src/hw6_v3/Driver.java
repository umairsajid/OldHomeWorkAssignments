package hw6_v3;
  

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student Jadd;
		Jadd = StudentFactory.CreateOutStateStudent(1000,"JaddOutOfState");
		
		System.out.println(Jadd.getName() + " pays " + Jadd.getTuition() + " for tuition ");
		
		Jadd = StudentFactory.CreateIntlStudent(2000,"JaddInternational");
		
		System.out.println(Jadd.getName() + " pays " + Jadd.getTuition() + " for tuition ");
	}

}
