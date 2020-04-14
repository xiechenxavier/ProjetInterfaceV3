package Modele;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Polygone extends FigureColoree{
	protected Point[] PointsVertex;//表示一个图形的所有顶点
	protected int nbPointsVertex;//点的个数
	private int seuilIndice=0;

	public Polygone(int nbPointsVertex) {
		this.nbPointsVertex=nbPointsVertex;//必须给出一共需要的顶点数量
		PointsVertex=new Point[nbPointsVertex];
		//		PointsVertex[0]=new Point();//这个是构成本多边形的关键顶点也是参考标准
	}
	public Point[] getPointsVertex() {
		return PointsVertex;
	}
	public void setPointsVertex(Point[] pointsVertex) {
		PointsVertex = pointsVertex;
	}
	//这个规则图形有几个顶点
	public int getNbPointsVertex() {
		return nbPointsVertex;
	}
	//设定顶点个数
	public void setNbPointsVertex(int nbPointsVertex) {
		this.nbPointsVertex = nbPointsVertex;
	}
	//是否在图形范围内部
	public abstract boolean Accesseur(double x, double y);
	// TODO Auto-generated method stub
	//图形变换，主要是变换形状
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
		if (this.EnSelection) {//如果被选择，则所有的选择正方形显现出来
			for (int i = 0;i<this.getNbPointsVertex();i++) {
				if(this.getPointsVertex()[i]!=null) {
					double x= this.getPointsVertex()[i].RendreX();
					double y=this.getPointsVertex()[i].RendreY();
					gc.setFill(Color.BLACK);//选择正方形为黑色
					gc.fillRect(x-TAILLE_CARRE_SELECTION/2,
							y-TAILLE_CARRE_SELECTION/2,
							TAILLE_CARRE_SELECTION, 
							TAILLE_CARRE_SELECTION);
				}
				gc.setFill(this.getColorCorant());//本图形的颜色
			}
		}
	}

	public boolean IsSeuil(double x,double y) {//在图形边缘顶点处,小正方行位置上
		Point[] VertexPoints=this.getPointsVertex();
		boolean[] colJudgeSeuil=new boolean[VertexPoints.length];
		boolean res=false;
		for(int i=0;i<colJudgeSeuil.length;i++ ) {
			boolean nx=this.CarreAccesseur(VertexPoints[i].RendreX(), VertexPoints[i].RendreY(), x, y);//是否在正方形内部
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
		return this.seuilIndice;//哪一个点
	}

	//顶点正方形范围的判断
	public boolean CarreAccesseur(double vertexX,double vertexY,double x, double y) {//某个顶点的vertexX，vertexY
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
	public abstract String toString();//名字
}
