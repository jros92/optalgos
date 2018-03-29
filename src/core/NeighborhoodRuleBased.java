package core;

import java.util.ArrayList;
import java.util.Random;

import algorithms.INeighborhood;
import algorithms.Neighborhood;

/**
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class NeighborhoodRuleBased extends Neighborhood implements INeighborhood {


	private int indexSmall, indexBig;

	public NeighborhoodRuleBased() {
		super("Rule-based");
		this.indexSmall = 1;	// start at 2nd rectangle, leave 1st in place (TODO: change this?)
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


		// Switch up rectangles (i) and (n-i)
		Rectangle temp = oldRectangles.get(indexSmall);
		oldRectangles.set(indexSmall, oldRectangles.get(nRectangles-1-indexSmall));
		oldRectangles.set(nRectangles-1-indexSmall, temp);

			
		// TODO: THE FOLLOWING CODE TAKEN FROM SIMPLE_INITIALIZER. Duplicate code right now.
		/* Create and add first box */
		FeasibleSolution neighbor = new FeasibleSolution(oldSolution.getInstance(), oldRectangles);
		Box currentBox = new Box(boxLength);
		neighbor.addBox(currentBox);
			
		Rectangle currentRectangle;

		String solutionListOfRectangles = "";

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

		System.out.println("[NEIGHBORHOOD] Solution with (" + neighbor.getRectangles().size() + ") Rectangles: " + solutionListOfRectangles);

		++this.indexSmall; // TODO: when all neighbors have been looked at, tell the algorithm that it has to terminate or sth...

		return neighbor;
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
