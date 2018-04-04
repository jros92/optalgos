package algorithms;

import java.awt.EventQueue;

import core.*;
import gui.FormSolutionViewer;

/**
 * Controls and handles the execution of the algorithm
 * Should be called as its own thread, example:
 * Thread solverThread = new Thread(solver); solverThread.start();
 * One solver controls one algorithm at a time, so a new Solver object
 * has to be created for each algorithm to be started.
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class Solver implements Runnable {
	
	private IOptimizationAlgorithm algorithm;
	private INeighborhood neighborhood;
	private IObjectiveFunction objFun;
	
	private FeasibleSolution solution;
	private FeasibleSolution initialSolution;

	private FormSolutionViewer viewer;
	
	private long maxIterations;
	private int timeLimit = 10; 	// in seconds; set <= 0 if no time criterion for termination is desired


	private double boundWorseNeighbor;
	private int numberOfNeighbors;

	private double currentCost;

	private int guiUpdateFrequency = 100; // in milliseconds
	private long lastGuiUpdate = 0;

	/* Sleep duration between iterations in milliseconds */
	private int sleepDuration;
	
	/* TRUE if the algorithm execution is paused (e.g. by the user) */
	private boolean pause = false;

	private int cntGuiUpdates = 0;	// TODO: Remove after debugging

	/**
	 * Instantiate a new solver for a given instance, using a specified algorithm and neighborhood
	 * @param algorithm
	 * @param neighborhood
	 * @param instance
	 * @param maxIterations
	 * @param numberOfNeighbors
	 */
	public Solver(IOptimizationAlgorithm algorithm, INeighborhood neighborhood, Instance instance, long maxIterations, int numberOfNeighbors) {
		this.algorithm = algorithm;
		this.neighborhood = neighborhood;
		this.objFun = neighborhood.getPreferredObjectiveFunction();
		
		/* Initialize a "bad" solution */
		IProblemInitializer problemInitializer = new SimpleInitializer();
		this.initialSolution = problemInitializer.initialize(instance);
		this.solution = this.initialSolution.clone();
		this.currentCost = this.objFun.getValue(this.solution);

		this.maxIterations = maxIterations;

		if (algorithm.needMultipleNeighbors())
			this.numberOfNeighbors = numberOfNeighbors;
		else
			this.numberOfNeighbors = 1;

		this.sleepDuration = 0; // Default value: 0 ms

		this.boundWorseNeighbor = 100; // TODO: Adjust with obj. function
	}
	
	@Override
	public void run() {
		try {
			this.solve();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FeasibleSolution getSolution() {
		return this.solution;
	}
	
	public void setViewer(FormSolutionViewer viewer) {
		this.viewer = viewer;
	}

	public void setGuiUpdateFrequency(int guiUpdateFrequency) {
		this.guiUpdateFrequency = guiUpdateFrequency;
	}

	public void setSleepDuration(int sleepDuration) {
		this.sleepDuration = sleepDuration;
	}

	public void solve() throws InterruptedException {
		       
		System.out.println("[SOLVER] Started " + this.toString() + ".");

		/* Start timing */
		long startTimeNano = System.nanoTime();
        long iterationStartTimeNano;

		/* Initializations */
		Neighbor[] neighbors;
		FeasibleSolution[] neighborsSolutions;
		Feature[] neighborsFeatures;
		FeasibleSolution neighbor;
		double costNeighbor;
		double[] neighborsCosts;
		
		long i = 0;
		int result = -1;

		/* Iteration */
		while ((i < this.maxIterations) && (result >= -1)) {
			if (this.pause) {
				System.out.println("[SOLVER] Execution paused");
				Thread.sleep(100);
			} else {
				System.out.println("[SOLVER] Iteration " + (i+1) +  " of " + algorithm + " Algorithm.");

                iterationStartTimeNano = System.nanoTime();

				// Waiting helps debugging
				if (this.sleepDuration > 0) {
					System.out.println("[SOLVER] Waiting " + this.sleepDuration + "ms ...");
					Thread.sleep(sleepDuration);
				}

				// Retrieve new neighbors
				neighbors = new Neighbor[this.numberOfNeighbors];
				neighborsSolutions = new FeasibleSolution[this.numberOfNeighbors];
				neighborsCosts = new double[this.numberOfNeighbors];
				neighborsFeatures = new Feature[this.numberOfNeighbors];
				int neighborIndex = 0;
				while ( (neighborIndex < this.numberOfNeighbors) &&
						(neighbors[neighborIndex] = this.neighborhood.getNeighbor(this.solution)) != null ) {
					neighborsSolutions[neighborIndex] = neighbors[neighborIndex].solution;
					neighborsCosts[neighborIndex] = this.objFun.getValue(neighborsSolutions[neighborIndex]);	// Update costs
					neighborsFeatures[neighborIndex] = neighbors[neighborIndex].feature;
					neighborIndex++;
				}

//				// Get neighbors
//				neighbor = this.neighborhood.getNeighbor(this.solution);

				/* If there are still new neighbors within neighborhood */
				if (neighborIndex > 0) {

					/* Determine new solution using algorithm */
					result = this.algorithm.doIteration(this.currentCost, neighborsCosts, neighborsFeatures);

					// Select new solution
					if (result >= 0) {
						boolean betterSolution = this.currentCost > neighborsCosts[result];

						this.solution = neighborsSolutions[result];	// Update solution
						this.currentCost = neighborsCosts[result];	// Update cost

						System.out.println("[SOLVER] Chose new solution @ " + this.solution.getBoxCount() + " Boxes and Cost: " + this.currentCost);

						/* If GUI is active, request to refresh image */
						if (viewer != null) {
							requestGuiUpdate(betterSolution);
						}
					} else {
						System.out.println("[SOLVER] Algorithm rejected all the neighbors, iterating with new neighbors from neighborhood");

//						if (costNeighbor <= this.currentCost + this.boundWorseNeighbor) {
//							// Store the best neighbor that is still acceptable,
//							// in case the searched neighborhood does not have a better solution
//							// TODO: implement? Probably not good, algorithm should handle this anyway
//						}


						//				System.out.println("No better solution found, sticking with current one.");
						//				result = -2;
						//				System.out.println("Terminating...");
						//				solution = solution;


					}
				} else {
					// neighbor returned is null, that means the neighborhood has been searched
					// and no better solution has been found
					// TODO: implement - Probably means that searched area of the neighborhood has to be adjusted
				}


				++i; // Increment iteration counter
				
				/* Check the algorithm specific termination condition */
				if (algorithm.terminated()) {
					System.out.println("[SOLVER] Algorithm termination condition reached. Terminating...");
					break;
				}

				/* Check the time limit */
				if ( (this.timeLimit > 0) && ( (System.nanoTime( ) - startTimeNano) / 1000000000 >= this.timeLimit) ) {
					System.out.println("[SOLVER] Time limit reached. Terminating...");
					break;
				}

                System.out.println("[SOLVER] Iteration took " + (System.nanoTime() - iterationStartTimeNano) + "ns.");
			}
		}

		/* Stop timing */
		long taskTimeNano  = System.nanoTime( ) - startTimeNano;
		long taskTimeMillis = taskTimeNano / 1000000;

		System.out.println("[SOLVER] Terminated after " + i + " iterations, delivered solution below\n\n"
						 + "========================================================================\n");
		this.solution.printToConsole();
		System.out.println("========================================================================\n\n"+
				"[SOLVER] Terminated after " + i + " iterations, delivered solution above\n");

		System.out.println("No. of Gui Updates: " + cntGuiUpdates); // TODO: Disable after debugging

		System.out.println("\nReduced box count by "
				+ (this.initialSolution.getBoxCount() - this.solution.getBoxCount())
				+ " | the initial solution used "
				+ this.initialSolution.getBoxCount()
				+ " boxes. Nice!");

		System.out.println("Elapsed wall clock time: " + (float)taskTimeMillis/1000 + " seconds.");



		/* Force GUI update to display final solution */
		if (viewer != null) {
			updateGUI(true);
		}
	}

	/**
	 * Request and update of the GUI.
	 * Function determines if the GUI will be updated depending on certain conditions
	 */
	private void requestGuiUpdate(boolean betterSolution) {
		// TODO: More options to control
		long currentTime = System.currentTimeMillis();
		if (this.lastGuiUpdate + this.guiUpdateFrequency < currentTime) {
			updateGUI(betterSolution);
			System.out.println("GUI update");
			this.lastGuiUpdate = currentTime;
		}
	}

	/**
	 * Update the GUI to display the current solution, using the Event Dispatch Thread
	 */
	public void updateGUI(boolean betterSolution) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				viewer.updateSolution(Solver.this.solution, betterSolution);
				viewer.validate();
			}
		});

		cntGuiUpdates++;
	}

	public String toString() {
		return "Solver for " + this.solution.getInstance().toString() + " using " + this.algorithm + " on " + this.neighborhood + " neighborhoods [timeLimit = " + this.timeLimit + "s]";
	}
}