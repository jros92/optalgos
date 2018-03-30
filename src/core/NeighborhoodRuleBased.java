package core;

import java.util.ArrayList;
import java.util.Random;

import algorithms.INeighborhood;
import algorithms.Neighborhood;
import org.w3c.dom.css.Rect;

/**
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class NeighborhoodRuleBased extends Neighborhood implements INeighborhood {


	private int indexSmall, indexBig;

	public NeighborhoodRuleBased() {
		super("Rule-based");
		this.indexSmall = 0;	// start at 1st rectangle
		this.indexBig = -1;
	}
	

	/**
	 * Return a neighbor
	 * @param oldSolution
	 * @return
	 */
	@Override
	public FeasibleSolution getNeighbor(FeasibleSolution oldSolution) {

		ArrayList<Rectangle> oldRectangles = oldSolution.getRectangles();
		int nRectangles = oldRectangles.size();
		int boxLength = oldSolution.getBoxLength();

		// Create neighbor as new solution
		FeasibleSolution neighbor = new FeasibleSolution(oldSolution.getInstance(), oldRectangles);

		// Switch up list of rectangles of neighbor
		reorderRectangles(neighbor.getRectangles());

		/* Create solution (Boxes) */

		/* Create and add first box */
		Box currentBox = new Box(boxLength);
		neighbor.addBox(currentBox);
			
		Rectangle currentRectangle;

		String solutionListOfRectangles = "";

		// TODO: THE FOLLOWING CODE TAKEN FROM SIMPLE_INITIALIZER. Duplicate code right now. (Check if still true first!)
		for (int j = 0; j < nRectangles; j++) {
			//currentRectangle = oldRectangles.get(j);
			currentRectangle = neighbor.getRectangles().get(j);

			int	addedResult = currentBox.addRectangleAtFirstFreePosition(currentRectangle);

			if (addedResult <= 0) {
				if (addedResult < 0) {
					// current rectangle has not been placed, so process it again in next loop step
					--j;
				}

				if (j < nRectangles - 1) {
					// If this is not the last rectangle and it has successfully been placed within a box, dont open a new one
					currentBox = new Box(boxLength);
					neighbor.addBox(currentBox);
				}
			}  else {
				solutionListOfRectangles += currentRectangle.toString() + ", ";
			}

		}

		System.out.println("[NEIGHBORHOOD] Returned solution with the following ordered set of (" + neighbor.getRectangles().size() + ") Rectangles: " + solutionListOfRectangles);
		return neighbor;
	}


	public void reorderRectangles(ArrayList<Rectangle> neighborsRectangles) {
		int nRectangles = neighborsRectangles.size();

		if (this.indexBig == -1) this.indexBig = nRectangles - 1;	// Initialize indexBig

		if (this.indexBig <= this.indexSmall) {
			++this.indexSmall;
			this.indexBig = nRectangles - 1; // Reset indexBig
		}



		// Switch up rectangles
		Rectangle temp = neighborsRectangles.get(this.indexSmall);
		neighborsRectangles.set(this.indexSmall, neighborsRectangles.get(this.indexBig));
		neighborsRectangles.set(this.indexBig, temp);

		/* Increment/Reset indices and check termination condition */
		if (this.indexSmall >= (nRectangles - 1)) {
			// terminate
			// TODO: when all neighbors have been looked at, tell the algorithm that it has to terminate or sth...
			System.out.println("[NEIGHBORHOOD] Entire neighborhood has been searched.");
		} else {
//			if (this.indexBig > this.indexSmall)
				--this.indexBig;
		}
	}

//	// Super simple and kinda stupid rule
//	@Override
//	public FeasibleSolution[] getNeighbors(FeasibleSolution solution, int numberOfNeighbors) {
//		ArrayList<Rectangle> oldRectangles = solution.getInstance().getRectangles();
//		int n = oldRectangles.size();
//		
//		if (n < numberOfNeighbors) numberOfNeighbors = n;
//		
//		FeasibleSolution[] solutions = new FeasibleSolution[numberOfNeighbors];
//
//		Random rand = new Random();
//		
//		for (int i = 0; (i < numberOfNeighbors) && (i < n); ++i) {
//			// Switch up rectangles (i) and (n-i)
//			Rectangle temp = oldRectangles.get(i);
//			oldRectangles.set(i, oldRectangles.get(n-1-i));
//			oldRectangles.set(n-1-i, temp);
//			solutions[i] = problemInitializer.initialize(solution.getInstance());
//		}
//	
//		return solutions;
//	}
	

//	public FeasibleSolution getNeighbor(FeasibleSolution oldSolution, int index, float seed) {
//		
//		Rectangle currentRectangle;
//			
//		
//		ArrayList<Box> boxes = oldSolution.getBoxes();
//		
//		
//		for (int i = 0; i < oldSolution.getnRectangles(); i++) {
//			currentRectangle = oldSolution.getRectangles().get(i);
//			
//			int	addedResult = currentBox.addRectangleAtFirstFreePosition(currentRectangle);
//	
//			if (addedResult <= 0) {
//				if (addedResult < 0) {
//					// current rectangle has not been placed, so process it again in next loop step 
//					--i;
//				}
//				
//				if (i < instance.getnRectangles() - 1) {
//					// If this is not the last rectangle and it has successfully been placed withing a box, dont open a new one
//					currentBox = new Box(instance.getBoxLength());
//					initialSolution.addBox(currentBox);
//				}
//			}
//			
//			
//	//		if ((currentBox.getMaxFreeDistance() < currentRectangle.getWidth()) && (currentBox.getMaxFreeDistance() < currentRectangle.getLength())) {
//	//			// Create new box
//	//			boxes.add(currentBox);
//	//			currentBox = new Box(instance.getBoxLength());
//	//			
//	//			currentBox.addRectangleAtFirstFreePosition(currentRectangle);
//	//			
//	//		}
//	//		
//		
//		}
//		
//		
//		return initialSolution;
//	
//	}
	
}
