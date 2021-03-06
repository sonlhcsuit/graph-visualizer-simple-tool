package uit.tool.app.components.visualizerComponent.graphComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import uit.tool.app.graph.Edge;
import uit.tool.app.graph.Vertex;

import java.util.Objects;

enum Type{
	Arc,
	Line,
}
public class EdgeView extends Path {
	private static final double defaultArrowHeadSize = 5;
	private static final double defaultArrowAngle = Math.PI / 6;  // 30 deg
	private static final double radius = 20;
	private final Vertex source;
	private final Vertex destination;

	/**
	 * for arrow drawing:
	 * https://math.stackexchange.com/questions/1314006/drawing-an-arrow
	 * https://stackoverflow.com/questions/47079268/how-to-draw-arrow-head-with-coordinates
	 * @param source the vertex the edge starts
	 * @param destination the vertex the edge ends
	 * @param type type of the line, straight line or ellipse arc
	 * @param withArrow end of the point have arrow or not
	 */
	EdgeView(Vertex source, Vertex destination, Type type, boolean withArrow) {

		super();
		this.source = source;
		this.destination = destination;

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

		if (type == Type.Line ) {
			getElements().add(new LineTo(endX, endY));
		} else if (type == Type.Arc) {
//			vX == 0 || vY == 0 mean that start point or end point are horizontal line or vertical line
			double angle = Math.atan2(vY, vX);
			double radiusX = vLength / 2;
			double radiusY = vLength / 8;
			ArcTo arcTo = new ArcTo(radiusX, radiusY, Math.toDegrees(angle), endX, endY, false, true);
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
//			Left wing vector
			double leftVectorX = cos * vX / vLength - sin * vY / vLength;
			double leftVectorY = sin * vX / vLength + cos * vY / vLength;
//			Right wings vector
			double rightVectorX = cos * vX / vLength + sin * vY / vLength;
			double rightVectorY = cos * vY / vLength - sin * vX / vLength;

//			A & B is 2 bottom points which make the triangle
			double Ax, Ay, Bx, By;
			Ax = endX - defaultArrowHeadSize * leftVectorX;
			Ay = endY - defaultArrowHeadSize * leftVectorY;
			Bx = endX - defaultArrowHeadSize * rightVectorX;
			By = endY - defaultArrowHeadSize * rightVectorY;

			if (type == Type.Line) {
				getElements().add(new MoveTo(endX, endY));
				getElements().add(new LineTo(Ax, Ay));
				getElements().add(new LineTo(Bx, By));
				getElements().add(new LineTo(endX, endY));
			} else if (type == Type.Arc) {
//				center of end point
				double pivotX, pivotY;
				pivotX = destination.getX() + radius;
				pivotY = destination.getY() + radius;

				leftVectorX = endX - defaultArrowHeadSize * leftVectorX;
				leftVectorY = endY - defaultArrowHeadSize * leftVectorY;
				rightVectorX = endX - defaultArrowHeadSize * rightVectorX;
				rightVectorY = endY - defaultArrowHeadSize * rightVectorY;

//				Secret formula, not precious
				double ll = Math.log(vLength) / Math.log(2);
				double ROTATE_ANGLE = 20 + (ll - 6) * 12.5;

				double[] rotatedA = rotate(pivotX, pivotY, leftVectorX, leftVectorY, Math.PI * ROTATE_ANGLE / 180);
				double rAx = rotatedA[0];
				double rAy = rotatedA[1];

				double[] rotatedB = rotate(pivotX, pivotY, rightVectorX, rightVectorY, Math.PI * ROTATE_ANGLE / 180);
				double rBx = rotatedB[0];
				double rBy = rotatedB[1];

				double[] rotatedE = rotate(pivotX, pivotY, endX, endY, Math.PI * ROTATE_ANGLE / 180);
				double rEx = rotatedE[0];
				double rEy = rotatedE[1];
				getElements().add(new MoveTo(rEx, rEy));
				getElements().add(new LineTo(rAx, rAy));
				getElements().add(new LineTo(rBx, rBy));
				getElements().add(new LineTo(rEx, rEy));

			}

		}

	}

	EdgeView(Vertex source, Vertex destination, Type type) {
		this(source, destination, type, false);
	}

	EdgeView(Vertex source, Vertex destination) {
		this(source, destination, Type.Line, false);
	}

	EdgeView(Edge edge) {
		this(edge.getSource(), edge.getDestination());
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EdgeView that = (EdgeView) o;
		return Objects.equals(source, that.source) && Objects.equals(destination, that.destination);
	}

	/**
	 * A shallow version of equals, allow to match the edge A -> B with B -> A (bi-direction edge)
	 *
	 * @param o EdgeView to be compared with
	 * @return 2 edgeview are the same or not, just consider the edge (2 vertex at endpoint), not the order (direction of edge)
	 */
	public boolean shallowEquals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EdgeView that = (EdgeView) o;
		return (Objects.equals(source, that.source) && Objects.equals(destination, that.destination)) ||
				Objects.equals(source, that.destination) && Objects.equals(destination, that.source);
	}

	/**
	 * Rotate the point around pivot point a angle in radian
	 *
	 * @param pivotX X-coordinate value of the pivot point
	 * @param pivotY Y-coordinate value of the pivot point
	 * @param X      X-coordinate of the point to be rotated
	 * @param Y      Y-coordinate of the point to be rotated
	 * @param angle  angle in radian
	 * @return an 2-element array present X & Y of result point respectively
	 */
	private double[] rotate(double pivotX, double pivotY, double X, double Y, double angle) {
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		X = X - pivotX;
		Y = Y - pivotY;
		double rX = X * cos - Y * sin;
		double rY = X * sin + Y * cos;
		rX = rX + pivotX;
		rY = rY + pivotY;
		return new double[]{rX, rY};
	}


}
