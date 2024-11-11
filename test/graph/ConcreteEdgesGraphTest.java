package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    /*
     * Testing add(String vertex)
     */
    @Test
    public void testAddVertex() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        assertTrue("Vertex should be added", graph.add("A"));
        assertFalse("Adding existing vertex should return false", graph.add("A"));
        assertTrue("Graph should contain the vertex", graph.vertices().contains("A"));
    }

    /*
     * Testing set(String source, String target, int weight)
     */
    @Test
    public void testSetEdge() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");

        int previousWeight = graph.set("A", "B", 5);
        assertEquals("No previous edge, should return 0", 0, previousWeight);
        assertEquals("Edge weight should be set to 5", 5, (int) graph.targets("A").get("B"));

        previousWeight = graph.set("A", "B", 10);
        assertEquals("Previous weight should be 5", 5, previousWeight);
        assertEquals("Edge weight should be updated to 10", 10, (int) graph.targets("A").get("B"));

        previousWeight = graph.set("A", "B", 0);
        assertEquals("Previous weight should be 10 before removing edge", 10, previousWeight);
        assertFalse("Edge should be removed", graph.targets("A").containsKey("B"));
    }

    /*
     * Testing remove(String vertex)
     */
    @Test
    public void testRemoveVertex() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        assertTrue("Vertex should be removed", graph.remove("A"));
        assertFalse("Vertex A should no longer exist", graph.vertices().contains("A"));
        assertTrue("Edge connected to A should be removed", graph.targets("B").isEmpty());

        assertFalse("Removing non-existing vertex should return false", graph.remove("A"));
    }

    /*
     * Testing vertices()
     */
    @Test
    public void testVertices() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
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

    /*
     * Testing sources(String target)
     */
    @Test
    public void testSources() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 3);

        Map<String, Integer> sources = graph.sources("B");
        assertEquals("Expected one source", 1, sources.size());
        assertTrue("Source should be A with weight 3", sources.containsKey("A") && sources.get("A") == 3);
    }

    /*
     * Testing targets(String source)
     */
    @Test
    public void testTargets() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 3);

        Map<String, Integer> targets = graph.targets("A");
        assertEquals("Expected one target", 1, targets.size());
        assertTrue("Target should be B with weight 3", targets.containsKey("B") && targets.get("B") == 3);
    }

    /*
     * Testing toString()
     */
    @Test
    public void testToString() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 3);

        String expected = "Vertices: [A, B]\nEdges:\nA -> B (3)\n";
        assertEquals("String representation should match", expected, graph.toString());
    }

    /*
     * Testing Edge class constructor, getSource(), getTarget(), getWeight(), toString()
     */
    @Test
    public void testEdgeConstructorAndAccessors() {
        Edge edge = new Edge("A", "B", 3);
        assertEquals("Source should be A", "A", edge.getSource());
        assertEquals("Target should be B", "B", edge.getTarget());
        assertEquals("Weight should be 3", 3, edge.getWeight());
    }

    @Test
    public void testEdgeToString() {
        Edge edge = new Edge("A", "B", 3);
        assertEquals("String representation should match", "A -> B (3)", edge.toString());
    }

    /*
     * Testing Edge class constructor with invalid input
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEdgeInvalidWeight() {
        new Edge("A", "B", -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEdgeNullSource() {
        new Edge(null, "B", 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEdgeNullTarget() {
        new Edge("A", null, 3);
    }
}
