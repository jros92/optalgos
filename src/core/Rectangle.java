package core;

/**
 * Class to resemble rectangles and provide functions to create, move and rotate them
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class Rectangle {
	
	private Point pos;
	private int height;
	private int width;
	private boolean rotated;
	
	public Rectangle(Point pos, int width, int length) {
		this.pos = pos;
		this.width = width;
		this.height = length;
		this.rotated = false;
	}

	public Rectangle(int width, int length) {
		this.pos = new Point(-1, -1);
		this.width = width;
		this.height = length;
		this.rotated = false;
	}
	
	public Point getPos() {
		return this.pos;
	}
	
	public void setPos(int x, int y) {
		this.pos = new Point(x, y);
	}
	
	public void setPos(Point pos) {
		this.pos = pos;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public boolean isRotated() {
		return this.rotated;
	}
	
	public int getArea() {
		return this.height * this.width;
	}
	
	/**
	 * Check if rectangle intersects with this rectangle
	 * @param r 
	 * @return true if intersection exists, false if not
	 */
	public boolean intersects(Rectangle r) {
		// taken from java.awt.Rectangle
		/* 
		 * Explanation from Stackoverflow: https://stackoverflow.com/questions/23302698/java-check-if-two-rectangles-overlap-at-any-point
		 * We can determine a rectangle with only one of its diagonal.
		 * Let's say left rectangle's diagonal is (x1, y1) to (x2, y2) (bottom left to top right)
		 * And right rectangle's diagonal is (x3, y3) to (x4, y4) (bottom left to top right)
		 * 
		 * Now, if any of these 4 conditions is true, you can say the rectangles are not overlapping:
    	 * x3 > x2 (OR)
    	 * y3 > y2 (OR)
    	 * x1 > x4 (OR)
    	 * y1 > y4 
		 */
		return r.getWidth() > 0 && r.getHeight() > 0 && this.width > 0 && this.height > 0
				&& r.getPos().getX() < this.pos.getX() + this.width && r.getPos().getX() + r.getWidth() > this.pos.getX()
				&& r.getPos().getY() < this.pos.getY() + this.height && r.getPos().getY() + r.getHeight() > this.pos.getY();
	}
	
	/**
	 * Move rectangle to the right by one height unit.
	 */
	public void moveRight() {
		this.pos.setX(this.pos.getX() + 1);
	}
	
	/**
	 * Move rectangle to the left by one height unit.
	 */
	public void moveLeft() {
		this.pos.setX(this.pos.getX() - 1);
	}
	
	/**
	 * Move rectangle up by one height unit.
	 */
	public void moveUp() {
		this.pos.setY(this.pos.getY() - 1);
	}
	
	/**
	 * Move rectangle down by one height unit.
	 */
	public void moveDown() {
		this.pos.setY(this.pos.getY() + 1);
	}
	
	/**
	 * Rotate the rectangle by 90 degrees, i.e. flip height and width
	 */
	public void rotate() {
		int tmp = this.height;
		this.height = this.width;
		this.width = tmp;
		rotated = true;
	}
	
//	public void draw(Graphics page, Color color)
//	{
//	    page.setColor(color);
//	    page.drawRect(pos.getX(), pos.getY(), width, height);
//	}
	
	@Override
	public Rectangle clone() {
		Rectangle newRectangle = new Rectangle(this.width, this.height);
		newRectangle.setPos(new Point(this.pos.getX(), this.pos.getY()));
		return newRectangle;
	}
	
	@Override
	public String toString() {
		return "Rectangle <pos = (" + pos.getX() + ", " + pos.getY() + "), W = " + width + ", L = " + height + ">";
	}
	
}