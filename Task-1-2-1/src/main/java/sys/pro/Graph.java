package sys.pro;

import java.util.List;

public interface Graph {
    void addVertex(Vertex v);
    void addEdge(Vertex from, Vertex to);

    void removeEdge(Vertex from, Vertex to);
    List<Vertex> getVertex();
    void generateGraphFrom(String filename);
}
