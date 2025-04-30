package at.fhv.algos;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph();
        InputStream input = Graph.class.getClassLoader().getResourceAsStream("graph1.txt");
        graph.loadFromInputStream(input);

        Graph.Vertex start = graph.getVertex("1");

        System.out.println("DFS Recursive Traversal:");
        graph.callDfsRec(start);

        System.out.println("\nDFS Traversal:");
        graph.callDfsIterative(start);

        System.out.println("\nBFS Traversal:");
        graph.callBfsIterative(start);
    }
}
