package at.fhv.algos;

import java.util.HashSet;
import java.util.Set;

public class Dijkstra {
    private String[] previous;
    private double[] distance;
    private Set<String> closeList;
    private Set<String> openList;

    public Dijkstra(int size) {
        previous = new String[size];
        distance = new double[size];
        closeList = new HashSet<>(size);
        openList = new HashSet<>(size);
    }

}
