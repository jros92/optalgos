package core;

/**
 * 
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class SimpleInitializer implements IProblemInitializer {
	
	public FeasibleSolution initialize(Instance instance) {

		FeasibleSolution initialSolution = new FeasibleSolution(instance);
		
		Rectangle currentRectangle;

		/* Create and add first box */
		Box currentBox = new Box(instance.getBoxLength());
		initialSolution.addBox(currentBox);
		
		// Fill boxes randomly (Create "bad" start state)
		// Actually, this is already a pretty good solution
		for (int i = 0; i < initialSolution.getRectangles().size(); i++) {
			currentRectangle = initialSolution.getRectangles().get(i);
			
			int	addedResult = currentBox.addRectangleAtFirstFreePosition(currentRectangle);

			if (addedResult <= 0) {
				if (addedResult < 0) {
					// current rectangle has not been placed, so process it again in next loop step 
					--i;
				}
				
				if (i < initialSolution.getRectangles().size() - 1) {
					// If this is not the last rectangle and it has successfully been placed withing a box, dont open a new one
					currentBox = new Box(initialSolution.getBoxLength());
					initialSolution.addBox(currentBox);
				}
			}
			
			
//			if ((currentBox.getMaxFreeDistance() < currentRectangle.getWidth()) && (currentBox.getMaxFreeDistance() < currentRectangle.getLength())) {
//				// Create new box
//				boxes.add(currentBox);
//				currentBox = new Box(initialSolution.getBoxLength());
//				
//				currentBox.addRectangleAtFirstFreePosition(currentRectangle);
//				
//			}
//			
			
		}
		
		return initialSolution;
	}
	
}
