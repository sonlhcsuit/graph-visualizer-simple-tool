package uit.tool.app.graph;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.text.ParseException;

public class Vertex {
	private Double x;
	private Double y;
	private String name;

	public Vertex(String name, Double x, Double y) {
		this.x = x;
		this.y = y;
		this.name = name;
	}

	Vertex() {
		name = "A";
		x = 0.0;
		y = 0.0;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getX() {
		return x;
	}


	public void setY(Double y) {
		this.y = y;
	}

	public Double getY() {
		return y;
	}

	public String toFileString() {
		return String.format("%s %.6f %.6f", name, x, y);
	}

	public static Vertex fromString(String s) throws IllegalStateException {
		String[] elements = s.strip().split(" ");
		if (elements.length != 3) {
			throw new IllegalStateException("Vertex string are not in correct format!");
		}
		String name = elements[0];
		String x = elements[1];
		String y = elements[2];
		try {
			return new Vertex(name, Double.parseDouble(x), Double.parseDouble(y));
		} catch (NumberFormatException e) {
			throw new IllegalStateException("Vertex string are not in correct format!");
		}
	}

	@Override
	public String toString() {
		return String.format("Vertex@%s{x:%f,y:%f,name:'%s'}", Integer.toHexString(hashCode()), x, y, name);
//		
	}

	//	static StackPane toVertexNode(){
//		StackPane s = new StackPane();
//	}
}
