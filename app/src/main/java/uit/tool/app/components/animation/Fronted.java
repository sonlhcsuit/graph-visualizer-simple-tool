package uit.tool.app.components.animation;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import uit.tool.app.graph.Vertex;

public class Fronted extends VertexAnimation{

	public Fronted(Vertex v) {
		super(v);
		((FillTransition) getTransition()).setToValue(Color.ORANGE);
	}

}
