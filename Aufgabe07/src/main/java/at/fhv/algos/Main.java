package at.fhv.algos;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();
        InputStream input = Graph.class.getClassLoader().getResourceAsStream("graph.txt");
        graph.loadFromInputStream(input);

        Graph.Node start = graph.getVertex("1");
        graph.callDijkstra(start);
    }
}
