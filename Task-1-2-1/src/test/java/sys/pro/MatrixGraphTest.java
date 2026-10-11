package sys.pro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatrixGraphTest {

    private MatrixGraph graph;

    @BeforeEach
    void setUp() {
        graph = new MatrixGraph();
    }

    @Test
    void testAddVertex() {
        Vertex v1 = new Vertex(1);
        graph.addVertex(v1);

        List<Vertex> vertices = graph.getVertex();
        assertEquals(1, vertices.size(), "Graph should contain 1 vertex");
        assertTrue(vertices.contains(v1), "vertices should contain that vertex");

        graph.addVertex(v1);
        assertEquals(1, graph.getVertex().size(), "No duplicates");
    }

    @Test
    void testMatrixCapacityExpansion() {
        for (int i = 0; i < 15; i++) {
            graph.addVertex(new Vertex(i));
        }

        assertEquals(15, graph.getVertex().size(), "array should be expanded to 15");
    }

    @Test
    void testAddAndRemoveEdge() throws Exception {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        graph.addVertex(v1);
        graph.addVertex(v2);

        graph.addEdge(v1, v2);
        boolean[][] matrixAfterAdd = getAdjacencyMatrix(graph);
        assertTrue(matrixAfterAdd[0][1], "true for v1 -> v2");

        graph.removeEdge(v1, v2);
        boolean[][] matrixAfterRemove = getAdjacencyMatrix(graph);
        assertFalse(matrixAfterRemove[0][1], "sets false when deleted");
    }

    @Test
    void testGetVertexIsDefensiveCopy() {
        Vertex v1 = new Vertex(1);
        graph.addVertex(v1);

        List<Vertex> returnedList = graph.getVertex();
        returnedList.add(new Vertex(2));

        assertEquals(1, graph.getVertex().size(), "cannot change graph from list");
    }

    @Test
    void testGenerateGraphFrom(@TempDir Path tempDir) throws IOException, Exception {
        Path tempFile = tempDir.resolve("graph_test.txt");
        String fileContent =
                """
                        3 2
                        10
                        20
                        30
                        10 20
                        20 30
                        """;

        Files.writeString(tempFile, fileContent);

        graph.generateGraphFrom(tempFile.toAbsolutePath().toString());

        List<Vertex> vertices = graph.getVertex();
        assertEquals(3, vertices.size(), "3 vertices");

        assertEquals(10, vertices.get(0).getName());
        assertEquals(20, vertices.get(1).getName());
        assertEquals(30, vertices.get(2).getName());

        boolean[][] matrix = getAdjacencyMatrix(graph);
        assertTrue(matrix[0][1], "edge from 10 to 20");
        assertTrue(matrix[1][2], "edge from 20 to 30");
        assertFalse(matrix[0][2], "no edge from 10 to 30");
    }

    private boolean[][] getAdjacencyMatrix(MatrixGraph graph) throws Exception {
        Field field = MatrixGraph.class.getDeclaredField("adjacencyMatrix");
        field.setAccessible(true);
        return (boolean[][]) field.get(graph);
    }
}