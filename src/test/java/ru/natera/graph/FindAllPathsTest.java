package ru.natera.graph;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FindAllPathsTest {

  @Test
  public void addVertex() {
    FindAllPaths.graph = new HashMap<>();
    FindAllPaths.isVisited = new HashSet<>();
    assertEquals(FindAllPaths.addVertex(1), Collections.emptySet());
  }

  @Test
  public void addAlreadyExistVertexValidCase() {
    FindAllPaths.graph = new HashMap<>();
    FindAllPaths.isVisited = new HashSet<>();
    FindAllPaths.addVertex(1);
    assertEquals(FindAllPaths.addVertex(1), Collections.emptySet());
  }

  @Test
  public void addEdgeBetweenExistingVertices() {
    FindAllPaths.graph = new HashMap<>();
    FindAllPaths.isVisited = new HashSet<>();

    FindAllPaths.addVertex(1);
    FindAllPaths.addVertex(2);

    FindAllPaths.addEdge(1, 2, 4);

    assertNotNull(FindAllPaths.graph.get(1));
    assertTrue(FindAllPaths.graph.get(1).contains(new FindAllPaths.Vertex(2, 4)));
    assertEquals(1, FindAllPaths.graph.get(1).size());
  }

  @Test
  public void addEdgeBetweenNotExistingVertices() {
    FindAllPaths.graph = new HashMap<>();
    FindAllPaths.isVisited = new HashSet<>();

    FindAllPaths.addEdge(1, 2, 4);

    assertNotNull(FindAllPaths.graph.get(1));
    assertTrue(FindAllPaths.graph.get(1).contains(new FindAllPaths.Vertex(2, 4)));
    assertEquals(1, FindAllPaths.graph.get(1).size());
  }

  @Test
  public void addUndirectedEdge() {
    FindAllPaths.graph = new HashMap<>();
    FindAllPaths.isVisited = new HashSet<>();

    FindAllPaths.addUndirectedEdge(1, 2, 4);

    assertNotNull(FindAllPaths.graph.get(1));
    assertNotNull(FindAllPaths.graph.get(2));
    assertTrue(FindAllPaths.graph.get(1).contains(new FindAllPaths.Vertex(2, 4)));
    assertTrue(FindAllPaths.graph.get(2).contains(new FindAllPaths.Vertex(1, 4)));
    assertEquals(1, FindAllPaths.graph.get(1).size());
    assertEquals(1, FindAllPaths.graph.get(2).size());
  }

  @Test
  public void addEdgeThatAlreadyExist() {
    FindAllPaths.graph = new HashMap<>();
    FindAllPaths.isVisited = new HashSet<>();

    FindAllPaths.addEdge(1, 2, 4);

    assertNotNull(FindAllPaths.graph.get(1));
    assertTrue(FindAllPaths.graph.get(1).contains(new FindAllPaths.Vertex(2, 4)));
    assertEquals(1, FindAllPaths.graph.get(1).size());

    FindAllPaths.addEdge(1, 2, 4);

    assertNotNull(FindAllPaths.graph.get(1));
    assertTrue(FindAllPaths.graph.get(1).contains(new FindAllPaths.Vertex(2, 4)));
    assertEquals(1, FindAllPaths.graph.get(1).size());
  }

  /**
   * Validation that the path search has received the expected result and there are no duplicates in the path list
   */
  @Test
  public void getPath() {
    final Integer s = 1;
    final Integer f = 7;

    FindAllPaths.graph = new HashMap<>();
    FindAllPaths.isVisited = new HashSet<>();

    FindAllPaths.addEdge(1, 2, 1);
    FindAllPaths.addEdge(1, 3, 1);
    FindAllPaths.addEdge(1, 4, 1);
    FindAllPaths.addEdge(2, 7, 1);
    FindAllPaths.addEdge(2, 8, 1);
    FindAllPaths.addEdge(3, 5, 1);
    FindAllPaths.addEdge(4, 6, 1);
    FindAllPaths.addEdge(5, 6, 1);
    FindAllPaths.addEdge(6, 7, 1);
    FindAllPaths.addEdge(6, 8, 1);
    FindAllPaths.addVertex(7);
    FindAllPaths.addEdge(8, 6, 1);
    FindAllPaths.addEdge(8, 7, 1);

    List<List<Integer>> actual = FindAllPaths.getPath(s, f);

    assertEquals(7, actual.size());
    assertEquals(actual.stream().distinct().count(), actual.size());

    assertEquals(Arrays.asList(1, 2, 8, 7), actual.get(0));
    assertEquals(Arrays.asList(1, 2, 8, 6, 7), actual.get(1));
    assertEquals(Arrays.asList(1, 2, 7), actual.get(2));
    assertEquals(Arrays.asList(1, 4, 6, 8, 7), actual.get(3));
    assertEquals(Arrays.asList(1, 4, 6, 7), actual.get(4));
    assertEquals(Arrays.asList(1, 3, 5, 6, 8, 7), actual.get(5));
    assertEquals(Arrays.asList(1, 3, 5, 6, 7), actual.get(6));
  }

  @Test
  public void getPathWithVertexNumberZero() {
    final Integer s = 1;
    final Integer f = 7;

    FindAllPaths.graph = new HashMap<>();
    FindAllPaths.isVisited = new HashSet<>();

    FindAllPaths.addEdge(0, 7, 1);
    FindAllPaths.addEdge(1, 0, 1);

    FindAllPaths.addVertex(7);

    assertEquals(1, FindAllPaths.getPath(s, f).size());
  }

  @Test
  public void getPathWithVertexWithNegativeNumber() {
    final Integer s = 1;
    final Integer f = 7;

    FindAllPaths.graph = new HashMap<>();
    FindAllPaths.isVisited = new HashSet<>();

    FindAllPaths.addEdge(-1, 7, 1);
    FindAllPaths.addEdge(1, -1, 1);

    FindAllPaths.addVertex(7);

    assertEquals(1, FindAllPaths.getPath(s, f).size());
  }

  @Test
  public void getPathBetweenTwoVerticesWithDifferentWeights() {
    final Integer s = 1;
    final Integer f = 7;

    FindAllPaths.graph = new HashMap<>();
    FindAllPaths.isVisited = new HashSet<>();

    FindAllPaths.addEdge(1, 2, 1);
    FindAllPaths.addEdge(2, 3, 1);
    FindAllPaths.addEdge(2, 3, 2);
    FindAllPaths.addEdge(2, 3, 3);
    FindAllPaths.addEdge(3, 7, 3);

    FindAllPaths.addVertex(7);

    assertEquals(3, FindAllPaths.getPath(s, f).size());
  }

  /**
   * Verify that there must be only one path, excluding Vertex 6, not connected to Vertex 7 (Finish)
   */
  @Test
  public void getPathBetweenTwoVerticesAdditionalPointOutsidePath() {
    final Integer s = 1;
    final Integer f = 7;

    FindAllPaths.graph = new HashMap<>();
    FindAllPaths.isVisited = new HashSet<>();

    FindAllPaths.addEdge(1, 2, 1);
    FindAllPaths.addEdge(2, 3, 1);
    FindAllPaths.addEdge(3, 7, 3);

    FindAllPaths.addEdge(3, 6, 3);

    FindAllPaths.addVertex(6);
    FindAllPaths.addVertex(7);

    assertEquals(1, FindAllPaths.getPath(s, f).size());
  }

  /**
   * Verify that when the Edges are added and the graph Vertices are automatically created
   */
  @Test
  public void getPathValidateAutoCreatedVerticesValidCase() {
    final Integer s = 1;
    final Integer f = 7;

    FindAllPaths.graph = new HashMap<>();
    FindAllPaths.isVisited = new HashSet<>();

    FindAllPaths.addEdge(1, 2, 1);
    FindAllPaths.addEdge(2, 3, 1);

    FindAllPaths.addVertex(7);

    assertEquals(0, FindAllPaths.getPath(s, f).size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPathWithStartVertexNotInGraph() {
    final Integer s = 1000;
    final Integer f = 7;

    FindAllPaths.graph = new HashMap<>();

    FindAllPaths.addVertex(7);

    FindAllPaths.getPath(s, f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPathWithFinishVertexNotInGraph() {
    final Integer s = 7;
    final Integer f = 1000;

    FindAllPaths.graph = new HashMap<>();

    FindAllPaths.addVertex(7);

    FindAllPaths.getPath(s, f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPathWithTheSameStartAndFinishVertex() {
    final Integer s = 7;
    final Integer f = 7;

    FindAllPaths.getPath(s, f);
  }
}
