package uit.tool.app.graph;


import java.util.*;
import java.util.stream.Collectors;

public class Graph {
	private final ArrayList<Vertex> vertexes;
	private int size;
	double[][] matrix;

	public Graph(ArrayList<Vertex> vertexes) throws NullPointerException {
		if (vertexes == null) {
			throw new NullPointerException("Must provided at least 1 vertex");
		}
		this.vertexes = vertexes;
		this.size = this.vertexes.size();
		this.matrix = new double[size][size];

	}

	public Graph(ArrayList<Vertex> vertexes, ArrayList<Edge> edges) throws NullPointerException {
		this(vertexes);
		if (edges == null) {
			throw new NullPointerException("Must provided at least 1 edge");
		}
		this.matrix = edgeListToMatrix(edges);
	}

	public TreeSet<String> getVertexNames() {
		return this.vertexes.stream().map(Vertex::getName).collect(Collectors.toCollection(TreeSet::new));
	}

	public ArrayList<Vertex> getVertexes() {
		return vertexes;
	}

	public void updateVertexPosition(Vertex vertex, double newX, double newY) {
		vertex.setX(newX);
		vertex.setY(newY);
	}

	public double[][] adjacencyMatrix() {
//		for (double[] doubles : this.matrix) {
//			System.out.println(Arrays.toString(doubles));
//		}
		return this.matrix;
	}

	public void updateEdge(int row, int col, double weight) throws IllegalStateException {
		if (row >= size || col >= size || row < 0 || col < 0) {
			throw new IllegalStateException("Vertex not existed");
		}
		matrix[row][col] = weight;

	}

	public void updateEdge(Vertex from, Vertex to, double weight) {
		int row = vertexes.indexOf(from);
		int col = vertexes.indexOf(to);
		updateEdge(row, col, weight);
	}

	boolean isVertexUnique(Vertex vertex) {
		TreeSet<String> vertexNames = getVertexNames();
		return !vertexNames.contains(vertex.getName());
	}

	public void addVertex(Vertex v) throws IllegalStateException {
		if (!isVertexUnique(v)) {
			throw new IllegalStateException("Vertex name existed!");
		}
		this.vertexes.add(v);
		this.vertexes.sort(Comparator.comparing(Vertex::getName));
		ArrayList<Edge> edges = getEdgeList();
		this.size = this.vertexes.size();
		this.matrix = edgeListToMatrix(edges);
	}

	public void renameVertex(Vertex v,String vertexName) throws IllegalStateException{
		if (!isVertexUnique(new Vertex(vertexName,0.0,0.0))) {
			throw new IllegalStateException("Vertex name existed!");
		}
		v.setName(vertexName);
		ArrayList<Edge> edges = getEdgeList();
		this.vertexes.sort(Comparator.comparing(Vertex::getName));
		this.matrix = edgeListToMatrix(edges);

	}
	public void removeVertex(Vertex v) throws IllegalStateException{
		int index = this.vertexes.indexOf(v);
		if (index ==-1){
			throw new IllegalStateException("Vertex does not existed");
		}
		double sum =0;
		for (int i =0;i<size;i++){
			sum = sum + matrix[index][i];
			sum = sum + matrix[i][index];
		}
		if (sum != 0){
			throw new IllegalStateException("Vertex is connect to another vertexes, Remove edge first!");
		}
		ArrayList<Edge> edges = getEdgeList();
		this.vertexes.remove(v);
		this.size = this.vertexes.size();
		this.matrix = new double[this.size][this.size];
		edgeListToMatrix(edges);
	}

	public ArrayList<Edge> getEdgeList() {
		ArrayList<Edge> E = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (matrix[i][j] != 0) {
					E.add(new Edge(vertexes.get(i), vertexes.get(j), matrix[i][j]));
				}
			}
		}
		return E;
	}

	private double[][] edgeListToMatrix(ArrayList<Edge> E) {
		double matrix[][]= new double[this.size][this.size];
		for (Edge e : E) {
			Vertex from = e.getSource();
			Vertex to = e.getDestination();
			int fi = this.vertexes.indexOf(from);
			int ti = this.vertexes.indexOf(to);
			if (fi > size || ti > size || fi < 0 || ti < 0) {
				throw new IllegalStateException("No vertex existed!");
			}
			matrix[fi][ti] = e.getWeight();
		}
		return matrix;
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

}
