package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
//fx:controller="application.MainController"
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import Modele.*;
import Controller.*;

public class MainController implements Initializable{


	@FXML
	private JFXButton Ligne,Rectangle,Ellipse,Triangle,Pinceau,Eraser;

	@FXML
	private MenuItem Infos;
	@FXML
	private RadioButton radiobtn;
	@FXML
	private TextField TextField;
	@FXML
	private BorderPane MainPane;
	@FXML
	private AnchorPane APane,FormesPane,ConfigurationPane;
	@FXML
	public ColorPicker setColor;
	@FXML
	public MenuItem Effacer,Enregistrer,Ouvrir,Annuler,Nouvelle;
	@FXML
	private ComboBox cbb;
	@FXML 
	private CheckBox Selectionner;
	@FXML
	private TextField grosseur_lineDroit,LargeurVal,HauteurVal;
	//	@FXML
	//	private AnchorPane APane1;
	//	@FXML
	//	private AnchorPane APane3;
	@FXML
	private Canvas mainCanvas;
	public static GraphicsContext gc;    
	public FabriqueModele fm;
	private FabriqueFigure ff;
	private Dessiner d;
	private Manipulation mp;
	private Enregistrer E;
	private boolean updateColor;
	//	private FabriqueModele fm;

	public double initX = 0, initY = 0, endX = 0, endY = 0;  
	private String FigureType="";
	Image imageRec = new Image(getClass().getResourceAsStream("/images/rectangle.png"),30,30,false,false);    
	Image imageEra = new Image(getClass().getResourceAsStream("/images/Eraser.png"),30,30,false,false);
	Image imageEll = new Image(getClass().getResourceAsStream("/images/ellipse2.png"),30,30,false,false);
	Image imageLine = new Image(getClass().getResourceAsStream("/images/minus.png"),30,30,false,false);
	Image imagePin = new Image(getClass().getResourceAsStream("/images/pencil.png"),25,25,false,false);
	Image imageTri = new Image(getClass().getResourceAsStream("/images/Triangular.png"),30,30,false,false);

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		gc = mainCanvas.getGraphicsContext2D();
		//choix();
		APane.setStyle("-fx-background-color: white");//css
		FormesPane.setStyle("-fx-background-color: white");
		ConfigurationPane.setStyle("-fx-background-color: white");
		Ligne.setGraphic(new ImageView(imageLine));
		Ligne.setStyle("-fx-background-color: white");
		Rectangle.setGraphic(new ImageView(imageRec));
		Rectangle.setStyle("-fx-background-color: white");
		Ellipse.setGraphic(new ImageView(imageEll));
		Ellipse.setStyle("-fx-background-color: white");
		Triangle.setGraphic(new ImageView(imageTri));
		Triangle.setStyle("-fx-background-color: white");
		Pinceau.setGraphic(new ImageView(imagePin));
		Pinceau.setStyle("-fx-background-color: white");
		Eraser.setGraphic(new ImageView(imageEra));
		Eraser.setStyle("-fx-background-color: white");
		//		mainCanvas.setStyle("-fx-background-color: white");
		setColor.setValue(Color.BLACK);
		ObservableList<String> List=FXCollections.observableArrayList("Option","Deplacer","Reforme");
		cbb.getItems().addAll(List);
		cbb.setPrefWidth(97);
		cbb.setValue("Option");//默认值为请做出选择--当前的情况没有任何操作图形的功能
		grosseur_lineDroit.setText("2");//默认值为2
		this.Selectionner.setSelected(false);
		LargeurVal.setText("0");
		HauteurVal.setText("0");
		FormesPane.setCursor(Cursor.HAND);
//		mainCanvas.setCursor(Cursor.CROSSHAIR);
	}

	public MainController() {
		mainCanvas=new Canvas(1000,1000);
		this.fm=new FabriqueModele();
		d=new Dessiner(this,fm);
		fm.construit(new Rectangle(4));
		ff=new FabriqueFigure(mainCanvas,fm,this.FigureType,this);
		mp=new Manipulation(fm,ff);
		E=new Enregistrer(fm,ff);
		updateColor=false;
	}

	public void CloseApp(ActionEvent event) {
		Platform.exit();
		System.exit(0);

	}


	public void choix(ActionEvent e) {

		mainCanvas.setOnMouseClicked(null);//选择画图形的功能的时候是为了防止与操作图形的功能出现重复的效果
		this.cbb.setValue("Option");//让操作功能回复到初始化的状态
		this.Selectionner.setSelected(false);
		this.MainPane.setOnKeyPressed(null);//没有选择的时候不允许通过键盘删除图形
		
		if(e.getSource()==Pinceau) {
			ImageCursor cursor = new ImageCursor(new Image("Images/pinceauIcon.png"));
			mainCanvas.setCursor(cursor);
			d.ChangetoEffaceur("dessin");
			mainCanvas.setOnMousePressed(eventP -> 

			{d.MousePressedF(eventP);}
					);
			mainCanvas.setOnMouseDragged(eventP ->
			d.MouseDrag(eventP)
					);
			mainCanvas.setOnMouseReleased(eventP->
			d.MouseReleased(eventP)
					);
		}else if(e.getSource()==Eraser) {
			ImageCursor cursor = new ImageCursor(new Image("Images/eraserIcon.png"));
			mainCanvas.setCursor(cursor);
			d.ChangetoEffaceur("Effaceur");
			mainCanvas.setOnMousePressed(eventP -> 
			{d.MousePressedF(eventP);}
					);
			mainCanvas.setOnMouseDragged(eventP ->
			d.MouseDrag(eventP)
					);
			mainCanvas.setOnMouseReleased(eventP->
			d.MouseReleased(eventP)
					);
		}else{
			mainCanvas.setCursor(Cursor.CROSSHAIR);
			if(e.getSource()==Ligne) {
				this.fm.construit(new LigneDroit(2));
				ff.setFigureType("LigneDroit");
			}
			else if(e.getSource()==Rectangle) {

				this.fm.construit(new Rectangle(4));
				ff.setFigureType("Rectangle");
			}
			else if(e.getSource()==Ellipse) {
				this.fm.construit(new Ellipse(2));
				ff.setFigureType("Ellipse");
			}
			else if(e.getSource()==Triangle) {
				this.fm.construit(new Triangle(3));
				ff.setFigureType("Triangle");
			}
			mainCanvas.setOnMousePressed(eventP -> 

			{ff.FirstPoint(eventP);}
					);
			mainCanvas.setOnMouseDragged(eventP ->
			ff.tempdrawFigure(eventP)
					);
			mainCanvas.setOnMouseReleased(eventP->
			ff.ReleasedSouris(eventP)
					);
		}

	}

	//函数用来修改一条直线模型的粗细
	public void setGrousseurDeLigne(ActionEvent e) {
		//		Color c=setColor.getValue();
		String str_grosseur=this.grosseur_lineDroit.getText();
		double int_grosseur=0;
		if(str_grosseur==null||str_grosseur.equals("")) {//当没有设置任何的grosseur值，我们默认为1
			int_grosseur=1.0;
			this.grosseur_lineDroit.setText("1");
		}
		else {
			int_grosseur=Double.parseDouble(str_grosseur);
		}
		if(int_grosseur>0) {//为正数，改变直线的粗细
			System.out.println(int_grosseur);
			this.fm.setLigneWidth(int_grosseur);
		}else {
			this.fm.setLigneWidth(1);
		}
	}

	public void setHeightWidthOfFigure(ActionEvent e) throws Exception {
		String Str_LargeurVal=this.LargeurVal.getText();
		String Str_HauteurVal=this.HauteurVal.getText();
		if(this.Selectionner.isSelected()) {//此时表示可以选中图形，因为我们需要选中图形然后改变图形的大小
			//如果没有设置，则无需改变
			if(Str_LargeurVal==null||Str_LargeurVal.equals("")) {
				this.LargeurVal.setText("0");
			}
			if(Str_HauteurVal==null||Str_HauteurVal.equals("")) {
				this.LargeurVal.setText("0");
			}
			double doub_LargeurVal=Double.parseDouble(Str_LargeurVal);
			double doub_HauteurVal=Double.parseDouble(Str_HauteurVal);
			//			FigureColoree fc=this.fm.getFigures().get(this.mp.getFormId());
			double WCourant=mp.getSelectedFormWidth();
			double HCourant=mp.getSelectedFormHeight();
			if(doub_LargeurVal==0) {
				if(doub_HauteurVal==0) {
					mp.Redimentionner(WCourant, HCourant);
				}else {
					mp.Redimentionner(WCourant, doub_HauteurVal);
				}

			}else {
				if(doub_HauteurVal==0) {
					mp.Redimentionner(doub_LargeurVal, HCourant);
				}else {
					mp.Redimentionner(doub_LargeurVal, doub_HauteurVal);
				}
			}
		}
		//图形没有处于被编辑的状态则不会触发函数
	}

	public double getGrosseur() {
		String str_grosseur=this.grosseur_lineDroit.getText();
		return Double.parseDouble(str_grosseur);
	}

	public void ManipulerFigure(ActionEvent e) {

		this.Selectionner.setSelected(true);
		mainCanvas.setOnMouseClicked(eventP->mp.ClickChoose(eventP));
		if(this.Selectionner.isSelected()) {
			String manipuleOption=cbb.getSelectionModel().getSelectedItem().toString();
			if(manipuleOption.equals("Deplacer")) {
				//
				mainCanvas.setCursor(Cursor.MOVE);
				mainCanvas.setOnMousePressed(eventP -> mp.Pressed(eventP,"Deplacer"));

				mainCanvas.setOnMouseReleased(eventP->mp.lacher());//松开即停止拖动

				mainCanvas.setOnMouseDragged(eventP->mp.dragFigure(eventP));//拖动图形
			}else if(manipuleOption.equals("Reforme")){
				mainCanvas.setCursor(null);
				mainCanvas.setOnMousePressed(eventP->{mp.Pressed(eventP, "Reforme");});

				mainCanvas.setOnMouseDragged(eventP->mp.ReFormeByDragging(eventP));

				mainCanvas.setOnMouseReleased(eventP->mp.lacher());//松开即停止拖动

			}
		}
	}

	//activer select checkBox
	public void Selected(ActionEvent e) {
		mainCanvas.setCursor(Cursor.HAND);
		if(this.Selectionner.isSelected()) {//当CheckBox被选择的时候，表示可以开始操作了
			mainCanvas.setOnMouseClicked(eventP->mp.ClickChoose(eventP));
			mainCanvas.setOnMousePressed(null);
			mainCanvas.setOnMouseReleased(null);
			mainCanvas.setOnMouseDragged(null);
			//			mainCanvas.setOnKeyTyped(keyEvent->{mp.DeleteParKeyBoard(keyEvent); System.out.println("fuck you");});
			this.MainPane.setOnKeyPressed(keyEvent->mp.DeleteParKeyBoard(keyEvent));
		}else {//没被选择图形时，没有任何对图形的动作
			mainCanvas.setOnMouseClicked(null);
			mainCanvas.setOnMousePressed(null);
			mainCanvas.setOnMouseReleased(null);
			mainCanvas.setOnMouseDragged(null);
			this.MainPane.setOnKeyPressed(null);

		}
	}

	//Choisir une couleur
	public void ChoisirColor(ActionEvent e) {
		Color c=setColor.getValue();
		FigureColoree figureCourant=this.fm.getFigureenCours();//锟斤拷玫锟角帮拷锟酵硷拷锟�
		figureCourant.changeColor(c);
		mp.upDateColor(c);
	}
	
	//EffcerLaPanel
	public void EffacerLaPanel(ActionEvent e) {
		gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
		d.ViderListeLignes();
		this.fm.ViderFigures();
		this.fm.ViderObject();
	}
	//锟斤拷取锟斤拷前锟斤拷锟斤拷色
	public Color getColor() {
		return this.setColor.getValue();
	}
	
	//保存部分:la partie de l'enregistrement
	public void SaveToFileJson(ActionEvent e) throws IOException {
		this.E.SaveFile();
	}
	//打开一个文件
	public void OpenFromFileJson(ActionEvent e)throws IOException {
		this.E.OpenFile();
	}
	//新建一个文件
	public void NewFile(ActionEvent e)throws IOException {
		this.E.Nouvelle();
	}
	
	public void UndoCanvas(ActionEvent e) {
		this.ff.undo();
		System.out.println("ca marche");
	}

	public void alertInformations(){
		String infos = "Ce projet est demande de creer une application de dessin. Cette application doit dessiner des forces geometriques pleines (ligne, rectangle, ellipse, triangle, etc).";
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		//alert.initOwner(primaryStage);
		alert.setTitle("A propos de cette application");
		alert.setHeaderText(null);
		alert.setContentText(infos);
		alert.showAndWait();
	}
}
