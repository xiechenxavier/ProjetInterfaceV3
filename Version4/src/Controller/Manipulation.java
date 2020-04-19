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

	//	private int countClick;//�������������ж���λ�λ��
	//	Canvas canvas;
	//	
	public Manipulation(FabriqueModele fmm,FabriqueFigure ff) {
		fm=fmm;
		//		this.canvas=c;
		this.ff=ff;
		forms=new ArrayList<FigureColoree>();
		totalCollection=fmm.getMainCollection();//�����ߵ��ܼ���
		Endeplacement=false;
		updatableColor=false;//�����Ƿ�����޸���ɫ
		Reformable=false;//�����Ƿ���Ըı��С
		IsSeuil=false;//���������Ƿ��ڱ߽�
		FormId=-1;
	}
	//����¼��ķ���,�����Ϊ��ѡ��һ��ͼ��
	@SuppressWarnings("unchecked")
	public void ClickChoose(MouseEvent e) {
		boolean isAccesse=false;
		boolean Selectable=true;//Ĭ�����Ϊ����

		DeSelectionnerTous();
		sourisX=e.getX();
		sourisY=e.getY();
		this.totalCollection=this.fm.getMainCollection();

		for(int i=this.totalCollection.size()-1;i>=0;i--) {//�������еĵ�ǰ�����±�
			if(totalCollection.get(i) instanceof FigureColoree&&((Polygone)totalCollection.get(i)).Accesseur(sourisX, sourisY)) {//�����ͼ��

				((FigureColoree)totalCollection.get(i)).setEnSelection(true);
				isAccesse=true;
				this.Updatable(true);//���Ը�����ɫ
				this.setFormId(i);//��õ��ͼ�ε�ID
				break;
			}
		}
		//������������һ��ͼ�ε�Indice
		for(int j=this.totalCollection.size()-1;j>=0;j--) {
			if(totalCollection.get(j) instanceof FigureColoree) {
				this.FormFinalId=j;
				break;
			}
		}

		if(!isAccesse) {//��������λ��������ͼ�ε��ⲿ����ȡ��ѡ��
			DeSelectionnerTous();
		}else {
			//�ж��Ƿ����ѡ��ͼ����list֮������֮�غϵİ���
			for(int z=this.fm.getMainCollection().size()-1;z>this.getFormId();z--) {
				Object ob=this.fm.getMainCollection().get(z);
				FigureColoree f=(FigureColoree) this.fm.getMainCollection().get(this.getFormId());
				if(ob instanceof ArrayList<?>&&((ArrayList<Point>)ob).get(0).getC().equals(Color.web("#f0f0f0"))&&this.Cheveauchement((Polygone)f,(ArrayList<Point>)ob)) {//�ǰ�����
					Selectable=false;
					System.out.println(Selectable);
				}
			}
			if(totalCollection.get(this.getFormId())!=null&&Selectable) {
				FigureColoree ObjectTemp=(FigureColoree)totalCollection.get(this.getFormId());//��ȡ���ͼ������λ��
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

		for(int i=this.totalCollection.size()-1;i>=0;i--) {//�������еĵ�ǰ�����±�
			if(totalCollection.get(i) instanceof FigureColoree) {//�����ͼ��
				if(((Polygone)totalCollection.get(i)).Accesseur(sourisX, sourisY)) {
					if(modeAction.equals("Deplacer")) {
						this.deplacable(true);//�����϶�ͼ��
					}else if(modeAction.equals("Reforme")) {
						this.setReformable(true);//�����޸�ͼ�δ�С
					}
					this.FormId=i;
					break;
				}
			}
		}
	}

	public void dragFigure(MouseEvent e) {
		if(Endeplacement) {
			double dx=e.getX()-sourisX;//�ƶ���x����
			double dy=e.getY()-sourisY;//�ƶ���y����
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

	public void lacher() {//�ͷ����ʱ���������϶������ܸı��С-
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
	//������ɫ
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

	//�Ƿ�������ڸı���ɫ
	public boolean getUpdatable() {
		return this.updatableColor;
	}

	//ȡ��������ͼ�ε�ѡ��
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
				if(this.IsSeuil) {//�������Χ֮�����ǿ��Բ������ͨ���������ı�ͼ�εĴ�С
					double dx=e.getX()-sourisX;//�ƶ���x����
					double dy=e.getY()-sourisY;//�ƶ���y����
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
	//��ǰѡ�е��Ǹ�ͼ�ο��
	public double getSelectedFormWidth() throws Exception{
		//		forms=fm.getFigures();
		if(totalCollection.isEmpty()) {
			throw new Exception("list est vide");
		}else {
			if(FormId<0) {//��ʱ��û�е��
				FigureColoree fc=this.fm.getFigureenCours();
				return ((Polygone)fc).getLargeur();
			}else {
				return 	((Polygone)totalCollection.get(FormId)).getLargeur();
			}
		}
	}
	//��ǰѡ�е��Ǹ�ͼ�γ���
	public double getSelectedFormHeight() throws Exception {
		forms=fm.getFigures();
		if(totalCollection.isEmpty()) {
			throw new Exception("list est vide");
		}else {
			if(FormId<0) {//��ʱ��û�е��
				FigureColoree fc=this.fm.getFigureenCours();
				return ((Polygone)fc).getHauteur();
			}else {
				return 	((Polygone)totalCollection.get(FormId)).getHauteur();
			}
		}
	}

	//������Ƥ�����Ƿ���ͼ���ཻ
	public boolean Cheveauchement(Polygone f,ArrayList<Point> ld) {
		boolean res=false;
		for(Point p:ld) {
			if(((Polygone)f).Accesseur(p.RendreX(), p.RendreY())) {//����ڷ�Χ�ڼ��ཻ
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
		if(totalCollection.isEmpty()) {//���formsΪ���򲻴������FormId����
			throw new NullPointerException("forms list est vide");
		}else {
			if(FormId<0) {//�����û��ѡ�У�����forms��Ϊ�գ�˵����ʱ�Ѿ�����ͼ�Σ����ǻ�û��ѡ���κ�һ��
				if(ke.getCode()==KeyCode.BACK_SPACE) {
					totalCollection.remove(fc);//ɾ�����ͼ��
				}
			}else {
				if(ke.getCode()==KeyCode.BACK_SPACE) {
					totalCollection.remove(FormId);//ɾ�����ͼ��
				}
			}
		}
		System.out.println("ca marche");
		//		this.fm.setFigures(forms);
		this.fm.setMainCollection(totalCollection);
		this.ff.EffaceretDessiner();
	}
}

