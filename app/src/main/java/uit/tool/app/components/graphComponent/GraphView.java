package uit.tool.app.components.graphComponent;

import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import uit.tool.app.components.Event.VertexEvent;
import uit.tool.app.graph.Edge;
import uit.tool.app.graph.Graph;
import uit.tool.app.graph.Vertex;
import uit.tool.app.interfaces.Loader;

import java.util.ArrayList;


public class GraphView extends AnchorPane implements Loader {

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
		this.setOnDragDropped((DragEvent event) -> {
			VertexView vertex = (VertexView) event.getGestureSource();
			AnchorPane.setTopAnchor(vertex, event.getY());
			AnchorPane.setLeftAnchor(vertex, event.getX());
			event.setDropCompleted(true);
			event.consume();
			this.fireEvent(new VertexEvent(VertexEvent.MOVE, vertex, event.getX(), event.getY()));

		});

		this.addEventFilter(VertexEvent.MOVE, this::handleVertexEvent);
	}

	public void handleVertexEvent(VertexEvent e) {
		updatePosition(e.getVertexView(), e.getNewX(), e.getNewY());
		renderGraph();
		System.out.printf("Width and Height: %f %f",this.getWidth(),this.getHeight());
	}

	public void renderGraph() {
		this.getChildren().clear();
		ArrayList<Vertex> V = this.graph.getVertexes();
		ArrayList<Edge> E = this.graph.getEdges();

		for (Edge e : E) {
			EdgeView ev = new EdgeView(e.getSource(),e.gerDestination());
			ev.toBack();
			this.getChildren().add(ev);
		}
		for (Vertex v : V) {
			this.getChildren().add(new VertexView(v));
		}


	}

	public void updatePosition(VertexView vertexView, double newX, double newY) {
		Vertex vertex = vertexView.getVertex();
		this.graph.updateVertexPosition(vertex, newX, newY);
	}

	public EdgeView drawLine(Vertex from, Vertex to) {
		return new EdgeView(from,to);
	}

}
