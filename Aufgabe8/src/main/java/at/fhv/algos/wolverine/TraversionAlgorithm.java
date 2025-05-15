package at.fhv.algos.wolverine;

import java.util.*;

public abstract class TraversionAlgorithm {
    protected Set<GameState> closeList = new HashSet<>();

    protected abstract void addToOpenList(List<GameState> state);

    protected abstract List<GameState> getNextPath();

    protected abstract boolean isOpenListEmpty();

    protected void dfsRec(GameState state) {
        if (state == null || closeList.contains(state)) {
            return;
        }

        closeList.add(state);

        for (GameState neighbor : state.getNeighbors()) {
            dfsRec(neighbor);
        }
    }

    protected List<GameState> traverseIterative(GameState start) {
        if (start == null) {
            return null;
        }

        addToOpenList(List.of(start));

        while (!isOpenListEmpty()) {
            List<GameState> path = getNextPath();
            GameState current = path.get(path.size() - 1);

            if (!closeList.contains(current)) {
                closeList.add(current);

                if (current.isTerminationState()) {
                    return path;
                }

                for (GameState neighbor : current.getNeighbors()) {
                    if (!closeList.contains(neighbor)) {
                        List<GameState> newPath = new ArrayList<>(path);
                        newPath.add(neighbor);
                        addToOpenList(newPath);
                    }
                }
            }
        }
        System.out.println("No solution found.");
        return null;
    }
}
