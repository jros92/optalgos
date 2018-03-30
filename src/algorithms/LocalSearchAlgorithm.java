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
	
	public LocalSearchAlgorithm(int terminateAfterNAttempts) {
		super(name);
		this.terminateAfterNAttempts = terminateAfterNAttempts;
		this.unsuccessfulAttempts = 0;
	}
	
//	@Override
//	public void setNeighborhood(INeighborhood neighborhood) {
//		this.neighborhood = neighborhood;
//	}
	
	@Override
	public int doIteration(double currentCost, double[] neighborsCosts) {
//		if (neighborhood == null) {
//			System.out.println("Neighborhood not set. Exiting...");
//			return -1;
//		}
		
//		System.out.println("Iteration of " + LocalSearchAlgorithm.name + " Algorithm.");
		
		
		/* ITERATION STEP */
		
//		neighborhood.getNeighbors(solution, 100);
		int result = -1;
		int numberOfNeighbors = neighborsCosts.length;
		for (int i = 0; i < numberOfNeighbors; ++i) {
			if (neighborsCosts[i] < currentCost) {
				// If a better neighbor is found, tell the solver to make this the new solution
				currentCost = neighborsCosts[i];
				result = i;
			}
		}
		
		/* Count unsuccessful attempts so algorithm can keep trying to find a better neighbor within neighborhood */
		if (result == -1) this.unsuccessfulAttempts += 1;
		else this.unsuccessfulAttempts = 0;
		
		System.out.println("[ALGORITHM] # of unsuccessful iterations is now " + this.unsuccessfulAttempts);
		
		return result;
	}

	@Override
	public int doIteration(double currentCost, double neighborCosts) {
		int result = -1;

		if (neighborCosts < currentCost) {
			currentCost = neighborCosts;
			result = 0;
		}


		/* Count unsuccessful attempts so algorithm can keep trying to find a better neighbor within neighborhood */
		if (result == -1) this.unsuccessfulAttempts += 1;
		else this.unsuccessfulAttempts = 0;

		System.out.println("[ALGORITHM] # of unsuccessful iterations is now " + this.unsuccessfulAttempts);

		return result;
	}

	/**
	 * Should the algorithm keep iterating?
	 * @return false if termination condition has been met.
	 */
	public boolean terminated() {
		if (unsuccessfulAttempts >= terminateAfterNAttempts) return true;
		
		return false;
	}

}
