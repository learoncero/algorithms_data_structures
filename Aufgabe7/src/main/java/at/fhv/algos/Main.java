package at.fhv.algos;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        Graph dijkstraGraph = new Graph();
        InputStream inputDijkstra = Graph.class.getClassLoader().getResourceAsStream("dijkstra.txt");
        dijkstraGraph.loadFromInputStream(inputDijkstra);

        System.out.println("Dijkstra Graph");
        Graph.Node startDijkstra = dijkstraGraph.getVertex("1");
        dijkstraGraph.dijkstra(startDijkstra);

        Graph primGraph = new Graph();
        InputStream inputPrim = Graph.class.getClassLoader().getResourceAsStream("prim.txt");
        primGraph.loadFromInputStream(inputPrim);

        System.out.println("------------------------------------------------------------------------\n");
        System.out.println("\nPrim Graph");
        primGraph.prim();
    }
}
