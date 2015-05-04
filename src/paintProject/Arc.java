package paintProject;

import java.awt.Polygon;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Arc extends Shape2D{
	
	Arc2D.Double A ;
	
	public Arc(int x1, int y1, int x2, int y2,int type){
		this.x1 = x1 ;
		this.x2 = x2 ;
		this.y1 = y1 ;
		this.y2 = y2 ;
		this.type = type ;
	}

	public Arc2D.Double draw(){
		double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);
        
		A = new Arc2D.Double(x, y, Math.abs(x1-x2), Math.abs(y1-y2) , 90, 135, Arc2D.OPEN);
		
		return A;
		
	}

	@Override
	public double area() {
		// TODO Auto-generated method stub
		return 0;
	}
}
