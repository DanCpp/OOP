package sys.pro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Implementation of a directed graph using an adjacency matrix.
 * This matrix dynamically expands when the number of vertices exceeds its capacity.
 */
public class MatrixGraph implements Graph {
    private final List<Vertex> vertices;
    private boolean[][] adjacencyMatrix;
    private static final int INITIAL_CAPACITY = 10;

    /**
     * Initializes an empty matrix graph with a default initial capacity.
     */
    public MatrixGraph() {
        this.vertices = new ArrayList<>();
        this.adjacencyMatrix = new boolean[INITIAL_CAPACITY][INITIAL_CAPACITY];
    }

    /**
     * Adds a vertex to the graph. Expands the adjacency matrix if needed.
     *
     * @param v the vertex to be added
     */
    @Override
    public void addVertex(Vertex v) {
        if (!vertices.contains(v)) {
            vertices.add(v);
            int currentSize = vertices.size();

            if (currentSize > adjacencyMatrix.length) {
                int newCapacity = adjacencyMatrix.length * 2;
                boolean[][] newMatrix = new boolean[newCapacity][newCapacity];

                for (int i = 0; i < adjacencyMatrix.length; i++) {
                    System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0,
                            adjacencyMatrix[i].length);
                }
                adjacencyMatrix = newMatrix;
            }
        }
    }

    /**
     * Removes a vertex and all its incident edges from the graph.
     *
     * @param v the vertex to be removed
     */
    @Override
    public void removeVertex(Vertex v) {
        int index = vertices.indexOf(v);
        if (index == -1) {
            return;
        }

        vertices.remove(index);
        int size = vertices.size();

        for (int i = index; i < size; i++) {
            for (int j = 0; j <= size; j++) {
                adjacencyMatrix[i][j] = adjacencyMatrix[i + 1][j];
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = index; j < size; j++) {
                adjacencyMatrix[i][j] = adjacencyMatrix[i][j + 1];
            }
        }

        for (int i = 0; i <= size; i++) {
            adjacencyMatrix[size][i] = false;
            adjacencyMatrix[i][size] = false;
        }
    }

    /**
     * Adds a directed edge between two vertices. Automatically adds missing vertices.
     *
     * @param from the source vertex
     * @param to   the target vertex
     */
    @Override
    public void addEdge(Vertex from, Vertex to) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);

        if (fromIndex == -1) {
            addVertex(from);
            fromIndex = vertices.indexOf(from);
        }
        if (toIndex == -1) {
            addVertex(to);
            toIndex = vertices.indexOf(to);
        }

        adjacencyMatrix[fromIndex][toIndex] = true;
    }

    /**
     * Removes a directed edge between the specified vertices if they exist.
     *
     * @param from the source vertex
     * @param to   the target vertex
     */
    @Override
    public void removeEdge(Vertex from, Vertex to) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);

        if (fromIndex != -1 && toIndex != -1) {
            adjacencyMatrix[fromIndex][toIndex] = false;
        }
    }

    /**
     * Returns a copy of the list containing all vertices in the graph.
     *
     * @return a list of vertices
     */
    @Override
    public List<Vertex> getVertex() {
        return new ArrayList<>(vertices);
    }

    /**
     * Clears the graph and rebuilds it using data from the provided file.
     *
     * @param filename the path to the graph data file
     * @throws FileNotFoundException if the file cannot be opened
     */
    @Override
    public void generateGraphFrom(String filename) throws FileNotFoundException {
        this.vertices.clear();
        this.adjacencyMatrix = new boolean[INITIAL_CAPACITY][INITIAL_CAPACITY];

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
     * Retrieves all adjacent vertices connected by an outgoing edge from the given vertex.
     *
     * @param v the source vertex
     * @return a list of neighboring vertices
     */
    @Override
    public List<Vertex> getNeighbors(Vertex v) {
        List<Vertex> neighbors = new ArrayList<>();
        int index = vertices.indexOf(v);
        if (index != -1) {
            for (int i = 0; i < vertices.size(); i++) {
                if (adjacencyMatrix[index][i]) {
                    neighbors.add(vertices.get(i));
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
     * Returns a formatted string representation of the adjacency matrix.
     *
     * @return the adjacency matrix as a string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MatrixGraph: ");
        if (vertices.isEmpty()) {
            sb.append("  (empty) ");
            return sb.toString();
        }

        sb.append(String.format("%6s", ""));
        for (Vertex v : vertices) {
            sb.append(String.format("%5s", v));
        }
        sb.append(" ");

        for (int i = 0; i < vertices.size(); i++) {
            sb.append(String.format("%4s: ", vertices.get(i)));
            for (int j = 0; j < vertices.size(); j++) {
                sb.append(String.format("%5d", adjacencyMatrix[i][j] ? 1 : 0));
            }
            sb.append(" ");
        }
        return sb.toString();
    }
}
