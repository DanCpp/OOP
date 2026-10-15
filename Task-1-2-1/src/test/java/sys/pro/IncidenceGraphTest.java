package sys.pro;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link IncidenceGraph}, inheriting common {@link Graph} contract
 * tests from {@link GraphTest} and adding incidence-matrix-specific checks.
 */
class IncidenceGraphTest extends GraphTest {

    @Override
    protected IncidenceGraph createGraph() {
        return new IncidenceGraph();
    }

    @Test
    void testIncidenceMatrixInternalValues() throws Exception {
        IncidenceGraph incGraph = (IncidenceGraph) graph;
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        incGraph.addEdge(v1, v2);
        incGraph.addEdge(v1, v1);

        List<?> edges = getEdges(incGraph);
        int[][] incidenceMatrix = getIncidenceMatrix(incGraph);
        List<Vertex> vertices = incGraph.getVertex();

        assertEquals(2, edges.size());

        int v1Index = vertices.indexOf(v1);
        int v2Index = vertices.indexOf(v2);

        assertEquals(1, incidenceMatrix[v1Index][0]);
        assertEquals(-1, incidenceMatrix[v2Index][0]);
        assertEquals(2, incidenceMatrix[v1Index][1]);
        assertEquals(0, incidenceMatrix[v2Index][1]);
    }

    @Test
    void testIncidenceMatrixShiftedOnRemoveVertex() throws Exception {
        IncidenceGraph incGraph = (IncidenceGraph) graph;
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        incGraph.addEdge(v1, v2);
        incGraph.addEdge(v2, v3);
        incGraph.addEdge(v1, v3);

        incGraph.removeVertex(v2);

        List<?> edges = getEdges(incGraph);
        int[][] incidenceMatrix = getIncidenceMatrix(incGraph);
        List<Vertex> vertices = incGraph.getVertex();

        assertEquals(1, edges.size(), "Only edge v1 -> v3 should remain");
        assertEquals(2, vertices.size());

        int v1Index = vertices.indexOf(v1);
        int v3Index = vertices.indexOf(v3);

        assertEquals(1, incidenceMatrix[v1Index][0]);
        assertEquals(-1, incidenceMatrix[v3Index][0]);
        assertEquals(0, incidenceMatrix[2][0], "Cleared row should be 0");
    }

    @Test
    void testIncidenceGraphToStringFormat() {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        graph.addEdge(v1, v2);

        String str = graph.toString();
        assertTrue(str.startsWith("IncidenceGraph:"));
        assertTrue(str.contains("1->2"));
        assertTrue(str.contains("-1"));
    }

    @Test
    void testIncidenceGraphAddBackEdge() throws Exception {
        IncidenceGraph incidenceGraph = (IncidenceGraph) graph;
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        incidenceGraph.addEdge(v1, v2);
        List<?> edges = getEdges(incidenceGraph);
        int[][] incidenceMatrix = getIncidenceMatrix(incidenceGraph);
        List<Vertex> vertices = incidenceGraph.getVertex();

        int v1Index = vertices.indexOf(v1);
        int v2Index = vertices.indexOf(v2);

        assertEquals(1, edges.size());
        assertEquals(1, incidenceMatrix[v1Index][0]);
        assertEquals(-1, incidenceMatrix[v2Index][0]);

        incidenceGraph.addEdge(v2, v1);

        edges = getEdges(incidenceGraph);
        incidenceMatrix = getIncidenceMatrix(incidenceGraph);
        vertices = incidenceGraph.getVertex();

        v1Index = vertices.indexOf(v1);
        v2Index = vertices.indexOf(v2);

        assertEquals(2, edges.size());
        assertEquals(1, incidenceMatrix[v1Index][0]);
        assertEquals(-1, incidenceMatrix[v2Index][0]);
        assertEquals(-1, incidenceMatrix[v1Index][1]);
        assertEquals(1, incidenceMatrix[v2Index][1]);
    }

    @SuppressWarnings("unchecked")
    private List<?> getEdges(IncidenceGraph graph) throws Exception {
        Field field = IncidenceGraph.class.getDeclaredField("edges");
        field.setAccessible(true);
        return (List<?>) field.get(graph);
    }

    private int[][] getIncidenceMatrix(IncidenceGraph graph) throws Exception {
        Field field = IncidenceGraph.class.getDeclaredField("incidenceMatrix");
        field.setAccessible(true);
        return (int[][]) field.get(graph);
    }
}
