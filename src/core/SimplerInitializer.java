package core;

/**
 * 
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class SimplerInitializer implements IProblemInitializer {

	public FeasibleSolution initialize(Instance instance) {
		FeasibleSolution initialSolution = new FeasibleSolution(instance);
		
		Box currentBox = new Box(instance.getBoxLength());
		initialSolution.addBox(currentBox);
		
		// Place one to two rectangles in each box (Create a bad start state)
		for (Rectangle currentRectangle : instance.getRectangles()) {

			boolean addedAtOrigin = currentBox.addRectangleAtOrigin(currentRectangle);
			
			if (!addedAtOrigin) {
				
				int addedResult = currentBox.addRectangleAtFirstFreePosition(currentRectangle);
				
				if (currentRectangle != instance.getRectangles().get(instance.getRectangles().size()-1)) {
					currentBox = new Box(instance.getBoxLength());
					initialSolution.addBox(currentBox);
				}
				
				if (addedResult < 0) currentBox.addRectangleAtFirstFreePosition(currentRectangle);
			}

		}
		
		return initialSolution;
	}

}
