/**
 * 
 */
package algorithms;

import java.util.Random;
import java.lang.Math;

/**
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class SimulatedAnnealingAlgorithm extends Algorithm implements IOptimizationAlgorithm {

	private CoolingSchedule coolingSchedule;
	
	public SimulatedAnnealingAlgorithm() {
		super("Simulated Annealing");
//		temperature = 1000;
		coolingSchedule = new CoolingSchedule();
	}
	
//	@Override
//	public void setNeighborhood(INeighborhood neighborhood) {
//		// TODO Auto-generated method stub
//		
//	}
	

	@Override
	public int doIteration(double currentCost, double[] neighborsCosts) {

//		neighborhood.getNeighbors(solution, 100);
		int result = -1;
//		int numberOfNeighbors = neighborsCosts.length;
//		for (int i = 0; i < numberOfNeighbors; ++i) {
		
		for (int k = 0; k < coolingSchedule.coolingScheduleLength - 1; ++k) {
			/* Iteration over cooling windows */
			System.out.println("[ALGORITHM] Current T = " + coolingSchedule.temperatures[k]);
			System.out.print("[ALGORITHM] Decisions: ");
			for (int i = 0; i < coolingSchedule.sequenceLength[k]; ++i) {
				/* ITERATION within cooling window k with n iterations */
				if (neighborsCosts[0] < currentCost) {
					currentCost = neighborsCosts[k];
					result = 0;
				} else {
					// Make probabilistic yes/no decision whether to take this solution that is worse
					
					float bias = (float) Math.exp((currentCost - neighborsCosts[0]) / coolingSchedule.temperatures[k]);
					boolean takeWorseSolution = getRandomBiasedBoolean(bias);
					
					if (takeWorseSolution) {
						currentCost = neighborsCosts[0];
						result = 0;
						System.out.print("YES ");
					} else {
						System.out.print("NO ");
					}
					
				}
			}
			System.out.println();
		}
		
		/* Count unsuccessful attempts so algorithm can keep trying to find a better neighbor within neighborhood */
//		if (result == -1) this.unsuccessfulAttempts += 1;
//		else this.unsuccessfulAttempts = 0;
		
//		System.out.println("[ALGORITHM] # of unsuccessful iterations is now " + this.unsuccessfulAttempts);
		
		return result;
	}

	@Override
	public boolean terminated() {
		//	TODO: termination as soon as “nothing significant has changed” for a while
		
		return false;
	}


	/**
	 * Generates a biased random boolean value
	 * @param bias if 0, always returns FALSE, if 1 always returns TRUE
	 * @return
	 */
	public boolean getRandomBiasedBoolean(float bias) {
		Random rand = new Random();
		return rand.nextFloat() < bias;
	}

	

}

class CoolingSchedule {
	int coolingScheduleLength = 20;
	
	double c = 10; // TODO: Choose well, dependent on problem
	
	double[] temperatures = new double[coolingScheduleLength];
	int[] sequenceLength = new int[coolingScheduleLength];
	
	CoolingSchedule() {
		System.out.println("\nCooling Schedule for Simulated Annealing: ");
		for (int i = 0; i < coolingScheduleLength; ++i) {
			temperatures[i] = c/Math.log(i+1);
			sequenceLength[i] = (coolingScheduleLength - i) * 200;	// TODO: Choose well
			System.out.println("Cooling iteration " + i + ": T = " + temperatures[i] + ", n = " + sequenceLength[i] + ".");
		}
		
		
	}
}