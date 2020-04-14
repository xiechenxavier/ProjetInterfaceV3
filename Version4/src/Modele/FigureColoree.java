package Modele;
import javafx.scene.paint.Color;

public abstract class FigureColoree {
	public static final int TAILLE_CARRE_SELECTION = 7;//ѡ��ʱС�����εı߳�
	private Color colorCorant;
	protected Point vertex;//���ɱ�ͼ�ε���Ҫ����
	protected boolean EnSelection;

	public FigureColoree() {
		EnSelection=false;
	}
	//��ȡ���㣬����Ҳ���ǵ�һ����
	public double getVertexY() {
		// TODO Auto-generated method stub
		return this.vertex.RendreY();
	}

	public double getVertexX() {
		// TODO Auto-generated method stub
		if(this.vertex!=null) {
		return this.vertex.RendreX();
		}else {
			throw new NullPointerException("");
		}
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

	public boolean isEnSelection() {
		return EnSelection;
	}

	public void setEnSelection(boolean enSelection) {
		EnSelection = enSelection;
	}
	@Override
	public String toString() {
		return "FigureColoree [vertex=" + vertex  + "]";
	}
	public void changeColor(Color c) {
		// TODO Auto-generated method stub
		this.colorCorant=c;
	}

	
}
