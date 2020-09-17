package application;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;

public class Main extends Application {

	private static final int CANVAS_HEIGHT = 350;
	private static final int CANVAS_WIDTH = 200;
	private static final int RECTHEIGHT = 41;
	private static final int RECTWIDTH = 41;
	private int count = -1;
	private WritableImage image2;
	private List<Image> images;
	private ImageView imageView;
	private Image image;
	private Canvas canvas;
	private PixelReader reader;
	private Color color;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// tworzenie elementow
			Button buttonClean = new Button("Czyœæ");
			Button buttonSelect = new Button("Wybierz");
			
			Label label = new Label("Wycinki w kolejnoœci malej¹cej œredniej");
			Label label1 = new Label("wartoœci skladowej czerwonej");
			//style przycisków 
			buttonClean.getStyleClass().add("my-button");
			buttonSelect.getStyleClass().add("my-button");
			label.getStyleClass().add("my-label");
			label1.getStyleClass().add("my-label");

			HBox root = new HBox(15);
			root.getStyleClass().add("my-root");
			root.setPadding(new Insets(15));
			
			HBox hbox = new HBox(15);
			VBox vbox = new VBox(10);
			VBox vbox1 = new VBox(5);
			FlowPane flowPane = new FlowPane(10, 10);
			images = new ArrayList<>();

			// rysowanie p³ótna oraz wczytanie obrazu do p³ótna
			canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
			canvas.setCursor(Cursor.CROSSHAIR);
			GraphicsContext gc = canvas.getGraphicsContext2D();
			image = new Image(getClass().getResourceAsStream("image.png"));
			gc.drawImage(image, 0, 0);

			// tworzenie PixelReadera
			reader = image.getPixelReader();
			
			// Akcja dla przycisku wybierz
			buttonSelect.setOnAction(event -> {
				
				//czyszczenie wszystkich pól
				images.clear();
				flowPane.getChildren().clear();
				count = -1;
				//wczytywanie obrazka
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Image file");
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
				File selectedFile = fileChooser.showOpenDialog(primaryStage);
				if(selectedFile!=null) {
					Image imageFile = convertFileToImage(selectedFile); 
					gc.drawImage(imageFile, 0, 0);
					reader = imageFile.getPixelReader(); 
				}
			});
			
			// Akcja dla przycisku czyœæ
			buttonClean.setOnAction(event -> {
				images.clear();
				flowPane.getChildren().clear();
				count = -1;
			});
			// Akcja dla myszy - kopiowanie fragmentow obrazka
			EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

				public void handle(MouseEvent event) {

					try {
						count++;
						if (count == 25) {
							images.remove(24);
							count -= 1;
						}
						image2 = new WritableImage(41, 41);
						PixelWriter writer = image2.getPixelWriter();
						
						int x = (int) event.getX();
						int y = (int) event.getY();
						color = reader.getColor(x, y);
						writer.setPixels(0, 0, 41, 41, reader, x - RECTWIDTH / 2, y - RECTHEIGHT / 2);

						flowPane.getChildren().clear();
						images.add(image2);

						Collections.sort(images, new Sorting());

						for (Image image4 : images) {
							imageView = new ImageView(image4);
							flowPane.getChildren().add(imageView);
						}

					} catch (Exception e) {
						Dialogs.errorDialog();
						count -= 1;
					}
				}
			};

			// dodnia akcji do p³ótna
			canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);

			// dodanie elementów do kontenerów
			hbox.getChildren().addAll(buttonClean,buttonSelect);
			vbox1.getChildren().addAll(label, label1);
			vbox.getChildren().addAll(hbox, vbox1, flowPane);
			root.getChildren().addAll(canvas, vbox);

			Scene scene = new Scene(root, 500, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Praca domowa 2a");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//metoda zamieniajaca (sprawdzajaca) file na Image 
	  private Image convertFileToImage(File imageFile) {
	        Image image = null;
	        try (FileInputStream fileInputStream = new FileInputStream(imageFile)){
	            image = new Image(fileInputStream);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return image;
	    }
}
