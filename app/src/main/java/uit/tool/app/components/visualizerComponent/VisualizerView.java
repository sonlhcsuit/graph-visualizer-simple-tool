package uit.tool.app.components.visualizerComponent;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import uit.tool.app.components.Logger;
import uit.tool.app.components.animation.VertexAnimation;
import uit.tool.app.components.animation.VisualAnimation;
import uit.tool.app.components.visualizerComponent.graphComponent.GraphView;
import uit.tool.app.components.visualizerComponent.matrixComponent.MatrixView;
import uit.tool.app.graph.Graph;
import uit.tool.app.graph.Vertex;
import uit.tool.app.interfaces.Loader;

import java.util.ArrayList;

public class VisualizerView extends TabPane implements Loader {
	@FXML
	private GraphView graphView;
	@FXML
	private MatrixView matrixView;
	@FXML
	private Graph graph;

	private Logger logger;

	public VisualizerView() {
		Loader.loadFXML(this);
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
		this.graphView.setGraph(graph);
		this.matrixView.setGraph(graph);
		this.graphView.render();
		this.matrixView.render();
	}

	public Graph getGraph() {
		return graph;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
		this.graphView.setLogger(this.logger);
		this.matrixView.setLogger(this.logger);
	}

	public Logger getLogger() {
		return logger;
	}

	public void initialize(){

	}
	public void render(){
		this.graphView.render();
		this.matrixView.render();
	}

	public void renderAnimation(ArrayList<VisualAnimation> sequence ){
		this.graphView.renderAnimation(sequence);
	}
}
