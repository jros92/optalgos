package demo;

import java.util.ArrayList;

import algorithms.LocalSearchAlgorithm;
import algorithms.SimulatedAnnealingAlgorithm;
import algorithms.Solver;
import core.*;

public class Demo {

	private int nInstances;
	private int nRectangles;
	private int lMin;
	private int lMax;
	private int lBox;
	private ArrayList<Instance> instances;


	public Demo(int nInstances, int nRectangles, int lMin, int lMax, int lBox) {
		super();
		this.nInstances = nInstances;
		this.nRectangles = nRectangles;
		this.lMin = lMin;
		this.lMax = lMax;
		this.lBox = lBox;
		
		instances = new ArrayList<Instance>();
		
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
	
	public ArrayList<Instance> getInstances() {
		return instances;
	}
	
	public void runDemo() {
		
		for (Instance instance : instances) {
			
			
			System.out.println(instance.toString());
			
			// Placeholder output
//			for (int i = 0; i < instance.getnRectangles(); i++) {
//				System.out.println(instance.getRectangles().get(i).toString());
//			}
			

			long maxIterations = 100000000L;
			int numberOfNeighbors = 1000;
			
			
			/* The algorithms should be called here, along with the visualization in the small demos */
//			Solver solver = new Solver(new LocalSearchAlgorithm(), new NeighborhoodRuleBased(), new ObjectiveFunction(), instance);
//			solver.solve(maxIterations, numberOfNeighbors);
			
//			Solver solver = new Solver(new SimulatedAnnealingAlgorithm(), new NeighborhoodGeometric(), new ObjectiveFunction(), instance);
//			solver.solve(maxIterations, numberOfNeighbors);
			
			
			
		}
		
		
	}
	
}
