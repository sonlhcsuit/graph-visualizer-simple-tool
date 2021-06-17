package uit.tool.app.graph;

public class Edge {
	private Vertex from = null;
	private Vertex to = null;
	Double weight;

	public Edge(Vertex from, Vertex to, Double weight) {
		this(from,to);
		this.weight = weight;
	}

	public Edge(Vertex from, Vertex to) {
		this.from = from;
		this.to = to;
	}
	public Vertex getSource(){
		return from;
	}
	public Vertex getDestination(){
		return to;
	}
}
