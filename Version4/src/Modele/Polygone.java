package Modele;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Polygone extends FigureColoree{
	protected Point[] PointsVertex;//��ʾһ��ͼ�ε����ж���
	protected int nbPointsVertex;//��ĸ���
	private int seuilIndice=0;

	public Polygone(int nbPointsVertex) {
		this.nbPointsVertex=nbPointsVertex;//�������һ����Ҫ�Ķ�������
		PointsVertex=new Point[nbPointsVertex];
		//		PointsVertex[0]=new Point();//����ǹ��ɱ�����εĹؼ�����Ҳ�ǲο���׼
	}
	public Point[] getPointsVertex() {
		return PointsVertex;
	}
	public void setPointsVertex(Point[] pointsVertex) {
		PointsVertex = pointsVertex;
	}
	//�������ͼ���м�������
	public int getNbPointsVertex() {
		return nbPointsVertex;
	}
	//�趨�������
	public void setNbPointsVertex(int nbPointsVertex) {
		this.nbPointsVertex = nbPointsVertex;
	}
	//�Ƿ���ͼ�η�Χ�ڲ�
	public abstract boolean Accesseur(double x, double y);
	// TODO Auto-generated method stub
	//ͼ�α任����Ҫ�Ǳ任��״
	public abstract void transformation(double dx ,double dy, int id_pts);

	public int carreDeSelection(int x,int y) {
		for (int i=0;i<this.nbPointsVertex;i++) {
			Point p = this.PointsVertex[i];
			if ((p.RendreX()-TAILLE_CARRE_SELECTION/2)<=x && 
					(p.RendreY()-TAILLE_CARRE_SELECTION/2)<=y &&
					(p.RendreX()+TAILLE_CARRE_SELECTION) > x &&
					(p.RendreY()+TAILLE_CARRE_SELECTION) > y)
				return i;
		}
		return -1;
	}
	public void afficher(GraphicsContext gc) {
		if (this.EnSelection) {//�����ѡ�������е�ѡ�����������ֳ���
			for (int i = 0;i<this.getNbPointsVertex();i++) {
				if(this.getPointsVertex()[i]!=null) {
					double x= this.getPointsVertex()[i].RendreX();
					double y=this.getPointsVertex()[i].RendreY();
					gc.setFill(Color.BLACK);//ѡ��������Ϊ��ɫ
					gc.fillRect(x-TAILLE_CARRE_SELECTION/2,
							y-TAILLE_CARRE_SELECTION/2,
							TAILLE_CARRE_SELECTION, 
							TAILLE_CARRE_SELECTION);
				}
				gc.setFill(this.getColorCorant());//��ͼ�ε���ɫ
			}
		}
	}

	public boolean IsSeuil(double x,double y) {//��ͼ�α�Ե���㴦,С������λ����
		Point[] VertexPoints=this.getPointsVertex();
		boolean[] colJudgeSeuil=new boolean[VertexPoints.length];
		boolean res=false;
		for(int i=0;i<colJudgeSeuil.length;i++ ) {
			boolean nx=this.CarreAccesseur(VertexPoints[i].RendreX(), VertexPoints[i].RendreY(), x, y);//�Ƿ����������ڲ�
			colJudgeSeuil[i]=nx;
			System.out.print(nx+" ");
		}
		System.out.println();
		for(int j=0;j<colJudgeSeuil.length;j++) {
			if(colJudgeSeuil[j]) {
				this.seuilIndice=j;
				res=true;
				break;
			}
		}
		return res;
	}
	public int getSeuilIndice() {
		return this.seuilIndice;//��һ����
	}

	//���������η�Χ���ж�
	public boolean CarreAccesseur(double vertexX,double vertexY,double x, double y) {//ĳ�������vertexX��vertexY
		// TODO Auto-generated method stub
		double x3=vertexX+TAILLE_CARRE_SELECTION*3;
		double y3=vertexY+TAILLE_CARRE_SELECTION*3;
		double x1=vertexX-TAILLE_CARRE_SELECTION*3;
		double y1=vertexY-TAILLE_CARRE_SELECTION*3;
		return (x>=x1)&&(x<=x3)&&(y>=y1)&&(y<=y3);
	}
	
	public abstract void Redimentionner(double newwidth,double newheight);//ca permet de redimetionner une graph
	public abstract double getLargeur();
	public abstract double getHauteur();
	public abstract String toString();//����
}
