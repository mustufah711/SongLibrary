package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.Song;
import view.confirmBox;
import view.listController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class SongLib extends Application {	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("/view/samples.fxml"));
			Scene scene = new Scene(root,589,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setTitle("Song Library");
			primaryStage.setScene(scene);
			primaryStage.show();	
			
			primaryStage.setOnCloseRequest(e -> {
				e.consume();
				closeProgram(primaryStage);
				
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

	private void closeProgram(Stage primaryStage)
	{
		Boolean answer = confirmBox.display("Do You Want To Exit?");
		
		if(answer)
			primaryStage.close();
	}
}