package demo;

import java.util.ArrayList;

import algorithms.*;
import core.*;
import gui.FormMain;
import gui.FormSolutionViewer;

public class Demo {

	private int nInstances;
	private int nRectangles;
	private int lMin;
	private int lMax;
	private int lBox;
	private boolean showSolutions;
	private ArrayList<Instance> instances;
	private ArrayList<FeasibleSolution> solutions;


	public Demo(int nInstances, int nRectangles, int lMin, int lMax, int lBox, boolean showSolutions) {
		super();
		this.nInstances = nInstances;
		this.nRectangles = nRectangles;
		this.lMin = lMin;
		this.lMax = lMax;
		this.lBox = lBox;
		this.showSolutions = showSolutions;
		
		instances = new ArrayList<Instance>();
		solutions = new ArrayList<FeasibleSolution>();
		
		InstanceGenerator1 iGen1 = new InstanceGenerator1();
		
		for (int i = 0; i < nInstances; i++) {
			Instance instance = iGen1.generate(nRectangles, lMin, lMax, lBox);
			instances.add(instance);
		}
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

		int instanceCount = 0;

		for (Instance instance : instances) {

			/* Start timing */
			long startTimeNano = System.nanoTime();

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
					// Generate instances for algo and nbh
					IOptimizationAlgorithm algorithm = Algorithm.generateInstance(algorithmChoice);
					INeighborhood neighborhood = Neighborhood.generateInstance(neighborhoodChoice);

					// Generate a new solver
					Solver solver = new Solver(algorithm, neighborhood, instance, maxIterations, numberOfNeighbors);
					solver.setSleepDuration(0);

					// Do not start solver as a new thread, but one after the other
					try {
						solver.solve();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// Once the solver terminates, add the solution to the list of solutions
					// then, open up a solution viewer and display the solution
					solutions.add(solver.getSolution());
					if (this.showSolutions) {
						FormSolutionViewer solutionViewer = new FormSolutionViewer(solver.getSolution(), FormMain.getDpi());
						solutionViewer.setVisible(true);
					}
				}
			}


			
			
		}
		
		
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
