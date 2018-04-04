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

    /**
     * The order of the pair of rectangles does not matter, as long as the two rectangles are the same
     * @param o
     * @return
     */
    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RectanglePair rectanglePair = (RectanglePair) o;

        if (!rectanglePair.rectangle1.equals(this.rectangle1)) {
            if (!rectanglePair.rectangle1.equals(this.rectangle2) &&
                    !rectanglePair.rectangle2.equals(this.rectangle1))
             return false;
        }

        if (!rectanglePair.rectangle2.equals(this.rectangle2)) {
            if (!rectanglePair.rectangle2.equals(this.rectangle1) &&
                    !rectanglePair.rectangle1.equals(this.rectangle2))
                return false;
        }

        return true;
    }

}
