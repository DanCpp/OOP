package sys.pro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IncidenceGraphTest {

    private IncidenceGraph graph;

    @BeforeEach
    void setUp() {
        graph = new IncidenceGraph();
    }

    @Test
    void testAddVertex() {
        Vertex v1 = new Vertex(1);
        graph.addVertex(v1);

        List<Vertex> vertices = graph.getVertex();
        assertEquals(1, vertices.size());
        assertTrue(vertices.contains(v1));

        graph.addVertex(v1);
        assertEquals(1, graph.getVertex().size());
    }

    @Test
    void testAddEdge() throws Exception {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        graph.addEdge(v1, v2);

        List<?> edges = getEdges(graph);
        int[][] incidenceMatrix = getIncidenceMatrix(graph);
        List<Vertex> vertices = graph.getVertex();

        assertEquals(1, edges.size());

        int v1Index = vertices.indexOf(v1);
        int v2Index = vertices.indexOf(v2);

        assertEquals(1, incidenceMatrix[v1Index][0]);
        assertEquals(-1, incidenceMatrix[v2Index][0]);

        graph.addEdge(v1, v2);
        assertEquals(1, getEdges(graph).size());
    }

    @Test
    void testRemoveEdge() throws Exception {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        graph.addEdge(v1, v2);
        graph.removeEdge(v1, v2);

        List<?> edges = getEdges(graph);
        assertEquals(0, edges.size(), "no more edge in list");
    }

    @Test
    void testGetVertexIsDefensiveCopy() {
        Vertex v1 = new Vertex(1);
        graph.addVertex(v1);

        List<Vertex> returnedList = graph.getVertex();
        returnedList.add(new Vertex(2));

        assertEquals(1, graph.getVertex().size(), "cannot change graph");
    }

    @Test
    void testGenerateGraphFrom(@TempDir Path tempDir) throws Exception {
        Path tempFile = tempDir.resolve("graph_incidence_test.txt");
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

        List<?> edges = getEdges(graph);
        assertEquals(2, edges.size());

        int[][] incidenceMatrix = getIncidenceMatrix(graph);
        Vertex v10 = vertices.stream().filter(v -> v.getName() == 10).findFirst().orElseThrow();
        Vertex v20 = vertices.stream().filter(v -> v.getName() == 20).findFirst().orElseThrow();
        Vertex v30 = vertices.stream().filter(v -> v.getName() == 30).findFirst().orElseThrow();

        final int v10Idx = vertices.indexOf(v10);
        final int v20Idx = vertices.indexOf(v20);
        final int v30Idx = vertices.indexOf(v30);

        assertEquals(1, incidenceMatrix[v10Idx][0]);
        assertEquals(-1, incidenceMatrix[v20Idx][0]);

        assertEquals(1, incidenceMatrix[v20Idx][1]);
        assertEquals(-1, incidenceMatrix[v30Idx][1]);
    }

    @Test
    void testGenerateGraphFromThrowsException() {
        assertThrows(FileNotFoundException.class, () -> {
            graph.generateGraphFrom("non_existent_file_12345.txt");
        });
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

    @Test
    void testIncidenceMatrixCapacityExpansion() throws Exception {
        for (int i = 0; i < 15; i++) {
            graph.addVertex(new Vertex(i));
        }
        assertEquals(15, graph.getVertex().size());

        for (int i = 0; i < 14; i++) {
            graph.addEdge(new Vertex(i), new Vertex(i + 1));
        }
        assertEquals(14, getEdges(graph).size());
    }

    @Test
    void testAddLoopEdge() throws Exception {
        Vertex v1 = new Vertex(1);
        graph.addEdge(v1, v1);

        int[][] incidenceMatrix = getIncidenceMatrix(graph);
        List<Vertex> vertices = graph.getVertex();

        int v1Index = vertices.indexOf(v1);
        assertEquals(2, incidenceMatrix[v1Index][0]);
    }

    @Test
    void testRemoveNonExistentEdge() throws Exception {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        graph.addEdge(v1, v2);

        assertDoesNotThrow(() -> graph.removeEdge(v2, v3));
        assertEquals(1, getEdges(graph).size());
    }

    @Test
    void testGetNeighbors() {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v2, v2); // loop

        List<Vertex> neighborsV1 = graph.getNeighbors(v1);
        assertEquals(2, neighborsV1.size());
        assertTrue(neighborsV1.contains(v2) && neighborsV1.contains(v3));

        List<Vertex> neighborsV2 = graph.getNeighbors(v2);
        assertEquals(1, neighborsV2.size());
        assertTrue(neighborsV2.contains(v2));
    }
}