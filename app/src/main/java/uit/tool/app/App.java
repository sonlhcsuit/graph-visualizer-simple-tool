package uit.tool.app;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import uit.tool.app.components.Event.EdgeEvent;
import uit.tool.app.components.Event.VertexEvent;
import uit.tool.app.components.Logger;
import uit.tool.app.components.graphComponent.GraphView;
import uit.tool.app.components.matrixComponent.MatrixView;
import uit.tool.app.components.navigationComponent.Navigation;
import uit.tool.app.graph.Graph;
import uit.tool.app.interfaces.Loader;

import java.io.File;
import java.io.FileWriter;


public class App extends BorderPane implements Loader {


	private boolean isMenuOpen;
//	@FXML
//	private Label hehe;
//	@FXML
//	private ImageView img;
//	@FXML
//	private VBox side;
	@FXML
	private Navigation navigation;
	@FXML
	private Logger logger;
	@FXML
	private GraphView graphView;
	@FXML
	private MatrixView matrixView;

	private Graph graph;
	private Callback<String, Void> writeLog;


	public App(){
		Loader.loadFXML(this);
	}

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


		this.navigation.getOpenFunc().setMenuFunction(this.openGraph);
		this.navigation.getSaveFunc().setMenuFunction(this.saveGraph);

//
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


	private final Callback<Void, Void> openGraph = (unused) -> {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open a new graph");
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Graph Files (*.graph)", "*.graph"),
				new FileChooser.ExtensionFilter("All Files","?.*")
		);
		String homePath = System.getProperty("user.home");
		fc.setInitialDirectory(new File(homePath));
		File f = fc.showOpenDialog(null);
		if (f != null) {
			System.out.println("Load Graph");
		}
		return null;
	};

	private final Callback<Void, Void> saveGraph = (unused) -> {
		FileChooser fc = new FileChooser();
		fc.setTitle("Save graph");
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Graph Files (*.graph)", "*.graph"),
				new FileChooser.ExtensionFilter("All Files","?.*")
		);
		String homePath = System.getProperty("user.home");
		fc.setInitialDirectory(new File(homePath));
		File f = fc.showSaveDialog(null);
		if (f != null) {
			try {
				FileWriter fw = new FileWriter(f.getAbsolutePath());
				fw.write("saved !");
				fw.close();
			}catch (Exception e){
				e.printStackTrace();
			}

		}
		return null;
	};
}
