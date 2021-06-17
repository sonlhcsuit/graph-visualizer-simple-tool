package uit.tool.app.components.graphComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import uit.tool.app.graph.Vertex;

public class EdgeView extends Path {
	private static final double defaultArrowHeadSize = 10.0;
	private static final double defaultArrowAngle = Math.PI/6;  // 30 deg


	EdgeView(Vertex source, Vertex destination) {
		super();
		strokeProperty().bind(fillProperty());
		setFill(Color.BLACK);
		double startX, startY, endX, endY;
		startX = source.getX() + 20;
		startY = source.getY() + 20;
		endX = destination.getX() + 20;
		endY = destination.getY() + 20;
		getElements().add(new MoveTo(startX, startY));
		getElements().add(new LineTo(endX, endY));

//		Calculate angle of the line
//		double angelRad = Math.atan2(endX,endY);
		double

		double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		QuadCurveTo quadCurveTo = new QuadCurveTo();
		quadCurveTo.setX(120.0f);
		quadCurveTo.setY(60.0f);
		quadCurveTo.setControlX(100.0f);
		quadCurveTo.setControlY(0.0f);
		getElements().add(quadCurveTo);




		//point1
		double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * defaultArrowHeadSize + endX;
		double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * defaultArrowHeadSize + endY;
//		//point2
//		double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * defaultArrowHeadSize + endX;
//		double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * defaultArrowHeadSize + endY;
//
//		getElements().add(new LineTo(x1, y1));
//		getElements().add(new LineTo(x2, y2));
//		getElements().add(new LineTo(endX, endY));
//
getElements().add(arrowHead);
	}

	EdgeView(Vertex source, Vertex destination, double weighted) {
		this(source, destination);
		System.out.println(weighted);
	}

}
