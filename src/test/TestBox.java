package test;

import core.Box;
import core.CoM;
import core.Rectangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestBox {

    @Test
    public void testGetCenterOfMass() {
        /* Box 1 */
        Box box = new Box(6, 0);
        Rectangle rect = new Rectangle(2,2, 0);
        box.addRectangleAtOrigin(rect);
        rect.setPos(2,2);

        CoM com = box.getCenterOfMass();
        CoM comNorm = box.getCenterOfMassNormalized();

        boolean test1 = (com.getX() == 3) && (com.getY() == 3);
        boolean test2 = (comNorm.getX() == 0.5) && (comNorm.getY() == 0.5);

        assertTrue("Absolute center of mass is not correct in box " + box.getId(), test1);
        assertTrue("Relative center of mass is not correct in box " + box.getId(), test2);

        /* Box 2 */
        box = new Box(6, 1);
        rect = new Rectangle(3,3, 1);
        box.addRectangleAtOrigin(rect);

        com = box.getCenterOfMass();
        comNorm = box.getCenterOfMassNormalized();

        test1 = (com.getX() == 1.5) && (com.getY() == 1.5);
        test2 = (comNorm.getX() == 0.25) && (comNorm.getY() == 0.25);

        assertTrue("Absolute center of mass is not correct in box " + box.getId(), test1);
        assertTrue("Relative center of mass is not correct in box " + box.getId(), test2);

        /* Box 3 */
        box = new Box(6, 2);
        rect = new Rectangle(6,3, 2);
        box.addRectangleAtOrigin(rect);

        com = box.getCenterOfMass();
        comNorm = box.getCenterOfMassNormalized();

        test1 = (com.getX() == 3) && (com.getY() == 1.5);
        test2 = (comNorm.getX() == 0.5) && (comNorm.getY() == 0.25);

        assertTrue("Absolute center of mass is not correct in box " + box.getId(), test1);
        assertTrue("Relative center of mass is not correct in box " + box.getId(), test2);

        /* Box 4 - EMPTY BOX*/
        box = new Box(6, 2);

        com = box.getCenterOfMass();
        comNorm = box.getCenterOfMassNormalized();

        test1 = (com.getX() == 0) && (com.getY() == 0);
        test2 = (comNorm.getX() == 0) && (comNorm.getY() == 0);

        assertTrue("Absolute center of mass is not correct in empty box " + box.getId(), test1);
        assertTrue("Relative center of mass is not correct in empty box " + box.getId(), test2);
    }
}
