package core;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The box class resembles the box of the fixed length <code>length</code>. It also consists all the logic 
 * to add and remove rectangles to/from the box, and maintains a list of free positions where new rectangles 
 * could still be added.
 * 
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class Box implements Comparable {
	
	private int length;
	private int id;
	private ArrayList<Rectangle> rectangles;
	private LinkedList<Point> freePositions;
	
	public Box(int length, int id) {
		this.length = length;
		this.id = id;
		this.rectangles = new ArrayList<Rectangle>();
		this.freePositions = new LinkedList<>();
		this.freePositions.add(new Point(0, 0));
	}
	
	public int getLength() {
		return this.length;
	}

	public int getId() {
		return id;
	}

	public ArrayList<Rectangle> getRectangles() {
		return this.rectangles;
	}
	
	/**
	 * Try to add a rectangle at the "first" free position in this box
	 * @param rectangle The rectangle to add to this box
	 * @return An integer value:
	 * -2 if box is completely full,
	 * -1 if  rectangle too big for this box,
	 * 0..2 if successfully added rectangle, number denoting how many new free positions were added
	 */
	public int addRectangleAtFirstFreePosition(Rectangle rectangle) {
		int result = 0;
		
		if (this.freePositions.size() < 1) {
			// No free positions left - box full
			return -2;
		} else {
			for (Point pos : this.freePositions) {
	//			Point pos = this.freePositions.get(0);
				
				// TODO: Put this in its own function, since it is crucial for integrity of application !!!
				
				// check if remaining space of box at the current position is enough for rectangle
				if (getFreeWidth(pos) < rectangle.getWidth()) {
					if (getFreeWidth(pos) < rectangle.getHeight()) {
						// Rectangle too big for available space at this position
						continue;
					} else {
						if (getFreeLength(pos) < rectangle.getWidth()) {
							// Rectangle too big for available space at this position
							continue;
						} else {
							// Rectangle can be rotated in order to fit into space
							rectangle.rotate();
						}
					}
				} else {
					if (getFreeLength(pos) < rectangle.getHeight()) {
						// Rectangle too big for available space at this position
						continue;
					}
				}
				
				/* Check if rectangle would intersect with any other rectangles */
				Rectangle rectangleAtNewPosition = new Rectangle(pos, rectangle.getWidth(), rectangle.getHeight(), rectangle.getId());
				if (intersectsOtherRectangles(rectangleAtNewPosition)) continue;	// Intersection found, skip this position

			
				/* If this code executes, there is enough space at the current position to place the rectangle */
				
				/* Add Rectangle at Position */ 
				rectangle.setPos(pos);
				this.rectangles.add(rectangle);

				/* Update Free Positions */
				this.freePositions.remove(pos);
				
				// Add Northeast corner
				int posNEx = rectangle.getPos().getX() + rectangle.getWidth();
				int posNEy = rectangle.getPos().getY();
				if ((posNEx < this.length) & (posNEy < this.length)) {
					this.freePositions.add(new Point(posNEx, posNEy));
					result++;
				}
					
				// Add Southeast corner
				// Southeast corner usually does not make sense to use since it is wasting space, so do not use it!
				// TODO: however, it does make sense if there are rectangles to the right or down of the newly added one...
//				int posSEx = rectangle.getPos().getX() + rectangle.getWidth();
//				int posSEy = rectangle.getPos().getY() + rectangle.getLength();
//				if ((posSEx < this.length) & (posSEy < this.length)) {
//					this.freePositions.add(new Point(posSEx, posSEy));
//					result++;
//				}
				
				// Add Southwest corner
				int posSWx = rectangle.getPos().getX();
				int posSWy = rectangle.getPos().getY() + rectangle.getHeight();
				if ((posSWx < this.length) & (posSWy < this.length)) {
					this.freePositions.add(new Point(posSWx, posSWy));
					result++;
				}
				
				// TODO: Sort free positions from top left to bottom right (maybe keep as is for initialization?)
				
				return result;
			}
		}
		
		return -1;
	}
	
	/**
	 * Check if rectangle would intersect with any other rectangles
	 * @param rect The rectangle to check
	 * @return true if intersection was found
	 */
	private boolean intersectsOtherRectangles(Rectangle rect) {
		for (Rectangle otherRect : this.getRectangles()) {
			if (rect == otherRect) continue;	// Do not have to check this for same rectangle
			if (rect.intersects(otherRect)) return true;
		}
		return false;
	}

	/**
	 * Check if rectangle would intersect with any other rectangles, with one exclusion
	 * @param rect The rectangle to check
	 * @param excludedRect The rectangle to exclude from the test (for translation, e.g.)
	 * @return true if intersection was found
	 */
	private boolean intersectsOtherRectangles(Rectangle rect, Rectangle excludedRect) {
		for (Rectangle otherRect : this.getRectangles()) {
			if (rect == otherRect) continue;	// Do not have to check this for same rectangle
			if (otherRect == excludedRect) continue;
			if (rect.intersects(otherRect)) return true;
		}
		return false;
	}

	/**
	 * Remove rectangle from this box. Caller needs remove the box if return value is true.
	 * @param rectangle
	 * @return true if empty after removal, false otherwise
	 */
	public boolean removeRectangle(Rectangle rectangle) {
//		rectangle.setPos(-1, -1); // CANNOT DO THIS!!!
		this.rectangles.remove(rectangle);
		return (this.getRectangles().size() == 0);
	}
	
	public LinkedList<Point> getFreePositions() {
		return this.freePositions;
	}
	
	public int getFreeWidth(Point pos) {
		int result = 0;
		result = this.length - pos.getX();
		return result;
	}
	
	public int getFreeLength(Point pos) {
		int result = 0;
		result = this.length - pos.getY();
		return result;
	}
	
	public int getMaxFreeWidth() {
		int result = 0; 
		
		for (Point freePos : this.freePositions) {
			int freeWidth = getFreeWidth(freePos);
			if (freeWidth > result)
				result = freeWidth;
		}
		
		return result;
	}
	
	public int getMaxFreeLength() {
		int result = 0; 
		
		for (Point freePos : this.freePositions) {
			int freeLength = getFreeLength(freePos);
			if (freeLength > result)
				result = freeLength;
		}
		
		return result;
	}
	
	public int getMaxFreeDistance() {
		int result = 0;
		
		int maxFreeWidth = getMaxFreeWidth();
		int maxFreeLength = getMaxFreeLength();
		
		if (maxFreeWidth >= maxFreeLength)
			result = maxFreeWidth;
		else
			result = maxFreeLength;
		
		return result;
	}
	
	/**
	 * Return absolute area of this box that is occupied.
	 * @return Absolute occupied area
	 */
	public int getOccupiedArea() {
		
		int occupiedArea = 0;
		for (Rectangle rectangle : this.rectangles) {
			occupiedArea += rectangle.getArea();
		}
		
		return occupiedArea;
	}
	
	
	/**
	 * Return area of this box that is free.
	 * @return Absolute free area
	 */
	public int getFreeArea() {
		
		int occupiedArea = this.getOccupiedArea();
		
		return ((this.length * this.length) - occupiedArea);
		
	}
	
	
	/**
	 * Return percentage of the box area that is filled already
	 * @return Relative filled area
	 */
	public double getPackingPercentage() {	
		return ((double)this.getOccupiedArea() / ((double)this.length * (double)this.length)) ;
	}

	/**
	 * Add a rectangle to the box at the origin. Only works if box is empty.
	 * @param rectangle
	 * @return true if successful, false if not because box was not empty
	 */
	public boolean addRectangleAtOrigin(Rectangle rectangle) {
		if (this.rectangles.size() == 0) {
			rectangle.setPos(0, 0);
			this.freePositions.remove(0);
			this.rectangles.add(rectangle);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Calculate the non-normalized (absolute) center of mass of this box
	 * @return a CoM object with the non-normalized x and y coordinates of the center of mass of this box
	 */
	public CoM getCenterOfMass() {
		double com_x = 0, com_y = 0;
		int totalMass = 0;

		if (this.rectangles.size() < 1) return new CoM(0,0);

		for (Rectangle r : this.rectangles) {
			int m = r.getArea();
			com_x += (double)m * ((double)r.getPos().getX() + (double)r.getWidth()/2.0);
			com_y += (double)m * ((double)r.getPos().getY() + (double)r.getHeight()/2.0);
			totalMass += m;
		}
		com_x /= (double)totalMass;
		com_y /= (double)totalMass;

		return new CoM(com_x, com_y);
	}


	/**
	 * Calculate the normalized (relative) center of mass of this box
	 * @return a CoM object with the normalized x and y coordinates of the center of mass of this box
	 */
	public CoM getCenterOfMassNormalized() {
		if (this.rectangles.size() < 1) return new CoM(0,0);

		CoM comAbsolute = getCenterOfMass();

		double com_x = comAbsolute.getX();
		double com_y = comAbsolute.getY();

		com_x /= (double)this.length;
		com_y /= (double)this.length;

		return new CoM(com_x, com_y);
	}

	/**
	 * Add a rectangle to the box at the lower right corner. Only works if rectangle does not overlap.
	 * Need to remove rectangle from old box if returns true.
	 * @param rectangle
	 * @return true if successful, false if not because there was not enough space at lower right
	 */
	public boolean addRectangleAtLowerRight(Rectangle rectangle) {
		/* Calculate new position of rectangle */
		Point pos = new Point(this.length - rectangle.getWidth(), this.length - rectangle.getHeight());

		/* Check if rectangle would intersect with any other rectangles */
		Rectangle rectangleAtNewPosition = new Rectangle(pos, rectangle.getWidth(), rectangle.getHeight(), rectangle.getId());
		if (intersectsOtherRectangles(rectangleAtNewPosition)) {
			// Intersection found, return false
			return false;
		} else {

			/* Add Rectangle at Position */
			rectangle.setPos(pos);
			this.rectangles.add(rectangle);

			/* Update Free Positions */
			this.freePositions.remove(pos);

			return true;
		}
	}

	/**
	 * Try to apply a translation of one length unit to the specified rectangle in the specified direction.
	 * Translation only gets applied if no intersection between rectangles will be created.
	 * @param r The rectangle to translate
	 * @param dir the direction of the translation
	 * @return true if translation was applied, false if not
	 */
	public boolean tryTranslate(Rectangle r, TranslationDirections dir) {

		Rectangle rectangleAtNewPosition = r.clone();

		translate(rectangleAtNewPosition, dir);

		if (intersectsOtherRectangles(rectangleAtNewPosition, r)) {
			// Intersection found, return false
			return false;
		} else {
			translate(r, dir);
			return true;
		}
	}

	/**
	 * Apply a translation of one length unit to the specified rectangle in the specified direction.
	 * Does not check for intersections!
	 * @param r The rectangle to translate
	 * @param dir The direction of the translation
	 */
	private void translate(Rectangle r, TranslationDirections dir) {
		switch (dir) {
			case Up:
				r.moveUp();
				break;
			case Right:
				r.moveRight();
				break;
			case Down:
				r.moveDown();
				break;
			case Left:
				r.moveLeft();
				break;
			default:
				break;
		}
	}

	/**
	 * Clone an entire box and all of the contained rectangles. Retain the positions of the rectangles as well.
	 * @return
	 */
	public Box clone() {
		Box result = new Box(this.length, this.id);
		result.freePositions.remove(0);

		for (Rectangle r : this.rectangles)
			result.rectangles.add(r.clone());

		for (Point p : this.freePositions)
			result.freePositions.add(p);

		return result;
	}

	/**
	 * Compares this object with the specified object for order.  Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 * <p>
	 * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
	 * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
	 * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
	 * <tt>y.compareTo(x)</tt> throws an exception.)
	 * <p>
	 * <p>The implementor must also ensure that the relation is transitive:
	 * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
	 * <tt>x.compareTo(z)&gt;0</tt>.
	 * <p>
	 * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
	 * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
	 * all <tt>z</tt>.
	 * <p>
	 * <p>It is strongly recommended, but <i>not</i> strictly required that
	 * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
	 * class that implements the <tt>Comparable</tt> interface and violates
	 * this condition should clearly indicate this fact.  The recommended
	 * language is "Note: this class has a natural ordering that is
	 * inconsistent with equals."
	 * <p>
	 * <p>In the foregoing description, the notation
	 * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
	 * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
	 * <tt>0</tt>, or <tt>1</tt> according to whether the value of
	 * <i>expression</i> is negative, zero or positive.
	 *
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 */
	@Override
	public int compareTo(Object o) {
		if (o == null) throw new NullPointerException();

		try {
			Box otherBox = (Box) o;
			return (int)(this.getPackingPercentage() - otherBox.getPackingPercentage());
		} catch (ClassCastException e) {
			throw e;
		}
	}
}