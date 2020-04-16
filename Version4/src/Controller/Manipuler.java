package Controller;

import java.util.ArrayList;

import Modele.FabriqueModele;
import Modele.FigureColoree;
import Modele.Point;
import Modele.Polygone;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Manipuler {
	FabriqueModele fm;
	FabriqueFigure ff;
	boolean Endeplacement,updatableColor,Reformable,IsSeuil;
	ArrayList<FigureColoree> forms;
	int FormId;
	//	private GraphicsContext MainController.gc;
	private double sourisX,sourisY;
	private int seuilIndice;

	//	private int countClick;//计算点击次数来判断如何换位置
	//	Canvas canvas;
	//	
	public Manipuler(FabriqueModele fmm,FabriqueFigure ff) {
		fm=fmm;
		//		this.canvas=c;
		this.ff=ff;
		forms=new ArrayList<FigureColoree>();
		Endeplacement=false;
		updatableColor=false;//管理是否可以修改颜色
		Reformable=false;//管理是否可以改变大小
		IsSeuil=false;//用来管理是否在边界
		FormId=-1;
	}
	//点击事件的方法,点击是为了选择一个图形
	public void ClickChoose(MouseEvent e) {
		forms=fm.getFigures();
		boolean isAccesse=false;
		DeSelectionnerTous();
		sourisX=e.getX();
		sourisY=e.getY();
		//首先：在多边形容器中找出位置，然后再根据这个图形在总的容器中找出位置与第一个图形在总容器的位置进行交换
		for(int i=forms.size()-1;i>=0;i--) {
			if(forms.get(i)!=null&&((Polygone)forms.get(i)).Accesseur(sourisX, sourisY)){//第一个被点击中的图形
				forms.get(i).setEnSelection(true);
				isAccesse=true;
				this.Updatable(true);//可以更改颜色
				this.FormId=i;
				break;
			}
		}
	
		if(!isAccesse) {//如果点击的位置在所有图形的外部，则取消选定
			DeSelectionnerTous();
		}else {
			if(forms.get(FormId)!=null) {
				int LastIndice=forms.size()-1;
				FigureColoree temp=forms.get(LastIndice);//第一个提出来
				forms.set(LastIndice, forms.get(FormId));//把第一个换成现在的
				forms.set(FormId, temp);//把现在的换成第一个
			}
			((Polygone)forms.get(FormId)).afficher(MainController.gc);
			this.fm.setFigures(forms);
		}
	}

	public void Pressed(MouseEvent e,String modeAction) {
		forms=fm.getFigures();
		//		forms.add(fm.getFigureenCours());
		sourisX=e.getX();
		sourisY=e.getY();

		for(int i=forms.size()-1;i>=0;i--) {
			if(forms.get(i)!=null&&((Polygone)forms.get(i)).Accesseur(sourisX, sourisY)){
				if(modeAction.equals("Deplacer")) {
					this.deplacable(true);//可以拖动图形
				}else if(modeAction.equals("Reforme")) {
					this.setReformable(true);//可以修改图形大小
				}
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
			this.ff.EffaceretDessinerV2();
		}
	}

	public void lacher() {//释放鼠标时，不继续拖动，不能改变大小-
		this.deplacable(false);
		this.setReformable(false);
		this.IsSeuil=false;
	}

	public void deplacable(boolean res) {
		Endeplacement=res;
	}
	public void setReformable(boolean res) {
		this.Reformable=res;
	}

	//更新颜色
	public void upDateColor(Color c) {
		if(this.updatableColor) {
			this.forms.get(FormId).setColorCorant(c);
		}
		//		System.out.println(this.updatableColor);
		this.ff.EffaceretDessinerV2();
	}


	public void Updatable(boolean res) {
		this.updatableColor=res;
	}

	//是否可以现在改变颜色
	public boolean getUpdatable() {
		return this.updatableColor;
	}

	//取消对所有图形的选定
	public void DeSelectionnerTous() {
		this.fm.DeSelectionnerAll();
		this.ff.EffaceretDessinerV2();
	}

	public void ReFormeByDragging(MouseEvent e) {
		FigureColoree f=forms.get(FormId);
		if(this.Reformable) {
			if(f!=null) {
				if(((Polygone)f).IsSeuil(sourisX,sourisY)) {
					seuilIndice=((Polygone)f).getSeuilIndice();
					System.out.println(seuilIndice);
					this.IsSeuil=true;
				}
			}
			if(this.IsSeuil) {//在这个范围之内我们可以操作鼠标通过拉伸来改变图形的大小
				double dx=e.getX()-sourisX;//移动的x距离
				double dy=e.getY()-sourisY;//移动的y距离
				((Polygone)f).transformation(dx, dy, seuilIndice);
				sourisX=e.getX();
				sourisY=e.getY();
			}
			this.ff.EffaceretDessiner();
		}
	}

	public void Redimentionner(double newWidth,double newHeight) {
		FigureColoree f=forms.get(FormId);
		((Polygone)f).Redimentionner(newWidth, newHeight);
		forms.set(FormId, f);
		this.fm.setFigures(forms);
		this.ff.EffaceretDessiner();
	}
	//当前选中的那个图形宽度
	public double getSelectedFormWidth() throws Exception{
		forms=fm.getFigures();
		if(forms.isEmpty()) {
			throw new Exception("list est vide");
		}else {
			if(FormId<0) {//暂时还没有点击
				FigureColoree fc=this.fm.getFigureenCours();
				return ((Polygone)fc).getLargeur();
			}else {
				return 	((Polygone)forms.get(FormId)).getLargeur();
			}
		}
	}
	//当前选中的那个图形长度
	public double getSelectedFormHeight() throws Exception {
		forms=fm.getFigures();
		if(forms.isEmpty()) {
			throw new Exception("list est vide");
		}else {
			if(FormId<0) {//暂时还没有点击
				FigureColoree fc=this.fm.getFigureenCours();
				return ((Polygone)fc).getHauteur();
			}else {
				return 	((Polygone)forms.get(FormId)).getHauteur();
			}
		}
	}


	//delete by keyboard
	public void DeleteParKeyBoard(KeyEvent ke) {
		forms=fm.getFigures();
		FigureColoree fc=this.fm.getFigureenCours();
		if(forms.isEmpty()) {//如果forms为空则不处理，如果FormId存在
			throw new NullPointerException("forms list est vide");
		}else {
			if(FormId<0) {//如果还没有选中，但是forms不为空，说明此时已经存在图形，但是还没有选中任何一个
				if(ke.getCode()==KeyCode.BACK_SPACE) {
					forms.remove(fc);//删除这个图形
				}
			}else {
				if(ke.getCode()==KeyCode.BACK_SPACE) {
					forms.remove(FormId);//删除这个图形
				}
			}
		}
		System.out.println("ca marche");
		this.fm.setFigures(forms);
		this.ff.EffaceretDessiner();
	}
}
