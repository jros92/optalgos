package algorithms;

import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.Date;

import core.FeasibleSolution;
import core.IProblemInitializer;
import core.Instance;
import core.SimpleInitializer;

import gui.FormSolutionViewer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

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

	private double boundWorseNeighbor;
	private int numberOfNeighbors;
	private double currentCost;
	private long lastGuiUpdate = 0;
	private int cntGuiUpdates = 0;
    private long pausedTime = 0, lastPausedTime = 0;

	/* User parameters */
	private int sleepDuration;		/* Sleep duration between iterations in milliseconds */
	private boolean paused = false;	/* TRUE if the algorithm execution is paused (e.g. by the user) */
	private int maxIterations;		/* maximum number of iterations */
	private int timeLimit = 10;		/* time limit in seconds; set <= 0 for no termination time criterion */
	private boolean autoTerminate;	/* Terminate as soon as nothing significant has changed for a while */
	private int guiUpdateFrequency = 100; /* Maximum update frequency for GUI, in milliseconds */


	/* Loggers */
//	static final Logger logger = LogManager.getLogger(Solver.class.getName());
	static final Logger logger = LogManager.getLogger("log");
	static final Logger loggerPerf = LogManager.getLogger("loggerPerf");

	/**
	 * Instantiate a new solver for a given instance, using a specified algorithm and neighborhood
	 * @param algorithm
	 * @param neighborhood
	 * @param instance
	 * @param maxIterations
	 * @param numberOfNeighbors
	 */
	public Solver(IOptimizationAlgorithm algorithm, INeighborhood neighborhood,
				  Instance instance, int maxIterations, int numberOfNeighbors) {
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

		// Configure Logging Level
		/* Levels to choose from: OFF, ERROR, WARN, INFO, DEBUG */
		Configurator.setLevel(LogManager.getLogger(Solver.class).getName(), Level.INFO);

		/* Set up loggers */
//		logger.info("Logging Level is: " + logger.getLevel());

		String logfileTimeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		System.setProperty("logfileTimeStamp", logfileTimeStamp);
		String logfilePrefix = (this.algorithm
				+ "_on_"
				+ this.neighborhood
				+ "_n_"
				+ this.initialSolution.getRectangles().size()
				+ "_L_"
				+ this.initialSolution.getInstance().getMinLength()
				+ "_-_"
				+  this.initialSolution.getInstance().getMaxLength()
				+ "_bxln_"
				+ this.initialSolution.getBoxLength()
		).replaceAll("\\s+",""); //remove whitespaces for filename
		System.setProperty("logfilePrefix", logfilePrefix);
		org.apache.logging.log4j.core.LoggerContext ctx =
				(org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		ctx.reconfigure();
//		logger.info("Logging Level for Performance Logger is: " + loggerPerf.getLevel());
		loggerPerf.info("time elapsed [ms]" + ";" + "iteration" + ";" + "box count" + ";" + "cost");
		// store performance data for initial solution
		loggerPerf.info("0" + ";" + "0" + ";" + this.solution.getBoxCount() + ";" + this.currentCost);
	}
	
	@Override
	public void run() {
		try {
			this.solve();
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.fatal(e.getStackTrace().toString());
		}
	}
	
	public FeasibleSolution getSolution() {
		return this.solution;
	}
	
	public void setViewer(FormSolutionViewer viewer) {
		this.viewer = viewer;
	}

    /**
     * Set the time limit (wall clock time) for the termination of the solver
     * @param timeLimit the desired time limit in seconds
     */
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
		logger.warn("Time Limit readjusted to " + timeLimit + " seconds");
	}

    /**
     * Pause the solver and update the GUI
     */
	public void pause() {
	    this.paused = true;
        this.lastPausedTime = System.nanoTime();
        logger.warn("[SOLVER] Execution paused by user");
	    updateGUI(false);
    }

    /**
     * Resume the solver
     */
    public void resume() {
        logger.warn("[SOLVER] Execution resumed by user");
        this.pausedTime += (System.nanoTime() - this.lastPausedTime)/1000000;
	    this.paused = false;
    }

    /**
     * Check if the solver is currently paused
     * @return true if the solver is currently paused, false otherwise
     */
    public boolean isPaused() {
	    return this.paused;
    }

	public void setGuiUpdateFrequency(int guiUpdateFrequency) {
		this.guiUpdateFrequency = guiUpdateFrequency;
	}

	public void setSleepDuration(int sleepDuration) {
		this.sleepDuration = sleepDuration;
	}

	/**
	 * Turn logging on or off
	 * @param enabled true to enable logging, false to disable logging
	 */
	public void enableLogging(boolean enabled) {
		// TODO: More granular options to control logging? (Console, Logfile, Performance file, Errors...)? NO TIME
		if (enabled)
			Configurator.setLevel("log", Level.INFO); //TODO: Option to control performance logging as well?
		else
			Configurator.setLevel("log", Level.OFF);

	}

	/**
	 * Run the solver.
	 * Can be called directly (blocking) or as its own thread, example:
	 * Thread solverThread = new Thread(solver); solverThread.start();
	 * The latter is preferred for GUI applications.
	 * @throws InterruptedException
	 */
	public void solve() throws InterruptedException {
		       
		logger.info("[SOLVER] Started " + this.toString() + ".");

		/* Start timing */
		long startTimeNano = System.nanoTime();
        long iterationStartTimeNano;

		/* Initializations */
		Neighbor[] neighbors;
		FeasibleSolution[] neighborsSolutions;
		Feature[] neighborsFeatures;
		double[] neighborsCosts;
		
		long i = 0;
		int result = -1;

		/* Iteration */
		while (i < this.maxIterations) {
			if (this.paused) {
				Thread.sleep(100);
			} else {
				logger.debug("[SOLVER] Iteration " + (i+1) +  " of " + algorithm + " Algorithm.");

                iterationStartTimeNano = System.nanoTime();

				// Sleep between iterations if desired
//				if (this.sleepDuration > 0) {
//					logger.info("[SOLVER] Waiting " + this.sleepDuration + "ms ...");
//					Thread.sleep(sleepDuration);
//				}

				boolean neighborhoodDepleted = false;

				// Retrieve new neighbors
				neighbors = new Neighbor[this.numberOfNeighbors];
				neighborsSolutions = new FeasibleSolution[this.numberOfNeighbors];
				neighborsCosts = new double[this.numberOfNeighbors];
				neighborsFeatures = new Feature[this.numberOfNeighbors];
				int neighborIndex = 0;

				// TODO: can neighborhood be depleted? Is there a way to still go on?
				while (neighborIndex < this.numberOfNeighbors) {

					if ((neighbors[neighborIndex] = this.neighborhood.getNeighbor(this.solution)) == null ) {
						neighborhoodDepleted = true;
						break;
					}

					neighborsSolutions[neighborIndex] = neighbors[neighborIndex].solution;
					neighborsFeatures[neighborIndex] = neighbors[neighborIndex].feature;
					// Update cost
					neighborsCosts[neighborIndex] = this.objFun.getValue(neighborsSolutions[neighborIndex]);
					neighborIndex++;
				}

				/* If Neighborhood is depleted, terminate */
				if (neighborhoodDepleted) {
					logger.info("[SOLVER] Neighborhood depleted. Terminating...");
					break;
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

						logger.info("[SOLVER] Chose new solution @ iteration " + (i+1)
								+ " with " + this.solution.getBoxCount() + " Boxes and Cost: " + this.currentCost);
						loggerPerf.info(((System.nanoTime( ) - startTimeNano) / 1000000)
								+ ";" + (i+1) + ";" + this.solution.getBoxCount() + ";" + this.currentCost);
						/* If GUI is active, request to refresh image */
						if (viewer != null) {
							requestGuiUpdate(betterSolution);
						}
					} else {
						logger.debug("[SOLVER] Algorithm rejected all the neighbors," +
								" iterating with new neighbors from neighborhood");

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
					logger.info("[SOLVER] Algorithm termination condition reached. Terminating...");
					break;
				}

				/* Check the time limit */
				if ( (this.timeLimit > 0)
                        && ( (System.nanoTime()-startTimeNano)/1000000000 >= this.timeLimit + this.pausedTime/1000) ) {
					logger.info("[SOLVER] Time limit reached. Terminating...");
					break;
				}

                logger.debug("[SOLVER] Iteration took "
						+ ((double)(System.nanoTime() - iterationStartTimeNano) / 1000000.0) + "ms.");
			}
		}

		/* Stop timing */
		long taskTimeNano  = System.nanoTime( ) - startTimeNano;
		long taskTimeMillis = taskTimeNano / 1000000;

		// Print and log statements
		printAndLogResults(i);

		String elapsedTimeString = "Elapsed wall clock time: " + (float)taskTimeMillis/1000 + " seconds";
		if (pausedTime > 0)
		    elapsedTimeString += " (" + pausedTime + "ms of which were paused)";
		elapsedTimeString += ".";
		logger.info(elapsedTimeString);

		/* Force GUI update to display final solution */
		if (viewer != null) {
			updateGUI(true);
		}
	}

	/**
	 * Request an update of the GUI.
	 * Function determines if the GUI will be updated depending on certain conditions
	 * @param betterSolution if false, update will only take place if so desired by the user; true will always update
	 */
	private void requestGuiUpdate(boolean betterSolution) {
		// TODO: More options to control
		long currentTime = System.currentTimeMillis();
		if (this.lastGuiUpdate + this.guiUpdateFrequency < currentTime) {
			updateGUI(betterSolution);
			logger.info("Requesting GUI update");
			this.lastGuiUpdate = currentTime;
		}
	}

	/**
	 * Update the GUI to display the current solution, using the Event Dispatch Thread
	 * @param betterSolution if false, update will only take place if so desired by the user; true will always update
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

	/**
	 * print and log information after termination of solver
	 * @param i last iteration index, will be displayed
	 */
	private void printAndLogResults(long i) {
		logger.info("[SOLVER] Terminated after " + i + " iterations, delivered solution below\n\n"
				+ "========================================================================\n");
		logger.info(this.solution.printDetailedSolution()
				+ "========================================================================\n\n"
				+ "[SOLVER] Terminated after " + i + " iterations, delivered solution above\n");

		logger.info("GUI updates during solving process: " + cntGuiUpdates);

		int reducedBoxCount = this.initialSolution.getBoxCount() - this.solution.getBoxCount();
		String resultMsg = "Reduced box count by "
				+ reducedBoxCount
				+ " | the initial solution used "
				+ this.initialSolution.getBoxCount()
				+ " boxes.";
		if (reducedBoxCount > 0) resultMsg += " Nice!";
		logger.info(resultMsg);
	}

	public String toString() {
		return "Solver for " + this.solution.getInstance().toString()
				+ " using " + this.algorithm
				+ " on " + this.neighborhood
				+ " neighborhoods";
//                + "[timeLimit = " + this.timeLimit + "s]";
	}
}