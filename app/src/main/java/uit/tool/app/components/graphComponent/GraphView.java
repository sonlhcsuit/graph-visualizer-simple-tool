package uit.tool.app.components.graphComponent;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import uit.tool.app.components.Event.VertexEvent;
import uit.tool.app.graph.Edge;
import uit.tool.app.graph.Graph;
import uit.tool.app.graph.Vertex;
import uit.tool.app.interfaces.Loader;

import java.util.ArrayList;


public class GraphView extends ScrollPane implements Loader {

	@FXML
	private AnchorPane graphArea;


	private double maxOffsetX;
	private double maxOffsetY;
	private Graph graph;

	public GraphView() {
		Loader.loadFXML(this);
		this.graph = Graph.sampleGraph();
		renderGraph();
	}


	public void initialize() {
//		this.setVmax(1000);
		this.setOnDragOver((DragEvent event) -> {
			event.acceptTransferModes(TransferMode.MOVE);
			event.consume();
		});
		this.graphArea.setPrefSize(400, 1500);
		//		this.setOnDragDropped(this::handleDroppedEvent);
		this.setOnMouseClicked(event -> {

			System.out.printf("Click X: %.2f Y: %.2f\n", event.getX(), event.getY());
			System.out.printf("Size X: %.2f Y: %.2f\n", this.graphArea.getWidth(), this.graphArea.getHeight());
			System.out.printf("Prefer Size X: %.2f Y: %.2f\n", this.getPrefWidth(), this.getPrefHeight());

			System.out.printf("Viewport X: %.2f Y: %.2f \n", this.getViewportBounds().getWidth(), this.getViewportBounds().getHeight());
			System.out.printf("Scrolled: X: %.2f Y: %.2f\n", this.getHvalue(), getVvalue() );
		});
//		this.height = this.graphArea.getHeight();
//		this.width = this.graphArea.getWidth();

		this.addEventFilter(VertexEvent.MOVE, this::handleVertexEvent);
	}


	private void handleDroppedEvent(DragEvent event) {
		/**
		 * Using this method to handle some computation when drop a vertex to Graph View
		 * @param e The drag event containing information
		 * @see VertexView
		 * @see Vertex
		 */

//		only allow vertex can be dragged & dropped, defined which Vertex are involved.
		VertexView vertex = (VertexView) event.getGestureSource();
		double relativeX = event.getX();
		double relativeY = event.getY();
		this.fireEvent(new VertexEvent(VertexEvent.MOVE, vertex, relativeX, relativeY));
		event.setDropCompleted(true);
		event.consume();
//		System.out.printf("re-X: %.2f re-Y: %.2f\n", event.getX(), event.getY());


	}

	private void syncVertexCoordinate(VertexView v, double newX, double newY) {
		/*this function is t
		 * */
		boolean isGraphAreaBiggerThanViewPort = false;
//		if (this.width )
		double oldX = AnchorPane.getLeftAnchor(v);
		double oldY = AnchorPane.getTopAnchor(v);
//		double dX = n - newY;
//		double dY = new
//		if (oldX )

	}

	public void handleVertexEvent(VertexEvent event) {
		/**
		 * This method is handle when a VertexView moved
		 * Update position of moved vertex by updating data in graph object then render new graph
		 * first by calculating absolute size of moved vertex
		 */

		Vertex vertex = event.getVertexView().getVertex();
//		Boundary of the scroll pane
		Bounds bound = this.getViewportBounds();
		double boundX = bound.getWidth();
		double boundY = bound.getHeight();

//		Scroll position of scroll pane, how many percent of date that viewport occupied (clipped), and what

		double scrollX = this.getHvalue();
		double scrollY = this.getVvalue();

		double relativeX = event.getRelativeX();
		double relativeY = event.getRelativeY();
		double absoluteX = 0;
		double absoluteY = 0;

//		with an assumption that we must calculate the different between old position (before moving) with vertex
		double oldX = vertex.getX();
		double oldY = vertex.getY();

		double difX = oldX - relativeX;
		double difY = oldX - relativeY;


//		Not overflow or less overflow, so relative & absolute are nearly the same;
		absoluteX = oldX + difX;
		absoluteY = oldY + difY;


//		Viewport Bound

//		this.graph.updateVertexPosition(vertex, absoluteX, absoluteY);
		//
//
//		if (this.graphArea.getWidth() - this.width > 10) {
//			this.width = this.graphArea.getWidth() + 100;
//			this.graphArea.setPrefWidth(this.width);
//		}
//		if (this.graphArea.getHeight() - this.height > 10) {
//			this.height = this.graphArea.getHeight() + 100;
//			this.graphArea.setPrefHeight(this.height);
//		}
//		renderGraph();


	}

	public void renderGraph() {
		this.maxOffsetX = 0;
		this.maxOffsetX = 0;

		this.graphArea.getChildren().clear();
		this.graphArea.getChildren().add(new Line(0,1000,400,1000));
		ArrayList<Vertex> V = this.graph.getVertexes();
		ArrayList<Edge> E = this.graph.getEdges();
		for (Edge e : E) {
			EdgeView ev = new EdgeView(e.getSource(), e.gerDestination());
			ev.toBack();
			this.graphArea.getChildren().add(ev);
		}
		for (Vertex v : V) {
			this.graphArea.getChildren().add(new VertexView(v));
			this.maxOffsetX = Math.max(this.maxOffsetX, v.getX());
			this.maxOffsetY = Math.max(this.maxOffsetY, v.getY());
		}
//		this.graphArea.setPrefWidth(maxOffsetX + 50);
//		this.graphArea.setPrefHeight(maxOffsetY + 50);


	}

//	public void updateVertexViewPosition(VertexView vertexView, double x, double y) {
//		/**
//		 * When user drag & drop VertexView on the application, the Vertex moves to new position
//		 * the position is relative because of using ScrollPane
//		 *
//		 * @param vertexView
//		 * @param relativeX
//		 * @param relativeY
//		 *
//		 * @see ScrollPane
//		 * @see VertexView
//		 */
//		Vertex vertex = vertexView.getVertex();
////		update information in the graph object (core object & re-render the graph)
//		this.graph.updateVertexPosition(vertex, x, y);
//	}

	public EdgeView drawLine(Vertex from, Vertex to) {
		return new EdgeView(from, to);
	}

}
