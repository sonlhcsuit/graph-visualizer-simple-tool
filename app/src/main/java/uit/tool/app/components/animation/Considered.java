package uit.tool.app.components.animation;

import javafx.animation.StrokeTransition;
import javafx.scene.paint.Color;
import uit.tool.app.graph.Vertex;

public class Considered extends EdgeAnimation{
	public Considered(Vertex origin, Vertex destination) {
		super(origin,destination);
		((StrokeTransition) getTransition()).setToValue(Color.BLUE);
	}
}
