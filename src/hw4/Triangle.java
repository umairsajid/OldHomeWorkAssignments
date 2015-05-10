package hw4;

import java.awt.Point;
import java.util.ArrayList;

public class Triangle implements Polygon {

	Point a;
	Point b;
	Point c;
	@Override
	public float getArea() {
		// TODO Auto-generated method stub
		return (float) Math.sqrt( (a.distance(b) + a.distance(c) + b.distance(c))/2 );
	}

	@Override
	public float getCentroid() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Point> getPoints() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Triangle(Point aa, Point bb, Point cc){
		a = aa;
		b = bb;
		c = cc;
		
	}
	public String toString(){
	
		return "The area of this triange is: " + this.getArea();
		
	}

}
