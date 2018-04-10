package core;

import algorithms.IObjectiveFunction;

public class ObjectiveFunction implements IObjectiveFunction {

	@Override
	public double getValue(FeasibleSolution solution) {

		double weightedCumulativeFreeAreaPercentage = 0;
		for (Box box : solution.getBoxes()) {
			weightedCumulativeFreeAreaPercentage += (double)box.getRectangles().size() * (double)box.getFreeArea();
		}

		double result = weightedCumulativeFreeAreaPercentage / (solution.getBoxLength() * solution.getBoxLength());
		result /= solution.getBoxCount();

		return result;
	}

}
