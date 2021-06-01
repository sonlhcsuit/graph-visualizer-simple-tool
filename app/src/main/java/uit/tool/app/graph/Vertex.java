package uit.tool.app.graph;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

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

//	static StackPane toVertexNode(){
//		StackPane s = new StackPane();
//	}
}
