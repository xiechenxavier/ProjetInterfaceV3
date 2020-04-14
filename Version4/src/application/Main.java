package application;
	
import java.net.URL;

import Controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			URL fxmlURL=getClass().getResource("Panel.fxml");
			FXMLLoader fxmlLoader=new FXMLLoader(fxmlURL);
			Pane root=fxmlLoader.load();
//			BorderPane root = new BorderPane();
			MainController mc=new MainController();
//			mc.DeleteFigure();
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
