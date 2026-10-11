package sys.pro;

import java.util.Objects;

/**
 * Represents a single vertex (or node) in a graph.
 * Each vertex is uniquely identified by an integer name.
 */
public class Vertex {
    private final int name;

    /**
     * Constructs a new vertex with the specified integer identifier.
     *
     * @param name the unique integer identifier for this vertex
     */
    Vertex(int name) {
        this.name = name;
    }

    /**
     * Retrieves the integer identifier of this vertex.
     *
     * @return the integer name of the vertex
     */
    int getName() {
        return name;
    }

    /**
     * Computes the hash code for this vertex based on its name.
     *
     * @return the hash code value for this vertex
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}