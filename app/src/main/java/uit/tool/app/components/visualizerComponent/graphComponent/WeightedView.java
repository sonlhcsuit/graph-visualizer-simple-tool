package uit.tool.app.components.visualizerComponent.graphComponent;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import uit.tool.app.graph.Vertex;

public class WeightedView extends Label {
	/**
	 * Draw an text, present the weight of the edge
	 * @param source the vertex which edge starts
	 * @param destination the vertex which edge ends
	 * @param weight weight value in float
 	 * @param isLine if the edge was draw in a straight line or not (if not a straight line, it must be a arc)
	 */
	WeightedView(Vertex source, Vertex destination, double weight, boolean isLine) {
		super();
		this.setText(String.format("%.2f", weight));
//		start point
		double startX = source.getX();
		double startY = source.getY();
//		end point
		double endX = destination.getX();
		double endY = destination.getY();
//		Vector of start point & end point
		double vX = endX - startX;
		double vY = endY - startY;
		double vLength = Math.sqrt(vX * vX + vY * vY);

//		Middle point of start point & end point
		double pivotX = (startX + endX) / 2;
		double pivotY = (startY + endY) / 2;

//		angle of vector
		double angle = Math.atan2(vY, vX);
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

//		Middle point of start & end
		pivotX = (startX + endX) / 2;
		pivotY = (startY + endY) / 2;

//		point on Perpendicular Bisector:
		double pointX = pivotX;
		double pointY = pivotY;

		if (isLine){
			pointY = pointY - 25;
		}else {
			pointY = pointY - vLength/8;
		}
		pointX = pointX - pivotX;
		pointY = pointY - pivotY;
		double rX = pointX * cos - pointY * sin;
		double rY = pointX * sin + pointY * cos;
		rX = rX + pivotX;
		rY = rY + pivotY;

		AnchorPane.setLeftAnchor(this, rX);
		AnchorPane.setTopAnchor(this, rY);

	}

	WeightedView(Vertex source, Vertex destination, double weight) {
		this(source, destination, weight, true);
	}
}
