package paintProject;

import java.awt.Color;

public class ShapeSaving {

	private int x1;
	private int x2;
	private int y1;
	private int y2;
	
	private Color fontColor;
	private Color fillColor;
	
	private int type;
	
	public ShapeSaving(){
		 
	}
	
	public int getX1(){
		return x1;
	}
	public int getY1(){
		return y1;
	}
	public int getX2(){
		return x2;
	}
	public int getY2(){
		return y2;
	}
	
	public void setX1(int d) {
		x1 = d;
	}
	public void setY1(int d) {
		y1 = d;
	}
	public void setX2(int d) {
		x2 = d;
	}
	public void setY2(int d) {
		y2 = d;
	}
	
	public Color getFont(){
		return fontColor;
	}
	public Color getFill() {
		return fillColor;
	}
	
	public void setFont(Color c) {
		fontColor = c;
	}
	public void setFill(Color c) {
		fillColor = c;
	}
	
	public int getType(){
		return type;
	}
	public void setType(int i) {
		type = i;
	}
}
