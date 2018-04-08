package core;

import algorithms.IObjectiveFunction;

public class ObjectiveFunctionGeometric implements IObjectiveFunction {

    @Override
    public double getValue(FeasibleSolution solution) {

        int boxCount = solution.getBoxCount();

        double weightedCumulativeFreeAreaPercentage = 0;
        for (Box box : solution.getBoxes()) {
            weightedCumulativeFreeAreaPercentage += (double)box.getRectangles().size() * (double)box.getFreeArea();
        }

        //double result = weightedCumulativeFreeAreaPercentage;
        double result = weightedCumulativeFreeAreaPercentage / (solution.getBoxLength() * solution.getBoxLength());

        return result;
    }
}
