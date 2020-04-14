package Modele;

public class Rectangle extends Polygone{

	public Rectangle(int nbPointsVertex) {
		super(nbPointsVertex);
		// TODO Auto-generated constructor stub
	}

	//判断一个点是否在矩形之内
	@Override
	public boolean Accesseur(double x, double y) {
		// TODO Auto-generated method stub

		double x3=this.getPointsVertex()[2].RendreX();
		double y3=this.getPointsVertex()[2].RendreY();
		double x1=this.getPointsVertex()[0].RendreX();
		double y1=this.getPointsVertex()[0].RendreY();
		if(x3>=x1&&y3>=y1) {
			return (x>=x1)&&(x<=x3)&&(y>=y1)&&(y<=y3);
		}else if(x3<=x1&&y3>=y1){
			return (x>=x3)&&(x<=x1)&&(y>=y1)&&(y<=y3);
		}else if(x3>=x1&&y3<y1) {
			return (x>=x1)&&(x<=x3)&&(y>=y3)&&(y<=y1);
		}else {
			return (x>=x3)&&(x<=x1)&&(y>=y3)&&(y<=y1);
		}
	}

	/**这个函数用来变换图形的大小*/
	@Override
	public void transformation(double dx, double dy, int id_pts) {
		// TODO Auto-generated method stub
		int last_pts = id_pts-1;
		int next_pts = id_pts+1;
		int opposit_pts = id_pts+2;
		if(id_pts == 3) {
			opposit_pts=1;
			next_pts = 0;
		}else if(id_pts == 0) {
			last_pts=3;
		}else if(id_pts == 2) {
			opposit_pts=0;
		}
		double MoveX=this.PointsVertex[id_pts].RendreX();
		double MoveY=this.PointsVertex[id_pts].RendreY();
		double next_ptsX=this.PointsVertex[next_pts].RendreX();
		double last_ptsY=this.PointsVertex[last_pts].RendreY();
		double opposit_ptsX=this.PointsVertex[opposit_pts].RendreX();
		double opposit_ptsY=this.PointsVertex[opposit_pts].RendreY();
		//移动过程中，对称点是不需要移动位置的， next_pts，y轴改变，x轴不变。 last_pts:y轴不变，x轴发生改变
		switch(id_pts) {
		case 0:
			if(MoveX<next_ptsX&&MoveY<last_ptsY) {
				this.PointsVertex[id_pts].translation(dx, dy);
				this.PointsVertex[next_pts].translation(0, dy);//只改变纵坐标，横坐标不变
				this.PointsVertex[last_pts].translation(dx, 0);//只改变横坐标，纵坐标不变
			}
			break;
		case 1:
			if(MoveX>opposit_ptsX&&MoveY<opposit_ptsY) {
				this.PointsVertex[id_pts].translation(dx, dy);
				this.PointsVertex[next_pts].translation(dx, 0);//只改变横坐标，纵坐标不变
				this.PointsVertex[last_pts].translation(0, dy);//只改变纵坐标，横坐标不变
			}
			break;
		case 2:
			if(MoveX>next_ptsX&&MoveY>last_ptsY) {
				this.PointsVertex[id_pts].translation(dx, dy);
				this.PointsVertex[next_pts].translation(0, dy);//只改变纵坐标，横坐标不变
				this.PointsVertex[last_pts].translation(dx, 0);//只改变横坐标，纵坐标不变
			}
			break;
		case 3:
			if(MoveX<opposit_ptsX&&MoveY>opposit_ptsY) {
				this.PointsVertex[id_pts].translation(dx, dy);
				this.PointsVertex[next_pts].translation(dx, 0);//只改变横坐标，纵坐标不变
				this.PointsVertex[last_pts].translation(0, dy);//只改变纵坐标，横坐标不变
			}
			break;
		default:
			break;
		}

		this.setPointsVertex(this.PointsVertex);
	}

	@Override
	public void Redimentionner(double width, double height) {
		// TODO Auto-generated method stub
		Point[] VetexPoints=this.getPointsVertex();
		double x3=VetexPoints[2].RendreX();//第三个点的横坐标
		double y3=VetexPoints[2].RendreY();//第三个点纵坐标
		double x1=VetexPoints[0].RendreX();//第一个点横坐标
		double y1=VetexPoints[0].RendreY();//第一个点纵坐标

		if(x3>x1&&y3>y1) {
			VetexPoints[1].setX(VetexPoints[0].RendreX()+width);
			VetexPoints[2].setX(VetexPoints[0].RendreX()+width);
			VetexPoints[2].setY(VetexPoints[0].RendreY()+height);
			VetexPoints[3].setY(VetexPoints[0].RendreY()+height);
		}else if(x3<x1&&y3>y1) {
			VetexPoints[1].setX(VetexPoints[0].RendreX()-width);
			VetexPoints[2].setX(VetexPoints[0].RendreX()-width);
			VetexPoints[2].setY(VetexPoints[0].RendreY()+height);
			VetexPoints[3].setY(VetexPoints[0].RendreY()+height);
		}else if(x3<x1&&y3<y1) {
			VetexPoints[1].setX(VetexPoints[0].RendreX()-width);
			VetexPoints[2].setX(VetexPoints[0].RendreX()-width);
			VetexPoints[2].setY(VetexPoints[0].RendreY()-height);
			VetexPoints[3].setY(VetexPoints[0].RendreY()-height);
		}else if(x3>x1&&y3<y1){
			VetexPoints[1].setX(VetexPoints[0].RendreX()+width);
			VetexPoints[2].setX(VetexPoints[0].RendreX()+width);
			VetexPoints[2].setY(VetexPoints[0].RendreY()-height);
			VetexPoints[3].setY(VetexPoints[0].RendreY()-height);
		}
		this.setPointsVertex(VetexPoints);
	}

	@Override
	public double getLargeur() {
		// TODO Auto-generated method stub
		Point[] VertexPoints=this.getPointsVertex();
		return Math.abs(VertexPoints[2].RendreX()-VertexPoints[0].RendreX());
	}

	@Override
	public double getHauteur() {
		// TODO Auto-generated method stub
		Point[] VertexPoints=this.getPointsVertex();
		return Math.abs(VertexPoints[2].RendreY()-VertexPoints[0].RendreY());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Rectangle";
	}


}
