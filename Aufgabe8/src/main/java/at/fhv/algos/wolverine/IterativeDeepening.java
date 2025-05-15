package at.fhv.algos.wolverine;

public class IterativeDeepening {
    private final int maxDepth;

    public IterativeDeepening(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public boolean search(GameState start, GameState target) {
        for (int depth = 0; depth <= maxDepth; depth += 1) {
            if (dls(start, target, depth)) {
                return true;
            }
        }
        return false;
    }

    private boolean dls(GameState start, GameState target, int depth) {
        if (start.equals(target)) {
            return true;
        }

        if (depth <= 0) {
            return false;
        }

        for (GameState neighbor : start.getNeighbors()) {
            if (dls(neighbor, target, depth - 1)) {
                return true;
            }
        }
        return false;
    }
}
