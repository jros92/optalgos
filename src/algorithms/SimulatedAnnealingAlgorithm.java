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
	private int currentTemperatureWindow, currentIterationAtTemperature;


	public SimulatedAnnealingAlgorithm() {
		super("Simulated Annealing");
		this.coolingSchedule = new CoolingSchedule();
		this.currentTemperatureWindow = 0;
		this.currentIterationAtTemperature = 0;
	}

    /**
     * Get the textual representation of the cooling schedule
     * @return String
     */
	public String getCoolingScheduleTextual() {
        return this.coolingSchedule.toString();
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

		int result = -1;

		if (currentTemperatureWindow < coolingSchedule.coolingScheduleLength) {
			/* Iteration over cooling windows */
//			System.out.println("[ALGORITHM] Current T = " + coolingSchedule.temperatures[currentTemperatureWindow]);
//			System.out.print("[ALGORITHM] Decisions: ");

			if (currentIterationAtTemperature < coolingSchedule.sequenceLength[currentTemperatureWindow]) {

				/* ITERATION within cooling window k with n iterations */
				if (neighborsCosts[0] < currentCost) {
					result = 0;
				} else {

					// Make probabilistic yes/no decision whether to take this solution that is worse
					float bias = (float) Math.exp((currentCost - neighborsCosts[0]) / coolingSchedule.temperatures[currentTemperatureWindow]);
					
					if (getRandomBiasedBoolean(bias)) {
						// Switch to neighbor even though it is worse than the current solution
						result = 0;
//						System.out.print("YES ");
					} else {
//						System.out.print("NO ");
					}
					
				}

				++currentIterationAtTemperature;

				if (currentIterationAtTemperature >= coolingSchedule.sequenceLength[currentTemperatureWindow]) {
					this.currentTemperatureWindow++;
					currentIterationAtTemperature = 0;
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


		if (currentTemperatureWindow >= this.coolingSchedule.coolingScheduleLength - 1) {
			System.out.println("[ALGORITHM] Reached end of Cooling Schedule, terminating.");
			return true;
		}

		//	TODO: termination as soon as “nothing significant has changed” for a while

		return false;
	}

	/**
	 * Simulated Annealing looks at one neighbor at a time
	 * @return false
	 */
	@Override
	public boolean needMultipleNeighbors() {
		return false;
	}

}

/**
 * A cooling schedule consisting of pairs of temperatures and sequence lengths
 */
class CoolingSchedule {
	int coolingScheduleLength = 100;

	double desiredStartingTemperature = 200f;        // TODO: Choose well, dependent on problem
	double c = desiredStartingTemperature / 1.4427f;
	
	double[] temperatures = new double[coolingScheduleLength];
	int[] sequenceLength = new int[coolingScheduleLength];
	int totalIterations = 0;

	CoolingSchedule() {
		for (int i = 0; i < coolingScheduleLength; ++i) {
			temperatures[i] = c / Math.log(i+2);    // from lecture slides
			sequenceLength[i] = (coolingScheduleLength - (i)) * 20 + 50;	// TODO: Choose well
            totalIterations += sequenceLength[i];
		}
	}

    @Override
    public String toString() {
        String result = "Cooling Schedule for Simulated Annealing: \n";
        result += "Total iterations in schedule: " + totalIterations + "\n";
        for (int i = 0; i < coolingScheduleLength; ++i) {
            result += "Cooling iteration " + i + ": T = " + temperatures[i] + ", n = " + sequenceLength[i] + ".\n";
        }
        return result;
	}
}