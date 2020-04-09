package Controller;
import Modele.*;
import application.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Dessiner{
	double sourisX,sourisY;//起始点鼠标进入点
	ArrayList<Point> points;
	ArrayList<ArrayList<Point>> lignes;
	//	GraphicsContext MainController.gc;
	MainController mc;

	//	Color c;

	public Dessiner(MainController mc) {
		points=new ArrayList<Point>();
		lignes=new ArrayList<ArrayList<Point>>();
		this.mc=mc;
	}

	public void MousePressedF(MouseEvent e) {
		sourisX=e.getX();
		sourisY=e.getY();
		points.add(new Point(sourisX,sourisY,this.mc.getColor()));
		//		System.out.println(e.getX()+","+e.getY());
		System.out.println(this.mc.getColor());
	}

	public void MouseDrag(MouseEvent e) {
		points.add(new Point(e.getX(),e.getY(),this.mc.getColor()));
		for(int i=0;i<points.size()-1;i++) {
			MainController.gc.setStroke(this.mc.getColor());
			MainController.gc.strokeLine(points.get(i).RendreX(), points.get(i).RendreY(), points.get(i+1).RendreX(), points.get(i+1).RendreY());
		}
	}
	public void MouseReleased(MouseEvent e) {
		points.add(new Point(e.getX(),e.getY(),this.mc.getColor()));
		lignes.add(points);
		points=new ArrayList<Point>();
	}
	public ArrayList<Point> getPoints(){
		return this.points;
	}
	public ArrayList<ArrayList<Point>> getLignes(){
		return this.lignes;
	}
	public void ViderListeLignes() {
		this.lignes=new ArrayList<ArrayList<Point>>();
		this.points=new ArrayList<Point>();
	}
	public void setColor(Color c) {
		for(Point p:points) {
			p.setC(c);
		}
	}
	public Color getColor() {
		if(points.isEmpty()) {
			return Color.BLACK;
		}else {
			return points.get(0).getC();
		}
	}

}
