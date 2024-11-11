package graph;

import java.util.*;

/**
 * A mutable, directed graph with vertices of type String.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Abstraction function and Representation invariant for ConcreteEdgesGraph:
    // AF(vertices, edges) = a directed graph where each String in vertices represents
    //    a node in the graph, and each Edge in edges represents an edge in the graph.
    // RI: vertices and edges are non-null, edges contain only non-null values,
    //     each edge connects valid vertices (in vertices set), and no two edges
    //     have the same source-target pair.

    private void checkRep() {
        assert vertices != null : "Vertices set should not be null";
        assert edges != null : "Edges list should not be null";
        
        for (Edge edge : edges) {
            assert edge != null : "Edge should not be null";
            assert vertices.contains(edge.getSource()) : "Edge source must be in vertices";
            assert vertices.contains(edge.getTarget()) : "Edge target must be in vertices";
        }
        
        // Ensure no duplicate edges with the same source and target
        Set<String> edgePairs = new HashSet<>();
        for (Edge edge : edges) {
            String edgePair = edge.getSource() + "->" + edge.getTarget();
            assert edgePairs.add(edgePair) : "Duplicate edges are not allowed";
        }
    }

    @Override
    public boolean add(String vertex) {
        boolean added = vertices.add(vertex);
        checkRep();
        return added;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        int previousWeight = 0;
        
        for (Iterator<Edge> it = edges.iterator(); it.hasNext();) {
            Edge edge = it.next();
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                previousWeight = edge.getWeight();
                it.remove();
                break;
            }
        }

        if (weight > 0) {
            edges.add(new Edge(source, target, weight));
        }

        add(source);
        add(target);
        
        checkRep();
        return previousWeight;
    }

    @Override
    public boolean remove(String vertex) {
        boolean removed = vertices.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        checkRep();
        return removed;
    }

    @Override
    public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return sources;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targets;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\nEdges:\n");
        for (Edge edge : edges) {
            sb.append(edge.toString()).append("\n");
        }
        return sb.toString();
    }
}

/**
 * Represents an immutable directed edge in a graph.
 * This class is internal to the representation of ConcreteEdgesGraph.
 */
class Edge {
    private final String source;
    private final String target;
    private final int weight;

    // Abstraction function and Representation invariant for Edge:
    // AF(source, target, weight) = a directed edge from source to target with a weight.
    // RI: source and target are non-null, weight >= 0

    public Edge(String source, String target, int weight) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target cannot be null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " -> " + target + " (" + weight + ")";
    }
}
