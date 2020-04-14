package Controller;

import java.util.ArrayList;
import Modele.Ellipse;
import Modele.FabriqueModele;
import Modele.FigureColoree;
import Modele.LigneDroit;
import Modele.Point;
import Modele.Polygone;
import Modele.Rectangle;
import Modele.Triangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class FabriqueFigure {
	FabriqueModele fm;
	//	private GraphicsContext MainController.gc;
	Canvas mainCanvas;
	String TypeFigure;
	MainController mac;
	ArrayList<Canvas> historyCanvas;

	public FabriqueFigure(Canvas mc,FabriqueModele fm,String TypeFigure,MainController mac) {
		this.mainCanvas=mc;
		this.fm=fm;
		//		this.MainController.gc=MainController.gc;
		this.mac=mac;
		historyCanvas=new ArrayList<Canvas>();
	}
	public void FirstPoint(MouseEvent e) {

		FigureColoree fc=this.fm.getFigureenCours();
		if(fc instanceof LigneDroit) {
			((LigneDroit)fc).setGrosseur(mac.getGrosseur());
		}
		this.fm.getFigureenCours().changeColor(mac.getColor());
		this.fm.getFigureenCours().setVertex(new Point(e.getX(),e.getY(),this.fm.getFigureenCours().getColorCorant()));//���ö����λ��
		System.out.println(this.fm.getFigureenCours().getColorCorant());
	}
	public void EffaceretDessiner() {

		MainController.gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
//		ArrayList<FigureColoree> forms=fm.getFigures();
//		for(FigureColoree f:forms) {
//			judgeShape(f);
//		}
//		this.afficherListLine();
//		Canvas c=mainCanvas;
//		this.addUnEtatCanvas(c);
		ArrayList<Object> collection=fm.getMainCollection();
		for(Object o:collection) {
			if(o instanceof FigureColoree) {
				judgeShape((FigureColoree)o);
			}else if(o instanceof ArrayList<?>) {
				ArrayList<Point> points=(ArrayList<Point>)o;
				int x1 = 1, y1 = 1, x2 = 0, y2 = 0;
				for (Point point : points) {
					x2 = (int) point.RendreX();
					y2 = (int) point.RendreY();   

					if (y1 != 1) {
						MainController.gc.setLineWidth(point.getG());
						MainController.gc.setStroke(point.getC());
						MainController.gc.strokeLine(x1, y1, x2, y2);
					}
					x1 = x2;
					y1 = y2;

				}
			}else {
				throw new NullPointerException("error");
			}
		}
		Canvas c=mainCanvas;
		this.addUnEtatCanvas(c);
	}

	public String getFigureType() {
		return this.TypeFigure;
	}
	public void setFigureType(String FigureType) {
		this.TypeFigure=FigureType;
	}

	//����������
	public void tempdrawFigure(MouseEvent e) {

		EffaceretDessiner();//�������еģ������µ�
		FigureColoree fc=fm.getFigureenCours();
		Point[] VertexPoints=((Polygone)fc).getPointsVertex();

		if(fc instanceof Rectangle) {

			VertexPoints[0]=new Point(fc.getVertexX(),fc.getVertexY(),fc.getColorCorant());//�׶���
			VertexPoints[2]=new Point(e.getX(),e.getY(),fc.getColorCorant());//���˵�ڶ�������������ƫ�£�����Ҫ������һ����Ҫ�����λ��
			VertexPoints[1]=new Point(e.getX(),fc.getVertexY(),fc.getColorCorant());
			VertexPoints[3]=new Point(fc.getVertexX(),e.getY(),fc.getColorCorant());

		}else if(fc instanceof Triangle){

			VertexPoints[0]=new Point(fc.getVertexX(),fc.getVertexY(),fc.getColorCorant());
			fc.setVertex(new Point(fc.getVertexX(),e.getY(),fc.getColorCorant()));
			VertexPoints[1]=new Point(e.getX(),e.getY(),fc.getColorCorant());//���˵�ڶ�������������ƫ�£�����Ҫ������һ����Ҫ�����λ��
			double border=Math.abs(e.getX()-fc.getVertexX());//�ȱ������α߳�
			double height=border*Math.sqrt(3)/2;
			double thirdVertexY=e.getY()-height;
			VertexPoints[2]=new Point((e.getX()+fc.getVertexX())/2,thirdVertexY,fc.getColorCorant());//������(pX1+pX2)/2

		}else if(fc instanceof LigneDroit) {
			VertexPoints[0]=new Point(fc.getVertexX(),fc.getVertexY(),fc.getColorCorant());
			VertexPoints[1]=new Point(e.getX(),e.getY(),fc.getColorCorant());

		}else if(fc instanceof Ellipse) {
			if(fc.getVertexX()<e.getX()) {
				VertexPoints[0]=new Point(fc.getVertexX(),fc.getVertexY(),fc.getColorCorant());
				VertexPoints[1]=new Point(e.getX(),e.getY(),fc.getColorCorant());
			}else {
				VertexPoints[1]=new Point(fc.getVertexX(),fc.getVertexY(),fc.getColorCorant());
				VertexPoints[0]=new Point(e.getX(),e.getY(),fc.getColorCorant());
			}
		}
		((Polygone)fc).setPointsVertex(VertexPoints);
		judgeShape(fc);
	}

	public void ReleasedSouris(MouseEvent e) {

		EffaceretDessiner();
		FigureColoree f=this.fm.getFigureenCours();
		judgeShape(f);
		boolean check=true;
		for(int i=0;i<((Polygone)f).getNbPointsVertex();i++) {
			if(((Polygone)f).getPointsVertex()[i]==null) {
				check=false;
				break;
			}
		}
		if(check) {
			this.fm.addFigure(f);
		}
		f.setEnSelection(true);
		((Polygone)f).afficher(MainController.gc);
		this.checkChoix(this.TypeFigure);

	}

	void judgeShape(FigureColoree fc) {

		int nbPointsVertex=((Polygone) fc).getNbPointsVertex();
		Point[] PointsVertex=((Polygone) fc).getPointsVertex();
		double[] PointsX=new double[nbPointsVertex];
		double[] PointsY=new double[nbPointsVertex];

		for(int i=0;i<nbPointsVertex;i++) {
			if(PointsVertex[i]!=null) {
				PointsX[i]=PointsVertex[i].RendreX();
				PointsY[i]=PointsVertex[i].RendreY();
			}
		}

		if(fc instanceof Rectangle) {

			MainController.gc.setFill(fc.getColorCorant());
			MainController.gc.fillPolygon(PointsX, PointsY,((Rectangle) fc).getNbPointsVertex());
		}else if(fc instanceof Triangle) {

			MainController.gc.setFill(fc.getColorCorant());
			MainController.gc.fillPolygon(PointsX, PointsY,((Triangle) fc).getNbPointsVertex());
		}else if(fc instanceof LigneDroit) {

			System.out.println(((LigneDroit)fc).getGrosseur());
			MainController.gc.setStroke(fc.getColorCorant());
			MainController.gc.setLineWidth(((LigneDroit)fc).getGrosseur());
			MainController.gc.strokeLine(PointsX[0], PointsY[0], PointsX[1], PointsY[1]);

		}else if(fc instanceof Ellipse) {

			double a=Math.abs(PointsX[1]-PointsX[0]);
			double b=Math.abs(PointsY[1]-PointsY[0]);
			MainController.gc.setFill(fc.getColorCorant());
			MainController.gc.fillOval(PointsX[0], PointsY[0], a, b);

		}

	}

	public void checkChoix(String FigureType) {
		switch(FigureType) {
		case "Rectangle":
			fm.construit(new Rectangle(4));
			break;
		case "Triangle":
			fm.construit(new Triangle(3));
			break;
		case "LigneDroit":
			fm.construit(new LigneDroit(2));
			break;
		case "Ellipse":
			fm.construit(new Ellipse(2));
			break;
		default:
			fm.construit(new Rectangle(4));
			break;
		}
	}

	public void addUnEtatCanvas(Canvas c) {
		this.historyCanvas.add(c);
	}

	public ArrayList<Canvas> getHistoryCanvas(){
		return this.historyCanvas;
	}

	public void undo() {
		int indice=historyCanvas.size()-2;
		this.mainCanvas=this.historyCanvas.get(indice);
		for(int i=historyCanvas.size()-1;i>indice;i--) {//�Ѷ������״̬ȫ��ɾ��
			this.historyCanvas.remove(i);
		}
	}
	
	public void redo() {
		
	}
}
