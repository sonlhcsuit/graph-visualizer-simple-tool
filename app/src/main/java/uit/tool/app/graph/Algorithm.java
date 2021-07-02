package uit.tool.app.graph;

import uit.tool.app.components.animation.Fronted;
import uit.tool.app.components.animation.VertexAnimation;
import uit.tool.app.components.animation.Visited;
import uit.tool.app.components.animation.VisualAnimation;
import uit.tool.app.components.animation.SetDefaultVertex;


import java.util.*;

import javax.swing.JOptionPane;

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

	public static ArrayList<VisualAnimation> Dijkstra(Graph graph, String from, String to){
		System.out.println(from);
		System.out.println(to);
//		Set up 
		ArrayList<VisualAnimation> animations = new ArrayList<>();
		ArrayList<Vertex> V = graph.getVertexes();
		ArrayList<String> vertexNames = new ArrayList<>(graph.getVertexNames());
		ArrayList<String> visited = new ArrayList<>(vertexNames.size());
		Stack<String> frontier = new Stack<>();
		double[][] edges = graph.adjacencyMatrix();

		return null;
	}

	public static ArrayList<VisualAnimation> hamiltonianPath(Graph graph) throws IllegalStateException{
		ArrayList<VisualAnimation> animations = new ArrayList<>();
		ArrayList<Vertex> vertexs = graph.getVertexes();
		double[][] edges = graph.adjacencyMatrix();
		ArrayList<String> vertexNames = new ArrayList<>(graph.getVertexNames());
		int size =  graph.getVertexes().size();
		System.out.println("Number vertex: " + size);
		printEdge(edges, size);
		printVertexNames(vertexNames);
		int [] path = new int[size];
		// Set all value path = -1
		for(int i = 0; i < size; i++){
			path[i] = -1;
		}
		printPath(path, size);
		path[0] = 0;
		animations.add( new Fronted(vertexs.get(path[0])));

		if(hamPathUtil(animations,vertexs, edges, path, 1, size) == false){
			System.out.println("\nSolution does not exist");
			throw new IllegalStateException("Hamiltonian path does not exist in the graph!!!");
		}
		else{
			printPath(path, size);
		}
		System.out.println(animations);
		return animations;
	}

	public static Boolean hamPathUtil(ArrayList<VisualAnimation> animations,ArrayList<Vertex> vertexs, double edges[][], int path [], int pos, int numVertex){

		if(pos == numVertex){
			animations.add(new Visited(vertexs.get(path[pos-1])));
			return true;
		}
		for (int v = 1; v < numVertex; v++)
        {
            /* Check if this vertex can be added to Hamiltonian
               Cycle */
			// System.out.println("v: " + v);
			// printPath(path, numVertex);
            if (isSafe(v, edges, path, pos))
            {
				animations.add(new Visited(vertexs.get(path[pos-1])));
                path[pos] = v;
				animations.add(new Fronted(vertexs.get(path[pos])));
                /* recur to construct rest of the path */
                if (hamPathUtil(animations,vertexs, edges, path, pos + 1, numVertex) == true)
                    return true;
                /* If adding vertex v doesn't lead to a solution,
                   then remove it */
				animations.add(new SetDefaultVertex(vertexs.get(path[pos])));
                path[pos] = -1;
            }
        }
		return false;
	}

	public static Boolean isSafe(int v, double edges[][], int path [], int pos){

        if (edges[path[pos - 1]][v] == 0 && edges[v][path[pos - 1]] == 0)
            return false;
        /* Check if the vertex has already been included.
           This step can be optimized by creating an array
           of size V */
        for (int i = 0; i < pos; i++)
            if (path[i] == v)
                return false;
        return true;
	}

	public static void printVertexNames(ArrayList<String> vertexNames){
		System.out.print("Vertex:");
		for(int i = 0; i < vertexNames.size(); i++){
			System.out.print(" " + vertexNames.get(i));
		}
		System.out.print("\n");
	}

	public static void printEdge(double edges[][], int size){
		for(int i = 0; i<size; i++){
			for(int j = 0; j<size; j++){
				System.out.print(" " + edges[i][j]);
			}
			System.out.print("\n");
		}		
	}

	public static void printPath(int path[], int size){
		System.out.print("Path:");
		for(int i = 0; i<size; i++){
			System.out.print(" " + path[i]);
		}
		System.out.println();
	}

	public static ArrayList<VisualAnimation> hamiltonianCycle(Graph graph){
		ArrayList<VisualAnimation> animations = new ArrayList<>();
		return animations;
	}

	
}
