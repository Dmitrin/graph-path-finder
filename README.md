GRAPH PATH FINDER
========

General description
========================

* A library that supports 2 types of directed and undirected graphs
  * method adds Vertex to the graph `addVertex(int num) `
  * method adds Edge directed - `addEdge(int from, int to, int weight)`
  * method adds Edge undirected `addUndirectedEdge(int from, int to, int weight)`

* Returns a list of edges between two Vertices of a `graph`
  * method `getPath(Integer s, Integer f)` returns a list of paths between two Vertices of the graph, where `s` is the starting Vertex for finding the path, `f` is the final Vertex

* Additional:
  * If the vertex `from` or` to` does not exist, it will be added
  * The verification added that the Vertex number `s` is not equal Vertex number `f`
  * Added the verification the Vertices number `s` and `f` added to the `graph`
