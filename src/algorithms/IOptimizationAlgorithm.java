package algorithms;

/**
 * Interface to be used by the GUI to visualize Algorithm steps
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public interface IOptimizationAlgorithm {
	
	public int doIteration(double currentCost, double[] neighborsCosts);

	public int doIteration(double currentCost, double neighborCosts);

	public boolean terminated();
	
	public String toString();
}
