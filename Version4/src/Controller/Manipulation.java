package Controller;

import java.util.ArrayList;
//import java.util.HashMap;

import Modele.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Manipulation {

	FabriqueModele fm;
	FabriqueFigure ff;
	boolean Endeplacement,updatableColor,Reformable,IsSeuil;
	ArrayList<Object> totalCollection;
	ArrayList<FigureColoree> forms;
	int FormId,FormFinalId;
	//	private GraphicsContext MainController.gc;
	private double sourisX,sourisY;
	private int seuilIndice;

	//	private int countClick;//计算点击次数来判断如何换位置
	//	Canvas canvas;
	//	
	public Manipulation(FabriqueModele fmm,FabriqueFigure ff) {
		fm=fmm;
		//		this.canvas=c;
		this.ff=ff;
		forms=new ArrayList<FigureColoree>();
		totalCollection=fmm.getMainCollection();//包括线的总集合
		Endeplacement=false;
		updatableColor=false;//管理是否可以修改颜色
		Reformable=false;//管理是否可以改变大小
		IsSeuil=false;//用来管理是否在边界
		FormId=-1;
	}
	//点击事件的方法,点击是为了选择一个图形
	@SuppressWarnings("unchecked")
	public void ClickChoose(MouseEvent e) {
		boolean isAccesse=false;
		boolean Selectable=true;//默认情况为可以

		DeSelectionnerTous();
		sourisX=e.getX();
		sourisY=e.getY();
		this.totalCollection=this.fm.getMainCollection();

		for(int i=this.totalCollection.size()-1;i>=0;i--) {//总容器中的当前遍历下标
			if(totalCollection.get(i) instanceof FigureColoree&&((Polygone)totalCollection.get(i)).Accesseur(sourisX, sourisY)) {//如果是图形

				((FigureColoree)totalCollection.get(i)).setEnSelection(true);
				isAccesse=true;
				this.Updatable(true);//可以更改颜色
				this.setFormId(i);//获得点击图形的ID
				break;
			}
		}
		//获得容器中最后一个图形的Indice
		for(int j=this.totalCollection.size()-1;j>=0;j--) {
			if(totalCollection.get(j) instanceof FigureColoree) {
				this.FormFinalId=j;
				break;
			}
		}

		if(!isAccesse) {//如果点击的位置在所有图形的外部，则取消选定
			DeSelectionnerTous();
		}else {
			//判断是否这个选择图形在list之后有与之重合的白线
			for(int z=this.fm.getMainCollection().size()-1;z>this.getFormId();z--) {
				Object ob=this.fm.getMainCollection().get(z);
				FigureColoree f=(FigureColoree) this.fm.getMainCollection().get(this.getFormId());
				if(ob instanceof ArrayList<?>&&((ArrayList<Point>)ob).get(0).getC().equals(Color.web("#f0f0f0"))&&this.Cheveauchement((Polygone)f,(ArrayList<Point>)ob)) {//是白线线
					Selectable=false;
					System.out.println(Selectable);
				}
			}
			if(totalCollection.get(this.getFormId())!=null&&Selectable) {
				FigureColoree ObjectTemp=(FigureColoree)totalCollection.get(this.getFormId());//获取这个图形所在位置
				for(int i=this.getFormId();i<FormFinalId;i++) {
					totalCollection.set(i,totalCollection.get(i+1));
				}
				totalCollection.set(FormFinalId, ObjectTemp);
				((Polygone)totalCollection.get(FormFinalId)).afficher(MainController.gc);
			}
			this.fm.setMainCollection(totalCollection);
		}
	}

	public void Pressed(MouseEvent e,String modeAction) {

		sourisX=e.getX();
		sourisY=e.getY();

		for(int i=this.totalCollection.size()-1;i>=0;i--) {//总容器中的当前遍历下标
			if(totalCollection.get(i) instanceof FigureColoree) {//如果是图形
				if(((Polygone)totalCollection.get(i)).Accesseur(sourisX, sourisY)) {
					if(modeAction.equals("Deplacer")) {
						this.deplacable(true);//可以拖动图形
					}else if(modeAction.equals("Reforme")) {
						this.setReformable(true);//可以修改图形大小
					}
					this.FormId=i;
					break;
				}
			}
		}
	}

	public void dragFigure(MouseEvent e) {
		if(Endeplacement) {
			double dx=e.getX()-sourisX;//移动的x距离
			double dy=e.getY()-sourisY;//移动的y距离
			FigureColoree f=(FigureColoree)totalCollection.get(FormId);
			Point[] pointsVertex=((Polygone)f).getPointsVertex();
			for(int i=0;i<pointsVertex.length;i++) {
				pointsVertex[i].setX(pointsVertex[i].RendreX()+dx);
				pointsVertex[i].setY(pointsVertex[i].RendreY()+dy);
			}
			((Polygone)f).setPointsVertex(pointsVertex);
			totalCollection.set(FormId, f);
			sourisX=e.getX();
			sourisY=e.getY();
			this.ff.EffaceretDessiner();
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

	public int getFormId() {
		return this.FormId;
	}
	public void setFormId(int x) {
		this.FormId=x;
	}
	//更新颜色
	public void upDateColor(Color c) {
		if(this.updatableColor) {
			((FigureColoree)(this.totalCollection.get(FormId))).setColorCorant(c);
		}
		//		System.out.println(this.updatableColor);
		this.ff.EffaceretDessiner();
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
		this.ff.EffaceretDessiner();
	}

	public void ReFormeByDragging(MouseEvent e) throws IndexOutOfBoundsException{
		if(FormId>=0) {
			FigureColoree f=(FigureColoree)totalCollection.get(FormId);
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
	}

	public void Redimentionner(double newWidth,double newHeight) {
		if(FormId>=0) {
			FigureColoree f=(FigureColoree)totalCollection.get(FormId);
			((Polygone)f).Redimentionner(newWidth, newHeight);
			totalCollection.set(FormId, f);
			//		this.fm.setFigures(forms);
			this.fm.setMainCollection(totalCollection);
			this.ff.EffaceretDessiner();
		}
	}
	//当前选中的那个图形宽度
	public double getSelectedFormWidth() throws Exception{
		//		forms=fm.getFigures();
		if(totalCollection.isEmpty()) {
			throw new Exception("list est vide");
		}else {
			if(FormId<0) {//暂时还没有点击
				FigureColoree fc=this.fm.getFigureenCours();
				return ((Polygone)fc).getLargeur();
			}else {
				return 	((Polygone)totalCollection.get(FormId)).getLargeur();
			}
		}
	}
	//当前选中的那个图形长度
	public double getSelectedFormHeight() throws Exception {
		forms=fm.getFigures();
		if(totalCollection.isEmpty()) {
			throw new Exception("list est vide");
		}else {
			if(FormId<0) {//暂时还没有点击
				FigureColoree fc=this.fm.getFigureenCours();
				return ((Polygone)fc).getHauteur();
			}else {
				return 	((Polygone)totalCollection.get(FormId)).getHauteur();
			}
		}
	}

	//画的橡皮擦线是否与图形相交
	public boolean Cheveauchement(Polygone f,ArrayList<Point> ld) {
		boolean res=false;
		for(Point p:ld) {
			if(((Polygone)f).Accesseur(p.RendreX(), p.RendreY())) {//如果在范围内即相交
				res=true;
				break;
			}
		}
		return res;
	}

	//delete by keyboard
	public void DeleteParKeyBoard(KeyEvent ke) {
		//		forms=fm.getFigures();
		FigureColoree fc=this.fm.getFigureenCours();
		if(totalCollection.isEmpty()) {//如果forms为空则不处理，如果FormId存在
			throw new NullPointerException("forms list est vide");
		}else {
			if(FormId<0) {//如果还没有选中，但是forms不为空，说明此时已经存在图形，但是还没有选中任何一个
				if(ke.getCode()==KeyCode.BACK_SPACE) {
					totalCollection.remove(fc);//删除这个图形
				}
			}else {
				if(ke.getCode()==KeyCode.BACK_SPACE) {
					totalCollection.remove(FormId);//删除这个图形
				}
			}
		}
		System.out.println("ca marche");
		//		this.fm.setFigures(forms);
		this.fm.setMainCollection(totalCollection);
		this.ff.EffaceretDessiner();
	}
}

