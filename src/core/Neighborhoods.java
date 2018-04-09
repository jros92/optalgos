package core;

public enum Neighborhoods {
    Geometric,
    RuleBased,
    GeometricNew,
    RuleBasedImproved;

    @Override
    public String toString() {
        switch(this) {
            case Geometric: return "Geometric";
            case RuleBased: return "Rule-Based";
            case GeometricNew: return "Geometric New";
            case RuleBasedImproved: return "Rule-Based Improved";
            default: throw new IllegalArgumentException();
        }
    }
}
