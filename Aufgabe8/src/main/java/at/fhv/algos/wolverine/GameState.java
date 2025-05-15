package at.fhv.algos.wolverine;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final int state;

    public GameState(int state) {
        this.state = state;
    }

    // Getters for the state: 0: left, 1: right
    private int getFarmer() {
        return (state >> 0) & 0b1;
    }

    private int getWolf() {
        return (state >> 1) & 0b1;
    }

    private int getCabbage() {
        return (state >> 2) & 0b1;
    }

    private int getGoat() {
        return (state >> 3) & 0b1;
    }

    public List<GameState> getNeighbors() {
        List<GameState> nextStates = new ArrayList<>();
        int farmer = getFarmer();
        int[] passengers = {0, 1, 2, 3};

        for (int passenger: passengers) {
            // Check if the passenger is on the same side as the farmer
            if (passenger == 0 || ((state >> passenger) & 1) == farmer) {
                int newState = state ^ (1 << 0); // Toggle the farmer
                // Toggle the passenger if it's not the farmer
                if (passenger != 0) {
                    newState ^= (1 << passenger);
                }
                GameState newGameState = new GameState(newState);

                // Check if the new state is valid
                if (newGameState.isValid()) {
                    nextStates.add(newGameState);
                }
            }
        }
        return nextStates;
    }

    private boolean isValid() {
        // Check if the wolf and goat are on the same side without the farmer
        if (getWolf() == getGoat() && getFarmer() != getWolf()) {
            return false;
        }
        // Check if the goat and cabbage are on the same side without the farmer
        if (getCabbage() == getGoat() && getFarmer() != getCabbage()) {
            return false;
        }
        return true;
    }

    public boolean isTerminationState() {
        return state == 0b1111;
    }

    @Override
    public GameState clone() {
        return new GameState(state);
    }

    @Override
    public int hashCode() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return state == gameState.state;
    }

    @Override
    public String toString() {
        return String.format("F:%d W:%d C:%d G:%d", getFarmer(), getWolf(), getCabbage(), getGoat());
    }
}
