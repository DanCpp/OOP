package sys.pro;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Represents a directed graph data structure.
 * Provides essential methods for graph manipulation and traversal.
 */
public interface Graph {

    /**
     * Adds a new vertex to the graph.
     *
     * @param v the vertex to be added
     */
    void addVertex(Vertex v);

    /**
     * Adds a directed edge between two vertices in the graph.
     *
     * @param from the source vertex
     * @param to   the target vertex
     */
    void addEdge(Vertex from, Vertex to);

    /**
     * Removes a directed edge between two vertices if it exists.
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
}