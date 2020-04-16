package Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import Modele.Ellipse;
import Modele.FabriqueModele;
import Modele.FigureColoree;
import Modele.LigneDroit;
import Modele.Point;
import Modele.Polygone;
import Modele.Rectangle;
import Modele.Triangle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Enregistrer {
	private FabriqueModele fm;
	private FabriqueFigure ff;
	private JSONArray lignesArray,figuresArray;//lignes��figures��������
	private boolean save_inAfile_Exist;
	private String file_Path;

	public Enregistrer(FabriqueModele fm,FabriqueFigure ff/*ArrayList<ArrayList<Point>> lignes,ArrayList<FigureColoree> figures*/) {
		this.fm=fm;
		this.ff=ff;
		lignesArray = new JSONArray();
		figuresArray=new JSONArray();//װ���е�figure
		save_inAfile_Exist=false;
		file_Path="";
	}

	public void generateFileJson() {
		ArrayList<Object> mainCollection=this.fm.getMainCollection();
		ArrayList<ArrayList<Point>> lignes=new ArrayList<ArrayList<Point>>();//��
		ArrayList<FigureColoree> figures=new ArrayList<FigureColoree>();

		for(Object o:mainCollection) {
			if(o instanceof ArrayList<?>) {
				lignes.add((ArrayList<Point>)o);
			}else if(o instanceof FigureColoree) {
				figures.add((FigureColoree)o);
			}else {
				throw new NullPointerException("null figures");
			}
		}
		//		ArrayList<FigureColoree> figures=this.fm.getFigures();//ͼ

		for(ArrayList<Point> ligne:lignes) {//�����װ���������
			JSONArray pointsArray = new JSONArray();
			for(Point p:ligne) {
				String formatJson="{'x':"+p.RendreX()+",'y':"+p.RendreY()+",'Color':"+p.getC()+"}";
				JSONObject jsonObject2 = new JSONObject(formatJson);//һ���������
				pointsArray.put(jsonObject2);//��������ʽ����
			}
			lignesArray.put(pointsArray);
		}

		System.out.println("pointsFigure: ");
		System.out.println(lignesArray.toString());

		for(FigureColoree fc:figures) {

			Point[] points=((Polygone)fc).getPointsVertex();

			Color c=fc.getColorCorant();

			String type=((Polygone)fc).toString();//�������һ��ͼ��

			JSONArray pointsArray = new JSONArray();

			for(Point p:points) {//���ж������ݴ���

				JSONObject jsonObject2 = new JSONObject();//һ���������
				jsonObject2.put("x", p.RendreX());
				jsonObject2.put("y", p.RendreY());
				jsonObject2.put("Color", p.getC());
				pointsArray.put(jsonObject2);//��������ʽ����
			}

			String formatJsonFigure="{'type':"+type+",'VertexPoints':"+pointsArray+",'ColorFigure':"+c+"}";
			JSONObject jSONFigureObject=new JSONObject(formatJsonFigure);
			figuresArray.put(jSONFigureObject);

		}
		System.out.println("formatJsonFigure:");
		System.out.println(figuresArray.toString());
	}

	public void OpenFile() throws IOException {

		//		ArrayList<ArrayList<Point>> lignesList=new ArrayList<ArrayList<Point>>();
		//		ArrayList<FigureColoree> figuresList=new ArrayList<FigureColoree>();

		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(null);//������Ǵ򿪵��ļ�
		//��������Ҫ��ʼ��ȡ�ļ��е�������
		char cbuf[] = new char[1000000000];
		if(file!=null) {
			InputStreamReader input =new InputStreamReader(new FileInputStream(file),"UTF-8");
			int len =input.read(cbuf);
			String text =new String(cbuf,0,len);
			//1.����һ��json����,��һ��
			JSONObject obj = new JSONObject(text.substring(text.indexOf("{")));   //���˶�����utf-8ǰ������ǩ�ֽ�,��{��ʼ��ȡ

			//2.ͨ��getXXX(String key)������ȡ��Ӧ��ֵ
			JSONArray lignesA=obj.getJSONArray("Lignes");
			JSONArray figuresA=obj.getJSONArray("Figures");

			for(int i=0;i<lignesA.length();i++) {

				JSONArray points=lignesA.getJSONArray(i);

				ArrayList<Point> currListPoint=new ArrayList<Point>();
				currListPoint.clear();//��ʼ�������������
				for(int j=0;j<points.length();j++) {

					JSONObject pt=points.getJSONObject(j);
					Color cpt=Color.web(pt.getString("Color"));
					double ValX=pt.getDouble("x");//������
					double ValY=pt.getDouble("y");//������
					currListPoint.add(new Point(ValX,ValY,cpt));
				}
				this.fm.getMainCollection().add(currListPoint);
			}

			//��ȡ����
			for(int i=0;i<figuresA.length();i++) {
				JSONObject figureCurr=figuresA.getJSONObject(i);

				Color cptfigure=Color.web(figureCurr.getString("ColorFigure"));

				String type=figureCurr.getString("type");//��ȡ���ͼ�ε����ͣ�֮�󷽱����

				FigureColoree fc=this.ContruitFigure(type);//�������͹���һ������ζ���

				JSONArray vertexPtArray=figureCurr.getJSONArray("VertexPoints");//���ж����ȡ

				Point[] vertexPtArr=new Point[vertexPtArray.length()];//�Ѷ���װ�����鵱��

				for(int j=0;j<vertexPtArray.length();j++) {

					JSONObject pt=vertexPtArray.getJSONObject(j);
					Color cpt=Color.web(pt.getString("Color"));
					double ValX=pt.getDouble("x");//������
					double ValY=pt.getDouble("y");//������
					vertexPtArr[j]=new Point(ValX,ValY,cpt);
				}

				((Polygone)fc).setPointsVertex(vertexPtArr);

				fc.setColorCorant(cptfigure);

				this.fm.addFigure(fc);//�������figurelist����Ȼ���ڻ�������ʾ
			}


			input.close();//�ر���

			this.ff.EffaceretDessiner();
		}
	}

	public void SaveFile() {
		this.generateFileJson();
		if(this.save_inAfile_Exist&&!this.file_Path.equals("")) {//����ļ�������ֱ�ӱ���
			/* д��Txt�ļ� */
			try {
				 RandomAccessFile writer = new RandomAccessFile(this.file_Path, "rw");
			        writer.seek(0);
			        
			        JSONObject totalData = new JSONObject();
					totalData.put("Lignes", this.lignesArray);
					totalData.put("Figures", this.figuresArray);

					String content=totalData.toString();

			        //д�����ĵ�ʱ���ֹ����
			        writer.writeUTF(content);
			        writer.close();
			        System.out.println("�ļ�����ɹ�");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}else {

			FileChooser fileChooser1 = new FileChooser();
			fileChooser1.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
			fileChooser1.setTitle("Save Image");
			//		System.out.println(pic.getId());
			File file = fileChooser1.showSaveDialog(null);//the file to save, we save it on the path where we chose
			try {
				file.createNewFile(); // �������ļ�
				BufferedWriter out;
				out = new BufferedWriter(new FileWriter(file));

				JSONObject totalData = new JSONObject();
				totalData.put("Lignes", this.lignesArray);
				totalData.put("Figures", this.figuresArray);

				String content=totalData.toString();

				out.write(content); 
				out.flush(); // �ѻ���������ѹ���ļ�
				out.close(); // 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void Nouvelle() {
		FileChooser fileChooser1 = new FileChooser();
		fileChooser1.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
		fileChooser1.setTitle("Nouveau Image");
		File file = fileChooser1.showSaveDialog(null);//the file to save, we save it on the path where we chose
		this.file_Path=file.getPath();
		save_inAfile_Exist=true;
	}


	public FigureColoree ContruitFigure(String type) {
		FigureColoree fc=null;
		switch(type) {
		case "Rectangle":
			fc=new Rectangle(4);
			break;
		case "Triangle":
			fc=new Triangle(3);
			break;
		case "LigneDroit":
			fc=new LigneDroit(2);
			break;
		case "Ellipse":
			fc=new Ellipse(2);
			break;
		default:
			break;
		}
		return fc;
	}
	public void keyEvent(KeyEvent e) {
		KeyCombination KeyNew = new KeyCodeCombination(KeyCode.N,KeyCombination.CONTROL_DOWN);
		KeyCombination KeySave = new KeyCodeCombination(KeyCode.S,KeyCombination.CONTROL_DOWN);
		KeyCombination KeyOpen = new KeyCodeCombination(KeyCode.O,KeyCombination.CONTROL_DOWN);

		if(KeyNew.match(e)) {
			System.out.println("Ctrl+N preseed");
			this.Nouvelle();
		}
		if(KeySave.match(e)) {
			System.out.println("Ctrl+S preseed");
			this.SaveFile();
		}
		if(KeyOpen.match(e)) {
			System.out.println("Ctrl+O preseed");
			try {
				this.OpenFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}