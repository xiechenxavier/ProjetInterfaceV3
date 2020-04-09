package Modele;
import javafx.scene.paint.Color;

public abstract class FigureColoree {
	public static final int TAILLE_CARRE_SELECTION = 6;//正方形边长
	private Color colorCorant;
	protected Point vertex;//构成本图形的主要顶点
	protected boolean EnSelection;
	protected double width;
	protected double height;
	public FigureColoree() {
		EnSelection=false;
	}
	
	public void setWidth(double width) {
		this.width=width;
	}
	public void setHeight(double height) {
		this.height=height;
	}
	
	public double getWidth() {
		return this.width;
	}
	public double getHeight() {
		return this.height;
	}

	//获取顶点，顶点也就是第一个点
	public double getVertexY() {
		// TODO Auto-generated method stub
		return this.vertex.RendreY();
	}

	public double getVertexX() {
		// TODO Auto-generated method stub
		return this.vertex.RendreX();
	}
	public void setVertex(Point p) {
		this.vertex=p;
	}

	public Color getColorCorant() {
		return colorCorant;
	}

	public void setColorCorant(Color colorCorant) {
		this.colorCorant = colorCorant;
	}

//	public Point getTab_points_mem() {
//		return vertex;
//	}
//
//	public void setTab_points_mem(Point tab_points_mem) {
//		this.tab_points_mem = tab_points_mem;
//	}

	public boolean isEnSelection() {
		return EnSelection;
	}

	public void setEnSelection(boolean enSelection) {
		EnSelection = enSelection;
	}
	@Override
	public String toString() {
		return "FigureColoree [vertex=" + vertex + ", width=" + width + ", height=" + height + "]";
	}
	public void changeColor(Color c) {
		// TODO Auto-generated method stub
		this.colorCorant=c;
	}

	
}
