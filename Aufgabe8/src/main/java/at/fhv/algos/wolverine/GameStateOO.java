package at.fhv.algos.wolverine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameStateOO {
    private Position farmer;
    private Position wolf;
    private Position cabbage;
    private Position goat;

    public GameStateOO(Position farmer, Position wolf, Position cabbage, Position goat) {
        this.farmer = farmer;
        this.wolf = wolf;
        this.cabbage = cabbage;
        this.goat = goat;
    }

    public List<GameStateOO> getNeighbors() {
        List<GameStateOO> nextStates = new ArrayList<>();
        Position[] passengers = {farmer, wolf, cabbage, goat};

        for (Position passenger: passengers) {
            if (getPosition(passenger) == farmer) {
                GameStateOO copy = this.clone();
                copy.farmer = copy.farmer.toggle();

                if (passenger != null) {
                    copy.setPosition(passenger, getPosition(passenger).toggle());
                }

                if (copy.isValid()) {
                    nextStates.add(copy);
                }
            }
        }
        return nextStates;
    }

    private Position getPosition(Object obj) {
        return switch (obj.toString()) {
            case "wolf" -> wolf;
            case "cabbage" -> cabbage;
            case "goat" -> goat;
            default -> farmer;
        };
    }

    private void setPosition(Object obj, Position pos) {
        switch (obj.toString()) {
            case "wolf":
                wolf = pos;
                break;
            case "cabbage":
                cabbage = pos;
                break;
            case "goat":
                goat = pos;
                break;
        }
    }

    private boolean isValid() {
        if (wolf.equals(goat) && !farmer.equals(wolf)) {
            return false;
        }
        if (goat.equals(cabbage) && !farmer.equals(goat)) {
            return false;
        }
        return true;
    }

    public boolean isTerminationState() {
        return farmer == Position.RIGHT && wolf == Position.RIGHT && cabbage == Position.RIGHT && goat == Position.RIGHT;
    }

    @Override
    public GameStateOO clone() {
        return new GameStateOO(farmer, wolf, cabbage, goat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStateOO that = (GameStateOO) o;
        return farmer == that.farmer && wolf == that.wolf && cabbage == that.cabbage && goat == that.goat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(farmer, wolf, cabbage, goat);
    }

    @Override
    public String toString() {
        return String.format("F:%s W:%s C:%s G:%s", farmer, wolf, cabbage, goat);
    }
}
