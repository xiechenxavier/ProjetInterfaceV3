package Modele;

public class LigneDroit extends Polygone{
	private double grosseur;
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

		double k1=(y2-y1)/(x2-x1);
		double b1=y1-(k1*x1);
		if(x1>x2) {
			return (x<=x1)&&(x>=x2)&&(y>=(k1*x+b1-5))&&(y<=(k1*x+b1+5)); 
		}else {
			return (x>=x1)&&(x<=x2)&&(y>=(k1*x+b1-5))&&(y<=(k1*x+b1+5));
		}
	}

	@Override
	public void transformation(double dx, double dy, int id_pts) {
		// TODO Auto-generated method stub
		this.PointsVertex[id_pts].translation(dx, dy);
	}
	//the current grosseur
	public double getGrosseur() {
		return this.grosseur;
	}
	//set Grosseur
	public void setGrosseur(double int_grosseur) {
		if(int_grosseur>0) {
			this.grosseur=int_grosseur;
		}
	}

	@Override
	public void Redimentionner(double width, double height) {
		// TODO Auto-generated method stub
		Point[] VetexPoints=this.getPointsVertex();
		VetexPoints[1].setX(VetexPoints[0].RendreX()+width);
		VetexPoints[1].setY(VetexPoints[0].RendreY()+height);
		this.setPointsVertex(VetexPoints);
	}

	@Override
	public double getLargeur() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHauteur() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "LigneDroit";
	}

}
