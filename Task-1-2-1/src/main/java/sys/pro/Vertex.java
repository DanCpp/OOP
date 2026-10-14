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
    public Vertex(int name) {
        this.name = name;
    }

    /**
     * Retrieves the integer identifier of this vertex.
     *
     * @return the integer name of the vertex
     */
    public int getName() {
        return name;
    }

    /**
     * Compares this vertex to the specified object for equality.
     * Two vertices are equal if they have the same integer name.
     *
     * @param o the object to compare with
     * @return {@code true} if the vertices have the same name, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vertex vertex)) {
            return false;
        }
        return name == vertex.name;
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

    /**
     * Returns the string representation of this vertex.
     *
     * @return the vertex name as a string
     */
    @Override
    public String toString() {
        return String.valueOf(name);
    }
}
