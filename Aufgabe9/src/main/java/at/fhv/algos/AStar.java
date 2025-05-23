package at.fhv.algos;

import java.util.*;

public class AStar {
    private static final int[] GOAL = {
            1, 2, 3,
            4, 5, 6,
            7, 8, 0
    };

    private static final int[][] DIRECTIONS = {
            {-1, 0}, // UP
            {1, 0},  // DOWN
            {0, -1}, // LEFT
            {0, 1}   // RIGHT
    };

    public enum HeuristicType { H1, H2, H3 }

    static class Node implements Comparable<Node> {
        int[] state;
        Node parent;
        int g; // cost so far
        int h; // heuristic cost
        int f;

        Node(int[] state, Node parent, int g, int h) {
            this.state = state;
            this.parent = parent;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f, other.f);
        }

        @Override
        public boolean equals(Object o) {
            return Arrays.equals(this.state, ((Node) o).state);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(state);
        }
    }

    public static void solve(int[] startState, HeuristicType heuristic) {
        PriorityQueue<Node> open = new PriorityQueue<>();
        Set<String> closed = new HashSet<>();

        Node start = new Node(startState, null, 0, getHeuristic(startState, heuristic));
        open.add(start);

        int expanded = 0;

        while (!open.isEmpty()) {
            Node current = open.poll();
            expanded += 1;

            if (Arrays.equals(current.state, GOAL)) {
                printSolution(current, expanded);
                return;
            }

            closed.add(Arrays.toString(current.state));

            int zeroIndex = indexOf(current.state, 0);
            int row = zeroIndex / 3;
            int col = zeroIndex % 3;

            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                    int newIndex = newRow * 3 + newCol;
                    int[] newState = Arrays.copyOf(current.state, 9);
                    newState[zeroIndex] = newState[newIndex];
                    newState[newIndex] = 0;

                    if (closed.contains(Arrays.toString(newState))) continue;

                    int g = current.g + 1;
                    int h = getHeuristic(newState, heuristic);
                    open.add(new Node(newState, current, g, h));
                }
            }
        }

        System.out.println("Keine Lösung gefunden.");
    }

    private static void printSolution(Node node, int expanded) {
        List<int[]> path = new ArrayList<>();
        while (node != null) {
            path.add(node.state);
            node = node.parent;
        }
        Collections.reverse(path);

        System.out.println("Lösungsschritte: " + (path.size() - 1));
        System.out.println("Expandierte Knoten: " + expanded);
        for (int[] state : path) {
            printState(state);
            System.out.println();
        }
    }

    private static int getHeuristic(int[] state, HeuristicType type) {
        switch (type) {
            case H1:
                return h1(state);
            case H2:
                return h2(state);
            case H3:
                return h1(state) + h2(state); // eigene Kombination
            default:
                return 0;
        }
    }

    private static int h1(int[] state) {
        int wrong = 0;
        for (int i = 0; i < 9; i += 1) {
            if (state[i] != 0 && state[i] != GOAL[i]) {
                wrong += 1;
            }
        }
        return wrong;
    }

    private static int h2(int[] state) {
        int sum = 0;
        for (int i = 0; i < 9; i += 1) {
            if (state[i] == 0) {
                continue;
            }
            int targetIndex = indexOf(GOAL, state[i]);
            int x1 = i / 3;
            int y1 = i % 3;
            int x2 = targetIndex / 3;
            int y2 = targetIndex % 3;
            sum += Math.abs(x1 - x2) + Math.abs(y1 - y2);
        }
        return sum;
    }

    private static int indexOf(int[] state, int value) {
        for (int i = 0; i < state.length; i += 1) {
            if (state[i] == value) return i;
        }
        return -1;
    }

    private static void printState(int[] state) {
        for (int i = 0; i < 9; i += 1) {
            System.out.print((state[i] == 0 ? " " : state[i]) + " ");
            if ((i + 1) % 3 == 0) System.out.println();
        }
    }

    public static void main(String[] args) {
        // Beispiel-Zustand (lösbar)
        int[] start = {
                1, 2, 3,
                4, 0, 6,
                7, 5, 8
        };

        System.out.println("A*-Suche mit Heuristik H1 (falsch stehende Kärtchen)");
        solve(start, HeuristicType.H1);

        System.out.println("\nA*-Suche mit Heuristik H2 (Manhattan-Distanz)");
        solve(start, HeuristicType.H2);

        System.out.println("\nA*-Suche mit Heuristik H3 (H1 + H2)");
        solve(start, HeuristicType.H3);
    }

}
