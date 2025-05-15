package at.fhv.algos.kruskal;

import java.util.*;

public class Kruskal {
    private class DisjointSet {
        private final Map<Graph.Node, Graph.Node> parent = new HashMap<>();

        public void makeSet(Collection<Graph.Node> nodes) {
            for (Graph.Node node : nodes) {
                parent.put(node, node);
            }
        }

        public Graph.Node find(Graph.Node node) {
            if (!parent.get(node).equals(node)) {
                parent.put(node, find(parent.get(node)));
            }
            return parent.get(node);
        }

        public void union(Graph.Node node1, Graph.Node node2) {
            Graph.Node root1 = find(node1);
            Graph.Node root2 = find(node2);
            if (!root1.equals(root2)) {
                parent.put(root1, root2);
            }
        }
    }

    public List<Graph.Edge> computeMST(Graph graph) {
        List<Graph.Edge> edges = graph.getEdges();
        Collections.sort(edges);

        DisjointSet disjointSet = new DisjointSet();
        disjointSet.makeSet(graph.getNodes());

        List<Graph.Edge> mst = new ArrayList<>();
        for (Graph.Edge edge : edges) {
            Graph.Node from = edge.getFrom();
            Graph.Node to = edge.getTo();

            if (disjointSet.find(from) != disjointSet.find(to)) {
                mst.add(edge);
                disjointSet.union(from, to);
            }
        }
        return mst;
    }
}
