package at.fhv.algos;

import java.io.*;
import java.util.*;

public class Graph {
    public static class Vertex {
        private String id;
        private String value;
        private Map<Vertex, Integer> neighbors = new HashMap<>();

        Vertex(String id, String value) {
            this.id = id;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public Map<Vertex, Integer> getNeighbors() {
            return neighbors;
        }
    }

    private final Map<String, Vertex> vertices = new HashMap<>();

    public void loadFromInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;

            String[] parts = line.split(";");
            if (parts.length == 2) {
                vertices.put(parts[0], new Vertex(parts[0], parts[1]));
            } else if (parts.length == 3) {
                Vertex from = vertices.get(parts[0]);
                Vertex to = vertices.get(parts[1]);
                int weight = Integer.parseInt(parts[2]);
                if (from != null && to != null) {
                    from.neighbors.put(to, weight);
                    to.neighbors.put(from, weight);
                }
            }
        }
        reader.close();
    }

    public String[] getVertexIds() {
        return vertices.keySet().toArray(new String[0]);
    }

    public Vertex getVertex(String id) {
        Vertex vertex = vertices.get(id);
        return vertex != null ? vertex : null;
    }

    public Map<Vertex, Integer> getNeighborsForId(String id) {
        Vertex vertex = vertices.get(id);
        return vertex != null ? vertex.neighbors : Collections.emptyMap();
    }

    public boolean containsEulerianPath() {
        int oddDegreeCount = 0;
        for (Vertex vertex : vertices.values()) {
            int degree = vertex.getNeighbors().size();
            if (degree % 2 != 0) {
                oddDegreeCount += 1;
            }
        }
        return oddDegreeCount == 0 || oddDegreeCount == 2;
    }

    public List<String> findEulerianPath() {
        if (!containsEulerianPath()) {
            return Collections.emptyList();
        }

        // Clone Graph (save edges)
        Map<Vertex, List<Vertex>> adj = new HashMap<>();
        for (Vertex v : vertices.values()) {
            adj.put(v, new LinkedList<>(v.getNeighbors().keySet()));
        }

        Vertex start = findStartVertexForEulerPath();
        List<String> path = new ArrayList<>();
        dfsEuler(start, adj, path);
        Collections.reverse(path);  // Da rekursiv rückwärts aufgestellt
        return path;
    }

    private Vertex findStartVertexForEulerPath() {
        for (Vertex v : vertices.values()) {
            int degree = v.getNeighbors().size();
            if (degree % 2 != 0) {
                return v; // one of the odd degree vertices
            }
        }
        // Fallback: return any vertex
        return vertices.values().iterator().next();
    }

    private void dfsEuler(Vertex v, Map<Vertex, List<Vertex>> adj, List<String> path) {
        List<Vertex> neighbors = adj.get(v);
        while (neighbors != null && !neighbors.isEmpty()) {
            Vertex next = neighbors.remove(0);
            adj.get(next).remove(v); // remove the edge in the reverse direction
            dfsEuler(next, adj, path);
        }
        path.add(v.getId());
    }

    public List<String[]> getEulerianPathEdges() {
        List<String> path = findEulerianPath();
        List<String[]> edges = new ArrayList<>();

        for (int i = 0; i < path.size() - 1; i += 1) {
            edges.add(new String[]{path.get(i), path.get(i + 1)});
        }

        return edges;
    }

}
