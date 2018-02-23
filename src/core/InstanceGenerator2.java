package core;

import java.util.ArrayList;
import java.util.Random;

public class InstanceGenerator2 implements IInstanceGenerator {
		
	private Random rand;
	private ArrayList<Rectangle> rectangles;

	public Instance generate(int n, int lMin, int lMax, int lBox) {

		rectangles = new ArrayList<Rectangle>();
		rand = new Random();

		// lMin and lMax not used for now, but might be soon
		lMin = -1;
		lMax = -1;
		
		// Create initial square and add it to list
//		int squareFactor = rand.nextInt(8) + 2;
//		int lSquare = lBox * squareFactor;
//		int lSquare = lBox * 2;
		int lSquare = lBox;
//		System.out.println("A_square = " + lSquare * lSquare);
		Rectangle square = new Rectangle(new Point(0, 0), lSquare, lSquare);
		rectangles.add(square);
		
		if (n > square.getArea()) {
			n = square.getArea();
			System.out.println(String.format("n is too large for initial square. "
					+ "Limiting number of rectangles to maximum (area of square): %d", n));
		}
		
		for (int i = 0; i < n-1; i++) {
			Rectangle rect;
			// Choose Rectangle to split
			// Random number generation for rectangle to split
			if (i == 0) {
				rect = rectangles.get(0);
			} else {
				int rectIndex = rand.nextInt(rectangles.size() - 1);
				rect = rectangles.get(rectIndex);
			}
			
			// true = split at width (horizontal edge)
			boolean edgeSelected;
			
			// Check for edge lengths of 1
			if (rect.getHeight() == 1) {
				if (rect.getWidth() == 1) {
					// Cannot split the current rectangle further - advance to next one
					i--;
					continue;
				}
				// have to split at horizontal edge
				edgeSelected = true;
			}
			else if (rect.getWidth() == 1) {
				// have to split at vertical edge
				edgeSelected = false;
			} else {
				// Get a biased random float number to choose longer edge over shorter edge,
				// proportional to the ratio of the lengths
				float edgeRatio = (float)rect.getWidth() / (float)(rect.getWidth() + rect.getHeight());
				edgeSelected = getRandomBiasedBoolean(edgeRatio);
			}
				
			// Randomly choose position to split edge at
			float splitPos = rand.nextFloat();
			if (splitPos == 0) splitPos += 0.00001;
			if (splitPos == 1) splitPos -= 0.00001;
			if (splitPos >= 0.5) splitPos = 1 - splitPos;
			
			Rectangle rect1;
			Rectangle rect2;
			
			// Split the rectangle
			if (edgeSelected) {
				// Split at horizontal edge
				
				int width1 = (int) Math.ceil(rect.getWidth() * splitPos);
				int width2 = (int) Math.floor(rect.getWidth() * (1 - splitPos));
				
				// Calculate position of second rectangle
				int pos2x = rect.getPos().getX(); 
				int pos2y = rect.getPos().getY();
				Point pos2 = new Point(pos2x + width1, pos2y);

				// Generate two new rectangles
				rect1 = new Rectangle(rect.getPos(), width1, rect.getHeight());
				rect2 = new Rectangle(pos2, width2, rect.getHeight());
			} else {
				// Split at vertical edge
				
				int length1 = (int) Math.ceil(rect.getHeight() * splitPos);
				int length2 = (int) Math.floor(rect.getHeight() * (1 - splitPos));
				
				// Calculate position of second rectangle
				int pos2x = rect.getPos().getX(); 
				int pos2y = rect.getPos().getY();
				Point pos2 = new Point(pos2x, pos2y + length1);
				
				// Generate two new rectangles
				rect1 = new Rectangle(rect.getPos(), rect.getWidth(), length1);
				rect2 = new Rectangle(pos2, rect.getWidth(), length2);
			}
			
			
			// Add generated rectangles to list of rectangles
			rectangles.add(rect1);
			rectangles.add(rect2);
			
			// Delete split rectangle from list of rectangles
			rectangles.remove(rect);
		}
		
		
		// Small Unit Test on the go, to be outsourced into the test package containing all the UnitTests
//		int areaRects = 0;
//		for (Rectangle rect : rectangles) {
//			areaRects += rect.getArea();
//		}
//		System.out.println("DEBUG\t A_rects = " + areaRects);
		
		return new Instance(n, lBox, 2, rectangles);
	}
	
	/*
	 * Generates a biased random boolean value
	 */
	public boolean getRandomBiasedBoolean(float p) {
		return rand.nextFloat() < p;
	}

}
;