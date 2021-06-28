package uit.tool.app.components.visualizerComponent.graphComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import uit.tool.app.graph.Vertex;

public class EdgeView extends Path {
	private static final double defaultArrowHeadSize = 5;
	private static final double defaultArrowAngle = Math.PI / 6;  // 30 deg
	private static final double radius = 20;

	public static final String ARC_UP = "ARC_UP";
	public static final String ARC_DOWN = "ARC_DOWN";
	public static final String LINE = "Line";
	public static final String ARROW = "ARROW";

	EdgeView(Vertex source, Vertex destination, String type, boolean withArrow) {
		/**
		 * for arrow drawing:
		 * https://math.stackexchange.com/questions/1314006/drawing-an-arrow
		 * https://stackoverflow.com/questions/47079268/how-to-draw-arrow-head-with-coordinates
		 */

		super();
		setStroke(Color.BLACK);
		setStrokeWidth(2);

		double startX, startY, endX, endY;
//		adjust 20 make start/end point at center of the circle (not the top-left)
		startX = source.getX() + radius;
		startY = source.getY() + radius;
		endX = destination.getX() + radius;
		endY = destination.getY() + radius;


//		Vector v from start point to endpoint
		double vX = endX - startX;
		double vY = endY - startY;
		double vLength = Math.sqrt(vX * vX + vY * vY);


		getElements().add(new MoveTo(startX, startY));

		if (LINE.equals(type)) {
			getElements().add(new LineTo(endX, endY));
		} else if (ARC_UP.equals(type) || ARC_DOWN.equals(type)) {
//			vX == 0 || vY == 0 mean that start point or end point are horizontal line or vertical line
			boolean isArcUp = !ARC_DOWN.equals(type);
			double angle = Math.atan2(vY, vX);
			double radiusX = vLength / 2;
			double radiusY = vLength / 8;
			ArcTo arcTo = new ArcTo(radiusX, radiusY, Math.toDegrees(angle), endX, endY, false, isArcUp);
			getElements().add(arcTo);
		}
		if (withArrow) {
			double angle = defaultArrowAngle;
			double cos = Math.cos(angle);
			double sin = Math.sin(angle);

//			endpoint of the arrow is at the border, not the center
			endX = endX - radius * vX / vLength;
			endY = endY - radius * vY / vLength;

//			draw arrow wings
			double aX = cos * vX / vLength - sin * vY / vLength;
			double aY = sin * vX / vLength + cos * vY / vLength;
			double bX = cos * vX / vLength + sin * vY / vLength;
			double bY = cos * vY / vLength - sin * vX / vLength;

			getElements().add(new MoveTo(endX, endY));
			getElements().add(new LineTo(endX - defaultArrowHeadSize * aX, endY - defaultArrowHeadSize * aY));
			getElements().add(new LineTo(endX - defaultArrowHeadSize * bX, endY - defaultArrowHeadSize * bY));
			getElements().add(new LineTo(endX, endY));
		}

	}

	EdgeView(Vertex source, Vertex destination, String type) {
		this(source, destination, type, false);
	}

	EdgeView(Vertex source, Vertex destination) {
		this(source, destination, LINE, false);
	}


}
