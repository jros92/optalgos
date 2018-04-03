package core;

import algorithms.IObjectiveFunction;

public class ObjectiveFunctionGeometric implements IObjectiveFunction {

    @Override
    public double getValue(FeasibleSolution solution) {
        int boxCount = solution.getBoxCount();

        double weightedCumulativeUnusedAreaPercentage = 0;
        for (Box box : solution.getBoxes()) {
            weightedCumulativeUnusedAreaPercentage += (double)box.getRectangles().size() * (1 - box.getPackingPercentage());

        }

//        System.out.println("[OBJFUN] weightedCumulativePackingPercentage = " + weightedCumulativeUnusedAreaPercentage);

        double[] weights = new double[] {0.9f, 0.1f};
//		double[] weights = new double[2];
//		weights[1] = 1f / (1f + weightedCumulativePackingPercentage / (double)boxCount);
//		weights[0] = 1f - weights[1];

//        System.out.println("[OBJFUN] Weights = [" + weights[0] + ", " + weights[1] + "]");

        double result;
        result = (double)boxCount * (double)weights[0];
        result -= weightedCumulativeUnusedAreaPercentage * weights[1];

        result = weightedCumulativeUnusedAreaPercentage;

        return result;
    }

}
