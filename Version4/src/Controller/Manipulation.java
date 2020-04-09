package Controller;

import java.util.ArrayList;
import Modele.FabriqueModele;
import Modele.FigureColoree;
import Modele.Point;
import Modele.Polygone;
import application.MainController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Manipulation {

	FabriqueModele fm;
	FabriqueFigure ff;
	boolean Endeplacement,updatableColor;
	ArrayList<FigureColoree> forms;
	int FormId;
//	private GraphicsContext MainController.gc;
	private double sourisX,sourisY;

	//	Canvas canvas;
	//	
	public Manipulation(FabriqueModele fmm/*,Canvas c*/,FabriqueFigure ff) {
		fm=fmm;
		//		this.canvas=c;
		this.ff=ff;
		forms=new ArrayList<FigureColoree>();
		Endeplacement=false;
		updatableColor=false;
	}
	//点击事件的方法
	public void ClickChoose(MouseEvent e) {
		forms=fm.getFigures();
		boolean isAccesse=false;
		DeSelectionnerTous();
		sourisX=e.getX();
		sourisY=e.getY();
		for(int i=0;i<forms.size();i++) {
			if(forms.get(i)!=null&&((Polygone)forms.get(i)).Accesseur(sourisX, sourisY)){
				forms.get(i).setEnSelection(true);
				isAccesse=true;
				((Polygone)forms.get(i)).afficher(MainController.gc);
				break;
			}
		}
//		System.out.println(((Polygone)fm.getFigureenCours()));
		if(!isAccesse) {//如果点击的位置在所有图形的外部，则取消选定
			DeSelectionnerTous();
		}
	}

	public void Pressed(MouseEvent e) {
		forms=fm.getFigures();
		//		forms.add(fm.getFigureenCours());
		sourisX=e.getX();
		sourisY=e.getY();

		for(int i=0;i<forms.size();i++) {
			if(forms.get(i)!=null&&((Polygone)forms.get(i)).Accesseur(sourisX, sourisY)){
				this.daplacable(true);//可以拖动图形
				this.Updatable(true);//可以更改颜色
				FormId=i;
				break;
			}
		}
	}

	public void dragFigure(MouseEvent e) {
		if(Endeplacement) {
			double dx=e.getX()-sourisX;//移动的x距离
			double dy=e.getY()-sourisY;//移动的y距离
			FigureColoree f=forms.get(FormId);
			Point[] pointsVertex=((Polygone)f).getPointsVertex();
			for(int i=0;i<pointsVertex.length;i++) {
				pointsVertex[i].setX(pointsVertex[i].RendreX()+dx);
				pointsVertex[i].setY(pointsVertex[i].RendreY()+dy);
			}
			((Polygone)f).setPointsVertex(pointsVertex);
			forms.set(FormId, f);
			sourisX=e.getX();
			sourisY=e.getY();
			this.ff.EffaceretDessiner();
		}
	}
	public void lacher() {//释放鼠标时，不继续拖动
		this.daplacable(false);
	}
	
	public void daplacable(boolean res) {
		Endeplacement=res;
	}
	
	//更新颜色
	public void upDateColor(Color c) {
		if(this.updatableColor) {
			this.forms.get(FormId).setColorCorant(c);
		}
//		System.out.println(this.updatableColor);
		this.ff.EffaceretDessiner();
	}
	
	
	public void Updatable(boolean res) {
		this.updatableColor=res;
	}
	public boolean getUpdatable() {
		return this.updatableColor;
	}

	public void DeSelectionnerTous() {
		this.fm.DeSelectionnerAll();
		this.ff.EffaceretDessiner();
	}
}

