package algorithms;

public enum Algorithms {
    LocalSearch,
    SimulatedAnnealing,
    TabooSearch;

    @Override
    public String toString() {
        switch(this) {
            case LocalSearch: return "Local Search";
            case SimulatedAnnealing: return "Simulated Annealing";
            case TabooSearch: return "Taboo Search";
            default: throw new IllegalArgumentException();
        }
    }
}
