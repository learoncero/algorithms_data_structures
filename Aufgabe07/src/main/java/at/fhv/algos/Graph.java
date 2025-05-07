package at.fhv.algos;

import java.io.*;
import java.util.*;

public class Graph {
    protected static class Node {
        private String id;
        private String value;
        private Map<Node, Integer> neighbors = new HashMap<>();

        Node(String id, String value) {
            this.id = id;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public Map<Node, Integer> getNeighbors() {
            return neighbors;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(id, node.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }

    private abstract class ShortestPathAlgorithm {
    }

    private class Dijkstra extends ShortestPathAlgorithm {
        private List<Node> closeList;
        private Map<Node, Double> distance;
        private Map<Node, Node> previous;
        private int nodeCount;

        Dijkstra(int size) {
            closeList = new LinkedList<>();
            distance = new HashMap<>(size);
            previous = new HashMap<>(size);
            nodeCount = size;
        }

        public void run(Node start) {
            initialize(start);
            findShortestPath(start);
            printDijkstraSpanningTree();

            for (Node node : nodes.values()) {
                if (!node.equals(start)) {
                    printShortestPath(node);
                }
            }
        }

        private void printDijkstraSpanningTree() {
            System.out.println("\nDijkstra's Spanning Tree:");
            for (Node node : nodes.values()) {
                System.out.println("Node: " + node.getValue() + ", Distance: " + distance.get(node) + ", " +
                        "Previous: " + (previous.get(node) != null ? previous.get(node).getValue() : "null"));
            }
            System.out.println("\n");
        }

        private void printShortestPath(Node target) {
            if (distance.get(target) == Double.MAX_VALUE) {
                System.out.println("No path to node " + target.getId());
                return;
            }

            List<Node> path = new ArrayList<>();
            Node current = target;

            while (current != null) {
                path.add(current);
                current = previous.get(current);
            }

            Collections.reverse(path);

            System.out.println("Shortest path from source to " + target.getValue() + ":");
            for (Node node : path) {
                System.out.print(node.getValue() + " ");
            }
            System.out.println("\nTotal distance: " + distance.get(target) + "\n");
        }

        private void initialize(Node start) {
            for (Node node : nodes.values()) {
                distance.put(node, Double.MAX_VALUE);
                previous.put(node, null);
            }
            distance.put(start, 0.0);
        }

        private void findShortestPath(Node start) {
            initialize(start);

            while (nodeCount > 0) {
                Node current = getMinDistanceNode();
                closeList.add(current);
                nodeCount -= 1;

                for (Map.Entry<Node, Integer> entry : current.getNeighbors().entrySet()) {
                    Node neighbor = entry.getKey();
                    int weight = entry.getValue();
                    if (!closeList.contains(neighbor)) {
                        double newDist = distance.get(current) + weight;
                        if (newDist < distance.get(neighbor)) {
                            distance.put(neighbor, newDist);
                            previous.put(neighbor, current);
                        }
                    }
                }
            }
        }

        private Node getMinDistanceNode() {
            Node minNode = null;
            double minDist = Double.MAX_VALUE;
            for (Node node : nodes.values()) {
                if (!closeList.contains(node) && distance.get(node) < minDist) {
                    minDist = distance.get(node);
                    minNode = node;
                }
            }
            return minNode;
        }
    }

    private final Map<String, Node> nodes = new HashMap<>();

    public void loadFromInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;

            String[] parts = line.split(";");
            if (parts.length == 2) {
                nodes.put(parts[0], new Node(parts[0], parts[1]));
            } else if (parts.length == 3) {
                Node from = nodes.get(parts[0]);
                Node to = nodes.get(parts[1]);
                int weight = Integer.parseInt(parts[2]);
                if (from != null && to != null) {
                    from.neighbors.put(to, weight);
                    to.neighbors.put(from, weight);
                }
            }
        }
        reader.close();
    }

    public Node getVertex(String id) {
        Node node = nodes.get(id);
        return node != null ? node : null;
    }

    public void callDijkstra(Node start) {
        Dijkstra dijkstra = new Dijkstra(nodes.size());
        dijkstra.run(start);
    }

}
