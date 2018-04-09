package demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algorithms.*;
import core.*;
import gui.FormMain;
import gui.FormSolutionViewer;
import algorithms.Timing;
import org.apache.logging.log4j.*;

public class Demo {

	private int nInstances;
	private int nRectangles;
	private int lMin;
	private int lMax;
	private int lBox;
	private boolean showSolutions;
	private boolean solverLogging;
	private ArrayList<Instance> instances;
	private ArrayList<FeasibleSolution> solutions;

	private static final Logger logger = LogManager.getLogger("DemoLogger");

	public Demo(int nInstances, int nRectangles, int lMin, int lMax, int lBox, boolean showSolutions, boolean solverLogging) {
		super();
		this.nInstances = nInstances;
		this.nRectangles = nRectangles;
		this.lMin = lMin;
		this.lMax = lMax;
		this.lBox = lBox;
		this.showSolutions = showSolutions;
		this.solverLogging = solverLogging;

		instances = new ArrayList<Instance>();
		solutions = new ArrayList<FeasibleSolution>();
		
		InstanceGenerator1 iGen1 = new InstanceGenerator1();
		
		for (int i = 0; i < nInstances; i++) {
			Instance instance = iGen1.generate(nRectangles, lMin, lMax, lBox);
			instances.add(instance);
		}

		/* Set up loggers */
		String demoLogfileTimeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		System.setProperty("demoLogfileTimeStamp", demoLogfileTimeStamp);
		String logfilePrefix = ("demo"
				+ "_nI_"
				+ this.nInstances
				+ "_n_"
				+ this.nRectangles
				+ "_L_"
				+ this.lMin
				+ "_-_"
				+  this.lMax
				+ "_bxln_"
				+ this.lBox
		).replaceAll("\\s+","");
		System.setProperty("demoLogfilePrefix", logfilePrefix);
		org.apache.logging.log4j.core.LoggerContext ctx =
				(org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		ctx.reconfigure();
		System.out.println("Logging Level for Performance Logger is: " + logger.getLevel());
	}
	
	public int getnInstances() {
		return nInstances;
	}
	
	public int getnRectangles() {
		return nRectangles;
	}
	
	int getlMin() {
		return lMin;
	}
	
	void setlMin(int lMin) {
		this.lMin = lMin;
	}
	
	int getlMax() {
		return lMax;
	}
	
	void setlMax(int lMax) {
		this.lMax = lMax;
	}
	
	int getlBox() {
		return lBox;
	}
	
	void setlBox(int lBox) {
		this.lBox = lBox;
	}

	boolean isShowSolutions() {return this.showSolutions; }
	
	public ArrayList<Instance> getInstances() {
		return this.instances;
	}

	public ArrayList<FeasibleSolution> getSolutions() {
		return this.solutions;
	}

	public void runDemo() {

		// Header for log file
		logger.info("Run #;Instance #;nRectangles;lMin;lMax;Box Length;Algorithm;" +
				"Neighborhood;Wall Clock Time [ms];CPU Time [ms];User Time [ms];" +
				"System Time [ms];Box Count;Packing %;Cost");

		int instanceCount = 0;
		int runCount = 0;

		/* Start timing for entire demo run */
		long startTimeDemoNano = System.nanoTime();
		long startSystemTimeNano = Timing.getSystemTime();
		long startUserTimeNano   = Timing.getUserTime();
		long startCPUTimeNano 	 = Timing.getCpuTime();


		for (Instance instance : instances) {

			++instanceCount;

			System.out.println("Demo starting to process instance " + instanceCount + " of " + this.nInstances + ": " + instance.toString());
			
			// Placeholder output
//			for (int i = 0; i < instance.getnRectangles(); i++) {
//				System.out.println(instance.getRectangles().get(i).toString());
//			}
			

			long maxIterations = 1000000L;
			int numberOfNeighbors = 500;
			
			
			/* The algorithms should be called here, along with the visualization in the small demos */
			for (Algorithms algorithmChoice : Algorithms.values()) {
				for (Neighborhoods neighborhoodChoice : Neighborhoods.values()) {
					++runCount;

					// Generate objects for algo and nbh
					IOptimizationAlgorithm algorithm = Algorithm.generateInstance(algorithmChoice);
					INeighborhood neighborhood = Neighborhood.generateInstance(neighborhoodChoice);

					// Generate a new solver object
					Solver solver = new Solver(algorithm, neighborhood, instance, maxIterations, numberOfNeighbors);
					solver.setSleepDuration(0);
					solver.enableLogging(this.solverLogging);

					// Start timing for this algorithm
					long startSolverTimeNano 		= System.nanoTime();
					long startSolverSystemTimeNano  = Timing.getSystemTime();
					long startSolverUserTimeNano    = Timing.getUserTime();
					long startSolverCPUTimeNano 	= Timing.getCpuTime();

					// Do not start solver as a new thread, but one after the other
					try {
						solver.solve();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// Once the solver terminates, Stop timing and log data
					long taskSolverElapsedTimeMillis = (System.nanoTime() - startSolverTimeNano) / 1000000;
					long taskSolverUserTimeMillis    = (Timing.getUserTime() - startSolverUserTimeNano) / 1000000;
					long taskSolverSystemTimeMillis  = (Timing.getSystemTime() - startSolverSystemTimeNano) / 1000000;
					long taskSolverCPUTimeMillis	 = (Timing.getCpuTime() - startSolverCPUTimeNano) / 1000000;

					// Log data
					logger.info(runCount + ";" +
							instanceCount + ";" +
							instance.getnRectangles() + ";" +
							instance.getMinLength() + ";" +
							instance.getMaxLength() + ";" +
							instance.getBoxLength() + ";" +
							algorithmChoice + ";" +
							neighborhoodChoice + ";" +
							taskSolverElapsedTimeMillis + ";" +
							taskSolverCPUTimeMillis + ";" +
							taskSolverUserTimeMillis + ";" +
							taskSolverSystemTimeMillis + ";" +
							solver.getSolution().getBoxCount() + ";" +
							solver.getSolution().calculateCumulativePackingPercentage() + ";" +
							neighborhood.getPreferredObjectiveFunction().getValue(solver.getSolution()));


					// add the solution to the list of solutions
					solutions.add(solver.getSolution());

					// then, open up a solution viewer and display the solution
					if (this.showSolutions) {
						FormSolutionViewer solutionViewer = new FormSolutionViewer(solver.getSolution(), FormMain.getDpi());
						solutionViewer.setVisible(true);
					}
				}
			}


		}

		/* Stop timing for entire demo run */
		long taskElapsedTimeNano = System.nanoTime() - startTimeDemoNano;
		long taskUserTimeNano    = Timing.getUserTime() - startUserTimeNano;
		long taskSystemTimeNano  = Timing.getSystemTime() - startSystemTimeNano;
		long taskCPUTimeNano	 = Timing.getCpuTime() - startCPUTimeNano;

		System.out.println("Batch process (Demo) ended after " + " seconds");
	}

	@Override
	public String toString() {
		return "Demo <nI=" + this.nInstances
				+ ", nR=" + this.nRectangles
				+ ", LR=" + this.lMin
				+ ".." + this.lMax
				+ ", LB=" + this.lBox
				+ ">";
	}
	
}
