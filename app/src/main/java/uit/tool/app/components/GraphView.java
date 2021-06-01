package uit.tool.app.components;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import uit.tool.app.graph.Graph;
import uit.tool.app.graph.Vertex;
import uit.tool.app.interfaces.Loader;

import java.util.ArrayList;


public class GraphView extends AnchorPane implements Loader {


	public GraphView() {
		Loader.loadFXML(this);
	}


	public void initialize() {
		this.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != this && event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}

				event.consume();
			}
		});
		this.setOnDragDropped((DragEvent event) -> {

			Dragboard db = event.getDragboard();
			System.out.println(event.getGestureSource());
			if (db.hasString()) {
				System.out.println("Dropped: " + db.getString());
				System.out.printf("%f %f", event.getX(), event.getY());
				AnchorPane.setTopAnchor((VertexNode) event.getGestureSource(), event.getY());
				AnchorPane.setLeftAnchor((VertexNode) event.getGestureSource(), event.getX());

				event.setDropCompleted(true);
			} else {
				event.setDropCompleted(false);
			}
			event.consume();
		});
//		this.setOnMouseClicked((MouseEvent e) -> {
//			System.out.println(this.getWidth());
//			System.out.println(this.getHeight());
//		});
//		renderGraph();

	}

	public void renderGraph() {
		Graph graph = Graph.sampleGraph();

		ArrayList<Vertex> V = graph.getVertexes();

		for (Vertex v : V) {
			this.getChildren().add(new Circle(v.getX(), v.getY(), 20));
		}


	}

}
