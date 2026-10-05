package sys.pro;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatrixGraph implements Graph {
    private final List<Vertex> vertices;
    private boolean[][] adjacencyMatrix;
    private static final int INITIAL_CAPACITY = 10;

    public MatrixGraph() {
        this.vertices = new ArrayList<>();
        this.adjacencyMatrix = new boolean[INITIAL_CAPACITY][INITIAL_CAPACITY];
    }

    @Override
    public void addVertex(Vertex v) {
        if (!vertices.contains(v)) {
            vertices.add(v);
            int currentSize = vertices.size();

            if (currentSize > adjacencyMatrix.length) {
                int newCapacity = adjacencyMatrix.length * 2;
                boolean[][] newMatrix = new boolean[newCapacity][newCapacity];

                for (int i = 0; i < adjacencyMatrix.length; i++) {
                    System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, adjacencyMatrix[i].length);
                }
                adjacencyMatrix = newMatrix;
            }
        }
    }

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

    @Override
    public void removeEdge(Vertex from, Vertex to) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);

        if (fromIndex != -1 && toIndex != -1) {
            adjacencyMatrix[fromIndex][toIndex] = false;
        }
    }

    @Override
    public List<Vertex> getVertex() {
        return new ArrayList<>(vertices);
    }

    @Override
    public void generateGraphFrom(String filename) throws FileNotFoundException {
        this.vertices.clear();
        this.adjacencyMatrix = new boolean[INITIAL_CAPACITY][INITIAL_CAPACITY];

        try (java.util.Scanner scanner = new Scanner(new java.io.File(filename))) {
            if (!scanner.hasNextInt()) {
                return;
            }

            int v = scanner.nextInt();
            int e = scanner.nextInt();

            java.util.Map<Integer, Vertex> vertexMap = new java.util.HashMap<>();

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