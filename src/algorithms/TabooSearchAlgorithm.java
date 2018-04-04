/**
 * 
 */
package algorithms;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class TabooSearchAlgorithm extends Algorithm implements IOptimizationAlgorithm {

	private LinkedList<Feature> tabuList;

	public TabooSearchAlgorithm() {
		super("Taboo Search");
		tabuList = new LinkedList<Feature>();
	}

	/**
	 * Perform an iteration of the Tabu Search algorithm
	 * @param currentCost cost of current solution
	 * @param neighborsCosts costs of the neighbors
	 * @param features The features that were modified for the neighbors
	 * @return the index of the new solution: -1 if sticking to current solution, 0..n if choosing one of the neighbors
	 */
	@Override
	public int doIteration(double currentCost, double[] neighborsCosts, Feature[] features) {

		// Find index of best neighbor to return to solver
		double max = Arrays.stream(neighborsCosts).max().getAsDouble();
//		int maxInd = Arrays.stream(array).max().i

		return 0;
	}

	/**
	 * Determines if the algorithm should keep iterating or terminate
	 * @return true if termination condition has been met.
	 */
	@Override
	public boolean terminated() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean needMultipleNeighbors() {
		return true;
	}


}
