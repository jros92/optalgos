package core;

import java.util.ArrayList;
import java.util.Random;

public class InstanceGenerator1 implements IInstanceGenerator {
	
	private Random rand;
	private ArrayList<Rectangle> rectangles;
	
	public Instance generate(int n, int lMin, int lMax, int lBox) {

		rectangles = new ArrayList<Rectangle>();
				
		rand = new Random();
		
		for (int i = 0; i < n; i++) {
			// Random number generation for L and W
			int l = rand.nextInt(lMax - lMin + 1) + lMin;
			int w = rand.nextInt(lMax - lMin + 1) + lMin;
			
			// Create rectangle with length and width, without position
			Rectangle rect = new Rectangle(l, w, i);
			
			// Add generated rectangle to list of rectangles
			rectangles.add(rect);
		}
		
		return new Instance(n, lBox, 1, rectangles);
		
	}

}
