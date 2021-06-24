package uit.tool.app.graph;

public class Edge {
	private Vertex from = null;
	private Vertex to = null;
	private double weight;

	public Edge(Vertex from, Vertex to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public Edge(Vertex from, Vertex to) {
		this(from, to, 0);
	}

	public Vertex getSource() {
		return from;
	}

	public Vertex getDestination() {
		return to;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Edge{" +
				"from=" + from +
				", to=" + to +
				", weight=" + weight +
				'}';
	}
}
