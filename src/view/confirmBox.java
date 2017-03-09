package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class confirmBox {
	
	static boolean result;
	
	public static boolean display(String text){
		
		Stage window = new Stage();
		
		//window.initModality(Modality.APPLICATION_MODAL);
		
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		
		//Just creating the set of text inputted and displaying it
		Label label = new Label();
		label.setText(text);
		
		yesButton.setOnAction(e ->{
			result = true;
			window.close();
		});
		
		noButton.setOnAction(e -> {
			result = false;
			window.close();
		});
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, yesButton, noButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout, 300, 200);
		window.setScene(scene);
		window.showAndWait();
		
		return result;
	}	
}
