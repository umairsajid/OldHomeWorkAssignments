package hw7;

import java.awt.Point;
import java.util.ArrayList;

public class Polygon   {

	ArrayList<Point> points;
	AreaCalculator areacalc;
	 	
	public Polygon(ArrayList <Point> pts, AreaCalculator ac){
		this.points = pts;
		this.areacalc = ac;
		
	}
	public void addPoint(Point p) {
		points.add(p);
	}
	public void setAreaCalc(AreaCalculator calc) {
		this.areacalc = calc;
	}
	public float getCentroid() {
		return 0;
	}
 
	public float getArea() {
		return areacalc.getArea(points);
	}
	
	
}
