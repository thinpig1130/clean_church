package org.church0691.view;
	
import org.church0691.dao.MemberDAO;

import common.MyUtils;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		MemberDAO dao = new MemberDAO();
		try {
			int cnt = dao.countAll(MyUtils.getConn());
			Scene scene;
			if(cnt == 0) {
				BorderPane startScreen = (BorderPane) FXMLLoader.load(getClass().getResource("StartSystem.fxml"));
				scene = new Scene(startScreen);
			}else {
				BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
				scene = new Scene(root);
			}			
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
