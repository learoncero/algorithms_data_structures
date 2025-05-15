package at.fhv.algos.wolverine;

public enum Position {
    LEFT,
    RIGHT;

    public Position toggle() {
        return this == LEFT ? RIGHT : LEFT;
    }
}
