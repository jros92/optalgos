//package core;
//
//import algorithms.INeighborhood;
//import algorithms.Neighborhood;
//
//import java.util.ArrayList;
//import java.util.Random;
//
///**
// * @author Jörg R. Schmidt <jroschmidt@gmail.com>
// *
// */
//public class NeighborhoodRuleBased_kindaWorkingWithMultiple extends Neighborhood implements INeighborhood {
//
////	private IProblemInitializer problemInitializer;
//
//	public NeighborhoodRuleBased_kindaWorkingWithMultiple() {
//		super("Rule-based");
////		problemInitializer = new SimpleInitializer();
//	}
//
//
//
//	/**
//	 * Return a specified number of neighbors as an array
//	 * @param solution
//	 * @param numberOfNeighbors
//	 * @return
//	 */
//	@Override
//	public FeasibleSolution[] getNeighbors(FeasibleSolution solution, int numberOfNeighbors) {
//
//		ArrayList<Rectangle> oldRectangles = solution.getRectangles();
//		int nRectangles = oldRectangles.size();
//		int boxLength = solution.getBoxLength();
//
//		if (nRectangles < numberOfNeighbors) numberOfNeighbors = nRectangles;
//
//
//		FeasibleSolution[] solutions = new FeasibleSolution[numberOfNeighbors];
//		Random rand = new Random();
//
//		for (int i = 0; (i < numberOfNeighbors) && (i < nRectangles); ++i) {
////			int switchIndex = Math.round((float)rand.nextFloat() * (nRectangles - 1));
//			int switchIndex = i;
//			// Switch up rectangles (i) and (n-i)
//			Rectangle temp = oldRectangles.get(switchIndex);
//			oldRectangles.set(switchIndex, oldRectangles.get(nRectangles-1-switchIndex));
//			oldRectangles.set(nRectangles-1-switchIndex, temp);
////			solutions[i] = problemInitializer.initialize(solution.getInstance());
//
//
//
//			// TODO: THE FOLLOWING CODE TAKEN FROM SIMPLE_INITIALIZER. Duplicate code right now.
//			/* Create and add first box */
//			solutions[i] = new FeasibleSolution(solution.getInstance(), oldRectangles);
//			Box currentBox = new Box(boxLength);
//			solutions[i].addBox(currentBox);
//
//			Rectangle currentRectangle;
//
//			String solutionListOfRectangles = "";
//
//			// Fill boxes randomly (Create "bad" start state)
//			// Actually, this is already a pretty good solution
//			for (int j = 0; j < nRectangles; j++) {
//				//currentRectangle = oldRectangles.get(j);
//				currentRectangle = solutions[i].getRectangles().get(j);
//
//				int	addedResult = currentBox.addRectangleAtFirstFreePosition(currentRectangle);
//
//				if (addedResult <= 0) {
//					if (addedResult < 0) {
//						// current rectangle has not been placed, so process it again in next loop step
//						--j;
//					}
//
//					if (j < nRectangles - 1) {
//						// If this is not the last rectangle and it has successfully been placed within a box, dont open a new one
//						currentBox = new Box(boxLength);
//						solutions[i].addBox(currentBox);
//					}
//				}  else {
//					solutionListOfRectangles += currentRectangle.toString() + ", ";
//				}
//
//
////				if ((currentBox.getMaxFreeDistance() < currentRectangle.getWidth()) && (currentBox.getMaxFreeDistance() < currentRectangle.getLength())) {
////					// Create new box
////					boxes.add(currentBox);
////					currentBox = new Box(instance.getBoxLength());
////
////					currentBox.addRectangleAtFirstFreePosition(currentRectangle);
////
////				}
////
//
//			}
//
//			System.out.println("[NEIGHBORHOOD] Solution [" + i + "] with (" + solutions[i].getRectangles().size() + ") Rectangles: " + solutionListOfRectangles);
//		}
//
//		return solutions;
//	}
//
////	// Super simple and kinda stupid rule
////	@Override
////	public FeasibleSolution[] getNeighbors(FeasibleSolution solution, int numberOfNeighbors) {
////		ArrayList<Rectangle> oldRectangles = solution.getInstance().getRectangles();
////		int n = oldRectangles.size();
////
////		if (n < numberOfNeighbors) numberOfNeighbors = n;
////
////		FeasibleSolution[] solutions = new FeasibleSolution[numberOfNeighbors];
////
////		Random rand = new Random();
////
////		for (int i = 0; (i < numberOfNeighbors) && (i < n); ++i) {
////			// Switch up rectangles (i) and (n-i)
////			Rectangle temp = oldRectangles.get(i);
////			oldRectangles.set(i, oldRectangles.get(n-1-i));
////			oldRectangles.set(n-1-i, temp);
////			solutions[i] = problemInitializer.initialize(solution.getInstance());
////		}
////
////		return solutions;
////	}
//
//
////	public FeasibleSolution getNeighbor(FeasibleSolution oldSolution, int index, float seed) {
////
////		Rectangle currentRectangle;
////
////
////		ArrayList<Box> boxes = oldSolution.getBoxes();
////
////
////		for (int i = 0; i < oldSolution.getnRectangles(); i++) {
////			currentRectangle = oldSolution.getRectangles().get(i);
////
////			int	addedResult = currentBox.addRectangleAtFirstFreePosition(currentRectangle);
////
////			if (addedResult <= 0) {
////				if (addedResult < 0) {
////					// current rectangle has not been placed, so process it again in next loop step
////					--i;
////				}
////
////				if (i < instance.getnRectangles() - 1) {
////					// If this is not the last rectangle and it has successfully been placed withing a box, dont open a new one
////					currentBox = new Box(instance.getBoxLength());
////					initialSolution.addBox(currentBox);
////				}
////			}
////
////
////	//		if ((currentBox.getMaxFreeDistance() < currentRectangle.getWidth()) && (currentBox.getMaxFreeDistance() < currentRectangle.getLength())) {
////	//			// Create new box
////	//			boxes.add(currentBox);
////	//			currentBox = new Box(instance.getBoxLength());
////	//
////	//			currentBox.addRectangleAtFirstFreePosition(currentRectangle);
////	//
////	//		}
////	//
////
////		}
////
////
////		return initialSolution;
////
////	}
//
//}
