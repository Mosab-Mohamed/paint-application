package paintProject;

import java.awt.geom.*;

public class MyRectangle extends Shape2D {

	Rectangle2D.Double rectangle ;
	double width, height ;
	public MyRectangle(int x1, int y1, int x2, int y2,int type){
		this.x1 = x1 ;
		this.x2 = x2 ;
		this.y1 = y1 ;
		this.y2 = y2 ;
		this.type = type ;
	}

	public Rectangle2D.Double draw(){
		double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);
        width = Math.abs(x1 - x2);
        height = Math.abs(y1 - y2);
        rectangle = new Rectangle2D.Double(x, y, width, height);
		return rectangle ;
	}
	
	public double area (){
		return width * height ;
	}
}
