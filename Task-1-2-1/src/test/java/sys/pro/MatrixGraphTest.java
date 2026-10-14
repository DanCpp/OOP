package sys.pro;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MatrixGraph}, inheriting common {@link Graph} contract
 * tests from {@link GraphTest} and adding adjacency-matrix-specific checks.
 */
class MatrixGraphTest extends GraphTest {

    @Override
    protected MatrixGraph createGraph() {
        return new MatrixGraph();
    }

    @Test
    void testAdjacencyMatrixInternalFlags() throws Exception {
        MatrixGraph matrixGraph = (MatrixGraph) graph;
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        matrixGraph.addEdge(v1, v2);
        boolean[][] matrix = getAdjacencyMatrix(matrixGraph);
        assertTrue(matrix[0][1]);
        assertFalse(matrix[1][0]);

        matrixGraph.removeEdge(v1, v2);
        assertFalse(matrix[0][1]);
    }

    @Test
    void testAdjacencyMatrixShiftedOnRemoveVertex() throws Exception {
        MatrixGraph matrixGraph = (MatrixGraph) graph;
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        matrixGraph.addEdge(v1, v2);
        matrixGraph.addEdge(v2, v3);
        matrixGraph.addEdge(v1, v3);

        matrixGraph.removeVertex(v2);

        boolean[][] matrix = getAdjacencyMatrix(matrixGraph);
        assertTrue(matrix[0][1], "v1 -> v3 should now be at [0][1]");
        assertFalse(matrix[0][2], "Old column index 2 should be cleared");
        assertFalse(matrix[2][0], "Old row index 2 should be cleared");
    }

    @Test
    void testMatrixGraphToStringFormat() {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        graph.addEdge(v1, v2);

        String str = graph.toString();
        assertTrue(str.startsWith("MatrixGraph:"));
        assertTrue(str.contains("1:"));
        assertTrue(str.contains("2:"));
    }

    private boolean[][] getAdjacencyMatrix(MatrixGraph graph) throws Exception {
        Field field = MatrixGraph.class.getDeclaredField("adjacencyMatrix");
        field.setAccessible(true);
        return (boolean[][]) field.get(graph);
    }
}
