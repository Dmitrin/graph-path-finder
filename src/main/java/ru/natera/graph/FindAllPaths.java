package ru.natera.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class FindAllPaths {

  static Map<Integer, Set<Vertex>> graph;
  static Set<Integer> isVisited;

  /**
   * Add Vertex to graph
   *
   * @param num Vertex number
   * @return List of related vertices (in outbound direction)
   */
  static Set<Vertex> addVertex(int num) {
    return graph.computeIfAbsent(num, k -> new HashSet<>());
  }

  /**
   * Add directed Edge to graph
   * If the specified Vertex {@code from} did not exist, the method for creating it will be called
   * If the specified vertex {@code to} did not exist, the method for creating it will be called
   *
   * @param from   - Vertex number 'from'
   * @param to     - Vertex number 'to'
   * @param weight - Vertex Edge
   */
  static void addEdge(int from, int to, int weight) {
    Set<Vertex> connectedVertices = graph.getOrDefault(from, addVertex(from));
    graph.getOrDefault(to, addVertex(to));
    connectedVertices.add(new Vertex(to, weight));
  }

  /**
   * Add undirected Edge to graph
   * If the specified Vertex {@code from} did not exist, the method for creating it will be called
   * If the specified vertex {@code to} did not exist, the method for creating it will be called
   *
   * @param from   - Vertex number 'from'
   * @param to     - Vertex number 'to'
   * @param weight - Vertex Edge
   */
  static void addUndirectedEdge(int from, int to, int weight) {
    Set<Vertex> toVertices = graph.getOrDefault(from, addVertex(from));
    toVertices.add(new Vertex(to, weight));
    Set<Vertex> fromVertices = graph.getOrDefault(to, addVertex(to));
    fromVertices.add(new Vertex(from, weight));
  }

  /**
   * Example of finding a list of paths between the two vertices of the graph {@code s} and {@code f}
   */
  public static void main(String[] args) {
    final Integer s = 1;
    final Integer f = 7;

    graph = new HashMap<>();
    isVisited = new HashSet<>();

    addEdge(1, 2, 1);
    addEdge(1, 3, 1);
    addEdge(1, 4, 1);
    addEdge(2, 7, 1);
    addEdge(2, 8, 1);
    addEdge(3, 5, 1);
    addEdge(4, 6, 1);
    addEdge(5, 6, 1);
    addEdge(6, 7, 1);
    addEdge(6, 8, 1);

    addVertex(7);
    addEdge(8, 6, 1);
    addEdge(8, 7, 1);

    printPath(getPath(s, f));
  }

  /**
   * Search and output to console all paths from the Vertex {@code s} to the Vertex {@code f}
   *
   * @param s - Start Vertex
   * @param f - Finish Vertex
   *
  */
  public static List<List<Integer>> getPath(Integer s, Integer f) {
    if (s.equals(f)) {
      throw new IllegalArgumentException("The values of the 's' and 'f' must be different!");
    }

    if (graph.get(s) == null) {
      throw new IllegalArgumentException("The vertex s is not in the graph!");
    }

    if (graph.get(f) == null) {
      throw new IllegalArgumentException("The vertex f is not in the graph!");
    }

    ArrayList<Integer> path = new ArrayList<>();
    path.add(s);

    List<List<Integer>> result = new ArrayList<>();

    findAllPaths(s, f, path, result);

    return result;
  }

  /**
   * Recursive method for traversing a graph in depth
   *
   * @param cur    - Current Vertex
   * @param f      - Finish Vertex
   * @param path   - Current path from Vertex s to Vertex {@code cur}
   * @param result - List of all the paths found from Vertex s to Vertex f
   */
  private static void findAllPaths(Integer cur, Integer f, List<Integer> path, List<List<Integer>> result) {
    if (cur.equals(f)) {
      result.add(new ArrayList<>(path));
      return;
    }

    isVisited.add(cur);

    for (Vertex vertex : graph.get(cur)) {
      int to = vertex.to;

      if (!isVisited.contains(to)) {
        path.add(to);
        findAllPaths(to, f, path, result);
        path.remove(path.size() - 1);
      }
    }

    isVisited.remove(cur);
  }

  /**
   * Print {@code path} to console
   *
   * @param path List of paths from Vertex s to Vertex f
   */
  public static void printPath(List<List<Integer>> path) {
    path.forEach(System.out::println);
  }

  /**
   * Vertex class
   */
  public static class Vertex {

    int to;
    int weight;

    public Vertex(int to, int weight) {
      this.to = to;
      this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Vertex vertex = (Vertex) o;
      return to == vertex.to &&
              weight == vertex.weight;
    }

    @Override
    public int hashCode() {
      return Objects.hash(to, weight);
    }
  }

}
