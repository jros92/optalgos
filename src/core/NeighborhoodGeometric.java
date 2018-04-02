/**
 *
 */
package core;

import algorithms.INeighborhood;
import algorithms.IObjectiveFunction;
import algorithms.Neighborhood;

/**
 * Geometric Neighborhood for the 'rectangle fitting' problem
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class NeighborhoodGeometric extends Neighborhood implements INeighborhood {

	public NeighborhoodGeometric() {
		super("Geometric");
	}

	@Override
	public IObjectiveFunction getPreferredObjectiveFunction() {
//		return new ObjectiveFunctionGeometric();
		return new ObjectiveFunction(); // TODO: use its own obj fun
	}

    @Override
    public FeasibleSolution getNeighbor(FeasibleSolution oldSolution) {
		// Create neighbor as new solution
		FeasibleSolution neighbor = oldSolution.clone();

		/* Don't move rectangles from boxes that are already full */
		Box lastBox;
		int i = 0;
		do {
			++i;
			lastBox = neighbor.getBoxes().get(neighbor.getBoxCount() - i);
		} while (lastBox.getPackingPercentage() >= 1);


		boolean moved = false;
		Box newBox;
		int j = 1;
		while (!moved) {
			newBox = neighbor.getBoxes().get(neighbor.getBoxCount() - (i+(j++)));
			if (moved = newBox.addRectangleAtLowerRight(lastBox.getRectangles().get(0)))
				lastBox.getRectangles().remove(lastBox.getRectangles().get(0));
		}



		return neighbor;
    }

	/**
	 * Return a specified number of neighbors as an array
	 * @param solution
	 * @param numberOfNeighbors
	 * @return
	 */
//	@Override
	public FeasibleSolution[] getNeighbors(FeasibleSolution solution, int numberOfNeighbors) {
		FeasibleSolution[] solutions = new FeasibleSolution[numberOfNeighbors];

		Rectangle rect0 = solution.getBoxes().get(0).getRectangles().get(0);

		rect0.moveRight();

		solutions[0] = solution;

		return solutions;
	}



}
