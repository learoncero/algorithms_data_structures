package at.fhv.algos.kruskal;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Graph kruskalGraph = new Graph();
        InputStream inputKruskal = Graph.class.getClassLoader().getResourceAsStream("kruskal.txt");
        kruskalGraph.loadFromInputStream(inputKruskal);

        Kruskal kruskal = new Kruskal();
        List<Graph.Edge> mst = kruskal.computeMST(kruskalGraph);

        System.out.println("MST:");
        for (Graph.Edge edge : mst) {
            System.out.println(edge);
        }

    }
}
