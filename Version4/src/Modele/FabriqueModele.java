package Modele;
import java.util.ArrayList;

import Controller.Dessiner;


public class FabriqueModele {
	ArrayList<FigureColoree> figures;//figure����
//	Dessiner d;//��ͼ���������
	private FigureColoree figureenCours;//��ǰ�����ͼ��
	private ArrayList<Object> mainCollection;


	public FabriqueModele(/*Dessiner d*/) {
		figures=new ArrayList<FigureColoree>();
//		this.d=d;
		mainCollection=new ArrayList<Object>();
	}

	public void addFigure(FigureColoree f) {
		this.figures.add(f);
		this.AddObjectCollection(f);
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
//	public Dessiner getDessiner() {
//		return this.d;
//	}
	
	public void DeSelectionnerAll() {
		for(FigureColoree f:figures) {
			f.setEnSelection(false);
		}
	}
	public void setLigneWidth(double gross) {
		if(this.figureenCours instanceof LigneDroit) {
			((LigneDroit)figureenCours).setGrosseur(gross);
		}
	}
	//����д��һ���µ�List de figures
	public void setFigures(ArrayList<FigureColoree> figures) {
		this.figures=figures;
	}
	
	public ArrayList<Object> getMainCollection(){
		return this.mainCollection;
	}
	public void AddObjectCollection(Object o) {
		this.mainCollection.add(o);
	}
}
