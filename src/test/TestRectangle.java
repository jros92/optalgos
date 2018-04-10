package test;

import core.Point;
import core.Rectangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestRectangle {

    @Test
    public void testIntersectionAreaAndOverlap() {
        /* Not overlapping */
        Rectangle r1 = new Rectangle(new Point(0,3),2,4,0);
        Rectangle r2 = new Rectangle(new Point(2,3),5,4,1);

        assertEquals("Intersection area not correct", 0, r1.intersection(r2));
        assertEquals("Overlap percentage not correct", 0, r1.overlapsBy(r2), 0.001);

         /* Completely overlapping */
        r1 = new Rectangle(new Point(0,3),2,4,0);
        r2 = new Rectangle(new Point(0,3),2,4,1);

        assertEquals("Intersection area not correct", 8, r1.intersection(r2));
        assertEquals("Overlap percentage not correct", 1, r1.overlapsBy(r2), 0.001);

        /* */
        r1 = new Rectangle(new Point(2,0),2,6,0);
        r2 = new Rectangle(new Point(0,1),4,4,1);

        assertEquals("Intersection area not correct", 8, r1.intersection(r2));

        /* */
        r1 = new Rectangle(new Point(0,3),2,4,0);
        r2 = new Rectangle(new Point(1,2),5,4,1);

        assertEquals("Intersection area not correct", 3, r1.intersection(r2));

        /* */
        r1 = new Rectangle(new Point(1,1),5,5,0);
        r2 = new Rectangle(new Point(0,0),7,7,1);

        assertEquals("Intersection area not correct", 25, r1.intersection(r2));
        assertEquals("Overlap percentage not correct", 25.0/49.0, r1.overlapsBy(r2), 0.001);

    }
}
