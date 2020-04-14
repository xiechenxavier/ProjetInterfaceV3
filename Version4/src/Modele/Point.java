package Modele;

import javafx.scene.paint.Color;

public class Point {
	
	private double x,y;
	private Color c;
	private double G;

	public Point(double a,double b,Color c) {
		x=a;
		y=b;
		this.c=c;
		
	}
	
	public Point (Point p) {
		if (p!=null) {
			x=p.x;
			y=p.y;			
		}
	}
	
	public double distance(Point p) {
		double res=0;
		if (p != null) {
			res=Math.sqrt((x-p.RendreX())*(x-p.RendreX())+(y-p.RendreY())*(y-p.RendreY()));
		}
		return res;
	}
	
	public void incrementerX(int value) {
		this.x+=value;
	}
	
	public void incrementerY(int value) {
		this.y+=value;
	}
	
	public void translation(double dx, double dy) {
		this.x+=dx;
		this.y+=dy;
	}

	public double RendreX() {
		return x;
	}
	
	public void setX(double d) {
		this.x = d;
	}
	
	public double RendreY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public String toString() {
		String res="X :"+this.x+",Y :"+this.y;
		return res;
	}
	
	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}
	public double getG() {
		return this.G;
	}
	public void setG(double G) {
		this.G=G;
	}
}
