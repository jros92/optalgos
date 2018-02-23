package algorithms;

import core.FeasibleSolution;

public interface IObjectiveFunction {

	public double getValue(FeasibleSolution solution);
	
	public double[] getValuesForNeighbors(FeasibleSolution[] solutions);
	
}
