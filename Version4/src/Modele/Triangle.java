package Modele;
public class Triangle extends Polygone{

	public Triangle(int nbPointsVertex) {
		super(nbPointsVertex);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Accesseur(double x, double y) {
		// TODO Auto-generated method stub
		double x3=this.getPointsVertex()[2].RendreX();
		double x1=this.getPointsVertex()[0].RendreX();
		double y1=this.getPointsVertex()[0].RendreY();
		double x2=this.getPointsVertex()[1].RendreX();
		double y2=this.getPointsVertex()[1].RendreY();
		if(x2>x1) {
			if(x>=x1&&x<=x3) {
				return (y>=y1-Math.sqrt(3)*x+Math.sqrt(3)*x1)&&(y<=y1);
			}else if(x>x3&&x<=x2) {
				return (y>=Math.sqrt(3)*x+y2-Math.sqrt(3)*x2)&&(y<=y1);
			}else {
				return false;
			}
		}else {
			if(x>=x2&&x<=x3) {
				return (y>=y2-Math.sqrt(3)*x+Math.sqrt(3)*x2)&&(y<=y1);
			}else if(x>x3&&x<=x1) {
				return (y>=Math.sqrt(3)*x+y1-Math.sqrt(3)*x1)&&(y<=y1);
			}else {
				return false;
			}

		}

	}

	@Override
	public void transformation(double dx, double dy, int id_pts) {
		// TODO Auto-generated method stub

		double MoveX=this.PointsVertex[id_pts].RendreX();
		double MoveY=this.PointsVertex[id_pts].RendreY();

		switch(id_pts) {
		case 0:
			if(MoveX<this.PointsVertex[1].RendreX()&&MoveY>this.PointsVertex[2].RendreY()) {//不能在领节点的反方向位置，也不能超过顶点的位置
				this.PointsVertex[id_pts].translation(dx, dy);
				this.PointsVertex[1].translation(dx*(-1), dy);
			}
			break;
		case 1:
			if(MoveX>this.PointsVertex[0].RendreX()&&MoveY>this.PointsVertex[2].RendreY()) {
				this.PointsVertex[id_pts].translation(dx, dy);
				this.PointsVertex[0].translation(dx*(-1), dy);
			}
			break;
		case 2:
			if(MoveY<this.PointsVertex[0].RendreY()) {
				this.PointsVertex[id_pts].translation(0,dy);
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
		double Sqrt3=Math.sqrt(3);

		double x1=VetexPoints[0].RendreX();
		double x2=VetexPoints[1].RendreX();

		if(x2>x1) {
			if(height>width) {//哪个大听谁的
				//			double realWidth=(height/Sqrt3)*2;
				VetexPoints[0].setY(VetexPoints[2].RendreY()+height);
				VetexPoints[0].setX(VetexPoints[2].RendreX()-(height/Sqrt3));
				VetexPoints[1].setX(VetexPoints[2].RendreX()+(height/Sqrt3));
				VetexPoints[1].setY(VetexPoints[2].RendreY()+height);
			}else {//width 大以此为标准
				//				double realHeight=width/2*Sqrt3;
				VetexPoints[0].setY(VetexPoints[2].RendreY()+(Sqrt3/2)*width);
				VetexPoints[0].setX(VetexPoints[2].RendreX()-(width/2));
				VetexPoints[1].setX(VetexPoints[2].RendreX()+(width/2));
				VetexPoints[1].setY(VetexPoints[2].RendreY()+(Sqrt3/2)*width);
			}
		}else {
			if(height>width) {//哪个大听谁的
				//			double realWidth=(height/Sqrt3)*2;
				VetexPoints[1].setY(VetexPoints[2].RendreY()+height);
				VetexPoints[1].setX(VetexPoints[2].RendreX()-(height/Sqrt3));
				VetexPoints[0].setX(VetexPoints[2].RendreX()+(height/Sqrt3));
				VetexPoints[0].setY(VetexPoints[2].RendreY()+height);
			}else {//width 大以此为标准
				//				double realHeight=width/2*Sqrt3;
				VetexPoints[1].setY(VetexPoints[2].RendreY()+(Sqrt3/2)*width);
				VetexPoints[1].setX(VetexPoints[2].RendreX()-(width/2));
				VetexPoints[0].setX(VetexPoints[2].RendreX()+(width/2));
				VetexPoints[0].setY(VetexPoints[2].RendreY()+(Sqrt3/2)*width);
			}
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
		return Math.abs(this.getPointsVertex()[2].RendreY()-this.getPointsVertex()[0].RendreY());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Triangle";
	}
}
