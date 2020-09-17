package application;

import java.util.Comparator;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Sorting implements Comparator<Image> {

	@Override
	public int compare(Image i1, Image i2) {
		return getRedValue(i1) - getRedValue(i2);
	}

	public int getRedValue(Image image) {
		
		 Double redVal;
		 int redInt;
		 int average;
		 int total = 0;
		 Color color;

		PixelReader reader = image.getPixelReader();
		for (int x = 0; x < (int) image.getHeight(); x++) {
			for (int y = 0; y < (int) image.getWidth(); y++) {
				color = reader.getColor(x, y);
				redVal = color.getRed() * 1000;
				redInt = redVal.intValue();
				total += redInt;

			}
		}
		average = total / (41 * 41);

		return average;
	}

}
