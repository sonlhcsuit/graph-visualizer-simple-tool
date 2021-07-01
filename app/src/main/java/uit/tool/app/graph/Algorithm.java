package uit.tool.app.graph;

import uit.tool.app.components.animation.Fronted;
import uit.tool.app.components.animation.VertexAnimation;
import uit.tool.app.components.animation.Visited;

import java.util.*;
import java.util.stream.Collectors;

public class Algorithm {
	public static ArrayList<VertexAnimation> BFS(Graph graph) {
		ArrayList<VertexAnimation> animations = new ArrayList<>();

		ArrayList<Vertex> V = graph.getVertexes();

		ArrayList<String> vertexNames = new ArrayList<>(graph.getVertexNames());
//
		ArrayList<String> visited = new ArrayList<>(vertexNames.size());

		Queue<String> frontier = new LinkedList<>();

		double[][] edges = graph.adjacencyMatrix();

		frontier.add(vertexNames.get(0));

		animations.add(new Fronted(V.get(0)));
		while (frontier.size() != 0) {

			String v = frontier.remove();

			animations.add(new Visited(V.get(vertexNames.indexOf(v))));
			visited.add(v);
			int vIndex = vertexNames.indexOf(v);
			for (int i = 0; i < edges.length; i++) {
				if (edges[vIndex][i] != 0) {
					if (!visited.contains(vertexNames.get(i))) {
						frontier.add(vertexNames.get(i));
						animations.add(new Fronted(V.get(i)));
					}
				}
			}


		}
		System.out.println(visited);
//		thứ tự chạy các animation tương ứng từng cạnh hoặc đỉnh
		return animations;

	}

	public static ArrayList<VertexAnimation> DFS(Graph graph) {
		ArrayList<VertexAnimation> animations = new ArrayList<>();

		ArrayList<Vertex> V = graph.getVertexes();

		ArrayList<String> vertexNames = new ArrayList<>(graph.getVertexNames());
//
		ArrayList<String> visited = new ArrayList<>(vertexNames.size());

		Stack<String> frontier = new Stack<>();

		double[][] edges = graph.adjacencyMatrix();

		frontier.push(vertexNames.get(0));

		animations.add(new Fronted(V.get(0)));
		while (frontier.size() != 0) {

			String v = frontier.pop();

			animations.add(new Visited(V.get(vertexNames.indexOf(v))));
			visited.add(v);
			int vIndex = vertexNames.indexOf(v);
			for (int i = 0; i < edges.length; i++) {
				if (edges[vIndex][i] != 0) {
					if (!visited.contains(vertexNames.get(i))) {
						frontier.push(vertexNames.get(i));
						animations.add(new Fronted(V.get(i)));
					}
				}
			}


		}
		System.out.println(visited);
//		thứ tự chạy các animation tương ứng từng cạnh hoặc đỉnh
		return animations;

	}

}
