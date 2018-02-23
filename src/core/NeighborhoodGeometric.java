/**
 * 
 */
package core;

import algorithms.INeighborhood;
import algorithms.Neighborhood;

/**
 * Geometric Neighborhood for the 'rectangle fitting' problem
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class NeighborhoodGeometric extends Neighborhood implements INeighborhood {

	public NeighborhoodGeometric() {
		super("Geometric");
	}
	
	/**
	 * Return a specified number of neighbors as an array
	 * @param solution
	 * @param numberOfNeighbors
	 * @return
	 */
	@Override
	public FeasibleSolution[] getNeighbors(FeasibleSolution solution, int numberOfNeighbors) {
		FeasibleSolution[] solutions = new FeasibleSolution[numberOfNeighbors];
		
		Rectangle rect0 = solution.getBoxes().get(0).getRectangles().get(0);
		
		rect0.moveRight();
		
		solutions[0] = solution;
		
		return solutions;
	}
	

}
