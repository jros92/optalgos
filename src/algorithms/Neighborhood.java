package algorithms;

import core.NeighborhoodGeometric;
import core.NeighborhoodGeometricNew;
import core.NeighborhoodRuleBased;
import core.Neighborhoods;

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
			case GeometricNew:
				result = new NeighborhoodGeometricNew();
				break;
			default:
				System.out.println("ERROR creating selected neighborhood. add new neighborhoods to Neighborhood.generateInstance()");
				result = null;
				break;
		}

		return result;
	}
}
