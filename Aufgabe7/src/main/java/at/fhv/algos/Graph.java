package at.fhv.algos;

import java.io.*;
import java.util.*;

public class Graph {
    protected static class Node {
        private final String id;
        private final String value;
        private final Map<Node, Integer> neighbors = new HashMap<>();

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
        protected final List<Node> closeList;
        protected final Map<Node, Double> distance;
        protected final Map<Node, Node> previous;
        protected int nodeCount;

        protected abstract boolean relax(Node current, Node neighbor, double weight);
        protected abstract void updateDistance(Node current, Node neighbor, double weight);

        protected void findShortestPath(Node start) {
            initialize(start);

            while (nodeCount > 0) {
                Node current = getMinDistanceNode();
                closeList.add(current);
                nodeCount -= 1;

                for (Map.Entry<Node, Integer> entry : current.getNeighbors().entrySet()) {
                    Node neighbor = entry.getKey();
                    int weight = entry.getValue();

                    if ((!closeList.contains(neighbor)) && (relax(current, neighbor, weight))) {
                        updateDistance(current, neighbor, weight);
                        previous.put(neighbor, current);
                    }
                }
            }
        }

        protected ShortestPathAlgorithm() {
            closeList = new LinkedList<>();
            distance = new HashMap<>();
            previous = new HashMap<>();
            nodeCount = nodes.size();
        }

        protected void initialize(Node start) {
            for (Node node : nodes.values()) {
                distance.put(node, Double.MAX_VALUE);
                previous.put(node, null);
            }
            distance.put(start, 0.0);
        }

        protected Node getMinDistanceNode() {
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

        protected void visualizeSpanningTree() {
            System.out.println("Spanning tree:");
            for (Node node : nodes.values()) {
                System.out.println("Node: " + node.getValue() + ", Distance: " + distance.get(node) + ", " +
                        "Previous: " + (previous.get(node) != null ? previous.get(node).getValue() : "null"));
            }
            System.out.println("\n");
        }
    }

    private class Dijkstra extends ShortestPathAlgorithm {

        Dijkstra() {
            super();
        }

        @Override
        public boolean relax(Node current, Node neighbor, double weight) {
            double newDist = distance.get(current) + weight;
            return newDist < distance.get(neighbor);
        }

        @Override
        protected void updateDistance(Node current, Node neighbor, double weight) {
            distance.put(neighbor, distance.get(current) + weight);
        }

        public void printShortestPath(Node target) {
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

            System.out.println("Shortest path from source to " + (target != null ? target.getValue() : null) + ":");
            for (Node node : path) {
                System.out.print(node.getValue() + " ");
            }
            System.out.println("\nTotal distance: " + distance.get(target) + "\n");
        }
    }

    private class Prim extends ShortestPathAlgorithm {
        Prim() {
            super();
        }

        @Override
        public boolean relax(Node current, Node neighbor, double weight) {
            return weight < distance.get(neighbor);
        }

        @Override
        protected void updateDistance(Node current, Node neighbor, double weight) {
            distance.put(neighbor, weight);
        }

        public Node getStartNodeWithMinimumEdge() {
            Node startNode = null;
            int minWeight = Integer.MAX_VALUE;

            for (Node node : nodes.values()) {
                for (Map.Entry<Node, Integer> entry : node.getNeighbors().entrySet()) {
                    if (entry.getValue() < minWeight) {
                        minWeight = entry.getValue();
                        startNode = node;
                    }
                }
            }
            return startNode;
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
        return nodes.get(id);
    }

    public void dijkstra(Node start) {
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.findShortestPath(start);
        dijkstra.visualizeSpanningTree();
        for (Node node : nodes.values()) {
            if (!node.equals(start)) {
                dijkstra.printShortestPath(node);
            }
        }
    }

    public void prim() {
        Prim prim = new Prim();
        Node start = prim.getStartNodeWithMinimumEdge();
        prim.findShortestPath(start);
        prim.visualizeSpanningTree();
    }
}
