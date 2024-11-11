package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;
import org.junit.Test;

public abstract class GraphInstanceTest {
    
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // ensure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("expected graph to contain added vertex",
                graph.vertices().contains("A"));
    }

    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("expected vertex to be added", graph.add("A"));
        assertFalse("expected duplicate vertex addition to return false", graph.add("A"));
        assertEquals("expected no duplicate vertices in graph", 1, graph.vertices().size());
    }

    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        
        int previousWeight = graph.set("A", "B", 5);
        assertEquals("expected no previous edge weight", 0, previousWeight);
        
        assertEquals("expected edge weight to be updated",
                5, (int) graph.targets("A").get("B"));
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("expected vertex to be removed", graph.remove("A"));
        assertFalse("expected vertex to no longer exist in graph", graph.vertices().contains("A"));
    }

    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 3);
        
        Set<String> sourcesOfB = graph.sources("B").keySet();
        Set<String> targetsOfA = graph.targets("A").keySet();
        
        assertTrue("expected A to be a source of B", sourcesOfB.contains("A"));
        assertTrue("expected B to be a target of A", targetsOfA.contains("B"));
        assertEquals("expected edge weight from A to B to be 3", 
                (Integer) 3, graph.targets("A").get("B"));
    }
}
