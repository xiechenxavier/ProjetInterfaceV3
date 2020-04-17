package Controller;

import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Enregistrer {
	private FabriqueModele fm;
	private FabriqueFigure ff;
	private JSONArray lignesArray,figuresArray;//lignes与figures两个数组
	private boolean save_inAfile_Exist;
	private String file_Path;

	public Enregistrer(FabriqueModele fm,FabriqueFigure ff/*ArrayList<ArrayList<Point>> lignes,ArrayList<FigureColoree> figures*/) {
		this.fm=fm;
		this.ff=ff;
		lignesArray = new JSONArray();
		figuresArray=new JSONArray();//装所有的figure
		save_inAfile_Exist=false;
		file_Path="";
	}

	public void generateFileJson() {
		ArrayList<Object> mainCollection=this.fm.getMainCollection();
		ArrayList<ArrayList<Point>> lignes=new ArrayList<ArrayList<Point>>();//线
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
		//		ArrayList<FigureColoree> figures=this.fm.getFigures();//图

		for(ArrayList<Point> ligne:lignes) {//这个是装数组的数组
			JSONArray pointsArray = new JSONArray();
			for(Point p:ligne) {
				String formatJson="{'x':"+p.RendreX()+",'y':"+p.RendreY()+",'Color':"+p.getC()+",'grosseur':"+p.getG()+"}";
				JSONObject jsonObject2 = new JSONObject(formatJson);//一个点的数据
				pointsArray.put(jsonObject2);//以数组形式储存
			}
			lignesArray.put(pointsArray);
		}

		System.out.println("pointsFigure: ");
		System.out.println(lignesArray.toString());

		for(FigureColoree fc:figures) {

			Point[] points=((Polygone)fc).getPointsVertex();

			Color c=fc.getColorCorant();

			String type=((Polygone)fc).toString();//这个是哪一种图形

			JSONArray pointsArray = new JSONArray();

			for(Point p:points) {//所有顶点数据储存

				JSONObject jsonObject2 = new JSONObject();//一个点的数据
				jsonObject2.put("x", p.RendreX());
				jsonObject2.put("y", p.RendreY());
				jsonObject2.put("Color", p.getC());
				pointsArray.put(jsonObject2);//以数组形式储存
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
		File file = fileChooser.showOpenDialog(null);//这个就是打开的文件
		//接下来就要开始读取文件中的内容了
		char cbuf[] = new char[1000000000];
		if(file!=null) {
			InputStreamReader input =new InputStreamReader(new FileInputStream(file),"UTF-8");
			int len =input.read(cbuf);
			String text =new String(cbuf,0,len);
			//1.构造一个json对象,第一层
			JSONObject obj = new JSONObject(text.substring(text.indexOf("{")));   //过滤读出的utf-8前三个标签字节,从{开始读取

			//2.通过getXXX(String key)方法获取对应的值
			JSONArray lignesA=obj.getJSONArray("Lignes");
			JSONArray figuresA=obj.getJSONArray("Figures");

			for(int i=0;i<lignesA.length();i++) {

				JSONArray points=lignesA.getJSONArray(i);

				ArrayList<Point> currListPoint=new ArrayList<Point>();
				currListPoint.clear();//初始化清空所有数据
				for(int j=0;j<points.length();j++) {

					JSONObject pt=points.getJSONObject(j);
					Color cpt=Color.web(pt.getString("Color"));
					double ValX=pt.getDouble("x");//横坐标
					double ValY=pt.getDouble("y");//纵坐标
					double grosseur=pt.getDouble("grosseur");
					Point p1=new Point(ValX,ValY,cpt);
					p1.setG(grosseur);
					currListPoint.add(p1);
				}
				this.fm.getMainCollection().add(currListPoint);
			}

			//获取数组
			for(int i=0;i<figuresA.length();i++) {
				JSONObject figureCurr=figuresA.getJSONObject(i);

				Color cptfigure=Color.web(figureCurr.getString("ColorFigure"));

				String type=figureCurr.getString("type");//获取这个图形的类型，之后方便操作

				FigureColoree fc=this.ContruitFigure(type);//根据类型构造一个多边形对象

				JSONArray vertexPtArray=figureCurr.getJSONArray("VertexPoints");//所有顶点获取

				Point[] vertexPtArr=new Point[vertexPtArray.length()];//把顶点装到数组当中

				for(int j=0;j<vertexPtArray.length();j++) {

					JSONObject pt=vertexPtArray.getJSONObject(j);
					Color cpt=Color.web(pt.getString("Color"));
					double ValX=pt.getDouble("x");//横坐标
					double ValY=pt.getDouble("y");//纵坐标
					vertexPtArr[j]=new Point(ValX,ValY,cpt);
				}

				((Polygone)fc).setPointsVertex(vertexPtArr);

				fc.setColorCorant(cptfigure);

				this.fm.addFigure(fc);//更新这个figurelist集合然后在画板中显示
			}


			input.close();//关闭流

			this.ff.EffaceretDessiner();
		}
	}
	
	public void GenererImage(Canvas canvas) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
		fc.setTitle("保存图片");
		File img = fc.showSaveDialog(null);
		String type = "PNG";

		try {
			WritableImage writableImage = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
			canvas.snapshot(null, writableImage);
			RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
			if(img != null)
			ImageIO.write(renderedImage, type, img);

		} catch (IOException ex) {
		}
	}

	public void SaveFile() {
		this.generateFileJson();
		if(this.save_inAfile_Exist&&!this.file_Path.equals("")) {//如果文件存在则直接保存
			/* 写入Txt文件 */
			try {
				 RandomAccessFile writer = new RandomAccessFile(this.file_Path, "rw");
			        writer.seek(0);
			        
			        JSONObject totalData = new JSONObject();
					totalData.put("Lignes", this.lignesArray);
					totalData.put("Figures", this.figuresArray);

					String content=totalData.toString();

			        //写入中文的时候防止乱码
			        writer.writeUTF(content);
			        writer.close();
			        System.out.println("文件保存成功");

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
				file.createNewFile(); // 创建新文件
				BufferedWriter out;
				out = new BufferedWriter(new FileWriter(file));

				JSONObject totalData = new JSONObject();
				totalData.put("Lignes", this.lignesArray);
				totalData.put("Figures", this.figuresArray);

				String content=totalData.toString();

				out.write(content); 
				out.flush(); // 把缓存区内容压入文件
				out.close(); // 
			} catch (IOException|NullPointerException e) {
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