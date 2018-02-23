package gui;

import java.awt.Color;
import java.util.Random;

public class ColorGenerator {

	/**
	 * Returns a random pastel color
	 * @return
	 */
	public static Color randomPastelColor() {
		Random random = new Random();
		final float hue = random.nextFloat();
		// Saturation between 0.1 and 0.3
		final float saturation = (random.nextInt(2000) + 1000) / 10000f; //1.0 for brilliant, 0.0 for dull
		final float luminance = 0.75f; //1.0 for brighter, 0.0 for black
		final Color color = Color.getHSBColor(hue, saturation, luminance);
		return color;
	}
	
	/**
	 * Returns a random RGB color
	 * @return
	 */
	public static Color randomRGBColor() {
		return new Color((int)(Math.random() * 0x1000000));
	}
	
	/**
	 * Returns a random transparent RGB color
	 * @return
	 */
	public static Color randomTransparentRGBColor() {
		int r = (int)(Math.random() * 255);
		int g = (int)(Math.random() * 255);
		int b = (int)(Math.random() * 255);
		int alpha = 128;
		
		return new Color(r, g, b, alpha);
	}
}
