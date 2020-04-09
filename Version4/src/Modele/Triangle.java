package Modele;
public class Triangle extends Polygone{

	public Triangle(int nbPointsVertex) {
		super(nbPointsVertex);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Accesseur(double x, double y) {
		// TODO Auto-generated method stub

//		double border=Math.abs(this.getPointsVertex()[1].RendreX()-this.getPointsVertex()[1].RendreX());//±ß³¤
//		double y3=this.getPointsVertex()[2].RendreY();
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
}
