package paintProject;

import java.awt.geom.*;

public class Ellipse extends Shape2D {

	Ellipse2D.Double ellipse ;
	double minor,major ;
	
	public Ellipse(int x1, int y1, int x2, int y2,int type){
		this.x1 = x1 ;
		this.x2 = x2 ;
		this.y1 = y1 ;
		this.y2 = y2 ;
		this.type = type ;
	}

	public Ellipse2D.Double draw(){
		double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);
        minor = Math.abs(x1-x2) ;
        major =  Math.abs(y1-y2) ;
        ellipse = new Ellipse2D.Double(x, y, minor ,major);
		return ellipse ;
	}
	
	public double area(){
		return Math.PI * (major/2) * (minor/2) ;
	}
}
