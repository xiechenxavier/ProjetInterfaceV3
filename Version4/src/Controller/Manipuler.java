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

	//	private int countClick;//�������������ж���λ�λ��
	//	Canvas canvas;
	//	
	public Manipuler(FabriqueModele fmm,FabriqueFigure ff) {
		fm=fmm;
		//		this.canvas=c;
		this.ff=ff;
		forms=new ArrayList<FigureColoree>();
		Endeplacement=false;
		updatableColor=false;//�����Ƿ�����޸���ɫ
		Reformable=false;//�����Ƿ���Ըı��С
		IsSeuil=false;//���������Ƿ��ڱ߽�
		FormId=-1;
	}
	//����¼��ķ���,�����Ϊ��ѡ��һ��ͼ��
	public void ClickChoose(MouseEvent e) {
		forms=fm.getFigures();
		ArrayList<Object> totalCollection=fm.getMainCollection();
		
		boolean isAccesse=false;
		DeSelectionnerTous();
		sourisX=e.getX();
		sourisY=e.getY();
		//���ȣ��ڶ�����������ҳ�λ�ã�Ȼ���ٸ������ͼ�����ܵ��������ҳ�λ�����һ��ͼ������������λ�ý��н���
		for(int i=forms.size()-1;i>=0;i--) {
			if(forms.get(i)!=null&&((Polygone)forms.get(i)).Accesseur(sourisX, sourisY)){//��һ��������е�ͼ��
				forms.get(i).setEnSelection(true);
				isAccesse=true;
				this.Updatable(true);//���Ը�����ɫ
				this.FormId=i;
				break;
			}
		}
		int formIdObject=0;//��ǰ���������е�ͼ���±�
		for(int j=0;j<totalCollection.size();j++) {
			if(totalCollection.get(j) instanceof FigureColoree) {
				if((FigureColoree)totalCollection.get(j)==forms.get(FormId)) {
					formIdObject=j;
					break;
				}
			}
		}
		int FinalformIdObject=0;//�������������һ��ͼ��
		for(int x=0;x<totalCollection.size();x++) {
			if(totalCollection.get(x) instanceof FigureColoree) {
				if((FigureColoree)totalCollection.get(x)==forms.get(forms.size()-1)) {
					formIdObject=x;
					break;
				}
			}
		}
		if(!isAccesse) {//��������λ��������ͼ�ε��ⲿ����ȡ��ѡ��
			DeSelectionnerTous();
		}else {
			if(forms.get(FormId)!=null) {
				int LastIndice=forms.size()-1;
				FigureColoree temp=forms.get(LastIndice);//��һ�������
				forms.set(LastIndice, forms.get(FormId));//�ѵ�һ���������ڵ�
				forms.set(FormId, temp);//�����ڵĻ��ɵ�һ��
				FigureColoree ObjectTemp=(FigureColoree)totalCollection.get(FinalformIdObject);
				totalCollection.set(FinalformIdObject,totalCollection.get(formIdObject) );
				totalCollection.set(formIdObject, ObjectTemp);
			}
			((Polygone)forms.get(FormId)).afficher(MainController.gc);
			this.fm.setFigures(forms);
			this.fm.setMainCollection(totalCollection);
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
					this.deplacable(true);//�����϶�ͼ��
				}else if(modeAction.equals("Reforme")) {
					this.setReformable(true);//�����޸�ͼ�δ�С
				}
				FormId=i;
				break;
			}
		}
	}

	public void dragFigure(MouseEvent e) {
		if(Endeplacement) {
			double dx=e.getX()-sourisX;//�ƶ���x����
			double dy=e.getY()-sourisY;//�ƶ���y����
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

	//������ɫ
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

	//�Ƿ�������ڸı���ɫ
	public boolean getUpdatable() {
		return this.updatableColor;
	}

	//ȡ��������ͼ�ε�ѡ��
	public void DeSelectionnerTous() {
		this.fm.DeSelectionnerAll();
		this.ff.EffaceretDessiner();
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

	public void Redimentionner(double newWidth,double newHeight) {
		FigureColoree f=forms.get(FormId);
		((Polygone)f).Redimentionner(newWidth, newHeight);
		forms.set(FormId, f);
		this.fm.setFigures(forms);
		this.ff.EffaceretDessiner();
	}
	//��ǰѡ�е��Ǹ�ͼ�ο��
	public double getSelectedFormWidth() throws Exception{
		forms=fm.getFigures();
		if(forms.isEmpty()) {
			throw new Exception("list est vide");
		}else {
			if(FormId<0) {//��ʱ��û�е��
				FigureColoree fc=this.fm.getFigureenCours();
				return ((Polygone)fc).getLargeur();
			}else {
				return 	((Polygone)forms.get(FormId)).getLargeur();
			}
		}
	}
	//��ǰѡ�е��Ǹ�ͼ�γ���
	public double getSelectedFormHeight() throws Exception {
		forms=fm.getFigures();
		if(forms.isEmpty()) {
			throw new Exception("list est vide");
		}else {
			if(FormId<0) {//��ʱ��û�е��
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
		if(forms.isEmpty()) {//���formsΪ���򲻴������FormId����
			throw new NullPointerException("forms list est vide");
		}else {
			if(FormId<0) {//�����û��ѡ�У�����forms��Ϊ�գ�˵����ʱ�Ѿ�����ͼ�Σ����ǻ�û��ѡ���κ�һ��
				if(ke.getCode()==KeyCode.BACK_SPACE) {
					forms.remove(fc);//ɾ�����ͼ��
				}
			}else {
				if(ke.getCode()==KeyCode.BACK_SPACE) {
					forms.remove(FormId);//ɾ�����ͼ��
				}
			}
		}
		System.out.println("ca marche");
		this.fm.setFigures(forms);
		this.ff.EffaceretDessiner();
	}
}
