package uit.tool.app.graph;


import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {
	private final ArrayList<Vertex> vertexes;
	private Setting setting;
	private int size;
	double[][] matrix;
	private String name;

	Graph(ArrayList<Vertex> vertexes) throws NullPointerException {
		if (vertexes == null) {
			throw new NullPointerException("Must provided at least 1 vertex");
		}
		this.vertexes = vertexes;
		this.size = this.vertexes.size();
		this.matrix = new double[size][size];

	}

	public Graph(ArrayList<Vertex> vertexes, ArrayList<Edge> edges, String name) throws NullPointerException {
		this(vertexes);
		if (edges != null) {
			this.matrix = edgeListToMatrix(edges);
		}
		this.name = name;
	}

	public Graph(ArrayList<Vertex> vertexes, ArrayList<Edge> edges, Setting setting) throws NullPointerException {
		this(vertexes);
		if (edges != null) {
			this.matrix = edgeListToMatrix(edges);
		}
		this.setting = setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public Setting getSetting() {
		return setting;
	}

	public String getName() {
		return name;
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
		ArrayList<Edge> edges = getEdgeList();
		this.vertexes.add(v);
		this.vertexes.sort(Comparator.comparing(Vertex::getName));
		this.size = this.vertexes.size();
		this.matrix = edgeListToMatrix(edges);
	}

	public void renameVertex(Vertex v, String vertexName) throws IllegalStateException {
		if (!isVertexUnique(new Vertex(vertexName, 0.0, 0.0))) {
			throw new IllegalStateException("Vertex name existed!");
		}
		v.setName(vertexName);
		ArrayList<Edge> edges = getEdgeList();
		this.vertexes.sort(Comparator.comparing(Vertex::getName));
		this.matrix = edgeListToMatrix(edges);
	}

	public void removeVertex(Vertex v) throws IllegalStateException {
		int index = this.vertexes.indexOf(v);
		if (index == -1) {
			throw new IllegalStateException("Vertex does not existed");
		}
		double sum = 0;
		for (int i = 0; i < size; i++) {
			sum = sum + matrix[index][i];
			sum = sum + matrix[i][index];
		}
		if (sum != 0) {
			throw new IllegalStateException("Vertex is connect to another vertexes, Remove edge first!");
		}
		ArrayList<Edge> edges = getEdgeList();
		this.vertexes.remove(v);
		this.size = this.vertexes.size();
		this.matrix = edgeListToMatrix(edges);
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
		double[][] matrix = new double[this.size][this.size];
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

	static public String convertToFileString(Graph g, String name) {
		String metadata = String.format("Name: %s", name);
		String numberVertex = String.format("Number of Vertex: %d", g.vertexes.size());
		StringBuilder vertexes = new StringBuilder();
		for (Vertex v : g.vertexes) {
			vertexes.append(v.toFileString()).append("\n");
		}

		StringBuilder edges = new StringBuilder();
		for (double[] row : g.matrix) {
			edges.append(Arrays.toString(row)).append("\n");
		}
		return String.format("%s\n%s\n%s%s", metadata, numberVertex, vertexes.toString(), edges.toString());
	}

	static public Graph parseFromFileString(String fileString) throws IllegalStateException {
		String[] lines = fileString.split("\n");
		String name = lines[0];
		String sizeString = lines[1];
		int size = 0;
		System.out.println(name);
		System.out.println(size);
		if (!name.matches("^Name:\s[a-zA-Z0-9\s]+$")) {
			throw new IllegalStateException("Cannot parse the file because file is damaged!");
		}
		name = name.replace("Name:", "").strip();

		if (!sizeString.matches("^Number of Vertex:\s[0-9]+$")) {
			throw new IllegalStateException("Cannot parse the file because file is damaged!");
		}
		size = Integer.parseInt(sizeString.replace("Number of Vertex:", "").strip());

		System.out.println(name);
		System.out.println(size);
		ArrayList<Vertex> V = new ArrayList<>();
		for (int i = 2; i < size + 2; i++) {
			V.add(Vertex.fromString(lines[i]));
		}
		ArrayList<Edge> E = new ArrayList<>();
		for (int i = 2 + size; i < size + size + 2; i++) {
			List<Double> t = Arrays.stream(lines[i].replace("[", "").replace("]", "").split(",")).map(Double::parseDouble).collect(Collectors.toList());
			if (t.size() != size) {
				throw new IllegalStateException("Cannot parse the file because file is damaged!");
			}
			for (int j = 0; j < size; j++) {
				if (t.get(j) != 0) {
					E.add(new Edge(V.get(i - size - 2), V.get(j), t.get(j)));
				}
			}
		}
		return new Graph(V, E, name);
	}

	static public Graph sampleGraph() {
		Vertex a = new Vertex("a", 200.0, 50.0);
		Vertex b = new Vertex("b", 300.0, 350.0);
		Vertex c = new Vertex("c", 1200.0, 50.0);
		Vertex d = new Vertex("d", 80.0, 450.0);
		Vertex e = new Vertex("e", 100.0, 200.0);

		ArrayList<Vertex> V = new ArrayList<>();
		V.add(a);
		V.add(b);
		V.add(c);
		V.add(d);
		V.add(e);

		Edge ab = new Edge(a, b, 4);
		Edge ba = new Edge(b, a, 2);

		Edge bc = new Edge(b, c, 5);
		Edge de = new Edge(d, e, 3);
		Edge ea = new Edge(e, a, 6);
		Edge be = new Edge(b, e, 1);
		Edge ac = new Edge(a, c, 2);
		Edge ca = new Edge(c, a, 2);

		ArrayList<Edge> E = new ArrayList<>();
//		E.add(ab);
//		E.add(ba);
//		E.add(bc);
//		E.add(de);
//		E.add(ea);
//		E.add(be);
		E.add(ac);
E.add(ca);

		String homePath = System.getProperty("user.home");
		String filepath = homePath + File.separator + "sample.graph";

		return new Graph(V, E, new Setting("Sample", filepath, true,true));
	}

}
