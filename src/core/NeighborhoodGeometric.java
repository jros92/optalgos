/**
 *
 */
package core;

import algorithms.INeighborhood;
import algorithms.IObjectiveFunction;
import algorithms.Neighborhood;
import java.util.Random;

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
//		Box lastBox;
//		int i = 0;
//		do {
//			++i;
//			lastBox = neighbor.getBoxes().get(neighbor.getBoxCount() - i);
//		} while (lastBox.getPackingPercentage() >= 1);
//
//
//
//
//		boolean moved = false;
//		Box newBox;
//		int j = 1;
//		while (!moved) {
//			newBox = neighbor.getBoxes().get(neighbor.getBoxCount() - (i+(j++)));
//			if (moved = newBox.addRectangleAtLowerRight(lastBox.getRectangles().get(0)))
//				lastBox.getRectangles().remove(lastBox.getRectangles().get(0));
//		}


		/* Take a random box and try to move rectangles out of it */
		Random rand = new Random();
		int boxIndex = rand.nextInt(neighbor.getBoxCount() - 1) + 1;

		Box box1 = neighbor.getBoxes().get(boxIndex);
		// Always take last rectangle in box
		Rectangle lastRect = box1.getRectangles().get(box1.getRectangles().size() - 1);

		/* Execute either a translation within the box or move to a different box at random */
		int actionSelector = rand.nextInt(2);

		if (actionSelector == 0) {
			/* Try to move to box to the left */
			Box prevBox = neighbor.getBoxes().get(boxIndex-1);
			if (prevBox.addRectangleAtFirstFreePosition(lastRect) > -1) {
				box1.removeRectangle(lastRect);
				if (box1.getRectangles().size() == 0) neighbor.removeBox(box1);
			}
		} else {
			/* Try to move up and left until  */
				if (!box1.tryTranslate(lastRect, TranslationDirections.Up))
					box1.tryTranslate(lastRect, TranslationDirections.Left);
			// TODO: Check for bugs!!!!
		}


		return neighbor;
    }



}
