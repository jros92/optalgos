package core;

import algorithms.IObjectiveFunction;

/**
 * Objective Function for Rule-Based neighborhood
 */
public class ObjectiveFunction implements IObjectiveFunction {

	@Override
	public double getValue(FeasibleSolution solution) {

		double weightedCumulativeFreeAreaPercentage = 0;
		for (Box box : solution.getBoxes()) {
			weightedCumulativeFreeAreaPercentage += (double)box.getRectangles().size() * (double)box.getFreeArea();
		}
		double result;
		result = weightedCumulativeFreeAreaPercentage / (double)(solution.getBoxLength() * solution.getBoxLength());
		result /= (double)solution.getBoxCount();

		return result;
	}

}
