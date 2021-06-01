package uit.tool.app.graph;


import java.util.ArrayList;
import java.util.stream.Collectors;

public class Graph{
	ArrayList<Vertex> vertexes = null;
	ArrayList<Edge> edges = null;
	public Graph(ArrayList<Vertex> vertexes,ArrayList<Edge> edges) throws NullPointerException{
		if ( vertexes == null || edges==null ){
			throw new NullPointerException("Must provided at least 1 vertex or egde");
		}
		this.vertexes = vertexes;
		this.edges = edges;
	};

	public ArrayList<String> getVertexNames(){
		return this.vertexes.stream().map(Vertex::getName).collect(Collectors.toCollection(ArrayList::new));
	}

}
