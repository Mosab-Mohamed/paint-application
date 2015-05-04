package paintProject;

import java.awt.*;
import java.awt.geom.*;

public class Circle extends Shape2D {

	Ellipse2D.Double circle ;
	double diameter ;
	
	public Circle(int x1, int y1, int x2, int y2,int type){
		this.x1 = x1 ;
		this.x2 = x2 ;
		this.y1 = y1 ;
		this.y2 = y2 ;
		this.type = type ;
	}

	public Ellipse2D.Double draw(){
		double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);
        diameter = Math.sqrt( ( (x1-x2)*(x1-x2) ) + ( (y1-y2)*(y1-y2) ) );
        circle = new Ellipse2D.Double(x, y, diameter, diameter);
		return  circle ;
	}
	
	public double area(){
		return  Math.PI*( diameter*diameter/4 ) ;
	}
}
