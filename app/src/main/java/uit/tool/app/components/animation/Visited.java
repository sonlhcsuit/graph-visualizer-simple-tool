package uit.tool.app.components.animation;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uit.tool.app.components.visualizerComponent.graphComponent.VertexView;


public class Visited {

	private final FillTransition ft;
	public Visited(VertexView v) {
		this.ft = new FillTransition(Duration.millis(1000),v.getCircle(), Color.BLACK, Color.GRAY);
	}

	public void play() {
		this.ft.play();
	}
}
