package sys.pro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ListGraphTest {

    private ListGraph graph;

    @BeforeEach
    void setUp() {
        graph = new ListGraph();
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

        Map<Vertex, List<Vertex>> adjList = getAdjacencyList(graph);

        assertTrue(adjList.containsKey(v1));
        assertTrue(adjList.containsKey(v2));

        assertTrue(adjList.get(v1).contains(v2));

        assertFalse(adjList.get(v2).contains(v1));

        graph.addEdge(v1, v2);
        assertEquals(1, adjList.get(v1).size());
    }

    @Test
    void testRemoveEdge() throws Exception {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        graph.addEdge(v1, v2);
        graph.removeEdge(v1, v2);

        Map<Vertex, List<Vertex>> adjList = getAdjacencyList(graph);
        assertFalse(adjList.get(v1).contains(v2), "no more edge in list");
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
        Path tempFile = tempDir.resolve("graph_list_test.txt");
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

        Map<Vertex, List<Vertex>> adjList = getAdjacencyList(graph);
        Vertex v10 = vertices.stream().filter(v -> v.getName() == 10).findFirst().orElseThrow();
        Vertex v20 = vertices.stream().filter(v -> v.getName() == 20).findFirst().orElseThrow();
        Vertex v30 = vertices.stream().filter(v -> v.getName() == 30).findFirst().orElseThrow();

        assertTrue(adjList.get(v10).contains(v20));
        assertTrue(adjList.get(v20).contains(v30));
        assertFalse(adjList.get(v10).contains(v30));
    }

    @Test
    void testGenerateGraphFromThrowsException() {
        assertThrows(FileNotFoundException.class, () -> {
            graph.generateGraphFrom("non_existent_file_12345.txt");
        });
    }

    @SuppressWarnings("unchecked")
    private Map<Vertex, List<Vertex>> getAdjacencyList(ListGraph graph) throws Exception {
        Field field = ListGraph.class.getDeclaredField("adjacencyList");
        field.setAccessible(true);
        return (Map<Vertex, List<Vertex>>) field.get(graph);
    }
}