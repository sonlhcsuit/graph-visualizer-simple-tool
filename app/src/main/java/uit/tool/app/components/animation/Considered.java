package uit.tool.app.components.animation;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import uit.tool.app.graph.Vertex;

public class Considered extends EdgeAnimation{
	public Considered(Vertex origin, Vertex destination) {
		super(origin,destination);
		((FillTransition) getTransition()).setToValue(Color.BLUE);
	}
}
