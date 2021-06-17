package uit.tool.app.components.graphComponent;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import uit.tool.app.graph.Vertex;

public class WeightedView extends Label {
	WeightedView(Vertex source, Vertex destination,double weight){
		super();
		this.setText(String.format("%.2f",weight));
		startX = source.getX() + radius;
		startY = source.getY() + radius;
		endX = destination.getX() + radius;
		endY = destination.getY() + radius;
	}
}
