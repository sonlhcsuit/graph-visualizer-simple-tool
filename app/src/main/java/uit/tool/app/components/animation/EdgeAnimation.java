package uit.tool.app.components.animation;

import uit.tool.app.graph.Edge;
import uit.tool.app.graph.Vertex;

public class EdgeAnimation extends VisualAnimation{
	private Edge edge;

	EdgeAnimation(){

	}

	EdgeAnimation(Edge e){
		this.edge = e;
	}
	EdgeAnimation(Vertex origin, Vertex destination){
		this.edge = new Edge(origin,destination);
	}
	public void setEdge(Edge edge) {
		this.edge = edge;
	}

	public Edge getEdge() {
		return edge;
	}

}
