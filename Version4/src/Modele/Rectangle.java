package Modele;

import java.util.Arrays;

public class Rectangle extends Polygone{

	public Rectangle(int nbPointsVertex) {
		super(nbPointsVertex);
		// TODO Auto-generated constructor stub
	}

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
}
