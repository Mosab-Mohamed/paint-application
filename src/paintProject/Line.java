package paintProject;

import java.awt.geom.*;

public class Line extends Shape2D {

	Line2D.Double line ;
	
	public Line(int x1, int y1, int x2, int y2,int type){
		this.x1 = x1 ;
		this.x2 = x2 ;
		this.y1 = y1 ;
		this.y2 = y2 ;
		this.type = type ;
	}

	public Line2D.Double draw(){
		line = new Line2D.Double(this.x1, this.y1, this.x2, this.y2);
		return line ;
	}
	
	public double area(){
		return 0 ;
	}
}
