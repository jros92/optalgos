package core;

public enum Neighborhoods {
    Geometric,
    RuleBased;

    @Override
    public String toString() {
        switch(this) {
            case Geometric: return "Geometric";
            case RuleBased: return "Rule-Based";
            default: throw new IllegalArgumentException();
        }
    }
}
