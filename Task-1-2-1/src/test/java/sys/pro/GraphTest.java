package sys.pro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Abstract base test class for all {@link Graph} implementations.
 * Contains common tests that verify the contract of the {@link Graph} interface.
 */
abstract class GraphTest {

    protected Graph graph;

    /**
     * Creates a new empty instance of the specific {@link Graph} implementation under test.
     *
     * @return a new graph instance
     */
    protected abstract Graph createGraph();

    @BeforeEach
    void setUp() {
        graph = createGraph();
    }

    @Test
    void testAddVertex() {
        Vertex v1 = new Vertex(1);
        graph.addVertex(v1);

        List<Vertex> vertices = graph.getVertex();
        assertEquals(1, vertices.size());
        assertTrue(vertices.contains(new Vertex(1)));

        graph.addVertex(new Vertex(1));
        assertEquals(1, graph.getVertex().size(), "Duplicate vertex should not be added");
    }

    @Test
    void testRemoveVertexRemovesIncidentEdges() {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        graph.addEdge(v1, v2);
        graph.addEdge(v2, v3);
        graph.addEdge(v1, v3);
        graph.addEdge(v2, v2);

        graph.removeVertex(v2);

        List<Vertex> vertices = graph.getVertex();
        assertEquals(2, vertices.size());
        assertFalse(vertices.contains(v2));
        assertTrue(vertices.contains(v1));
        assertTrue(vertices.contains(v3));

        List<Vertex> v1Neighbors = graph.getNeighbors(v1);
        assertEquals(1, v1Neighbors.size());
        assertTrue(v1Neighbors.contains(v3));
        assertFalse(v1Neighbors.contains(v2));

        assertTrue(graph.getNeighbors(v2).isEmpty());
    }

    @Test
    void testAddVertexAfterRemoveVertex() {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        graph.addEdge(v1, v2);
        graph.removeVertex(v1);
        graph.addEdge(v2, v3);

        assertEquals(2, graph.getVertex().size());
        assertEquals(List.of(v3), graph.getNeighbors(v2));
    }

    @Test
    void testAddEdge() {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        graph.addEdge(v1, v2);

        assertEquals(2, graph.getVertex().size());
        assertTrue(graph.getNeighbors(v1).contains(v2));
        assertFalse(graph.getNeighbors(v2).contains(v1));

        graph.addEdge(v1, v2);
        assertEquals(1, graph.getNeighbors(v1).size(), "Duplicate edge should not be added");
    }

    @Test
    void testAddLoopEdge() {
        Vertex v1 = new Vertex(1);
        graph.addEdge(v1, v1);

        List<Vertex> neighbors = graph.getNeighbors(v1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(v1));
    }

    @Test
    void testRemoveEdge() {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        graph.addEdge(v1, v2);
        graph.removeEdge(v1, v2);

        assertTrue(graph.getNeighbors(v1).isEmpty(), "Edge should be removed");
        assertEquals(2, graph.getVertex().size(), "Vertices should remain after edge removal");
    }

    @Test
    void testRemoveNonExistentEdge() {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        graph.addEdge(v1, v2);

        assertDoesNotThrow(() -> graph.removeEdge(v2, v3));
        assertEquals(1, graph.getNeighbors(v1).size());
    }

    @Test
    void testGetNeighbors() {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v2, v2);

        List<Vertex> neighborsV1 = graph.getNeighbors(v1);
        assertEquals(2, neighborsV1.size());
        assertTrue(neighborsV1.contains(v2) && neighborsV1.contains(v3));

        List<Vertex> neighborsV2 = graph.getNeighbors(v2);
        assertEquals(1, neighborsV2.size());
        assertTrue(neighborsV2.contains(v2));

        assertTrue(graph.getNeighbors(new Vertex(999)).isEmpty());
    }

    @Test
    void testGetVertexIsDefensiveCopy() {
        Vertex v1 = new Vertex(1);
        graph.addVertex(v1);

        List<Vertex> returnedList = graph.getVertex();
        returnedList.add(new Vertex(2));

        assertEquals(1, graph.getVertex().size(), "Modifying returned list must not change graph");
    }

    @Test
    void testCapacityExpansion() {
        for (int i = 0; i < 15; i++) {
            graph.addVertex(new Vertex(i));
        }
        assertEquals(15, graph.getVertex().size());

        for (int i = 0; i < 14; i++) {
            graph.addEdge(new Vertex(i), new Vertex(i + 1));
        }
        for (int i = 0; i < 14; i++) {
            assertEquals(List.of(new Vertex(i + 1)), graph.getNeighbors(new Vertex(i)));
        }
    }

    @Test
    void testGenerateGraphFrom(@TempDir Path tempDir) throws Exception {
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
        assertEquals(3, vertices.size());
        assertTrue(vertices.contains(new Vertex(10)));
        assertTrue(vertices.contains(new Vertex(20)));
        assertTrue(vertices.contains(new Vertex(30)));

        assertEquals(List.of(new Vertex(20)), graph.getNeighbors(new Vertex(10)));
        assertEquals(List.of(new Vertex(30)), graph.getNeighbors(new Vertex(20)));
        assertTrue(graph.getNeighbors(new Vertex(30)).isEmpty());
    }

    @Test
    void testGenerateGraphFromThrowsException() {
        assertThrows(FileNotFoundException.class, () -> {
            graph.generateGraphFrom("non_existent_file_12345.txt");
        });
    }

    @Test
    void testEqualsAndHashCodeSameRepresentation() {
        Graph other = createGraph();

        assertEquals(graph, other);
        assertEquals(graph.hashCode(), other.hashCode());
        assertEquals(graph, graph);
        assertNotEquals(graph, null);
        assertNotEquals(graph, "not a graph");

        // Populate in different vertex and edge order
        graph.addEdge(new Vertex(1), new Vertex(2));
        graph.addEdge(new Vertex(1), new Vertex(3));
        graph.addVertex(new Vertex(4));

        other.addVertex(new Vertex(4));
        other.addEdge(new Vertex(1), new Vertex(3));
        other.addEdge(new Vertex(1), new Vertex(2));

        assertEquals(graph, other);
        assertTrue(graph.equals(other));
        assertEquals(graph.hashCode(), other.hashCode());

        other.addEdge(new Vertex(2), new Vertex(3));
        assertNotEquals(graph, other);
    }

    @Test
    void testEqualsAcrossDifferentRepresentations() {
        Graph listGraph = new ListGraph();
        Graph matrixGraph = new MatrixGraph();
        Graph incidenceGraph = new IncidenceGraph();

        for (Graph g : List.of(graph, listGraph, matrixGraph, incidenceGraph)) {
            g.addEdge(new Vertex(1), new Vertex(2));
            g.addEdge(new Vertex(2), new Vertex(3));
            g.addEdge(new Vertex(3), new Vertex(1));
            g.addVertex(new Vertex(4));
        }

        assertEquals(graph, listGraph);
        assertEquals(graph, matrixGraph);
        assertEquals(graph, incidenceGraph);
        assertTrue(Graph.equals(listGraph, matrixGraph));
        assertTrue(Graph.equals(matrixGraph, incidenceGraph));

        assertEquals(graph.hashCode(), listGraph.hashCode());
        assertEquals(graph.hashCode(), matrixGraph.hashCode());
        assertEquals(graph.hashCode(), incidenceGraph.hashCode());

        // Remove the same vertex across all representations and verify they stay equal
        for (Graph g : List.of(graph, listGraph, matrixGraph, incidenceGraph)) {
            g.removeVertex(new Vertex(2));
        }

        assertEquals(graph, listGraph);
        assertEquals(graph, matrixGraph);
        assertEquals(graph, incidenceGraph);

        // Modify one graph and verify inequality
        listGraph.removeEdge(new Vertex(3), new Vertex(1));
        assertNotEquals(graph, listGraph);
    }

    @Test
    void testToStringNotEmptyAndReflectsChanges() {
        String emptyStr = graph.toString();
        assertNotNull(emptyStr);
        assertTrue(emptyStr.contains("(empty)"));

        Vertex v10 = new Vertex(10);
        Vertex v20 = new Vertex(20);
        graph.addEdge(v10, v20);

        String populatedStr = graph.toString();
        assertTrue(populatedStr.contains("10"));
        assertTrue(populatedStr.contains("20"));

        graph.removeVertex(v20);
        String afterRemovalStr = graph.toString();
        assertTrue(afterRemovalStr.contains("10"));
        assertFalse(afterRemovalStr.contains("20"));
    }
}
