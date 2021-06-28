package uit.tool.app.components.visualizerComponent;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import uit.tool.app.components.visualizerComponent.graphComponent.GraphView;
import uit.tool.app.components.visualizerComponent.matrixComponent.MatrixView;
import uit.tool.app.graph.Graph;
import uit.tool.app.interfaces.Loader;

public class VisualizerView extends TabPane implements Loader {
	@FXML
	private GraphView graphView;
	@FXML
	private MatrixView matrixView;
	@FXML
	private Graph graph;

	public VisualizerView() {
		Loader.loadFXML(this);
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public Graph getGraph() {
		return graph;
	}

	public void initialize(){

	}
}
