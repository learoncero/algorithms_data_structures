package at.fhv.algos.wolverine;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BfsIterative extends TraversionAlgorithm {
    private Queue<List<GameState>> openList = new LinkedList<>();

    @Override
    protected void addToOpenList(List<GameState> path) {
        openList.add(path);
    }

    @Override
    protected List<GameState> getNextPath() {
        return openList.poll();
    }

    @Override
    protected boolean isOpenListEmpty() {
        return openList.isEmpty();
    }
}
