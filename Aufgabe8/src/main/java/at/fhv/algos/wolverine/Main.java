package at.fhv.algos.wolverine;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        GameState start = new GameState(0b0000); // start state: all on the left side

        // DFS
        TraversionAlgorithm dfs = new DfsIterative();
        List<GameState> dfsSolution = dfs.traverseIterative(start);
        printSolution("DFS", dfsSolution);

        // BFS
        TraversionAlgorithm bfs = new BfsIterative();
        List<GameState> bfsSolution = bfs.traverseIterative(start);
        printSolution("BFS", bfsSolution);

        // ID
        int maxDepth = 7;
        IterativeDeepening id = new IterativeDeepening(maxDepth);
        boolean idSolution = id.search(start, new GameState(0b1111));
        System.out.println("=== ID ===");
        System.out.println(idSolution ? "Solution found." : "No solution found.");
    }

    private static void printSolution(String label, List<GameState> solution) {
        System.out.println("=== " + label + " ===");
        if (solution != null) {
            System.out.println("Lösung gefunden in " + (solution.size() - 1) + " Schritten:");
            for (GameState state : solution) {
                System.out.println(state);
            }
        } else {
            System.out.println("Keine Lösung gefunden.");
        }
    }
}
