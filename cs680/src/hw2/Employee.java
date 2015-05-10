package hw2;

public class Employee {
	private float yearsOfService;
	
	public String lastName;
	
	public String firstName;
	
	private String socSecNum;
	
	public String employeeID;
	public Employee(){
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		Employee test = new Employee();
		test.setSocSecNum(new String("-1"));
		System.out.println(test.getSocSecNum());
		
		System.out.println("a.jpg".endsWith("jpg"));
	}

	public void setSocSecNum(String socSecNum) {
		this.socSecNum = socSecNum;
	}

	public String getSocSecNum() {
		return socSecNum;
	}
	
}


