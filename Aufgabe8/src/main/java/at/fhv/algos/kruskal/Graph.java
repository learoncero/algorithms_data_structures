package at.fhv.algos.kruskal;

import java.io.*;
import java.util.*;

public class Graph {
    private final Map<String, Node> nodes = new HashMap<>();
    private final List<Edge> edges = new ArrayList<>();

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
                    edges.add(new Edge(from, to, weight));
                }
            }
        }
        reader.close();
    }

    public List<Node> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public static class Node {
        private String id;
        private String value;
        private Map<Node, Integer> neighbors = new HashMap<>();

        Node(String id, String value) {
            this.id = id;
            this.value = value;
        }
    }

    public static class Edge implements Comparable<Edge> {
        private Node from;
        private Node to;
        private int weight;

        Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public Node getFrom() {
            return from;
        }

        public Node getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from.value +
                    ", to=" + to.value+
                    ", weight=" + weight +
                    '}';
        }
    }
}
