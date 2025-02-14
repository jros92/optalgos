package algorithms;

import core.*;

public abstract class Neighborhood {

	private final String name;
	
	public Neighborhood(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	public static INeighborhood generateInstance(Neighborhoods choice) {
		INeighborhood result;
		switch (choice) {
			case Geometric:
				result = new NeighborhoodGeometric();
				break;
			case RuleBased:
				result = new NeighborhoodRuleBased();
				break;
			case GeometricRandom:
				result = new NeighborhoodGeometricRandom();
				break;
//			case RuleBasedImproved:
//				result = new NeighborhoodRuleBasedImproved();
//				break;
			default:
				System.out.println("ERROR creating selected neighborhood. add new neighborhoods to Neighborhood.generateInstance()");
				result = null;
				break;
		}

		return result;
	}
}
