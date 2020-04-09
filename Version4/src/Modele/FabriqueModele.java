package Modele;
import java.util.ArrayList;

import Controller.Dessiner;


public class FabriqueModele {
	ArrayList<FigureColoree> figures;//figure集合
	Dessiner d;//画图功能类对象
	private FigureColoree figureenCours;//当前处理的图形


	public FabriqueModele(Dessiner d) {
		figures=new ArrayList<FigureColoree>();
		this.d=d;

	}

	public void addFigure(FigureColoree f) {
		this.figures.add(f);
	}
	public void construit(FigureColoree f) {
		this.figureenCours=f;
	}

	public FigureColoree getFigureenCours() {
		return this.figureenCours;
	}
	public ArrayList<FigureColoree> getFigures(){
		return this.figures;
	}
	public void ViderFigures() {
		this.figures=new ArrayList<FigureColoree>();
	}
	public Dessiner getDessiner() {
		return this.d;
	}
	public void DeSelectionnerAll() {
		for(FigureColoree f:figures) {
			f.setEnSelection(false);
		}
	}
}
