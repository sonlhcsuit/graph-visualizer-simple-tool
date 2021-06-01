package uit.tool.app.graph;

public class Edge {
	private Vertex from = null;
	private Vertex to = null;
	Double weight = 0.0;

	public Edge(Vertex from, Vertex to, Double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public Edge(Vertex from, Vertex to) {
		this.from = from;
		this.to = to;
		this.weight = 0.0;
	}
}
