package uit.tool.app.components.visualizerComponent.graphComponent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import uit.tool.app.graph.Edge;
import uit.tool.app.graph.Vertex;

import java.util.Objects;

public class EdgeView extends Path {
	private static final double defaultArrowHeadSize = 5;
	private static final double defaultArrowAngle = Math.PI / 6;  // 30 deg
	private static final double radius = 20;

	public static final String ARC = "ARC";
	public static final String LINE = "Line";


	private Vertex source;
	private Vertex destination;
	EdgeView(Vertex source, Vertex destination, String type, boolean withArrow) {
		/**
		 * for arrow drawing:
		 * https://math.stackexchange.com/questions/1314006/drawing-an-arrow
		 * https://stackoverflow.com/questions/47079268/how-to-draw-arrow-head-with-coordinates
		 */
		super();
		this.source = source;
		this.destination =destination;

		setStroke(Color.BLACK);
		setStrokeWidth(3);

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
		} else if (ARC.equals(type)) {
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

			if (LINE.equals(type)) {
				getElements().add(new MoveTo(endX, endY));
				getElements().add(new LineTo(Ax, Ay));
				getElements().add(new LineTo(Bx, By));
				getElements().add(new LineTo(endX, endY));
			} else if (ARC.equals(type)) {
//				center of end point
				double pivotX, pivotY;
				pivotX = destination.getX() + radius;
				pivotY = destination.getY() + radius;

				leftVectorX = endX - defaultArrowHeadSize * leftVectorX;
				leftVectorY = endY - defaultArrowHeadSize * leftVectorY;
				rightVectorX = endX - defaultArrowHeadSize * rightVectorX;
				rightVectorY = endY - defaultArrowHeadSize * rightVectorY;

//				Secret formula
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

	EdgeView(Vertex source, Vertex destination, String type) {
		this(source, destination, type, false);
	}

	EdgeView(Vertex source, Vertex destination) {
		this(source, destination, LINE, false);
	}

	EdgeView(Edge edge){
		this(edge.getSource(),edge.getDestination());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EdgeView that = (EdgeView) o;
		return Objects.equals(source, that.source) && Objects.equals(destination,that.destination);
	}
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
