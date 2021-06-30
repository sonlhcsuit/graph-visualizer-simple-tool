package uit.tool.app.components.animation;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uit.tool.app.components.visualizerComponent.graphComponent.VertexView;


public class Visited {

	private final FillTransition ft;
	public Visited(VertexView v) {
		this.ft = new FillTransition(Duration.millis(3000),v.getCircle(), Color.BLACK, Color.GRAY);
		ft.setCycleCount(4);
		ft.setAutoReverse(true);
	}

	public void play() {
		this.ft.play();
	}
}
