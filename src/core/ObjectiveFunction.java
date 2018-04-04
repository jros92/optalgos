package core;

import algorithms.IObjectiveFunction;

public class ObjectiveFunction implements IObjectiveFunction {

	@Override
	public double getValue(FeasibleSolution solution) {
//		System.out.println("Current Box Count: " + solution.getBoxCount());
//		return solution.getBoxCount();

		int boxCount = solution.getBoxCount();

		double weightedCumulativePackingPercentage = 0;
		for (Box box : solution.getBoxes()) {
			weightedCumulativePackingPercentage += (double)box.getRectangles().size() * (double)box.getOccupiedArea();
		}

//		System.out.println("[OBJFUN] weightedCumulativePackingPercentage = " + weightedCumulativePackingPercentage);

		double[] weights = new double[] {0.9f, 0.1f};
//		double[] weights = new double[2];
//		weights[1] = 1f / (1f + weightedCumulativePackingPercentage / (double)boxCount);
//		weights[0] = 1f - weights[1];

//		System.out.println("[OBJFUN] Weights = [" + weights[0] + ", " + weights[1] + "]");

		double result;
		result = (double)boxCount * (double)weights[0];
		result -= weightedCumulativePackingPercentage * weights[1];

		result = -weightedCumulativePackingPercentage;

		//TODO: Use avg. covered area
		// result -= (double)solution.calculateCumulativePackingPercentage() * (double)weights[1];



//		System.out.println("Objective Function Value: " + result);
		
		return result;
	}
	
	/**
	 * Get objective functions values for neighbors
	 */
	public double[] getValuesForNeighbors(FeasibleSolution[] solutions) {
		int n = solutions.length;
		
		double[] results = new double[n];
		
		for (int i = 0; i < n; i++) {
			results[i] = getValue(solutions[i]);
		}
		
		return results;
	}


}
