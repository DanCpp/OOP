package sys.pro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Implementation of a directed graph using an adjacency list.
 * It uses a LinkedHashMap to maintain the insertion order of vertices.
 */
public class ListGraph implements Graph {
    private final Map<Vertex, List<Vertex>> adjacencyList;

    /**
     * Initializes an empty adjacency list graph.
     */
    public ListGraph() {
        this.adjacencyList = new LinkedHashMap<>();
    }

    /**
     * Adds a vertex to the graph if it is not already present.
     *
     * @param v the vertex to be added
     */
    @Override
    public void addVertex(Vertex v) {
        adjacencyList.putIfAbsent(v, new ArrayList<>());
    }

    /**
     * Removes a vertex and all its incident edges from the graph.
     *
     * @param v the vertex to be removed
     */
    @Override
    public void removeVertex(Vertex v) {
        if (!adjacencyList.containsKey(v)) {
            return;
        }

        adjacencyList.remove(v);
        for (List<Vertex> neighbors : adjacencyList.values()) {
            neighbors.remove(v);
        }
    }

    /**
     * Adds a directed edge between two vertices. Missing vertices are added automatically.
     *
     * @param from the source vertex
     * @param to   the target vertex
     */
    @Override
    public void addEdge(Vertex from, Vertex to) {
        addVertex(from);
        addVertex(to);

        List<Vertex> edges = adjacencyList.get(from);
        if (!edges.contains(to)) {
            edges.add(to);
        }
    }

    /**
     * Removes a directed edge between the specified vertices if it exists.
     *
     * @param from the source vertex
     * @param to   the target vertex
     */
    @Override
    public void removeEdge(Vertex from, Vertex to) {
        List<Vertex> edges = adjacencyList.get(from);
        if (edges != null) {
            edges.remove(to);
        }
    }

    /**
     * Returns a copy of the list of all vertices currently in the graph.
     *
     * @return a list of vertices
     */
    @Override
    public List<Vertex> getVertex() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    /**
     * Clears the graph and populates it with vertices and edges from a text file.
     *
     * @param filename the path to the graph data file
     * @throws FileNotFoundException if the file cannot be found or read
     */
    @Override
    public void generateGraphFrom(String filename) throws FileNotFoundException {
        adjacencyList.clear();

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
     * Retrieves a list of adjacent vertices reachable from the given vertex.
     *
     * @param v the source vertex
     * @return a list of neighboring vertices
     */
    @Override
    public List<Vertex> getNeighbors(Vertex v) {
        return new ArrayList<>(adjacencyList.getOrDefault(v, new ArrayList<>()));
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
     * Returns a formatted string representation of the adjacency list.
     *
     * @return the adjacency list as a string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ListGraph: ");
        if (adjacencyList.isEmpty()) {
            sb.append("  (empty) ");
            return sb.toString();
        }

        for (Map.Entry<Vertex, List<Vertex>> entry : adjacencyList.entrySet()) {
            sb.append("  ")
              .append(entry.getKey())
              .append(" -> ")
              .append(entry.getValue())
              .append(" ");
        }
        return sb.toString();
    }
}
