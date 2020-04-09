package Modele;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Polygone extends FigureColoree{
	protected Point[] PointsVertex;//表示一个图形的所有顶点
	protected int nbPointsVertex;

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
	public int getNbPointsVertex() {
		return nbPointsVertex;
	}
	public void setNbPointsVertex(int nbPointsVertex) {
		this.nbPointsVertex = nbPointsVertex;
	}

	public abstract boolean Accesseur(double x, double y);
	// TODO Auto-generated method stub

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
}
