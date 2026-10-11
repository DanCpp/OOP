package sys.pro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Implementation of a directed graph using an incidence matrix.
 * Rows represent vertices and columns represent edges.
 * A value of 1 means the edge leaves the vertex, -1 means it enters,
 * and 2 represents a self-loop.
 */
public class IncidenceGraph implements Graph {
    private final List<Vertex> vertices;
    private final List<Edge> edges;
    private int[][] incidenceMatrix;
    private static final int INITIAL_CAPACITY = 10;

    /**
     * Internal class representing a directed edge in the graph.
     */
    private static class Edge {
        Vertex from;
        Vertex to;

        /**
         * Constructs a new edge.
         *
         * @param from the source vertex
         * @param to   the target vertex
         */
        Edge(Vertex from, Vertex to) {
            this.from = from;
            this.to = to;
        }
    }

    /**
     * Initializes an empty incidence graph with a default initial capacity.
     */
    public IncidenceGraph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.incidenceMatrix = new int[INITIAL_CAPACITY][INITIAL_CAPACITY];
    }

    /**
     * Adds a vertex to the graph if it is not already present.
     * Expands the incidence matrix if necessary.
     *
     * @param v the vertex to be added
     */
    @Override
    public void addVertex(Vertex v) {
        if (!vertices.contains(v)) {
            vertices.add(v);
            ensureCapacity(vertices.size(), edges.size());
        }
    }

    /**
     * Adds a directed edge between two vertices.
     * If the vertices do not exist, they are added to the graph automatically.
     *
     * @param from the source vertex
     * @param to   the target vertex
     */
    @Override
    public void addEdge(Vertex from, Vertex to) {
        if (!vertices.contains(from)) addVertex(from);
        if (!vertices.contains(to)) addVertex(to);

        for (Edge e : edges) {
            if (e.from.equals(from) && e.to.equals(to)) return;
        }

        Edge newEdge = new Edge(from, to);
        edges.add(newEdge);
        ensureCapacity(vertices.size(), edges.size());

        int vFromIndex = vertices.indexOf(from);
        int vToIndex = vertices.indexOf(to);
        int edgeIndex = edges.size() - 1;

        if (from.equals(to)) {
            incidenceMatrix[vFromIndex][edgeIndex] = 2;
        } else {
            incidenceMatrix[vFromIndex][edgeIndex] = 1;
            incidenceMatrix[vToIndex][edgeIndex] = -1;
        }
    }

    /**
     * Removes the directed edge between the specified vertices, if it exists.
     *
     * @param from the source vertex
     * @param to   the target vertex
     */
    @Override
    public void removeEdge(Vertex from, Vertex to) {
        int edgeIndex = -1;
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            if (e.from.equals(from) && e.to.equals(to)) {
                edgeIndex = i;
                break;
            }
        }

        if (edgeIndex != -1) {
            edges.remove(edgeIndex);
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = edgeIndex; j < edges.size(); j++) {
                    incidenceMatrix[i][j] = incidenceMatrix[i][j + 1];
                }
                incidenceMatrix[i][edges.size()] = 0;
            }
        }
    }

    /**
     * Returns a copy of the list of all vertices currently in the graph.
     *
     * @return a list of vertices
     */
    @Override
    public List<Vertex> getVertex() {
        return new ArrayList<>(vertices);
    }

    /**
     * Clears the current graph and populates it using data from the specified text file.
     *
     * @param filename the path to the file containing graph data
     * @throws FileNotFoundException if the file cannot be found or read
     */
    @Override
    public void generateGraphFrom(String filename) throws FileNotFoundException {
        this.vertices.clear();
        this.edges.clear();
        this.incidenceMatrix = new int[INITIAL_CAPACITY][INITIAL_CAPACITY];

        try (Scanner scanner = new Scanner(new File(filename))) {
            if (!scanner.hasNextInt()) return;

            int v = scanner.nextInt();
            int e = scanner.nextInt();

            Map<Integer, Vertex> vertexMap = new HashMap<>();

            for (int i = 0; i < v; i++) {
                int vertexName = scanner.nextInt();
                Vertex newVertex = new Vertex(vertexName);
                addVertex(newVertex);
                vertexMap.put(vertexName, newVertex);
            }

            for (int i = 0; i < e; i++) {
                int fromName = scanner.nextInt();
                int toName = scanner.nextInt();

                Vertex fromVertex = vertexMap.get(fromName);
                Vertex toVertex = vertexMap.get(toName);

                if (fromVertex != null && toVertex != null) {
                    addEdge(fromVertex, toVertex);
                }
            }
        }
    }

    /**
     * Ensures the incidence matrix is large enough to hold the given number of
     * vertices and edges. Expands the internal array if needed.
     *
     * @param vSize the required number of vertices
     * @param eSize the required number of edges
     */
    private void ensureCapacity(int vSize, int eSize) {
        int vLen = incidenceMatrix.length;
        int eLen = incidenceMatrix[0].length;

        if (vSize > vLen || eSize > eLen) {
            int newVLen = Math.max(vLen * 2, vSize);
            int newELen = Math.max(eLen * 2, eSize);
            int[][] newMatrix = new int[newVLen][newELen];

            for (int i = 0; i < vLen; i++) {
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, eLen);
            }
            incidenceMatrix = newMatrix;
        }
    }

    /**
     * Retrieves all adjacent vertices that can be reached from a given vertex
     * via an outgoing directed edge.
     *
     * @param v the source vertex
     * @return a list of neighboring vertices
     */
    @Override
    public List<Vertex> getNeighbors(Vertex v) {
        List<Vertex> neighbors = new ArrayList<>();
        int vIndex = vertices.indexOf(v);
        if (vIndex == -1) return neighbors;

        for (int e = 0; e < edges.size(); e++) {
            if (incidenceMatrix[vIndex][e] == 1 || incidenceMatrix[vIndex][e] == 2) {
                for (int i = 0; i < vertices.size(); i++) {
                    if ((incidenceMatrix[i][e] == -1 || incidenceMatrix[i][e] == 2) &&
                            (i != vIndex || incidenceMatrix[vIndex][e] == 2)) {
                        neighbors.add(vertices.get(i));
                        break;
                    }
                }
            }
        }
        return neighbors;
    }
}