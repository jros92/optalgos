package core;

public interface IProblemInitializer {
	
	/**
	 * Initialize a feasible solution from a given instance
	 * @param instance
	 * @return
	 */
	public FeasibleSolution initialize(Instance instance);
	
}
