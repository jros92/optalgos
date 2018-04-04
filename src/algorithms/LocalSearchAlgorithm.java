package algorithms;

/**
 * General/Tuned Local Search Scheme for Optimization Problems
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class LocalSearchAlgorithm extends Algorithm implements IOptimizationAlgorithm {

	private int terminateAfterNAttempts;
	private int unsuccessfulAttempts;
	private static String name = "Local Search";
//	private INeighborhood neighborhood;
	
	LocalSearchAlgorithm(int terminateAfterNAttempts) {
		super(name);
		this.terminateAfterNAttempts = terminateAfterNAttempts;
		this.unsuccessfulAttempts = 0;
	}
	
//	@Override
//	public void setNeighborhood(INeighborhood neighborhood) {
//		this.neighborhood = neighborhood;
//	}

	/**
	 * Perform an iteration of the algorithm
	 *
	 * @param currentCost    cost of current solution
	 * @param neighborsCosts costs of the neighbors
	 * @param features       The features that were modified for the neighbors
	 * @return the index of the new solution: -1 if sticking to current solution, 0..n if choosing one of the neighbors
	 */
	@Override
	public int doIteration(double currentCost, double[] neighborsCosts, Feature[] features) {

		int result = -1;
//		int numberOfNeighbors = neighborsCosts.length;
//		for (int i = 0; i < numberOfNeighbors; ++i) {

			if (neighborsCosts[0] < currentCost) {
				// If a better neighbor is found, tell the solver to make this the new solution
				result = 0;
			}

//		}
		
		/* Count unsuccessful attempts so algorithm can keep trying to find a better neighbor within neighborhood */
		if (result == -1) this.unsuccessfulAttempts += 1;
		else this.unsuccessfulAttempts = 0;
		
		System.out.println("[ALGORITHM] # of unsuccessful iterations is now " + this.unsuccessfulAttempts);
		
		return result;
	}

//	public int doIteration(double currentCost, double neighborCosts) {
//		int result = -1;
//
//		if (neighborCosts < currentCost) {
//			currentCost = neighborCosts;
//			result = 0;
//		}
//
//
//		/* Count unsuccessful attempts so algorithm can keep trying to find a better neighbor within neighborhood */
//		if (result == -1) this.unsuccessfulAttempts += 1;
//		else this.unsuccessfulAttempts = 0;
//
//		System.out.println("[ALGORITHM] # of unsuccessful iterations is now " + this.unsuccessfulAttempts);
//
//		return result;
//	}


	/**
	 * Determines if the algorithm should keep iterating or terminate
	 * @return true if termination condition has been met.
	 */
	public boolean terminated() {
//		if (unsuccessfulAttempts >= terminateAfterNAttempts) return true;
		
		return false;
	}


	@Override
	public boolean needMultipleNeighbors() {
		return false;
	}

}
