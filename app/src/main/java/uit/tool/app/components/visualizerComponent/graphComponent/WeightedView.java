package uit.tool.app.components.visualizerComponent.graphComponent;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import uit.tool.app.graph.Vertex;

public class WeightedView extends Label {
	WeightedView(Vertex source, Vertex destination, double weight) {
		super();
		this.setText(String.format("%.2f", weight));
		double startX = source.getX();
		double startY = source.getY();
		double endX = destination.getX();
		double endY = destination.getY();
		double vX = endX - startX;
		double vY = endY - startY;

		AnchorPane.setLeftAnchor(this, (startX + endX) / 2 + vX * 0.05);
		AnchorPane.setTopAnchor(this, (startY + endY) / 2 - vY * 0.05);
//		this.setFont(new Font(20));
	}
}
