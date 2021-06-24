package uit.tool.app;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.util.Callback;
import uit.tool.app.components.Event.EdgeEvent;
import uit.tool.app.components.Event.VertexEvent;
import uit.tool.app.components.Logger;
import uit.tool.app.components.graphComponent.GraphView;
import uit.tool.app.components.matrixComponent.MatrixView;
import uit.tool.app.graph.Graph;
import uit.tool.app.graph.Vertex;


public class App extends BorderPane {

	private boolean isMenuOpen;
	@FXML
	private Label hehe;
	@FXML
	private ImageView img;
	@FXML
	private VBox side;
	@FXML
	private Logger logger;
	@FXML
	private GraphView graphView;
	@FXML
	private MatrixView matrixView;

	private Graph graph;
	private Callback<String, Void> writeLog;

	public void initialize() {
		isMenuOpen = true;
		this.graph = Graph.sampleGraph();
		this.writeLog = (String message) -> {
			this.logger.writeLog(message);
			return null;
		};
		this.graphView.setGraph(this.graph);
		this.matrixView.setGraph(this.graph);
		render();
		this.graphView.setWriteLog(this.writeLog);


		this.matrixView.addEventFilter(EdgeEvent.UPDATE_WEIGHT, this::weightHandler);
		this.graphView.addEventFilter(VertexEvent.REMOVE, this::removeEventHandler);
		this.graphView.addEventFilter(VertexEvent.RENAME, this::renameEventHandler);
		this.graphView.addEventFilter(VertexEvent.ADD, this::addEventFilter);

	}
	public void weightHandler(EdgeEvent event) {
		this.graph.updateEdge(event.getRow(), event.getCol(), event.getWeight());
		this.graphView.render();
	}

	private void addEventFilter(VertexEvent event) {
		render();
	}

	private void renameEventHandler(VertexEvent event) {
		render();
	}


	private void removeEventHandler(VertexEvent event) {
		render();
	}

	public void render() {
		this.graphView.render();
		this.matrixView.render();
	}



	public void click() {
		if (isMenuOpen) {
			this.side.setPrefWidth(64);
		} else {
			this.side.setPrefWidth(256);
		}
		isMenuOpen = !isMenuOpen;
	}

}
