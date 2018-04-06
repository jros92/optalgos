package core;

import java.util.ArrayList;

import algorithms.INeighborhood;
import algorithms.IObjectiveFunction;
import algorithms.Neighbor;
import algorithms.Neighborhood;

/**
 * Rule-Based Neighborhood for Rectangle Fitting optimization problem
 * Features are pairs of rectangles that are swapped in the list of rectangles
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

	@Override
	public IObjectiveFunction getPreferredObjectiveFunction() {
//		return new ObjectiveFunctionNegative();
		return new ObjectiveFunction();
	}

	/**
	 * Find and return a neighbor
	 * @param oldSolution
	 * @return one neighbor out of the current neighborhood, null if there are no more neighbors
	 */
	@Override
	public Neighbor getNeighbor(FeasibleSolution oldSolution) {

		ArrayList<Rectangle> oldRectangles = oldSolution.getRectangles();

		// Create neighbor as new solution
		// This function clones the rectangles but deletes all the boxes
		FeasibleSolution neighboredSolution = new FeasibleSolution(oldSolution.getInstance(), oldRectangles);

		// Switch up list of rectangles of neighbor
		RectanglePair swappedRectangles;
		if ((swappedRectangles = reorderRectangles(neighboredSolution.getRectangles())) == null) return null;

		/* Create solution (boxes) from reordered permutation of rectangles */
		createSolutionBasedOnRule(neighboredSolution);

//		System.out.println("[NEIGHBORHOOD] Returned solution with the following ordered set of (" + neighbor.getRectangles().size() + ") Rectangles: " + solutionListOfRectangles); // used for debugging
		Neighbor neighbor = new Neighbor(neighboredSolution, swappedRectangles);
		return neighbor;
	}


	/**
	 *
	 * @param neighborsRectangles
	 * @return
	 */
	public RectanglePair reorderRectangles(ArrayList<Rectangle> neighborsRectangles) {
		int nRectangles = neighborsRectangles.size();

		if (this.indexBig == -1) this.indexBig = nRectangles;	// Initialize indexBig

		if (this.indexBig <= this.indexSmall + 1) {
			++this.indexSmall;
			this.indexBig = nRectangles; // Reset indexBig
		}

				/* Increment/Reset indices and check termination condition */
		if (this.indexSmall >= (nRectangles - 1)) {
			// terminate
			// TODO: when all neighbors have been looked at, tell the algorithm that it has to terminate or sth...
			System.out.println("[NEIGHBORHOOD] Entire neighborhood has been searched.");
			return null;
		} else {
			--this.indexBig;
		}



		// Switch up rectangles
		Rectangle temp = neighborsRectangles.get(this.indexSmall);
		neighborsRectangles.set(this.indexSmall, neighborsRectangles.get(this.indexBig));
		neighborsRectangles.set(this.indexBig, temp);

		return new RectanglePair(neighborsRectangles.get(indexSmall), neighborsRectangles.get(indexBig));
	}

	/**
	 *
	 * @param neighboredSolution
	 */
	private void createSolutionBasedOnRule(FeasibleSolution neighboredSolution) {

		int boxLength = neighboredSolution.getBoxLength();
		int nRectangles = neighboredSolution.getRectangles().size();

		/* Create and add first box */
		Box currentBox = new Box(boxLength, 0);
		neighboredSolution.addBox(currentBox);

		int boxIndex = 1;

		Rectangle currentRectangle;

		String solutionListOfRectangles = "";

		// TODO: THE FOLLOWING CODE TAKEN FROM SIMPLE_INITIALIZER. Duplicate code right now. Create a function?
		for (int j = 0; j < nRectangles; j++) {
			//currentRectangle = oldRectangles.get(j);
			currentRectangle = neighboredSolution.getRectangles().get(j);

			int	addedResult = currentBox.addRectangleAtFirstFreePosition(currentRectangle);

			if (addedResult <= 0) {
				if (addedResult < 0) {
					// current rectangle has not been placed, so process it again in next loop step
					--j;
				}

				if (j < nRectangles - 1) {
					// If this is not the last rectangle and it has successfully been placed within a box, dont open a new one
					currentBox = new Box(boxLength, boxIndex);
					neighboredSolution.addBox(currentBox);
					boxIndex++;
				}
			}  else {
//				solutionListOfRectangles += currentRectangle.toString() + ", ";	// used for debugging
			}

		}
	}

}
