package uit.tool.app.graph;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Graph {
	ArrayList<Vertex> vertexes = null;
	ArrayList<Edge> edges = null;
	double[][] matrix;

	public Graph(ArrayList<Vertex> vertexes, ArrayList<Edge> edges) throws NullPointerException {
		if (vertexes == null || edges == null) {
			throw new NullPointerException("Must provided at least 1 vertex or edge");
		}
		this.vertexes = vertexes;
		this.edges = edges;
		int size = this.vertexes.size();
		this.matrix = new double[size][size];

		for (Edge e : this.edges) {
			Vertex from = e.getSource();
			Vertex to = e.getDestination();
			int fi = this.vertexes.indexOf(from);
			int ti = this.vertexes.indexOf(to);
			this.matrix[fi][ti] = e.getWeight();
		}
	}


	public ArrayList<String> getVertexNames() {
		return this.vertexes.stream().map(Vertex::getName).collect(Collectors.toCollection(ArrayList::new));
	}

	public ArrayList<Vertex> getVertexes() {
		return vertexes;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void updateVertexPosition(Vertex vertex, double newX, double newY) {
		vertex.setX(newX);
		vertex.setY(newY);
	}

	static public Graph sampleGraph() {
		Vertex a = new Vertex("a", 200.0, 50.0);
		Vertex b = new Vertex("b", 300.0, 350.0);
		Vertex c = new Vertex("c", 450.0, 250.0);
		Vertex d = new Vertex("d", 80.0, 450.0);
		Vertex e = new Vertex("e", 100.0, 200.0);

		ArrayList<Vertex> V = new ArrayList<>();
		V.add(a);
		V.add(b);
		V.add(c);
		V.add(d);
		V.add(e);

		Edge ab = new Edge(a, b, 4);
		Edge bc = new Edge(b, c, 5);
		Edge de = new Edge(d, e, 3);
		Edge ea = new Edge(e, a, 6);
		Edge be = new Edge(b, e, 1);
		Edge ac = new Edge(a, c, 2);
		ArrayList<Edge> E = new ArrayList<>();
		E.add(ab);
		E.add(bc);
		E.add(de);
		E.add(ea);
		E.add(be);
		E.add(ac);
		return new Graph(V, E);
	}

	public double[][] adjacencyMatrix() {
		for (double[] doubles : this.matrix) {
			System.out.println(Arrays.toString(doubles));
		}
		return this.matrix;
	}

	public void updateWeight(int row, int col, double weight) {
		matrix[row][col] = weight;
		Vertex from = vertexes.get(row);
		Vertex to = vertexes.get(col);

		for (Edge e : edges) {
			if (from == e.getSource() && to == e.getDestination()) {
				e.setWeight(weight);
			}
		}
		adjacencyMatrix();
	}

	public void updateWeight(Vertex from, Vertex to, double weight) {
		int fi = vertexes.indexOf(from);
		int ti = vertexes.indexOf(to);
		matrix[fi][ti] = weight;
	}
}
