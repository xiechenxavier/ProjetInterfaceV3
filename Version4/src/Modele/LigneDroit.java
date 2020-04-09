package Modele;

public class LigneDroit extends Polygone{

	public LigneDroit(int nbPointsVertex) {
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
		double k=(y2-y1)/(x2-x1);
		double b=y1-(k*x1);
		if(x1>x2) {
			return (x<=x1)&&(x>=x2)&&(y==(k*x+b)); 
		}else {
			return (x>=x1)&&(x<=x2)&&(y==(k*x+b));
		}
	}

}
