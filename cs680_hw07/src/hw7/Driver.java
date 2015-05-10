package hw7;

import java.awt.Point;
import java.util.ArrayList;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Point> al = new ArrayList<Point>();
		al.add( new Point(0,2));
		al.add( new Point(0,4));
		al.add( new Point(2,2));
		Polygon p = new Polygon( al, new TriangleAreaCalc() );
		System.out.println("The Area of the triange is: " + p.getArea()); // triangle’s area
		p.addPoint( new Point(4,4) );
		p.setAreaCalc( new RectangleAreaCalc() );
		System.out.println("The Area of the rectangle is: " + p.getArea()); // rectangle's area
	}

}
