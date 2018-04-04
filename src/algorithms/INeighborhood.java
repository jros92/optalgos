package algorithms;

import core.FeasibleSolution;

/**
 * Interface for finding neighbors. Implementing classes have to interact with the
 * concrete mathematical optimization problem in order to create neighbors.
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public interface INeighborhood {
	
	/**
	 * Retrieve one neighbor for the provided solution
	 * @param solution
	 * @return One neighbor if there are still new neighbors within the area of the neighborhood, null otherwise
	 */
	public Neighbor getNeighbor(FeasibleSolution solution);

	/**
	 *
	 * @return
	 */
	public IObjectiveFunction getPreferredObjectiveFunction();

	public String toString();
}
