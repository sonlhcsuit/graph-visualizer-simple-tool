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

	public ArrayList<Vertex> getVertexes() {
		return vertexes;
	}

	static public Graph sampleGraph(){
		Vertex a = new Vertex("a", 100.0, 100.0);
		Vertex b = new Vertex("b", 200.0, 100.0);
		Vertex c = new Vertex("c", 200.0, 200.0);
		Vertex d = new Vertex("d", 150.0, 150.0);
		Vertex e = new Vertex("e", 100.0, 200.0);

		ArrayList<Vertex> V = new ArrayList<>();
		V.add(a);
		V.add(b);
		V.add(c);
		V.add(d);
		V.add(e);

		Edge ab = new Edge(a, b);
		Edge bc = new Edge(b, c);
		Edge de = new Edge(d, e);
		Edge ea = new Edge(e, a);
		Edge be = new Edge(b, e);

		ArrayList<Edge> E = new ArrayList<>();
		E.add(ab);
		E.add(bc);
		E.add(de);
		E.add(ea);
		E.add(be);
		Graph graph = new Graph(V, E);
		return graph;
	}

}
