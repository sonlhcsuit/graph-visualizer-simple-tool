package uit.tool.app.components.animation;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uit.tool.app.components.visualizerComponent.graphComponent.VertexView;
import uit.tool.app.graph.Vertex;


public class Visited extends VertexAnimation {

	//	private final FillTransition ft;
	public Visited(Vertex v) {
		super(v);
		((FillTransition) getTransition()).setToValue(Color.GREEN);
	}

	@Override
	public String toString() {
		return String.format("%s@%s{target:%s}", getClass().getSimpleName(), Integer.toHexString(hashCode()), getVertex());
	}
}
