package at.fhv.algos.wolverine;

import java.util.List;
import java.util.Stack;

public class DfsIterative extends TraversionAlgorithm {
    private Stack<List<GameState>> openList = new Stack<>();

    @Override
    protected void addToOpenList(List<GameState> path) {
        openList.push(path);
    }

    @Override
    protected List<GameState> getNextPath() {
        return openList.pop();
    }

    @Override
    protected boolean isOpenListEmpty() {
        return openList.isEmpty();
    }
}
