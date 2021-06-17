package uit.tool.app.components.graphComponent;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
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
		this.setOnDragOver((DragEvent event) -> {
			event.acceptTransferModes(TransferMode.MOVE);
			event.consume();
		});
		this.setOnDragDropped(this::handleDroppedEvent);
//		this.setOnMouseClicked(event->{
//			System.out.printf("X: %.2f Y: %.2f\n",event.getX(),event.getY());
//		});
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
		this.updatePositionVertexView(event.getVertexView(), event.getRelativeX(), event.getRelativeY());
		renderGraph();
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

	}

	public void renderGraph() {
		this.maxOffsetX = 0;
		this.maxOffsetX = 0;

		this.graphArea.getChildren().clear();
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
		this.graphArea.setPrefWidth(maxOffsetX + 50);
		this.graphArea.setPrefHeight(maxOffsetY + 50);


	}

	public void updatePositionOfVertexView(VertexView vertexView, double relativeX, double relativeY) {
		/**
		 * When user drag &
		 * @param vertexView
		 */

		Vertex vertex = vertexView.getVertex();
//		this.graph.updateVertexPosition(vertex, newX, newY);
	}

	public EdgeView drawLine(Vertex from, Vertex to) {
		return new EdgeView(from, to);
	}

}
