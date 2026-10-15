package sys.pro;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ListGraph}, inheriting common {@link Graph} contract
 * tests from {@link GraphTest} and adding adjacency-list-specific checks.
 */
class ListGraphTest extends GraphTest {

    @Override
    protected ListGraph createGraph() {
        return new ListGraph();
    }

    @Test
    void testAdjacencyListInternalStructure() throws Exception {
        ListGraph listGraph = (ListGraph) graph;
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        listGraph.addEdge(v1, v2);

        Map<Vertex, List<Vertex>> adjList = getAdjacencyList(listGraph);
        assertTrue(adjList.containsKey(v1));
        assertTrue(adjList.containsKey(v2));
        assertTrue(adjList.get(v1).contains(v2));
        assertFalse(adjList.get(v2).contains(v1));
    }

    @Test
    void testAdjacencyListCleanedOnRemoveVertex() throws Exception {
        ListGraph listGraph = (ListGraph) graph;
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        listGraph.addEdge(v1, v2);
        listGraph.addEdge(v2, v3);
        listGraph.addEdge(v1, v3);

        listGraph.removeVertex(v2);

        Map<Vertex, List<Vertex>> adjList = getAdjacencyList(listGraph);
        assertFalse(adjList.containsKey(v2));
        assertFalse(adjList.get(v1).contains(v2));
        assertTrue(adjList.get(v1).contains(v3));
    }

    @Test
    void testListGraphToStringFormat() {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        graph.addEdge(v1, v2);

        String str = graph.toString();
        assertTrue(str.startsWith("ListGraph:"));
        assertTrue(str.contains("1 -> [2]"));
        assertTrue(str.contains("2 -> []"));
    }

    @SuppressWarnings("unchecked")
    private Map<Vertex, List<Vertex>> getAdjacencyList(ListGraph graph) throws Exception {
        Field field = ListGraph.class.getDeclaredField("adjacencyList");
        field.setAccessible(true);
        return (Map<Vertex, List<Vertex>>) field.get(graph);
    }
}
