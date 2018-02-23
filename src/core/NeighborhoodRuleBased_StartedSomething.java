package core;

import java.util.ArrayList;
import java.util.Random;

import algorithms.INeighborhood;
import algorithms.Neighborhood;

/**
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class NeighborhoodRuleBased_StartedSomething extends Neighborhood implements INeighborhood {
	
	public NeighborhoodRuleBased_StartedSomething() {
		super("Rule-based");
	}
	
	/**
	 * Return a specified number of neighbors as an array
	 * @param solution
	 * @param numberOfNeighbors
	 * @return
	 */
	@Override
	public FeasibleSolution[] getNeighbors(FeasibleSolution solution, int numberOfNeighbors) {
		FeasibleSolution[] solutions = new FeasibleSolution[numberOfNeighbors];

		Random rand = new Random();
		
		for (int i = 0; i < numberOfNeighbors; ++i) {
//			solutions[i] = getNeighbor(solution, i, rand.nextFloat());
		}
	
		return solutions;
	}
	

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
