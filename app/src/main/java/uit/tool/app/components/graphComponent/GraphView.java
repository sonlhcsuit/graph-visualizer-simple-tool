package uit.tool.app.components.graphComponent;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import uit.tool.app.components.Event.VertexEvent;
import uit.tool.app.graph.Graph;
import uit.tool.app.graph.Vertex;
import uit.tool.app.interfaces.Loader;

import java.util.ArrayList;


public class GraphView extends AnchorPane implements Loader {


	public GraphView() {
		Loader.loadFXML(this);
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
			this.fireEvent(new VertexEvent(VertexEvent.MOVE, vertex));

		});

		this.addEventFilter(VertexEvent.MOVE,this::handleVertexEvent);
	}
	public void handleVertexEvent(VertexEvent e){
		System.out.println(e.getVertexView());
		System.out.println("moved");
	}

	public void renderGraph() {
		Graph graph = Graph.sampleGraph();
		ArrayList<Vertex> V = graph.getVertexes();

		for (Vertex v : V) {
			this.getChildren().add(new VertexView(v.getX(), v.getY(), v.getName()));
		}
	}

	public void drawLine(VertexView from ,VertexView to){

	}

}
