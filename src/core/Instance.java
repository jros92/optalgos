package core;

import java.util.ArrayList;

/**
 * An instance of a 'rectangle fitting' problem
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 */
public class Instance {

	private static int generatedInstances = 0;
	private int id;
	private int nRectangles;
	private int boxLength;
	private int generatorType;
	private ArrayList<Rectangle> rectangles;
	
	/**
	 * Create an instance with n rectangles and box length boxLength
	 * 
	 * @param nRectangles Number of rectangles
	 * @param boxLength Fixed box length
	 * @param generatorType Generator type used (1 or 2)
	 * @param rectangles The set of rectangles for this instance
	 */
	public Instance(int nRectangles, int boxLength, int generatorType, ArrayList<Rectangle> rectangles) {
		super();
		generatedInstances++;
		this.id = generatedInstances;
		this.nRectangles = nRectangles;
		this.boxLength = boxLength;
		this.generatorType = generatorType;
		
		this.rectangles = rectangles;
	}
	
	public int getId() {
		return id;
	}
	
	public int getnRectangles() {
		return nRectangles;
	}
		
	public int getBoxLength() {
		return boxLength;
	}
	
	public int getGeneratorType() {
		return generatorType;
	}
	
	/**
	 * Return the rectangles contained in this instance
	 * @return An ArrayList containing all the rectangles
	 */
	public ArrayList<Rectangle> getRectangles() {
		return this.rectangles;
	}

	public void printRectangles() {
		for (int i = 0; i < nRectangles; i++) {
			Rectangle rect = rectangles.get(i);
			System.out.println("Rectangle " + i + " : L = " + rect.getHeight() + ", W = " + rect.getWidth() + ".");
		}
	}
	
	@Override
	public Instance clone() {
		ArrayList<Rectangle> newRectangles = new ArrayList<Rectangle>();
		
		for (Rectangle rectangle : this.rectangles) {
			newRectangles.add(rectangle.clone());
		}
		
		Instance newInstance = new Instance(nRectangles, boxLength, generatorType, newRectangles);
		return newInstance;
	}
	
	/**
	 *  Returns a textual representation of an instance that looks like this:
	 *  Instance #<N> [N = <n>, GenType = <i>, BoxLength = <l>]
	 */
	@Override
	public String toString() {
		return "Instance #" + this.id + " [N = " + this.nRectangles + ", GenType = " + this.generatorType + ", BoxLength = " + this.boxLength + "]";
	}
	
}
