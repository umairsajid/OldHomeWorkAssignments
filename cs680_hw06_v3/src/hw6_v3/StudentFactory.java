package hw6_v3;

public class StudentFactory {
	
	public static Student CreateInStateStudent(float tu, String nm){
		Student s = new InStateStudent(tu,nm,-1);
		while(!s.setCategory().equals("InState"));
		
		return s;
			
	}

	public static Student CreateOutStateStudent(float tu, String nm){
		Student s = new OutStateStudent(tu,nm,-1);
		while(!s.setCategory().equals("OutState"));
		
		return s;
			
	}

	public static Student CreateIntlStudent(float tu, String nm){
		Student s = new IntlStudent(tu,nm,111);
		while(!s.setCategory().equals("International"));
		
		return s;
			
	}
}
