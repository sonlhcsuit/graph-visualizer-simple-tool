package uit.tool.app.graph;

import uit.tool.app.components.animation.*;

import java.util.*;

public class Algorithm {
	public static ArrayList<VisualAnimation> BFS(Graph graph) {
//		Need that fucking array list to be visualize
//		Add Visual type in this array list and it will do the magic for u
		ArrayList<VisualAnimation> animations = new ArrayList<>();
//		Actual vertex, need it to bind it to animation
		ArrayList<Vertex> V = graph.getVertexes();
//		Vertex name, for better understand
		ArrayList<String> vertexNames = new ArrayList<>(graph.getVertexNames());
//		Visited (traversal)
		ArrayList<String> visited = new ArrayList<>(vertexNames.size());
//		Frontier (stack)
		Queue<String> frontier = new LinkedList<>();
		double[][] edges = graph.adjacencyMatrix();

//		The very first vertex
		frontier.add(vertexNames.get(0));
		animations.add(new Fronted(V.get(0)));
		while (frontier.size() != 0) {
			String currentVertex = frontier.remove();
			animations.add(new Visited(V.get(vertexNames.indexOf(currentVertex))));
			visited.add(currentVertex);
			int vIndex = vertexNames.indexOf(currentVertex);
			for (int i = 0; i < edges.length; i++) {
				if (edges[vIndex][i] != 0) {
					if (!visited.contains(vertexNames.get(i)) && !frontier.contains(vertexNames.get(i))) {
						frontier.add(vertexNames.get(i));
						animations.add(new Fronted(V.get(i)));
					}
				}
			}
		}
		return animations;
	}

	public static ArrayList<VisualAnimation> DFS(Graph graph) {

		ArrayList<VisualAnimation> animations = new ArrayList<>();
		ArrayList<Vertex> V = graph.getVertexes();
		ArrayList<String> vertexNames = new ArrayList<>(graph.getVertexNames());
		ArrayList<String> visited = new ArrayList<>(vertexNames.size());
		Stack<String> frontier = new Stack<>();
		double[][] edges = graph.adjacencyMatrix();

		frontier.push(vertexNames.get(0));
		animations.add(new Fronted(V.get(0)));

		while (frontier.size() != 0) {

			String currentVertex = frontier.pop();
			animations.add(new Visited(V.get(vertexNames.indexOf(currentVertex))));
			visited.add(currentVertex);
			int vIndex = vertexNames.indexOf(currentVertex);

			for (int i = 0; i < edges.length; i++) {
				if (edges[vIndex][i] != 0) {
					if (!visited.contains(vertexNames.get(i)) && !frontier.contains(vertexNames.get(i))) {
						frontier.push(vertexNames.get(i));
						animations.add(new Fronted(V.get(i)));
					}
				}
			}
		}
//		thứ tự chạy các animation tương ứng từng cạnh hoặc đỉnh
		return animations;

	}

	public static ArrayList<VisualAnimation> Dijkstra(Graph graph, String from, String to) {
//		Set up
		ArrayList<VisualAnimation> animations = new ArrayList<>();
		ArrayList<Vertex> V = graph.getVertexes();
		ArrayList<String> vertexNames = new ArrayList<>(graph.getVertexNames());
		Set<String> vertexNamesSet = new HashSet<>(vertexNames);


		int sourceIndex = vertexNames.indexOf(from);
		double[][] edges = graph.adjacencyMatrix();
		int size = edges.length;


//		create hash table, can easily find the minimum value from source to destination
		HashMap<String, Double> distance = new HashMap<>();
		HashMap<String, String> previous = new HashMap<>();


		for (String vertexName : vertexNames) {
			distance.put(vertexName, Double.MAX_VALUE);
			previous.put(vertexName, null);
		}
		distance.put(vertexNames.get(sourceIndex), 0.0);

		while (vertexNamesSet.size() != 0) {
//			find the minimum value from source to any other vertexes
			String vertexU = null;
			double minDistance = Double.MAX_VALUE;
			for (String key : vertexNamesSet) {
				if (distance.get(key) < minDistance  ) {
					vertexU = key;
					minDistance = distance.get(key);
				}
			}
			animations.add(new Visited(V.get(vertexNames.indexOf(vertexU))));

			vertexNamesSet.remove(vertexU);
			String vertexV = null;

			int uIndex = vertexNames.indexOf(vertexU);
			for (int vIndex = 0; vIndex < size; vIndex++) {
				if (edges[uIndex][vIndex] != 0) {
					vertexV = vertexNames.get(vIndex);
					double alt = distance.get(vertexU) + edges[uIndex][vIndex];
					if (alt < distance.get(vertexV)) {
						distance.put(vertexV,alt);
						previous.put(vertexV,vertexU);
//						them canh
//						animations.add(new Considered(
//								V.get(vertexNames.indexOf(vertexU)),
//								V.get(vertexNames.indexOf(vertexV))
//						));
						animations.add(new Fronted(V.get(vertexNames.indexOf(vertexV))));
					}
				}
			}
			if (vertexV != null){
				animations.add(new Visited(V.get(vertexNames.indexOf(previous.get(vertexV)))));
//				animations.add(new Selected(
//						V.get(vertexNames.indexOf(vertexU)),
//						V.get(vertexNames.indexOf(vertexV))
//				));
			}
		}

		for (String k : previous.keySet()){
			System.out.printf("%s -> %s\n",previous.get(k),k);
		}

		return animations;
	}
}
