package paintProject;

import java.awt.*;

public abstract class Shape2D {

	protected int x1,x2,y1,y2;
	Color fillColor = null ;
	Color fontColor = Color.BLACK ;
	int type ;
	
	public abstract Shape draw();
	public abstract double area();


}
