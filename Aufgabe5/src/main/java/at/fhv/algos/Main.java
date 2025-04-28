package at.fhv.algos;

import java.io.InputStream;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph();
        InputStream input = Main.class.getClassLoader().getResourceAsStream("graph.txt");
        graph.loadFromInputStream(input);

        if (graph.containsEulerianPath()) {
            System.out.println("Der Graph enthält einen Eulerpfad:");
            List<String> path = graph.findEulerianPath();
            System.out.println(String.join(" -> ", path));
        } else {
            System.out.println("Der Graph enthält KEINEN Eulerpfad.");
        }


        GraphVisualizer frame = new GraphVisualizer(graph);
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
