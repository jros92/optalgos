package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.*;


public class TestInstanceGenerator2 {

	@Test
	public void noRectanglesIntersect() {
		IInstanceGenerator iGen2 = new InstanceGenerator2();

		for (int i = 0; i < 100; ++i) {
			Instance instance = iGen2.generate(50, -1, -1, 20);

			for (Rectangle rect : instance.getRectangles()) {
				for (Rectangle otherRect : instance.getRectangles())
					if (!otherRect.equals(rect))
						assertTrue("One or more rectangles intersect in instance #" + (i+1), !rect.intersects(otherRect));
			}

		}
	}

//	@Test
//	public void TotalAreaEqualsSquareArea() {
//		IInstanceGenerator iGen2 = new InstanceGenerator2();
//
//        Instance inst1 = iGen2.generate(10, -1, -1, 5);
//        int areaRects = 0;
//		for (Rectangle rect : inst1.getRectangles()) {
//			areaRects += rect.getArea();
//		}
//
//		int areaSquare = 0; // How to get square area out of generate() function if its a random number and not equals the box length???
//
//        assertEquals("Total Area of all generated rectangles must equal area of initial square", areaSquare, areaRects);
//
//    }
//
//	@Test
//	public void NoRectangleWidthEdgeZero() {
//		IInstanceGenerator iGen = new InstanceGenerator2();
//
//        Instance instance = iGen.generate(10, -1, -1, 5);
//        boolean noRectangleEdgeZero = true;
//
//        for (Rectangle rect : instance.getRectangles()) {
//			noRectangleEdgeZero = noRectangleEdgeZero & (rect.getHeight() > 0) & (rect.getWidth() > 0);
//		}
//
//        assertTrue("No generated rectangle has an edge length of zero", noRectangleEdgeZero);
//
//	}
}
