package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.control.TextField;
import Modele.*;
import Controller.*;

public class MainController implements Initializable{


	@FXML
	private JFXButton Ligne,Rectangle,Ellipse,Triangle,Pinceau,Circle;
	
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
	public MenuItem Effacer;
	@FXML
	private ComboBox cbb;
	@FXML 
	private CheckBox Selectionner;
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
	private boolean updateColor;
	//	private FabriqueModele fm;

	public double initX = 0, initY = 0, endX = 0, endY = 0;  
	private String FigureType="";
	Image imageRec = new Image(getClass().getResourceAsStream("/images/rectangle.png"),30,30,false,false);    
	Image imageCir = new Image(getClass().getResourceAsStream("/images/circle1.png"),30,30,false,false);
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
		Circle.setGraphic(new ImageView(imageCir));
		Circle.setStyle("-fx-background-color: white");
		//		mainCanvas.setStyle("-fx-background-color: white");
		setColor.setValue(Color.BLACK);
		ObservableList<String> List=FXCollections.observableArrayList("Option","Deplacer","ReSize","ͻ��","����","setColor");
		cbb.getItems().addAll(List);
		cbb.setPrefWidth(97);
	}

	public MainController() {
		mainCanvas=new Canvas(1000,1000);
		d=new Dessiner(this);
		this.fm=new FabriqueModele(d);
		fm.construit(new Rectangle(4));
		ff=new FabriqueFigure(mainCanvas,fm,this.FigureType,this);
		mp=new Manipulation(fm,ff);
		updateColor=false;
	}

	public void CloseApp(ActionEvent event) {
		Platform.exit();
		System.exit(0);

	}


	public void choix(ActionEvent e) {

		if(e.getSource()==Pinceau) {
			mainCanvas.setOnMousePressed(eventP -> 

			{d.MousePressedF(eventP);}
					);
			mainCanvas.setOnMouseDragged(eventP ->
			d.MouseDrag(eventP)
					);
			mainCanvas.setOnMouseReleased(eventP->
			d.MouseReleased(eventP)
					);
		}else {
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
			ff.tempdrawRectangle(eventP)
					);
			mainCanvas.setOnMouseReleased(eventP->
			ff.ReleasedSouris(eventP)
					);
		}

	}

	public void ManipulerFigure(ActionEvent e) {
		String manipuleOption=cbb.getSelectionModel().getSelectedItem().toString();

		if(manipuleOption.equals("Deplacer")) {
			//
			mainCanvas.setOnMouseClicked(eventP->mp.ClickChoose(eventP));
			mainCanvas.setOnMousePressed(eventP -> mp.Pressed(eventP));
			mainCanvas.setOnMouseReleased(eventP->mp.lacher());
			//
			mainCanvas.setOnMouseDragged(eventP->mp.dragFigure(eventP));
		}
	}

	public void Selected(ActionEvent e) {
//		if(this.Selectionner.isSelected()) {
//			mainCanvas.setOnMouseClicked(eventP->mp.ClickChoose(eventP));
//			mainCanvas.setOnMousePressed(null);
//			mainCanvas.setOnMouseReleased(null);
//			mainCanvas.setOnMouseDragged(null);
////			this.mp.Updatable(true);
//		}
	}
	
	public void ChoisirColor(ActionEvent e) {
			Color c=setColor.getValue();
			FigureColoree figureCourant=this.fm.getFigureenCours();//��õ�ǰ��ͼ��
			figureCourant.changeColor(c);
			mp.upDateColor(c);
	}
	public void EffacerLaPanel(ActionEvent e) {
		gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
		d.ViderListeLignes();
		this.fm.ViderFigures();
	}
	//��ȡ��ǰ����ɫ
	public Color getColor() {
		return this.setColor.getValue();
	}


	public void alertInformations(){
		String infos = "Ce projet est demand�� de cr��er une application de dessin. Cette application doit dessiner des forces g��om��triques pleines (ligne, rectangle, ellipse, triangle, etc).";
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		//alert.initOwner(primaryStage);
		alert.setTitle("A propos de cette application");
		alert.setHeaderText(null);
		alert.setContentText(infos);
		alert.showAndWait();
	}
}
