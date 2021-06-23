package uit.tool.app;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.util.Callback;
import uit.tool.app.components.Event.EdgeWeight;
import uit.tool.app.components.Logger;
import uit.tool.app.components.graphComponent.GraphView;
import uit.tool.app.components.matrixComponent.MatrixView;
import uit.tool.app.graph.Graph;


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
		this.matrixView.render();
		this.graphView.render();
		this.graphView.setWriteLog(this.writeLog);
		this.matrixView.addEventFilter(EdgeWeight.UPDATE,this::weightHandler);

	}
	public void weightHandler(EdgeWeight event){
		this.graph.updateWeight(event.getRow(),event.getCol(),event.getWeight());
		this.graphView.render();
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
