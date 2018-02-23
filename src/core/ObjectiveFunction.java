package core;

import algorithms.IObjectiveFunction;

public class ObjectiveFunction implements IObjectiveFunction {

	@Override
	public double getValue(FeasibleSolution solution) {
//		System.out.println("Current Box Count: " + solution.getBoxCount());
//		return solution.getBoxCount();
		
		float[] weights = new float[] {0.7f, 0.3f};
		
		double result = 0d;
		
		result += (double)solution.getBoxCount() * (double)weights[0];
		result += (double)solution.getBoxCount() * (double)weights[1];
		//TODO: Use avg. covered area
		// result -= (double)solution.calculcateCumulativePackingPercentage() * (double)weights[1];
		
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
