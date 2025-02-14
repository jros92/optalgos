package gui;

import core.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class InstanceCanvas extends Canvas {
	
	private Instance instance;
	
	private int scaleFactor = 40;	// 1 length unit = <scaleFactor> pixels
	
	public InstanceCanvas(Instance instance, int dpi) {
		this.instance = instance;
		this.scaleFactor = (int) Math.round(dpi/2.4);
	}
	
	public void paint(Graphics graphics) {
		System.out.println("\nDrawing");
		
		this.setBackground(Color.white);
		
		int spacing = scaleFactor / 20;	// spacing between rectangles
		if (spacing < 2) spacing = 2; // ensure minimum spacing of 1 px
		
		Rectangle oldRect = new Rectangle(new Point(0, 0), 0, 0, -1);
		int rowMaxHeight = 0;
		int i = 1;
		
		
		for (Rectangle rect : instance.getRectangles()) {
			System.out.println("Rectangle " + i + ": " + rect.toString());
			
			graphics.setColor(ColorGenerator.randomPastelColor());
//			graphics.setColor(ColorGenerator.randomRGBColor());
			
			int canvasWidth = (int) this.getSize().getWidth();
			int canvasHeight = (int) this.getSize().getHeight();
			System.out.println("Canvas Width = " + canvasWidth + ", Height = " + canvasHeight);
			
			if (rect.getPos().getX() < 0 || rect.getPos().getY() < 0) {
				// Rectangle doesn't have valid position, print it next to previous one or open new row if not enough space to the right on canvas
				int newXpos = (oldRect.getPos().getX() + oldRect.getWidth());
//				int newYpos = (oldRect.getPos().getY() + oldRect.getLength());
				int newYpos = oldRect.getPos().getY();
				
				rect.setPos(newXpos, newYpos);
				
				int newYend = newYpos * scaleFactor + spacing + rect.getHeight() * scaleFactor;
				
				// check if enough space to the right of last rect on canvas
				int newXend = newXpos * scaleFactor + spacing + rect.getWidth() * scaleFactor;
				if (newXend > canvasWidth) {
					// open new row below highest/longest rectangle of previous row
					System.out.println("Opening new row.");
					
					newXpos = 0;
					newYpos = newYend + spacing;
					
//					rowMaxHeight = 0;
					
					newYend = newYpos * scaleFactor + spacing + rect.getHeight() * scaleFactor;
				}
				
				if (newYend > rowMaxHeight) rowMaxHeight = newYend;
				
				graphics.drawRect(newXpos * scaleFactor + spacing, newYpos * scaleFactor + spacing, rect.getWidth() * scaleFactor, rect.getHeight() * scaleFactor);
//				graphics.drawRect(0, 0, rect.getWidth() * scaleFactor, rect.getLength() * scaleFactor);
				System.out.println(String.format("Rectangle had default position (0,0) and was printed next to last rectangle at: (%d, %d)", newXpos, newYpos));
			} else {
				// Rectangle already has a valid position (usually because it was generated by instance generator 2, not 1)
				graphics.drawRect(rect.getPos().getX() * scaleFactor, rect.getPos().getY() * scaleFactor, rect.getWidth() * scaleFactor, rect.getHeight() * scaleFactor);
			}
			
			
			oldRect = rect;
			i++;
		}
	}
	

}
