/**
 * 
 */
package algorithms;

import java.util.Random;
import java.lang.Math;

/**
 * Implementation of the Simulated Annealing Algorithm
 * employing a cooling schedule with k pairs of Temperatures T_i and sequence length n_i with logarithmic scheme
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class SimulatedAnnealingAlgorithm extends Algorithm implements IOptimizationAlgorithm {

	private CoolingSchedule coolingSchedule;
	private int currentIteration, currentIterationAtTemperature;


	public SimulatedAnnealingAlgorithm() {
		super("Simulated Annealing");
		this.coolingSchedule = new CoolingSchedule();
		this.currentIteration = 0;
		this.currentIterationAtTemperature = 0;
	}

	/**
	 * Perform an iteration of the Simulated Annealing algorithm
	 * @param currentCost cost of current solution
	 * @param neighborsCosts costs of the neighbors
	 * @param features Not needed for this algorithm. The features that were modified for the neighbors
	 * @return the index of the new solution: -1 if sticking to current solution, 0..n if choosing one of the neighbors
	 */
	@Override
	public int doIteration(double currentCost, double[] neighborsCosts, Feature[] features) {

		this.currentIteration++;

//		neighborhood.getNeighbors(solution, 100);
		int result = -1;
		int numberOfNeighbors = neighborsCosts.length;
//		for (int i = 0; i < numberOfNeighbors; ++i) {
		
		for (int k = 0; k < coolingSchedule.coolingScheduleLength - 1; ++k) {
			/* Iteration over cooling windows */
			System.out.println("[ALGORITHM] Current T = " + coolingSchedule.temperatures[k]);
			System.out.print("[ALGORITHM] Decisions: ");

			for (currentIterationAtTemperature = 0;
				 currentIterationAtTemperature < coolingSchedule.sequenceLength[k];
				 ++currentIterationAtTemperature) {

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


	/**
	 * Generates a biased random boolean value
	 * @param bias if 0, always returns FALSE, if 1 always returns TRUE
	 * @return
	 */
	public boolean getRandomBiasedBoolean(float bias) {
		Random rand = new Random();
		return rand.nextFloat() < bias;
	}

	/**
	 * Determines if the algorithm should keep iterating or terminate
	 * @return true if termination condition has been met.
	 */
	@Override
	public boolean terminated() {


		if (currentIteration >= this.coolingSchedule.coolingScheduleLength - 1) {
			System.out.println("[ALGORITHM] Reached end of Cooling Schedule, terminating.");
			return true;
		}

		//	TODO: termination as soon as “nothing significant has changed” for a while

		return false;
	}

	@Override
	public boolean needMultipleNeighbors() {
		return false;
	}

}

/**
 * A cooling schedule consisting of pairs of temperatures and sequence lengths
 */
class CoolingSchedule {
	int coolingScheduleLength = 10;
	
	double c = 100; // TODO: Choose well, dependent on problem
	
	double[] temperatures = new double[coolingScheduleLength];
	int[] sequenceLength = new int[coolingScheduleLength];
	
	CoolingSchedule() {
		System.out.println("\nCooling Schedule for Simulated Annealing: ");
		for (int i = 0; i < coolingScheduleLength; ++i) {
			temperatures[i] = c/Math.log(i+1);
			sequenceLength[i] = (coolingScheduleLength - i) * 100;	// TODO: Choose well
			System.out.println("Cooling iteration " + i + ": T = " + temperatures[i] + ", n = " + sequenceLength[i] + ".");
		}
		
		
	}
}