package uit.tool.app.graph;

import uit.tool.app.components.animation.VertexAnimation;
import uit.tool.app.components.animation.Visited;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Algorithm {
	public static void BFS(Graph graph) {
		System.out.println("run bfs");
//		ArrayList<Vertex> vertex = graph.getVertexes();
//
//
//		ArrayList<Vertex> visited = new ArrayList<>(vertex.size());
//		Stack<Vertex> frontier = new Stack<>();
//
//
//		double [][] edges = graph.adjacencyMatrix();
//
//		frontier.add(vertex.get(0));
//		while (frontier.size()!=0){
//
//			vertex.remove();
//
//		}
//


	}

	public static ArrayList<VertexAnimation> DFS(Graph graph) {

		ArrayList<VertexAnimation> animations = new ArrayList<>();
		ArrayList<Vertex> V = graph.getVertexes();
		ArrayList<String> vertexes = new ArrayList<>(graph.getVertexNames());
//
		ArrayList<String> visited = new ArrayList<>(vertexes.size());
		Stack<String> frontier = new Stack<>();
		double[][] edges = graph.adjacencyMatrix();

		frontier.push(vertexes.get(0));
		while (frontier.size() != 0) {
			String v = frontier.pop();
			int vIndex = vertexes.indexOf(v);
			for (int i = 0; i < edges.length; i++) {
				if (edges[vIndex][i] != 0) {
					if (!visited.contains(vertexes.get(i))) {
						frontier.push(vertexes.get(i));
					}
				}
			}

			animations.add(new Visited(V.get(vertexes.indexOf(v))));
			visited.add(v);
		}
		System.out.println(visited);
//		thứ tự chạy các animation tương ứng từng cạnh hoặc đỉnh
		return animations;

	}
}
