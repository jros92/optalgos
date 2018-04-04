package algorithms;

import core.FeasibleSolution;

public class Neighbor {
    public FeasibleSolution solution;
    public Feature feature;

    public Neighbor(FeasibleSolution solution, Feature feature) {
        this.solution = solution;
        this.feature = feature;
    }
}
