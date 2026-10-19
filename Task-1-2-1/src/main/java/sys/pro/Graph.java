package sys.pro;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a directed graph data structure.
 * Provides essential methods for graph manipulation and traversal.
 */
public interface Graph {

    /**
     * Adds a new vertex to the graph.
     * Nothing happens if vertex is already present.
     *
     * @param v the vertex to be added
     */
    void addVertex(Vertex v);

    /**
     * Removes a present vertex in the graph.
     * Nothing happens if vertex is not present.
     * @param v is the vertex to be removed
     */
    void removeVertex(Vertex v);

    /**
     * Adds a directed edge between two vertices in the graph.
     * Nothing happens if edge (from, to) is already present.
     *
     * @param from the source vertex
     * @param to   the target vertex
     */
    void addEdge(Vertex from, Vertex to);

    /**
     * Removes a directed edge between two vertices if it exists.
     * Nothing happens if edge is not present.
     *
     * @param from the source vertex
     * @param to   the target vertex
     */
    void removeEdge(Vertex from, Vertex to);

    /**
     * Retrieves a list of all vertices currently present in the graph.
     *
     * @return a list of vertices
     */
    List<Vertex> getVertex();

    /**
     * Clears the current graph and rebuilds it using data from a specified file.
     *
     * @param filename the path to the text file containing graph data
     * @throws FileNotFoundException if the specified file cannot be found
     */
    void generateGraphFrom(String filename) throws FileNotFoundException;

    /**
     * Retrieves a list of adjacent vertices reachable from the given vertex
     * via outgoing edges.
     *
     * @param v the source vertex
     * @return a list of neighboring vertices
     */
    List<Vertex> getNeighbors(Vertex v);

    /**
     * Compares this graph with another graph for structural equality,
     * regardless of the underlying graph representation.
     *
     * @param other the other graph to compare with
     * @return {@code true} if both graphs have the same vertices and directed edges
     */
    default boolean equals(Graph other) {
        return Graph.equals(this, other);
    }

    /**
     * Compares two graphs for structural equality, regardless of their
     * concrete implementation (IncidenceGraph, MatrixGraph, or ListGraph).
     *
     * @param g1 the first graph
     * @param g2 the second graph
     * @return {@code true} if both graphs contain the exact same vertices and edges
     */
    static boolean equals(Graph g1, Graph g2) {
        if (g1 == g2) {
            return true;
        }
        if (g1 == null || g2 == null) {
            return false;
        }

        List<Vertex> vList1 = g1.getVertex();
        List<Vertex> vList2 = g2.getVertex();
        if (vList1.size() != vList2.size()) {
            return false;
        }

        Set<Vertex> vertices1 = new HashSet<>(vList1);
        Set<Vertex> vertices2 = new HashSet<>(vList2);
        if (!vertices1.equals(vertices2)) {
            return false;
        }

        for (Vertex v : vertices1) {
            Set<Vertex> neighbors1 = new HashSet<>(g1.getNeighbors(v));
            Set<Vertex> neighbors2 = new HashSet<>(g2.getNeighbors(v));
            if (!neighbors1.equals(neighbors2)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Computes a representation-independent hash code for a graph based on
     * its vertices and outgoing edges.
     *
     * @param graph the graph to hash
     * @return the hash code value
     */
    static int hashCode(Graph graph) {
        if (graph == null) {
            return 0;
        }
        int hash = 0;
        for (Vertex v : new HashSet<>(graph.getVertex())) {
            hash += Objects.hash(v, new HashSet<>(graph.getNeighbors(v)));
        }
        return hash;
    }

    /**
     * Compares the specified object with this graph for equality.
     * Returns {@code true} if the specified object is also a {@link Graph}
     * and the two graphs have the same vertices and edges.
     *
     * @param o object to be compared for equality with this graph
     * @return {@code true} if the specified object is equal to this graph
     */
    @Override
    boolean equals(Object o);

    /**
     * Returns the hash code value for this graph.
     *
     * @return the hash code value for this graph
     */
    @Override
    int hashCode();

    /**
     * Returns a string representation of this graph.
     *
     * @return a string representation of this graph
     */
    @Override
    String toString();
}
