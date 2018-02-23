package algorithms;

public abstract class Algorithm {
	
	private final String name;
	
	public Algorithm(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	public static IOptimizationAlgorithm generateInstance(Algorithms choice) {
		IOptimizationAlgorithm result;
		switch (choice) {
			case LocalSearch:
				result = new LocalSearchAlgorithm(20);
				break;
			case SimulatedAnnealing:
				result = new SimulatedAnnealingAlgorithm();
				break;
			case TabooSearch:
				result = new TabooSearchAlgorithm();
				break;
			default:
				result = new LocalSearchAlgorithm(20);
				break;
		}

		return result;
	}
}

