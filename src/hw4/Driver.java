package hw4;

import java.awt.Point;
import java.util.ArrayList;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Polygon> test = new ArrayList<Polygon>();
		
		test.add(new Triangle(new Point(0,2),new Point(2,2),new Point(1,3)));
		test.add(new Triangle(new Point(0,0),new Point(2,0),new Point(1,3)));
		
		test.add(new Rectangle(new Point(0,2),new Point(2,2),new Point(2,0), new Point(0,0)));
		test.add(new Rectangle(new Point(1,3),new Point(3,3),new Point(3,1), new Point(1,1)));
		
		
		while(test.iterator().hasNext()){
			System.out.println(test.get(0).toString());
			test.remove(0);
		}
		 		
	}

}
