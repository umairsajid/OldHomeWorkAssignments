package hw4;

import java.awt.Point;
import java.util.ArrayList;

public class Rectangle implements Polygon {

	Point a;
	Point b;
	Point c;
	Point d;
	
	@Override
	public float getArea() {
		float area = 0;
		
		if (a.getX()==b.getX()){
			if(a.getY() == c.getY()) 
				area= (float) (a.distance(b) * a.distance(c));
			else 
				area= (float) (a.distance(b) * a.distance(d));
		}
		
		if (a.getX() == c.getX()){
			if(a.getY() == b.getY())
				area = (float) (a.distance(c) * a.distance(b));
			else   
				area =(float) (a.distance(c) * a.distance(d));
		}
		else{
			if(a.getY() == b.getY())
				area = (float) (a.distance(d) * a.distance(b));
			else   
				area =(float) (a.distance(d) * a.distance(c));
		}
			
		return area;
		
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
	
	public Rectangle(Point aa, Point bb, Point cc, Point dd){
		
		a = aa;
		b = bb;
		c = cc;
		d = dd;
	}
	public String toString(){
		
		return "The area of this rectangle is: " + this.getArea();
		
	}
}
