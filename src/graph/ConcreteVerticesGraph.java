package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Constructor
    public ConcreteVerticesGraph() {
        checkRep();
    }
    
    // Abstraction function and Representation invariant for ConcreteVerticesGraph:
    // AF(vertices) = a directed graph where each Vertex in vertices represents a node
    //    in the graph, and each edge in Vertex's edges represents an edge in the graph.
    // RI: vertices is not null, does not contain null elements,
    //    and no two vertices have the same label.

    private void checkRep() {
        assert vertices != null : "vertices list should not be null";
        Set<String> labels = new HashSet<>();
        for (Vertex v : vertices) {
            assert v != null : "vertex should not be null";
            assert labels.add(v.getLabel()) : "duplicate vertex label";
        }
    }

    @Override
    public boolean add(String vertex) {
        if (findVertex(vertex) == null) {
            vertices.add(new Vertex(vertex));
            checkRep();
            return true;
        }
        return false;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        Vertex sourceVertex = findOrAddVertex(source);
        Integer previousWeight = sourceVertex.getEdges().get(target);
        sourceVertex.addEdge(target, weight);
        checkRep();
        return previousWeight == null ? 0 : previousWeight;
    }
    
    @Override
    public boolean remove(String vertex) {
        Vertex v = findVertex(vertex);
        if (v != null) {
            vertices.remove(v);
            for (Vertex vert : vertices) {
                vert.removeEdge(vertex);
            }
            checkRep();
            return true;
        }
        return false;
    }
    
    @Override
    public Set<String> vertices() {
        Set<String> vertexLabels = new HashSet<>();
        for (Vertex v : vertices) {
            vertexLabels.add(v.getLabel());
        }
        return Collections.unmodifiableSet(vertexLabels);
    }
    
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex v : vertices) {
            if (v.getEdges().containsKey(target)) {
                sources.put(v.getLabel(), v.getEdges().get(target));
            }
        }
        return sources;
    }
    
    @Override
    public Map<String, Integer> targets(String source) {
        Vertex v = findVertex(source);
        return v == null ? Collections.emptyMap() : v.getEdges();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) {
            sb.append(v.toString()).append("\n");
        }
        return sb.toString();
    }
    
    private Vertex findVertex(String label) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }
    
    private Vertex findOrAddVertex(String label) {
        Vertex v = findVertex(label);
        if (v == null) {
            v = new Vertex(label);
            vertices.add(v);
        }
        return v;
    }
}

class Vertex {
    private final String label;
    private final Map<String, Integer> edges = new HashMap<>();
    
    // Constructor
    public Vertex(String label) {
        this.label = label;
    }
    
    // Abstraction function and Representation invariant for Vertex:
    // AF(label, edges) = a node labeled 'label' with directed edges and weights in 'edges'.
    // RI: label != null, edges != null, no edge weight is negative.

    private void checkRep() {
        assert label != null : "label should not be null";
        assert edges != null : "edges map should not be null";
        for (int weight : edges.values()) {
            assert weight >= 0 : "edge weights must be non-negative";
        }
    }
    
    public String getLabel() {
        return label;
    }
    
    public boolean addEdge(String target, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative");
        }
        if (weight == 0) {
            return edges.remove(target) != null;
        } else {
            edges.put(target, weight);
            checkRep();
            return true;
        }
    }
    
    public boolean removeEdge(String target) {
        return edges.remove(target) != null;
    }
    
    public Map<String, Integer> getEdges() {
        return Collections.unmodifiableMap(edges);
    }
    
    @Override
    public String toString() {
        return label + " edges: " + edges;
    }
}
