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
		this.setOnDragOver((DragEvent event) -> {
			event.acceptTransferModes(TransferMode.MOVE);
			event.consume();
		});
		this.setOnDragDropped(this::handleDroppedEvent);

		this.addEventFilter(VertexEvent.MOVE, this::handleVertexEvent);

////	debug purpose
//		this.setOnMouseClicked(event -> {
//			System.out.printf("Click X: %.2f Y: %.2f\n", event.getX(), event.getY());
//			System.out.printf("Size X: %.2f Y: %.2f\n", this.graphArea.getWidth(), this.graphArea.getHeight());
//			System.out.printf("Prefer Size X: %.2f Y: %.2f\n", this.getPrefWidth(), this.getPrefHeight());
//			System.out.printf("Viewport X: %.2f Y: %.2f \n", this.getViewportBounds().getWidth(), this.getViewportBounds().getHeight());
//			System.out.printf("Scrolled: X: %.2f Y: %.2f\n", this.getHvalue(), getVvalue()*(this.graphArea.getHeight() - this.getViewportBounds().getHeight()) );
//		});
	}

	private double syncAxeValue(double size, double viewport, double relative, double scrollPercent) {
		/**
		 * This method is using to compute absolute position of overflow object in clipped viewport in scrollpane
		 * The viewport will slide (pan) between the start point and end point of the line axe
		 * The viewport has it size, too. Scroll value is the difference of actual start point of the line content and the actual start
		 * point of viewport(measured in percent)
		 *
		 * Explain with example
		 *
		 *     |                          axe                              |
		 *     |-----------------------------------------------------------|
		 *     |  percent ||         |        ||
		 *                ||         X        ||
		 *                ||     viewport    ||
		 *
		 *
		 * @param size the size (how long) of the axe (the line axe)
		 * @param viewport the size(how long) of the viewport (the viewport line)
		 * @param relative the size(how long) from X to start point of the viewport
		 * @param scrollPercent the difference between start point of viewport and axe (measured in percent)
		 */

//		The different between actual edge vs relative edge (how many pixel was panned)
//		percentage is 1 as long as the current viewport can reach the lowest edge (end point of axe)
//		so we need to minus the viewport size to better accuracy
		double differenceAxe = (size - viewport) * scrollPercent;

		return differenceAxe + relative;
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
//		for (Vertex v : V) {
//			this.graphArea.getChildren().add(new VertexView(v));
//			this.maxOffsetX = Math.max(this.maxOffsetX, v.getX());
//			this.maxOffsetY = Math.max(this.maxOffsetY, v.getY());
//		}

		this.graphArea.setPrefWidth(maxOffsetX + 100);
		this.graphArea.setPrefHeight(maxOffsetY + 100);
	}

	public void handleVertexEvent(VertexEvent event) {
		/**
		 * This method is handle when a VertexView moved
		 * Update position of moved vertex by updating data in graph object then render new graph
		 * first by calculating absolute size of moved vertex
		 */

		Vertex vertex = event.getVertexView().getVertex();
		double absoluteX = syncAxeValue(
				this.graphArea.getWidth(), this.getViewportBounds().getWidth(),
				event.getRelativeX(), this.getHvalue()
		) - 20;
		double absoluteY = syncAxeValue(
				this.graphArea.getHeight(), this.getViewportBounds().getHeight(),
				event.getRelativeY(), this.getVvalue()
		) - 20;

		this.graph.updateVertexPosition(vertex, absoluteX, absoluteY);
		renderGraph();
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

	}
}
