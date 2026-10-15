package sys.pro;

import java.util.*;

/**
 * Utility class that provides algorithms for topological sorting of a directed graph.
 * It supports sorting using both Depth-First Search (DFS) and Kahn's algorithm.
 */
public class TopSort {

    /**
     * Performs a topological sort on the given graph using Depth-First Search (DFS).
     *
     * @param graph the directed graph to be sorted
     * @return a list of vertices in topologically sorted order
     * @throws IllegalStateException if the graph contains a cycle
     */
    public static List<Vertex> dfsSort(Graph graph) {
        List<Vertex> result = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        Set<Vertex> inStack = new HashSet<>();

        for (Vertex v : graph.getVertex()) {
            if (!visited.contains(v)) {
                if (!dfs(v, graph, visited, inStack, result)) {
                    throw new IllegalStateException("Cycle");
                }
            }
        }

        Collections.reverse(result);
        return result;
    }

    /**
     * A recursive helper method for the DFS-based topological sort.
     *
     * @param current the current vertex being visited
     * @param graph   the graph being traversed
     * @param visited a set of all completely processed vertices
     * @param inStack a set of vertices currently in the recursion stack (used to detect cycles)
     * @param result  the list accumulating the sorted vertices
     * @return {@code true} if no cycle is detected, {@code false} otherwise
     */
    private static boolean dfs(Vertex current, Graph graph, Set<Vertex> visited,
                               Set<Vertex> inStack, List<Vertex> result) {
        visited.add(current);
        inStack.add(current);
        for (Vertex neighbor : graph.getNeighbors(current)) {
            if (inStack.contains(neighbor)) {
                return false;
            }
            if (!visited.contains(neighbor)) {
                if (!dfs(neighbor, graph, visited, inStack, result)) {
                    return false;
                }
            }
        }

        inStack.remove(current);
        result.add(current);
        return true;
    }

    /**
     * Performs a topological sort on the given graph using Kahn's algorithm (BFS-based).
     *
     * @param graph the directed graph to be sorted
     * @return a list of vertices in topologically sorted order
     * @throws IllegalStateException if the graph contains a cycle
     */
    public static List<Vertex> kahnsSort(Graph graph) {
        Map<Vertex, Integer> inDegree = new HashMap<>();
        List<Vertex> allVertices = graph.getVertex();

        for (Vertex v : allVertices) {
            inDegree.putIfAbsent(v, 0);
            for (Vertex neighbor : graph.getNeighbors(v)) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
            }
        }

        Queue<Vertex> queue = new LinkedList<>();
        for (Map.Entry<Vertex, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<Vertex> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            result.add(current);

            for (Vertex neighbor : graph.getNeighbors(current)) {
                int count = inDegree.get(neighbor) - 1;
                inDegree.put(neighbor, count);

                if (count == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (result.size() != allVertices.size()) {
            throw new IllegalStateException("Cycle");
        }

        return result;
    }
}