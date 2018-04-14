package core;

import algorithms.IObjectiveFunction;

/**
 * Objective Function for Geometric Neighborhood
 */
public class ObjectiveFunctionGeometric implements IObjectiveFunction {

    @Override
    public double getValue(FeasibleSolution solution) {

        int boxCount = solution.getBoxCount();
        double comPenalty = 0;  // penalty for center of mass
        CoM com;

        for (Box box : solution.getBoxes()) {
            com = box.getCenterOfMassNormalized();
            comPenalty += (com.getX() + com.getY());
        }

        // Normalize penalty
        comPenalty /= (2.0 * (double)boxCount);

        double weightedCumulativeFreeAreaPercentage = 0;
        for (Box box : solution.getBoxes()) {
            weightedCumulativeFreeAreaPercentage += (double)box.getRectangles().size() * (double)box.getFreeArea();
        }

        double result = weightedCumulativeFreeAreaPercentage / (solution.getBoxLength() * solution.getBoxLength());
        result /= (double)boxCount;

        result += comPenalty;

//        System.out.println("[OBJFUN] comPenalty=" + comPenalty + ", result=" + result);

        return result;
    }
}
