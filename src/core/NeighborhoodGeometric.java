/**
 *
 */
package core;

import algorithms.INeighborhood;
import algorithms.IObjectiveFunction;
import algorithms.Neighborhood;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Geometric Neighborhood for the 'rectangle fitting' problem
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class NeighborhoodGeometric extends Neighborhood implements INeighborhood {

	private FeasibleSolution previousOldSolution;
	private int indexRectangle = 0;
	private LinkedList<Box> ignoredBoxes;

	public NeighborhoodGeometric() {
		super("Geometric");
		ignoredBoxes = new LinkedList<>();
	}

	@Override
	public IObjectiveFunction getPreferredObjectiveFunction() {
//		return new ObjectiveFunctionGeometric();
		return new ObjectiveFunction(); // TODO: use its own obj fun
	}

    @Override
    public FeasibleSolution getNeighbor(FeasibleSolution oldSolution) {
		if (!oldSolution.equals(previousOldSolution)) {
			// Algorithm chose previous neighbor, so reset all the instance fields
			previousOldSolution = oldSolution;
			indexRectangle = 0;
			ignoredBoxes = new LinkedList<>();
		}

		// Create neighbor as new solution
		FeasibleSolution neighbor = oldSolution.clone();


		// Get the box to empty
//		Box boxToEmpty = neighbor.findLeastFilledBox(ignoredBoxes);
		Box boxToEmpty = null;
		List<Box> filteredBoxes = neighbor.getBoxes().stream().filter(box -> !ignoredBoxes.contains(box))
				.collect(Collectors.toList());
		boxToEmpty = filteredBoxes.get((int)(Math.random() * filteredBoxes.size()));
//				.findAny().get();

		System.out.println("Box to empty: " + boxToEmpty.getId());

//		Rectangle rectangle = boxToEmpty.getRectangles().get(indexRectangle);
		Rectangle rectangle = boxToEmpty.getRectangles().get(0);
		System.out.println("Rectangle to move: [" + indexRectangle + "], " + rectangle.toString());
		indexRectangle++;
		if (indexRectangle >= boxToEmpty.getRectangles().size()) {
			indexRectangle = 0;
			ignoredBoxes.add(boxToEmpty);
		}

		/* Check boxes to add rectangle to */
		ArrayList<Box> boxes = neighbor.getBoxes();
		for (int i = 0; i < boxes.size(); ++i) {
			Box b = boxes.get(i);

			if (b == boxToEmpty) continue;	// do not check same box
			//try to add rectangle to box
			if (b.addRectangleAtFirstFreePosition(rectangle) >= 0) {
				if (boxToEmpty.removeRectangle(rectangle)) neighbor.removeBox(boxToEmpty);
				break;
			} else System.out.println("Rectangle did not fit in box " + b.getId() + ". Moving on to next box.");

			// If rectangle could not be placed in any box, ignore this box in the future and look at a different box
			if (i >= boxes.size() - 1) ignoredBoxes.add(boxToEmpty);

		}



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


//		/* Take a random box and try to move rectangles out of it */
//		Random rand = new Random();
//		int boxIndex = rand.nextInt(neighbor.getBoxCount() - 1) + 1;
//
//		Box box1 = neighbor.getBoxes().get(boxIndex);
//		// Always take last rectangle in box
//		Rectangle lastRect = box1.getRectangles().get(box1.getRectangles().size() - 1);
//
//		/* Execute either a translation within the box or move to a different box at random */
//		int actionSelector = rand.nextInt(2);
//
//		if (actionSelector == 0) {
//			/* Try to move to box to the left */
//			Box prevBox = neighbor.getBoxes().get(boxIndex-1);
//			if (prevBox.addRectangleAtFirstFreePosition(lastRect) > -1) {
//				box1.removeRectangle(lastRect);
//				if (box1.getRectangles().size() == 0) neighbor.removeBox(box1);
//			}
//		} else {
//			/* Try to move up and left until  */
//				if (!box1.tryTranslate(lastRect, TranslationDirections.Up))
//					box1.tryTranslate(lastRect, TranslationDirections.Left);
//			// TODO: Check for bugs!!!!
//		}


		return neighbor;
    }




}
