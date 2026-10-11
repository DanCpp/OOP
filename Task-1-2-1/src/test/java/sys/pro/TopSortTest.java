package sys.pro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TopSortTest {

    private Graph graph;
    private Vertex v1;
    private Vertex v2;
    private Vertex v3;
    private Vertex v4;

    @BeforeEach
    void setUp() {
        graph = new ListGraph();

        v1 = new Vertex(1);
        v2 = new Vertex(2);
        v3 = new Vertex(3);
        v4 = new Vertex(4);
    }

    @Test
    void testDfsSortAcyclic() {
        // 1 -> 2, 1 -> 3, 2 -> 4, 3 -> 4
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v2, v4);
        graph.addEdge(v3, v4);

        List<Vertex> result = TopSort.dfsSort(graph);

        assertEquals(4, result.size());
        assertTrue(isValidTopologicalSort(result, graph));
    }

    @Test
    void testKahnsSortAcyclic() {
        // 1 -> 2, 1 -> 3, 2 -> 4, 3 -> 4
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v2, v4);
        graph.addEdge(v3, v4);

        List<Vertex> result = TopSort.kahnsSort(graph);

        assertEquals(4, result.size());
        assertTrue(isValidTopologicalSort(result, graph));
    }

    @Test
    void testDfsSortCycleThrowsException() {
        // 1 -> 2, 2 -> 3, 3 -> 1
        graph.addEdge(v1, v2);
        graph.addEdge(v2, v3);
        graph.addEdge(v3, v1);

        assertThrows(IllegalStateException.class, () -> {
            TopSort.dfsSort(graph);
        });
    }

    @Test
    void testKahnsSortCycleThrowsException() {
        // 1 -> 2, 2 -> 3, 3 -> 1
        graph.addEdge(v1, v2);
        graph.addEdge(v2, v3);
        graph.addEdge(v3, v1);

        assertThrows(IllegalStateException.class, () -> {
            TopSort.kahnsSort(graph);
        });
    }

    @Test
    void testDisconnectedGraph() {
        // 1 -> 2, 3 -> 4
        graph.addEdge(v1, v2);
        graph.addEdge(v3, v4);

        List<Vertex> dfsResult = TopSort.dfsSort(graph);
        assertEquals(4, dfsResult.size());
        assertTrue(isValidTopologicalSort(dfsResult, graph));

        List<Vertex> kahnResult = TopSort.kahnsSort(graph);
        assertEquals(4, kahnResult.size());
        assertTrue(isValidTopologicalSort(kahnResult, graph));
    }

    @Test
    void testEmptyGraph() {
        Graph emptyGraph = new ListGraph();

        List<Vertex> dfsResult = TopSort.dfsSort(emptyGraph);
        assertTrue(dfsResult.isEmpty());

        List<Vertex> kahnResult = TopSort.kahnsSort(emptyGraph);
        assertTrue(kahnResult.isEmpty());
    }

    private boolean isValidTopologicalSort(List<Vertex> order, Graph g) {
        List<Vertex> allVertices = g.getVertex();
        if (order.size() != allVertices.size()) {
            return false;
        }

        Map<Vertex, Integer> positions = new HashMap<>();
        for (int i = 0; i < order.size(); i++) {
            positions.put(order.get(i), i);
        }

        for (Vertex from : allVertices) {
            for (Vertex to : g.getNeighbors(from)) {
                if (positions.get(from) > positions.get(to)) {
                    return false;
                }
            }
        }
        return true;
    }
}