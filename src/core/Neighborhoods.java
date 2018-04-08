package core;

public enum Neighborhoods {
    Geometric,
    RuleBased,
    GeometricNew;

    @Override
    public String toString() {
        switch(this) {
            case Geometric: return "Geometric";
            case RuleBased: return "Rule-Based";
            case GeometricNew: return "Geometric New";
            default: throw new IllegalArgumentException();
        }
    }
}
