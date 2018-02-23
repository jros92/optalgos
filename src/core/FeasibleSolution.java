package core;

import java.util.ArrayList;

public class FeasibleSolution {
	
	private Instance instance;
	private int boxLength;
	private ArrayList<Box> boxes;
	private ArrayList<Rectangle> rectangles;

	/**
	 * Create a new start solution from an instance
	 * @param instance
	 */
	public FeasibleSolution(Instance instance) {
		this.instance = instance;
		this.boxLength = instance.getBoxLength();
		this.boxes = new ArrayList<Box>();
		this.rectangles = new ArrayList<Rectangle>();
		for(Rectangle r : instance.getRectangles())
			this.rectangles.add(r.clone());
		
	}
	
	public Instance getInstance() {
		return this.instance;
	}
	
	public int getBoxLength() {
		return this.boxLength;
	}
	
	public int getBoxCount() {
		return this.boxes.size();
	}
	
	public ArrayList<Box> getBoxes() {
		return this.boxes;
	}
	
	public void addBox(Box box) {
		this.boxes.add(box);
	}
	
	public ArrayList<Rectangle> getRectangles() {
		return this.rectangles;
	}
	
	/**
	 * Remove an empty box.
	 * @param box The box to remove
	 * @return false if box is not empty and cannot be removed, true if successful
	 */
	public boolean removeBox(Box box) {
		if (box.getRectangles().size() > 0) {
			System.out.println("Cannot delete a box that is not empty.");
			return false;
		} else {
			this.boxes.remove(box);
			return true;
		}
	}
	
	/**
	 * Calculate the cumulative packing percentage of the solution
	 * @return
	 */
	public double calculateCumulativePackingPercentage() {
		double result = 0;
		for (Box box : this.boxes) {
			result += (double)box.getOccupiedArea();
		}
		result /= ((double)this.boxes.size() * (double)this.boxLength * (double)this.boxLength);
		return result;
	}
	
	
	/**
	 * 
	 */
	public String printDetailedSolution() {
		String result = "";
		int boxCnt = 0;
		result += String.format("Solution (below) uses " + this.boxes.size() + " boxes and fills %.2f%% of available area.\n", calculateCumulativePackingPercentage()*100);
		for (Box box : this.boxes) {
			result += String.format("Box " + boxCnt + " : " + box.getRectangles().size() + " rectangles : %.2f%% filled\n", box.getPackingPercentage()*100);
			for (Rectangle rect : box.getRectangles()) {
				result += rect.toString() + "\n";
			}
			result += "\n";
			++boxCnt;
		}
		result += String.format("Solution (above) uses " + this.boxes.size() + " boxes and fills %.2f%% of available area.\n", calculateCumulativePackingPercentage()*100);
		return result;
	}
	
	/**
	 * Print the solution to the console
	 */
	public void printToConsole() {
		System.out.println(printDetailedSolution());
	}
	
	/**
	 * Print the solution to the console
	 */
	public void writeToLog() {
		System.out.println(printDetailedSolution());
	}
	
}
