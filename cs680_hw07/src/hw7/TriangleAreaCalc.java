package hw7;

import java.awt.Point;
import java.util.ArrayList;

public class TriangleAreaCalc implements AreaCalculator {

	@Override
	public float getArea(ArrayList<Point> points) {
			return (float) Math.sqrt( (points.get(0).distance(points.get(1)) 
									 + points.get(0).distance(points.get(2)) 
									 + points.get(1).distance(points.get(2)))/2 );
		}
	}
	


