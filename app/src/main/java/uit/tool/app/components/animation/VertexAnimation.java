package uit.tool.app.components.animation;

import javafx.animation.FillTransition;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uit.tool.app.components.visualizerComponent.graphComponent.VertexView;
import uit.tool.app.graph.Edge;
import uit.tool.app.graph.Vertex;

public class VertexAnimation extends VisualAnimation{
	private Vertex vertex ;
	private FillTransition fillTransition;
	private VertexView target;
	private final long time = 1000;
	VertexAnimation(){
		this.fillTransition= new FillTransition();
		this.fillTransition.setDuration(Duration.millis(time));
	}

	VertexAnimation(Vertex vertex){
		this();
		this.vertex = vertex;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	@Override
	public Transition getTransition() {
		return fillTransition;
	}

	@Override
	public void setTransition(Transition transition) {
		this.fillTransition = (FillTransition) transition;
	}

	public void setTarget(VertexView target) {
		this.target = target;
		this.fillTransition.setShape(target.getCircle());
	}

	public VertexView getTarget() {
		return target;
	}



	@Override
	public void play() {
		this.fillTransition.play();
	}
}
