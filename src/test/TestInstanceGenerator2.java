package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.*;


public class TestInstanceGenerator2 {

	@Test
	public void TotalAreaEqualsSquareArea() {
		IInstanceGenerator iGen2 = new InstanceGenerator2();

        Instance inst1 = iGen2.generate(10, -1, -1, 5);
        int areaRects = 0;
		for (Rectangle rect : inst1.getRectangles()) {
			areaRects += rect.getArea();
		}
        
		int areaSquare = 0; // How to get square area out of generate() function if its a random number and not equals the box length???
		
        assertEquals("Total Area of all generated rectangles must equal area of initial square", areaSquare, areaRects);

    }
	
	@Test
	public void NoRectangleWidthEdgeZero() {
		IInstanceGenerator iGen = new InstanceGenerator2();

        Instance instance = iGen.generate(10, -1, -1, 5);
        boolean noRectangleEdgeZero = true;
        
        for (Rectangle rect : instance.getRectangles()) {
			noRectangleEdgeZero = noRectangleEdgeZero & (rect.getHeight() > 0) & (rect.getWidth() > 0);
		}
        
        assertTrue("No generated rectangle has an edge length of zero", noRectangleEdgeZero);
	
	}
}
