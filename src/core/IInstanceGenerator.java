package core;

/**
 * Interface for Instance Generators
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public interface IInstanceGenerator {
	
	/**
	 * Generate a new instance of the Rectangle Fitting Optimization Problem
	 * @param n		Number of rectangles
	 * @param lMin	(Optional) Minimal edge length of rectangles
	 * @param lMax	(Optional) Maximal edge length of rectangles
	 * @param lBox	Edge length of the square boxes that the rectangles will be placed in
	 * @return		An instance with desired parameters
	 */
	public Instance generate(int n, int lMin, int lMax, int lBox);
	
}
