package Controller;
import Modele.*;
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
	FabriqueModele fm;
	//	GraphicsContext MainController.gc;
	public static final double grosseur=1;
	public static final double Effgrosseur=15;
	MainController mc;
	String Mode;

	//	Color c;

	public Dessiner(MainController mc,FabriqueModele fm) {
		points=new ArrayList<Point>();
		lignes=new ArrayList<ArrayList<Point>>();
		this.mc=mc;
		Mode="dessin";
		this.fm=fm;
	}

	public void MousePressedF(MouseEvent e) {
		sourisX=e.getX()-5;
		sourisY=e.getY()+30;
		Point p=null;
		if(this.Mode.equals("Effaceur")) {
			p=new Point(sourisX,sourisY,Color.web("#f0f0f0"));
			p.setG(Effgrosseur);
		}else {
			p=new Point(sourisX,sourisY,this.mc.getColor());
			p.setG(grosseur);
		}
		points.add(p);
		//		System.out.println(e.getX()-5+","+e.getY()+30);
		System.out.println(this.mc.getColor());
	}
	public void MouseDrag(MouseEvent e) {
		Point p=null;
		if(this.Mode.equals("Effaceur")) {
			p=new Point(e.getX()-5,e.getY()+30,Color.web("#f0f0f0"));
			p.setG(Effgrosseur);
		}else {
			p=new Point(e.getX()-5,e.getY()+30,this.mc.getColor());
			p.setG(grosseur);
		}
		points.add(p);
		for(int i=0;i<points.size()-1;i++) {
			if(this.Mode.equals("Effaceur")) {
				MainController.gc.setLineWidth(Effgrosseur);
				MainController.gc.setStroke(Color.web("#f0f0f0"));
			}else {
				MainController.gc.setLineWidth(grosseur);
				MainController.gc.setStroke(this.mc.getColor());
			}
			MainController.gc.strokeLine(points.get(i).RendreX(), points.get(i).RendreY(), points.get(i+1).RendreX(), points.get(i+1).RendreY());
		}
	}
	public void MouseReleased(MouseEvent e) {
		Point p=new Point(e.getX()-5,e.getY()+30,Color.web("#f0f0f0"));
		p.setG(Effgrosseur);
		points.add(p);
		lignes.add(points);
		this.fm.AddObjectCollection(points);//加入到总的里面的
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
			if(this.Mode.equals("Dessin")) {
				return Color.BLACK;
			}else {
				return Color.web("#f0f0f0");
			}
		}else {
			return points.get(0).getC();
		}
	}

	public void ChangetoEffaceur(String mode) {
		//		this.setColor(Color.web("#f0f0f0"));
		this.Mode=mode;//切换模式
	}

	public void setLignesPoint(ArrayList<ArrayList<Point>> lignes) {
		this.lignes=lignes;
	}

}
