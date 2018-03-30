package algorithms;

import java.awt.EventQueue;

import javax.swing.JButton;

import core.FeasibleSolution;
import core.IProblemInitializer;
import core.Instance;
import core.SimpleInitializer;
import gui.FormSolutionViewer;

/**
 * Controls the execution of the algorithm
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class Solver implements Runnable {
	
	private IOptimizationAlgorithm algorithm;
	private INeighborhood neighborhood;
	private IObjectiveFunction objFun;
	
	private FeasibleSolution solution;
	
	private FormSolutionViewer viewer;
	
	private long maxIterations;
	private double boundWorseNeighbor;
	private int numberOfNeighbors;

	/* Sleep duration between iterations in milliseconds */
	private int sleepDuration;
	
	/* TRUE if the algorithm execution is paused (e.g. by the user) */
	private boolean pause = false;
	
	/**
	 * Instantiate a new solver for a given instance, algorithm, neighborhood and objective function
	 * @param algorithm
	 * @param neighborhood
	 * @param objFun
	 * @param instance
	 * @param maxIterations
	 * @param numberOfNeighbors
	 */
	public Solver(IOptimizationAlgorithm algorithm, INeighborhood neighborhood, IObjectiveFunction objFun, Instance instance, long maxIterations, int numberOfNeighbors) {
		this.algorithm = algorithm;
		this.neighborhood = neighborhood;
		this.objFun = objFun;
		
		/* Initialize a "bad" solution */
		IProblemInitializer problemInitializer = new SimpleInitializer();
		FeasibleSolution initialSolution = problemInitializer.initialize(instance);
		
		this.solution = initialSolution;	
		
		this.maxIterations = maxIterations;
		this.numberOfNeighbors = numberOfNeighbors;

		this.sleepDuration = 250;

		this.boundWorseNeighbor = 100; // TODO: Adjust with obj. function

//		this.algorithm.setNeighborhood(neighborhood);
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

	public void setSleepDuration(int sleepDuration) {
		this.sleepDuration = sleepDuration;
	}

	public void solve() throws InterruptedException {
		       
		System.out.println("[SOLVER] Started solver for " + objFun + " using " + this.algorithm + " on " + this.neighborhood + " neighborhoods.");
		
		
		/* If GUI is active, refresh image */
//		if (viewer != null) {
//
////				viewer.solutionDisplayPanel.revalidate();
////				viewer.solutionDisplayPanel.repaint();
//			viewer.revalidate();
////				viewer.repaint();
//			viewer.repaintCustom();
//		}
//		algorithm.initialize(); // TODO: not necessary because already initialized, right?

		
		
		FeasibleSolution[] neighbors;
		FeasibleSolution neighbor;
		double cost;
		double costNeighbor;
		double[] costs;
		
		long i = 0;
		int result = -1;
		
//		while (i < 1) {
		while ((i < this.maxIterations) && (result >= -1)) {
			if (this.pause) {
				System.out.println("[SOLVER] Execution paused");
				Thread.sleep(100);
			} else {
				System.out.println("[SOLVER] Iteration " + (i+1) +  " of " + algorithm + " Algorithm.");
				
				// Waiting helps debugging
				if (this.sleepDuration > 0) {
					System.out.println("[SOLVER] Waiting " + this.sleepDuration + "ms ...");
					Thread.sleep(sleepDuration);
				}

				// Get neighbors
				neighbor = this.neighborhood.getNeighbor(this.solution);

				if (neighbor != null) {

					// Update cost
					cost = this.objFun.getValue(this.solution);
					costNeighbor = this.objFun.getValue(neighbor);
					//				costs = this.objFun.getValuesForNeighbors(neighbors);

					System.out.println("[SOLVER] Current cost: " + cost);

					// Determine new solution using algorithm
					//				result = this.algorithm.doIteration(cost, costs);
					result = this.algorithm.doIteration(cost, costNeighbor);

					// Select new solution
					if (result >= 0) {
						this.solution = neighbor;
						System.out.println("[SOLVER] Found better solution, changing neighborhood.");

						/* If GUI is active, refresh image */
						if (viewer != null) {
							updateGUI();
						}
					} else {
						System.out.println("[SOLVER] No better solution in neighborhood, iterating with new neighbor");

						if (costNeighbor <= cost + this.boundWorseNeighbor) {
							// Store the best neighbor that is still acceptable,
							// in case the searched neighborhood does not have a better solution
							// TODO: implement
						}


						//				System.out.println("No better solution found, sticking with current one.");
						//				result = -2;
						//				System.out.println("Terminating...");
						//				solution = solution;


					}
				} else {
					// neighbor returned is null, that means neighborhood has been searched and no better solution has been found
					// TODO: implement
				}


				++i; // Increment iteration counter
				
				/* Check the algorithm specific termination condition */
				if (algorithm.terminated()) {
					System.out.println("[SOLVER] Algorithm termination condition reached. Terminating...");
					break;
				}
			}
		}
		
		System.out.println("[SOLVER] Terminated after " + i + " iterations, delivered solution below\n\n"
				+ "=============================================\n");
		solution.printToConsole();
	}
	
	/**
	 * Update the solution in the GUI, using the Event Dispatch Thread
	 */
	public void updateGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				viewer.solutionDisplayPanel.add(new JButton());
//				viewer.solutionDisplayPanel.revalidate();
//				viewer.revalidate();
//				viewer.repaint();
//				viewer.repaintCustom();
				
//				viewer.solutionDisplayPanel.revalidate();
//				viewer.solutionDisplayPanel.repaint();
				
				viewer.updateSolution(Solver.this.solution);
				viewer.validate();
			}
		});
	}


}