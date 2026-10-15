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

    private static final int LOOP_EDGE = 2;
    private static final int FROM_EDGE = 1;
    private static final int NO_EDGE = 0;
    private static final int TO_EDGE = -1;

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
     * Removes a vertex and all its incident edges from the graph.
     *
     * @param v the vertex to be removed
     */
    @Override
    public void removeVertex(Vertex v) {
        int vertexIndex = vertices.indexOf(v);
        if (vertexIndex == -1) {
            return;
        }

        for (int i = edges.size() - 1; i >= 0; i--) {
            Edge edge = edges.get(i);
            if (edge.from.equals(v) || edge.to.equals(v)) {
                removeEdge(edge.from, edge.to);
            }
        }

        vertices.remove(vertexIndex);
        for (int i = vertexIndex; i < vertices.size(); i++) {
            for (int j = 0; j < edges.size(); j++) {
                incidenceMatrix[i][j] = incidenceMatrix[i + 1][j];
            }
        }
        for (int j = 0; j < edges.size(); j++) {
            incidenceMatrix[vertices.size()][j] = NO_EDGE;
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
        if (!vertices.contains(from)) {
            addVertex(from);
        }
        if (!vertices.contains(to)) {
            addVertex(to);
        }

        for (Edge e : edges) {
            if (e.from.equals(from) && e.to.equals(to)) {
                return;
            }
        }

        Edge newEdge = new Edge(from, to);
        edges.add(newEdge);
        ensureCapacity(vertices.size(), edges.size());

        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);
        int edgeIndex = edges.size() - 1;

        if (from.equals(to)) {
            incidenceMatrix[fromIndex][edgeIndex] = LOOP_EDGE;
        } else {
            incidenceMatrix[fromIndex][edgeIndex] = FROM_EDGE;
            incidenceMatrix[toIndex][edgeIndex] = TO_EDGE;
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
                incidenceMatrix[i][edges.size()] = NO_EDGE;
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
            if (!scanner.hasNextInt()) {
                return;
            }

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
     * @param vertexSize the required number of vertices
     * @param edgeSize   the required number of edges
     */
    private void ensureCapacity(int vertexSize, int edgeSize) {
        int vertexLen = incidenceMatrix.length;
        int edgeLen = incidenceMatrix[0].length;

        if (vertexSize > vertexLen || edgeSize > edgeLen) {
            int newVertexLen = Math.max(vertexLen * 2, vertexSize);
            int newEdgeLen = Math.max(edgeLen * 2, edgeSize);
            int[][] newMatrix = new int[newVertexLen][newEdgeLen];

            for (int i = 0; i < vertexLen; i++) {
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, edgeLen);
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
        int vertexIndex = vertices.indexOf(v);
        if (vertexIndex == -1) {
            return neighbors;
        }

        for (int e = 0; e < edges.size(); e++) {
            if (incidenceMatrix[vertexIndex][e] == FROM_EDGE
                    || incidenceMatrix[vertexIndex][e] == LOOP_EDGE) {
                for (int i = 0; i < vertices.size(); i++) {
                    if ((incidenceMatrix[i][e] == TO_EDGE || incidenceMatrix[i][e] == LOOP_EDGE)
                            && (i != vertexIndex || incidenceMatrix[vertexIndex][e] == LOOP_EDGE)) {
                        neighbors.add(vertices.get(i));
                        break;
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Compares this graph with another object for structural equality.
     *
     * @param o the object to compare with
     * @return {@code true} if {@code o} is a {@link Graph} with the same vertices and edges
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Graph other)) {
            return false;
        }
        return Graph.equals(this, other);
    }

    /**
     * Returns the hash code for this graph.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Graph.hashCode(this);
    }

    /**
     * Returns a formatted string representation of the incidence matrix.
     *
     * @return the incidence matrix as a string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IncidenceGraph:");
        if (vertices.isEmpty()) {
            sb.append("  (empty)  ");
            return sb.toString();
        }

        sb.append(String.format("%6s", ""));
        for (Edge edge : edges) {
            sb.append(String.format("%8s", edge.from + "->" + edge.to));
        }
        sb.append("");

        for (int i = 0; i < vertices.size(); i++) {
            sb.append(String.format("%4s: ", vertices.get(i)));
            for (int j = 0; j < edges.size(); j++) {
                sb.append(String.format("%8d", incidenceMatrix[i][j]));
            }
            sb.append("");
        }
        return sb.toString();
    }
}
