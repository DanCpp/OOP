package sys.pro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ListGraph implements Graph {
    private final Map<Vertex, List<Vertex>> adjacencyList;

    public ListGraph() {
        this.adjacencyList = new LinkedHashMap<>();
    }

    @Override
    public void addVertex(Vertex v) {
        adjacencyList.putIfAbsent(v, new ArrayList<>());
    }

    @Override
    public void addEdge(Vertex from, Vertex to) {
        addVertex(from);
        addVertex(to);

        List<Vertex> edges = adjacencyList.get(from);
        if (!edges.contains(to)) {
            edges.add(to);
        }
    }

    @Override
    public void removeEdge(Vertex from, Vertex to) {
        List<Vertex> edges = adjacencyList.get(from);
        if (edges != null) {
            edges.remove(to);
        }
    }

    @Override
    public List<Vertex> getVertex() {
        return new ArrayList<>(adjacencyList.keySet());
    }

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
}