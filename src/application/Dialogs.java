package application;

import javafx.scene.control.Alert;


public class Dialogs {
	
	public static void errorDialog() {
		Alert errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.setTitle("B��d!");
		errorAlert.setHeaderText("B��d programu!");
		errorAlert.setContentText("Obszar do skopiowania znajduje si� poza p��tnem canvas");
		errorAlert.showAndWait();
	}
}
