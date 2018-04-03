package core;

public class SimplestInitializer implements IProblemInitializer {

	@Override
	public FeasibleSolution initialize(Instance instance) {
		FeasibleSolution initialSolution = new FeasibleSolution(instance);

		Box currentBox;

		int boxIndex = 0;
		// Place one rectangle in each box (Create the worst possible start state)
		for (Rectangle currentRectangle : instance.getRectangles()) {
			currentBox = new Box(instance.getBoxLength(), boxIndex);
			currentBox.addRectangleAtOrigin(currentRectangle);
			initialSolution.addBox(currentBox);
			boxIndex++;
		}

		return initialSolution;
	}

}
