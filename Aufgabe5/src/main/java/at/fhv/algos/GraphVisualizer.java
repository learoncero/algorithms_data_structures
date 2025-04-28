package at.fhv.algos;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphVisualizer extends JFrame {
    public GraphVisualizer(Graph graph) {
        super("Graph Visualizer");
        mxGraph mx = new mxGraph();
        Object parent = mx.getDefaultParent();

        // Farben vorbereiten
        Map<String, Object> edgeStyles = new HashMap<>();
        edgeStyles.put(mxConstants.STYLE_STROKECOLOR, "#000000"); // black
        mx.getStylesheet().putCellStyle("DEFAULT_EDGE", edgeStyles);

        Map<String, Object> highlightStyle = new HashMap<>(edgeStyles);
        highlightStyle.put(mxConstants.STYLE_STROKECOLOR, "#FF0000"); // red
        highlightStyle.put(mxConstants.STYLE_STROKEWIDTH, 3);
        mx.getStylesheet().putCellStyle("HIGHLIGHT_EDGE", highlightStyle);

        // Pfad-Kanten merken
        Set<String> highlightedEdges = new HashSet<>();
        for (String[] edge : graph.getEulerianPathEdges()) {
            highlightedEdges.add(edge[0] + "->" + edge[1]);
        }

        mx.getModel().beginUpdate();
        try {
            Map<String, Object> vertexMap = new HashMap<>();

            int i = 0;
            for (String id : graph.getVertexIds()) {
                Graph.Vertex node = graph.getVertex(id);
                vertexMap.put(id, mx.insertVertex(parent, null, node.getValue(), 100 + i * 100, 100, 80, 30));
                i += 1;
            }

            // add edges
            for (String fromId : graph.getVertexIds()) {
                Graph.Vertex from = graph.getVertex(fromId);
                for (Graph.Vertex to : from.getNeighbors().keySet()) {
                    String edgeKey = fromId + "->" + to.getId();
                    String style = highlightedEdges.contains(edgeKey) ? "HIGHLIGHT_EDGE" : "DEFAULT_EDGE";
                    mx.insertEdge(parent, null, "", vertexMap.get(fromId), vertexMap.get(to.getId()), style);
                }
            }

        } finally {
            mx.getModel().endUpdate();
        }

        getContentPane().add(new mxGraphComponent(mx));
    }
}
