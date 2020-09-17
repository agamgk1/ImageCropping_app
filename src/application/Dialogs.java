package application;

import javafx.scene.control.Alert;


public class Dialogs {
	
	public static void errorDialog() {
		Alert errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.setTitle("B³¹d!");
		errorAlert.setHeaderText("B³¹d programu!");
		errorAlert.setContentText("Obszar do skopiowania znajduje siê poza p³ótnem canvas");
		errorAlert.showAndWait();
	}
}
