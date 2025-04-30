package at.fhv.algos;

import java.io.*;
import java.util.*;

public class Graph {
    protected static class Vertex {
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

    private abstract class GraphTraversionAlgorithm {
        protected Map<String, Boolean> closeList = new HashMap<>();

        protected abstract void addVertexToOpenList(Vertex vertex);
        protected abstract Vertex getNextVertex();
        protected abstract boolean isOpenListEmpty();

        protected void dfsRec(Vertex vertex) {
            if (vertex == null || closeList.containsKey(vertex.getId())) {
                return;
            }

            closeList.put(vertex.getId(), true);
            System.out.println(vertex.getId() + " " + vertex.getValue());

            for (Map.Entry<Vertex, Integer> entry : vertex.getNeighbors().entrySet()) {
                dfsRec(entry.getKey());
            }
        }

        protected void traverseIterative(Vertex vertex) {
            if (vertex == null) {
                return;
            }

            addVertexToOpenList(vertex);

            while (!isOpenListEmpty()) {
                Vertex current = getNextVertex();

                if (closeList.containsKey(current.getId())) {
                    continue;
                }

                closeList.put(current.getId(), true);
                System.out.println(current.getId() + " " + current.getValue());

                for (Map.Entry<Vertex, Integer> entry : current.getNeighbors().entrySet()) {
                    if (!closeList.containsKey(entry.getKey().getId())) {
                        addVertexToOpenList(entry.getKey());
                    }
                }
            }
        }
    }

    private class DfsIterative extends GraphTraversionAlgorithm {
        private Stack<Vertex> openList = new Stack<>();

        @Override
        protected void addVertexToOpenList(Vertex vertex) {
            openList.push(vertex);
        }

        @Override
        protected Vertex getNextVertex() {
            return openList.pop();
        }

        @Override
        protected boolean isOpenListEmpty() {
            return openList.isEmpty();
        }

    }

    private class BfsIterative extends GraphTraversionAlgorithm {
        private Queue<Vertex> openList = new LinkedList<>();

        @Override
        protected void addVertexToOpenList(Vertex vertex) {
            openList.add(vertex);
        }

        @Override
        protected Vertex getNextVertex() {
            return openList.poll();
        }

        @Override
        protected boolean isOpenListEmpty() {
            return openList.isEmpty();
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

    public Vertex getVertex(String id) {
        Vertex vertex = vertices.get(id);
        return vertex != null ? vertex : null;
    }

    public void callDfsRec(Vertex vertex) {
        new GraphTraversionAlgorithm() {
            @Override
            protected void addVertexToOpenList(Vertex vertex) {}

            @Override
            protected Vertex getNextVertex() {
                return null;
            }

            @Override
            protected boolean isOpenListEmpty() {
                return true;
            }
        }.dfsRec(vertex);
    }

    public void callDfsIterative(Vertex vertex) {
        new DfsIterative().traverseIterative(vertex);
    }

    public void callBfsIterative(Vertex vertex) {
        new BfsIterative().traverseIterative(vertex);
    }
}
