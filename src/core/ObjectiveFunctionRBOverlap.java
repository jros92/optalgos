package core;

import algorithms.IObjectiveFunction;

/**
 * ObjFun with penalty for overlap, tailored for Rule-Based Neighborhood with decreasing overlap
 */
public class ObjectiveFunctionRBOverlap implements IObjectiveFunction {

	// TODO: Penalty has to be small (or even zero?) in the beginning, and increase steadily

	@Override
	public double getValue(FeasibleSolution solution) {

		double weightedCumulativeFreeAreaPercentage = 0;
		double overlapPenalty = 0;
		for (Box box : solution.getBoxes()) {
			weightedCumulativeFreeAreaPercentage += (double)box.getRectangles().size() * (double)box.getFreeArea();
			int rectangleIndex = 0;
			for (Rectangle r : box.getRectangles()) {
				for (int i = rectangleIndex + 1; i < box.getRectangles().size(); ++i) {
					overlapPenalty += r.overlapsBy(box.getRectangles().get(i));
				}
				++rectangleIndex;
			}
		}

		weightedCumulativeFreeAreaPercentage /= (double)(solution.getBoxLength() * solution.getBoxLength());

		double result = weightedCumulativeFreeAreaPercentage + overlapPenalty;
		result /= solution.getBoxCount();

		return result;
	}

}
