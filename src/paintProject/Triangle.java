package paintProject;

import java.awt.geom.*;

public class Triangle extends Shape2D {

	GeneralPath triangle ;
	double base ,hieght  ;
	
	public Triangle(int x1, int y1, int x2, int y2,int type){
		this.x1 = x1 ;
		this.x2 = x2 ;
		this.y1 = y1 ;
		this.y2 = y2 ;
		this.type = type ;
	}

	public GeneralPath draw(){
		GeneralPath path = new GeneralPath(new Line2D.Double(this.x1, this.y1, this.x2, this.y2));
		if(x2 > x1){
			path.append(new Line2D.Double(this.x2, this.y2, x1 - (Math.abs(x1-x2)) , this.y2), true);
			path.append(new Line2D.Double(x1 - (Math.abs(x1-x2)), this.y2, x1  , this.y1), true);
		}
		
		else{
			path.append(new Line2D.Double(this.x2, this.y2, x1 + (Math.abs(x1-x2)) , this.y2), true);
			path.append(new Line2D.Double(x1 + (Math.abs(x1-x2)), this.y2, x1  , this.y1), true);
		}
		base = Math.abs(x1-x2) * 2 ;
		hieght = Math.abs(y1-y2);
		triangle = path ;
		return triangle;
	}
	
	public double area(){
		return 0.5 * base * hieght ;
	}
}
