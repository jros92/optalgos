package core;

import algorithms.Feature;

/**
 * Feature for Rule-Based neighborhoods
 */
public class RectanglePair extends Feature {

    public Rectangle rectangle1, rectangle2;

    public RectanglePair(Rectangle r1, Rectangle r2) {
        this.rectangle1 = r1;
        this.rectangle2 = r2;
    }

}
