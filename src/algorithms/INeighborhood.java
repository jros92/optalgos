package algorithms;

import core.FeasibleSolution;

/**
 * Interface for finding neighbors. Implementing classes have to interact the concrete mathematical problem in order to create neighbors.
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public interface INeighborhood {
	
	/**
	 * 
	 * @param solution
	 * @return
	 */
	public FeasibleSolution[] getNeighbors(FeasibleSolution solution, int numberOfNeighbors);
	
	public String toString();
}
