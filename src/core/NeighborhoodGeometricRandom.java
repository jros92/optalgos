/**
 *
 */
package core;

import algorithms.INeighborhood;
import algorithms.IObjectiveFunction;
import algorithms.Neighbor;
import algorithms.Neighborhood;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Geometric Neighborhood for the 'rectangle fitting' problem
 * purely random selection of rectangles to move
 * fill boxes from the beginning
 * Features are singular rectangles
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class NeighborhoodGeometricRandom extends Neighborhood implements INeighborhood {

	public NeighborhoodGeometricRandom() {
		super("Geometric Random");
	}

	@Override
	public IObjectiveFunction getPreferredObjectiveFunction() {
		return new ObjectiveFunctionGeometric();
	}

    @Override
    public Neighbor getNeighbor(FeasibleSolution oldSolution) {

		// Create neighbor as new solution
		FeasibleSolution neighbor = oldSolution.clone();

		/* Choose the box to take rectangles out of at random */
		int boxIndex = (int)Math.floor(Math.random() * neighbor.getBoxCount());
		Box boxToEmpty = neighbor.getBoxes().get(boxIndex);

		// Always take the first rectangle out of that box
		Rectangle rectangle = boxToEmpty.getRectangles().get(0);

		/* Find the first box we can add the rectangle to */
		ArrayList<Box> boxes = neighbor.getBoxes();
		for (int i = 0; i < boxes.size(); ++i) {
			Box b = boxes.get(i);

			if (b.getFreeArea() < rectangle.getArea()) continue;	// Box too full for rectangle, continue
			if (b == boxToEmpty) continue;	// do not check box that the rectangle came out of

			// try to add rectangle to this box
			if (b.addRectangleAtFirstFreePosition(rectangle) >= 0) {
				// Remove rectangles from box and delete box if this was the last rectangle, then break and return
				if (boxToEmpty.removeRectangle(rectangle)) neighbor.removeBox(boxToEmpty);
				break;
			}
		}

		/* Assemble neighbor with solution and modified feature (the moved rectangle) */
		Neighbor result = new Neighbor(neighbor, rectangle);

		return result;
    }




}
