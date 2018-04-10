package core;

/**
 * A point object with coordinates as double values, used for the center of mass
 */
public class CoM {
    private double x;
    private double y;

    public CoM(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
