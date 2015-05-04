package paintProject;

import java.awt.geom.*;

public class Square extends Shape2D {

	Rectangle2D.Double square ;
	double length;
	public Square(int x1, int y1, int x2, int y2,int type){
		this.x1 = x1 ;
		this.x2 = x2 ;
		this.y1 = y1 ;
		this.y2 = y2 ;
		this.type = type ;
	}

	public Rectangle2D.Double draw(){
		double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);
        length = Math.max( Math.abs(x1 - x2) , Math.abs(y1-y2) );
        square = new Rectangle2D.Double(x, y, length, length);
		return square ;
	}
	
	public double area(){
		return length*length ;
	}
	
}
