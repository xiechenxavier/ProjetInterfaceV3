package Modele;

public class Ellipse extends Polygone{

	public Ellipse(int nbPointsVertex) {
		super(nbPointsVertex);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Accesseur(double x, double y) {
		// TODO Auto-generated method stub
		/**/
		double x2=this.getPointsVertex()[1].RendreX();
		double y2=this.getPointsVertex()[1].RendreY();
		double x1=this.getPointsVertex()[0].RendreX();
		double y1=this.getPointsVertex()[0].RendreY();
		if(x2>=x1&&y2>=y1) {
			return (x>=x1)&&(x<=x2)&&(y>=y1)&&(y<=y2);
		}else if(x2<=x1&&y2>=y1){
			return (x>=x2)&&(x<=x1)&&(y>=y1)&&(y<=y2);
		}else if(x2>=x1&&y2<y1) {
			return (x>=x1)&&(x<=x2)&&(y>=y2)&&(y<=y1);
		}else {
			return (x>=x2)&&(x<=x1)&&(y>=y2)&&(y<=y1);
		}

	}
	//标准椭圆范围之内
	public boolean EllipseAccesseur(double x,double y) {
		double x1=this.getPointsVertex()[0].RendreX();
		double y1=this.getPointsVertex()[0].RendreY();
		double x2=this.getPointsVertex()[1].RendreX();
		double y2=this.getPointsVertex()[1].RendreY();
		double centerX=(x1+x2)/2;
		double centerY=(y1+y2)/2;	
		double realX=Math.abs(x-centerX);
		double realY=Math.abs(y-centerY);

		double a=Math.abs(x2-x1)/2;
		double b=Math.abs(y2-y1)/2;
		return ((realX*realX)/(a*a)+(realY*realY)/(b*b))<=1;
	}

	@Override
	public void transformation(double dx, double dy, int id_pts) {
		// TODO Auto-generated method stub
		double MoveX=this.PointsVertex[id_pts].RendreX();
		double MoveY=this.PointsVertex[id_pts].RendreY();//选中的点
		if(id_pts==0) {
			if(MoveX<this.PointsVertex[1].RendreX()&&MoveY<this.PointsVertex[1].RendreY()) {
				this.PointsVertex[id_pts].translation(dx, dy);
			}
		}else if(id_pts==1){
			if(MoveX>this.PointsVertex[0].RendreX()&&MoveY>this.PointsVertex[0].RendreY()) {
				this.PointsVertex[id_pts].translation(dx, dy);
			}
		}
		this.setPointsVertex(this.PointsVertex);
	}


	@Override
	public void Redimentionner(double width, double height) {
		// TODO Auto-generated method stub
		Point[] VetexPoints=this.getPointsVertex();
		double x2=VetexPoints[1].RendreX();//第三个点的横坐标
		double y2=VetexPoints[1].RendreY();//第三个点纵坐标
		double x1=VetexPoints[0].RendreX();//第一个点横坐标
		double y1=VetexPoints[0].RendreY();//第一个点纵坐标
		if(x2>x1&&y2>y1) {
			VetexPoints[1].setX(VetexPoints[0].RendreX()+width);
			VetexPoints[1].setY(VetexPoints[0].RendreY()+height);
		}else if(x2<x1&&y2>y1) {
			VetexPoints[1].setX(VetexPoints[0].RendreX()-width);
			VetexPoints[1].setY(VetexPoints[0].RendreY()+height);
		}else if(x2<x1&&y2<y1) {
			VetexPoints[1].setX(VetexPoints[0].RendreX()-width);
			VetexPoints[1].setY(VetexPoints[0].RendreY()-height);
		}else {
			VetexPoints[1].setX(VetexPoints[0].RendreX()+width);
			VetexPoints[1].setY(VetexPoints[0].RendreY()-height);
		}
		this.setPointsVertex(VetexPoints);
	}

	@Override
	public double getLargeur() {
		// TODO Auto-generated method stub
		return Math.abs(this.getPointsVertex()[1].RendreX()-this.getPointsVertex()[0].RendreX());
	}

	@Override
	public double getHauteur() {
		// TODO Auto-generated method stub
		return Math.abs(this.getPointsVertex()[1].RendreY()-this.getPointsVertex()[0].RendreY());	
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Ellipse";
	}

}
