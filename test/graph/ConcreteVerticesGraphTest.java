package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    // Testing add(String vertex)
    @Test
    public void testAddVertex() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        assertTrue("Vertex A should be added successfully", graph.add("A"));
        assertFalse("Adding vertex A again should return false", graph.add("A"));
    }

    // Testing set(String source, String target, int weight)
    @Test
    public void testSetEdge() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");

        assertEquals("New edge from A to B with weight 2 should return 0", 0, graph.set("A", "B", 2));
        assertEquals("Updating edge from A to B with weight 3 should return previous weight 2", 2, graph.set("A", "B", 3));
        assertEquals("Setting weight of edge from A to B to 0 should remove the edge and return previous weight 3", 3, graph.set("A", "B", 0));
        assertTrue("Edge from A to B should be removed", graph.targets("A").isEmpty());
    }

    // Testing remove(String vertex)
    @Test
    public void testRemoveVertex() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        assertTrue("Vertex B should be removed", graph.remove("B"));
        assertFalse("Removing non-existent vertex C should return false", graph.remove("C"));
        assertTrue("Edge from A to B should also be removed", graph.targets("A").isEmpty());
    }

    // Testing vertices()
    @Test
    public void testVertices() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");

        Set<String> vertices = graph.vertices();
        assertEquals("Expected vertices count", 2, vertices.size());
        assertTrue("Vertices should contain A and B", vertices.contains("A") && vertices.contains("B"));

        try {
            vertices.add("C");
            fail("Vertices set should be unmodifiable");
        } catch (UnsupportedOperationException e) {
            // expected exception
        }
    }

    // Testing sources(String target)
    @Test
    public void testSources() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 4);

        Map<String, Integer> sources = graph.sources("B");
        assertEquals("B should have one source", 1, sources.size());
        assertTrue("Source should be A with weight 4", sources.containsKey("A") && sources.get("A") == 4);
    }

    // Testing targets(String source)
    @Test
    public void testTargets() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 3);

        Map<String, Integer> targets = graph.targets("A");
        assertEquals("A should have one target", 1, targets.size());
        assertTrue("Target should be B with weight 3", targets.containsKey("B") && targets.get("B") == 3);
    }

    // Testing toString()
    @Test
    public void testToString() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 3);

        String expectedOutput = "A edges: {B=3}\nB edges: {}\n";
        assertEquals("toString should return correct representation of graph", expectedOutput, graph.toString());
    }

    /*
     * Testing Vertex...
     */

    @Test
    public void testVertexAddEdge() {
        Vertex vertex = new Vertex("A");
        assertTrue("Adding edge from A to B with weight 2 should return true", vertex.addEdge("B", 2));
        assertEquals("Edge weight from A to B should be 2", (Integer) 2, vertex.getEdges().get("B"));
    }

    @Test
    public void testVertexRemoveEdge() {
        Vertex vertex = new Vertex("A");
        vertex.addEdge("B", 2);
        assertTrue("Removing edge from A to B should return true", vertex.removeEdge("B"));
        assertFalse("Edge from A to B should be removed", vertex.getEdges().containsKey("B"));
    }

    @Test
    public void testVertexToString() {
        Vertex vertex = new Vertex("A");
        vertex.addEdge("B", 2);
        assertEquals("toString should return correct representation of vertex", "A edges: {B=2}", vertex.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVertexInvalidEdgeWeight() {
        new Vertex("A").addEdge("B", -1);
    }
}
