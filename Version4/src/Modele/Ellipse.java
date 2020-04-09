package Modele;

public class Ellipse extends Polygone{

	public Ellipse(int nbPointsVertex) {
		super(nbPointsVertex);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Accesseur(double x, double y) {
		// TODO Auto-generated method stub
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

}
