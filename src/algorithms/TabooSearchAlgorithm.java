/**
 * 
 */
package algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;


/**
 * Implementation of the Tabu Search Optimization Algorithm
 *
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class TabooSearchAlgorithm extends Algorithm implements IOptimizationAlgorithm {

	private LinkedList<Feature> tabuList;
	private int tabuListSizeLimit = 50; // Tabus expire after a fixed number of iterations
	private double bestCostSoFar;

	public TabooSearchAlgorithm() {
		super("Taboo Search");
		tabuList = new LinkedList<Feature>();
		bestCostSoFar = Integer.MAX_VALUE;
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

		int result = -1;

		/* Find index of best neighbor to return to solver */
//		double max = Arrays.stream(neighborsCosts).max().getAsDouble();
//		int maxInd = Arrays.stream(array).max().i

		// Filter for Taboos
		int fIndex = 0;
		for (Feature f : features) {
			for (Feature tabu : tabuList) {
				if (tabu.equals(f)) {
					neighborsCosts[fIndex] = Double.MAX_VALUE;
					break;
				}
			}

			fIndex++;
		}

		// Convert array to List for Streaming
		List<Double> list = DoubleStream.of(neighborsCosts).boxed().collect(Collectors.toList());

		// Find index of best neighbor that does not use a prohibited feature (a feature on the tabu list)
		result = IntStream.range(0,list.size())
				.reduce((i,j) -> list.get(i) > list.get(j) ? j : i)
				.getAsInt();  // or throw


		/* Update List of Tabus */
		tabuList.addLast(features[result]);
		if (tabuList.size() > tabuListSizeLimit) tabuList.removeFirst();


		/* Check Aspiration Condition and drop all Taboos if true */
		if (neighborsCosts[result] <= bestCostSoFar) {
			System.out.println("[ALGORITHM] Dropping all Tabus.");
			bestCostSoFar = neighborsCosts[result];
			tabuList = new LinkedList<Feature>();
		}


		return result;
	}


	/**
	 * Determines if the algorithm should keep iterating or terminate
	 * @return true if termination condition has been met.
	 */
	@Override
	public boolean terminated() {
		return false;
	}


	/**
	 * Taboo Search works on multiple neighbors.
	 * @return true
	 */
	@Override
	public boolean needMultipleNeighbors() {
		return true;
	}


}
