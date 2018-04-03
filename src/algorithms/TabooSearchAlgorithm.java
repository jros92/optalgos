/**
 * 
 */
package algorithms;

import core.Rectangle;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class TabooSearchAlgorithm extends Algorithm implements IOptimizationAlgorithm {

	private LinkedList tabuList;

	public TabooSearchAlgorithm() {
		super("Taboo Search");
		tabuList = new LinkedList();
	}

//	@Override
//	public void setNeighborhood(INeighborhood neighborhood) {
//		// TODO Auto-generated method stub
//		
//	}



	@Override
	public int doIteration(double currentCost, double[] neighborsCosts) {

		// Find index of best neighbor to return to solver
		double max = Arrays.stream(neighborsCosts).max().getAsDouble();
//		int maxInd = Arrays.stream(array).max().i

		return 0;
	}

	@Override
	public int doIteration(double currentCost, double neighborCosts) {
		return 0;
	}

	@Override
	public boolean terminated() {
		// TODO Auto-generated method stub
		return true;
	}


}
