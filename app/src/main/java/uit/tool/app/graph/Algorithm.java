package uit.tool.app.graph;


import uit.tool.app.components.animation.*;

import uit.tool.app.graph.Utilities.*;

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
			if (!from.equals(vertexU)){
				animations.add(new Selected(
						V.get(vertexNames.indexOf(previous.get(vertexU))),
						V.get(vertexNames.indexOf(vertexU))
				));
			}
			vertexNamesSet.remove(vertexU);

			int uIndex = vertexNames.indexOf(vertexU);
			for (int vIndex = 0; vIndex < size; vIndex++) {
				if (edges[uIndex][vIndex] != 0) {
					String vertexV = vertexNames.get(vIndex);
					double alt = distance.get(vertexU) + edges[uIndex][vIndex];
					if (alt < distance.get(vertexV)) {
						distance.put(vertexV,alt);
						previous.put(vertexV,vertexU);
						animations.add(new Considered(
								V.get(vertexNames.indexOf(vertexU)),
								V.get(vertexNames.indexOf(vertexV))
						));
						animations.add(new Fronted(V.get(vertexNames.indexOf(vertexV))));

					}
				}
			}
		}
		return animations;
	}

	public static ArrayList<VisualAnimation> hamiltonianPath(Graph graph) throws IllegalStateException{
		ArrayList<VisualAnimation> animations = new ArrayList<>();
		ArrayList<Vertex> vertexs = graph.getVertexes();
		double[][] edges = graph.adjacencyMatrix();
		ArrayList<String> vertexNames = new ArrayList<>(graph.getVertexNames());
		int size =  graph.getVertexes().size();
		System.out.println("Number vertex: " + size);
		Utilities.printEdge(edges, size);
		Utilities.printVertexNames(vertexNames);
		int [] path = new int[size];
		// Set all value path = -1
		for(int i = 0; i < size; i++){
			path[i] = -1;
		}
		Utilities.printPath(path, size);
		path[0] = 0;
		animations.add( new Fronted(vertexs.get(path[0])));

		if(hamPathUtil(animations,vertexs, edges, path, 1, size) == false){
			System.out.println("\nSolution does not exist");
			throw new IllegalStateException("Hamiltonian path does not exist in the graph!!!");
		}
		else{
			Utilities.printPath(path, size);
		}
		System.out.println(animations);
		return animations;
	}

	public static Boolean hamPathUtil(ArrayList<VisualAnimation> animations,ArrayList<Vertex> vertexs, double edges[][], int path [], int pos, int numVertex){

		if(pos == numVertex){
			animations.add(new Selected(vertexs.get(path[pos-2]), vertexs.get(path[pos-1])));
			animations.add(new Visited(vertexs.get(path[pos-1])));
			return true;
		}
		for (int v = 1; v < numVertex; v++)
        {
            if (isSafe(v, edges, path, pos))
            {

				Vertex prevVertex = vertexs.get(path[pos-1]);
				animations.add(new Visited(prevVertex));
				if(pos-2 >= 0){
					Vertex prevprevVertex = vertexs.get(path[pos-2]);
					animations.add(new Selected(prevprevVertex, prevVertex));
				}
                path[pos] = v;
				Vertex curVertex = vertexs.get(path[pos]);
				animations.add(new Fronted(curVertex));
				animations.add(new Considered(prevVertex, curVertex));
                if (hamPathUtil(animations,vertexs, edges, path, pos + 1, numVertex) == true)
                    return true;
				animations.add(new SetBlackEdge(prevVertex, curVertex));
				animations.add(new SetDefaultVertex(curVertex));
                path[pos] = -1;
            }
        }
		return false;
	}

	public static Boolean isSafe(int v, double edges[][], int path [], int pos){

        if (edges[path[pos - 1]][v] == 0 && edges[v][path[pos - 1]] == 0)
            return false;
        for (int i = 0; i < pos; i++)
            if (path[i] == v)
                return false;
        return true;
	}

	public static ArrayList<VisualAnimation> hamiltonianCycle(Graph graph){
		ArrayList<VisualAnimation> animations = new ArrayList<>();
		return animations;
	}

	
}
