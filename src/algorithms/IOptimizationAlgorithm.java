package algorithms;

/**
 * Interface to be used by the Solver to encapsulate algorithms
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public interface IOptimizationAlgorithm {

	/**
	 * Perform an iteration of the algorithm
	 * @param currentCost cost of current solution
	 * @param neighborsCosts costs of the neighbors
	 * @param features The features that were modified for the neighbors
	 * @return the index of the new solution: -1 if sticking to current solution, 0..n if choosing one of the neighbors
	 */
	public int doIteration(double currentCost, double[] neighborsCosts, Feature[] features);

	/**
	 * Determines if the algorithm should keep iterating or terminate
	 * @return true if termination condition has been met.
	 */
	public boolean terminated();

	/**
	 * Determines if the algorithm should keep iterating or terminate
	 * @return true if algorithm works on set of neighbors, false if algorithm looks at one neighbor at a time
	 */
	public boolean needMultipleNeighbors();
	
	public String toString();
}
