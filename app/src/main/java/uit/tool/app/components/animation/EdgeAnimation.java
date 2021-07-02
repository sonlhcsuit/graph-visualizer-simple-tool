package uit.tool.app.components.animation;

import javafx.animation.FillTransition;
import javafx.animation.StrokeTransition;
import javafx.animation.Transition;
import javafx.util.Duration;
import uit.tool.app.components.visualizerComponent.graphComponent.EdgeView;
import uit.tool.app.components.visualizerComponent.graphComponent.VertexView;
import uit.tool.app.graph.Edge;
import uit.tool.app.graph.Vertex;

public class EdgeAnimation extends VisualAnimation{
	private Edge edge;
	private StrokeTransition strokeTransition ;
	private EdgeView target;
	private final long time = 1000;
	EdgeAnimation(){
		this.strokeTransition= new StrokeTransition();
		this.strokeTransition.setDuration(Duration.millis(time));
	}

	EdgeAnimation(Edge e){
		this();
		this.edge = e;
	}
	EdgeAnimation(Vertex origin, Vertex destination){
		this();
		this.edge = new Edge(origin,destination);
	}
	public void setEdge(Edge edge) {
		this.edge = edge;
	}

	public Edge getEdge() {
		return edge;
	}

	 public void setTarget(EdgeView edgeView){
		this.target  = edgeView;
		this.strokeTransition.setShape(edgeView);
	}

	@Override
	public Transition getTransition() {
		return this.strokeTransition;
	}


	@Override
	public void setTransition(Transition transition) {

	}
}
