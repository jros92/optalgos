package core;

import java.util.ArrayList;

/**
 * The box class resembles the box of the fixed length <code>length</code>. It also consists all the logic 
 * to add and remove rectangles to/from the box, and maintains a list of free positions where new rectangles 
 * could still be added.
 * 
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class Box {
	
	private int length;
	private ArrayList<Rectangle> rectangles;
	private ArrayList<Point> freePositions;
	
	public Box(int length) {
		this.length = length;
		this.rectangles = new ArrayList<Rectangle>();
		this.freePositions = new ArrayList<Point>();
		this.freePositions.add(new Point(0, 0));
	}
	
	public int getLength() {
		return this.length;
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
	 * 0..3 if successfully added rectangle, number denoting how many new free positions were added
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
//					else {				
//						if (getFreeLength(pos) < rectangle.getWidth()) {
//							if (getFreeWidth(pos) < rectangle.getHeight()) {
//								// Rectangle too big for available space at this position
//								continue;
//							} else {
//								// Rectangle can be rotated in order to fit into space
//								rectangle.rotate();
//							}
//						}
//					}
				}
				
				/* Check if rectangle would intersect with any other rectangles */
				Rectangle rectangleAtNewPosition = new Rectangle(pos, rectangle.getWidth(), rectangle.getHeight());
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
	 * @return
	 */
	private boolean intersectsOtherRectangles(Rectangle rect) {
		for (Rectangle otherRect : this.getRectangles()) {
			if (rect == otherRect) continue;	// Do not have to check this for same rectangle
			if (rect.intersects(otherRect)) return true;
		}
		return false;
	}
	
	public void removeRectangle(Rectangle rectangle) {
		rectangle.setPos(-1, -1);
		this.rectangles.remove(rectangle);
	}
	
	public ArrayList<Point> getFreePositions() {
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
		
}