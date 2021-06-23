package uit.tool.app.components.graphComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import uit.tool.app.graph.Vertex;

public class EdgeView extends Path {
	private static final double defaultArrowHeadSize = 5;
	private static final double defaultArrowAngle = Math.PI / 6;  // 30 deg
	private static final double radius = 20;
	public static final String ARC_UP = "Arc_Up";
	public static final String ARC_DOWN = "Arc_Down";
	public static final String LINE = "Line";

	EdgeView(Vertex source, Vertex destination, String type) {
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

//		end point not terminate at center but at border
		double vX = endX - startX;
		double vY = endY - startY;
		double vLength = Math.sqrt(vX * vX + vY * vY);
		endX = endX - (radius - 0) * vX / vLength;
		endY = endY - (radius - 0) * vY / vLength;

		startX = startX + radius * vX / vLength;
		startY = startY + radius * vY / vLength;


		double angle = defaultArrowAngle;
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

//		draw arrow wings
		double aX = cos * vX / vLength - sin * vY / vLength;
		double aY = sin * vX / vLength + cos * vY / vLength;
		double bX = cos * vX / vLength + sin * vY / vLength;
		double bY = cos * vY / vLength - sin * vX / vLength;

		getElements().add(new MoveTo(startX, startY));

		if (LINE.equals(type)) {
//			Using line, good but not with more than one connection between 2 vertex
			getElements().add(new LineTo(endX, endY));
		} else {
//			Using arc (suck)
			boolean isArcUp = !ARC_DOWN.equals(type);
			ArcTo arcTo = new ArcTo(vX == 0 ? 20 : vX, vY == 0 ? 20 : vY, 0, endX, endY, false, isArcUp);
			getElements().add(arcTo);
		}


//		Using quad curve (suck,too)
//		double controlX = (startX + endX) / 2 - vX/2;
//		double controlY = (startY + endY) / 2 + vY/2;
//		QuadCurveTo quad = new QuadCurveTo(controlX,controlY,endX,endY);
//		getElements().add(quad);

		getElements().add(new LineTo(endX - defaultArrowHeadSize * aX, endY - defaultArrowHeadSize * aY));
		getElements().add(new LineTo(endX - defaultArrowHeadSize * bX, endY - defaultArrowHeadSize * bY));
		getElements().add(new LineTo(endX, endY));

	}

	EdgeView(Vertex source, Vertex destination) {
		this(source, destination, LINE);
	}


}
