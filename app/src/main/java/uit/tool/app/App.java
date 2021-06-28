package uit.tool.app;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import uit.tool.app.components.Event.EdgeEvent;
import uit.tool.app.components.Event.SettingEvent;
import uit.tool.app.components.Event.VertexEvent;
import uit.tool.app.components.Logger;
import uit.tool.app.components.menuComponent.Menu;
import uit.tool.app.components.visualizerComponent.VisualizerView;
import uit.tool.app.components.visualizerComponent.graphComponent.GraphView;
import uit.tool.app.components.visualizerComponent.matrixComponent.MatrixView;
import uit.tool.app.components.navigationComponent.Navigation;
import uit.tool.app.graph.Graph;
import uit.tool.app.interfaces.Loader;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


public class App extends BorderPane implements Loader {


	private boolean isMenuOpen;
	//	@FXML
//	private Label hehe;
//	@FXML
//	private ImageView img;
//	@FXML
//	private VBox side;
	@FXML
	private VisualizerView visualizerView;
	@FXML
	private Navigation navigation;
	@FXML
	private Logger logger;
	@FXML
	private Menu menu;

	private Graph graph;


	public App() {
		Loader.loadFXML(this);
	}

	public void initialize() {
		isMenuOpen = true;
		this.visualizerView.setLogger(this.logger);
//
//		this.graphView.setLogger(this.logger);
//		this.matrixView.setLogger(this.logger);
//
////		Set up function for navigation
//		this.navigation.getOpenFunc().setMenuFunction(this.openGraph);
//		this.navigation.getSaveFunc().setMenuFunction(this.saveGraph);


		Graph g = Graph.sampleGraph();
		this.graph = g;
		this.visualizerView.setGraph(g);
		this.menu.setSetting(g.getSetting());


		this.addEventFilter(SettingEvent.SAVE_GRAPH, (SettingEvent event) -> {
			try {
				Graph.save(this.graph);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		this.addEventFilter(SettingEvent.TOGGLE_DIRECTED, (SettingEvent event) -> {
			this.visualizerView.render();
		});

		this.addEventFilter(SettingEvent.TOGGLE_WEIGHTED, (SettingEvent event) -> {
			this.visualizerView.render();
		});
//
////		Set event filter, whenever graph change, automatically render new view
//		this.addEventHandler(EdgeEvent.UPDATE_WEIGHT, this::weightHandler);
//		this.addEventHandler(VertexEvent.REMOVE, this::removeEventHandler);
//		this.addEventHandler(VertexEvent.RENAME, this::renameEventHandler);
//		this.addEventHandler(VertexEvent.ADD, this::addEventFilter);
	}
//
//	private void updateGraph(Graph g) {
//		this.graph = g;
//		this.graphView.setGraph(g);
//		this.matrixView.setGraph(g);
//		this.graphView.render();
//		this.matrixView.render();
//	}
//
//	public void weightHandler(EdgeEvent event) {
//		this.graph.updateEdge(event.getRow(), event.getCol(), event.getWeight());
//		this.graphView.render();
//	}
//
//	private void addEventFilter(VertexEvent event) {
//		System.out.println("hihi");
//		render();
//	}

//	private void renameEventHandler(VertexEvent event) {
//		render();
//	}
//
//	private void removeEventHandler(VertexEvent event) {
//		render();
//	}
//
//	public void render() {
//		this.graphView.render();
//		this.matrixView.render();
//	}


	private final Callback<Void, Void> openGraph = (unused) -> {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open a new graph");
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Graph Files (*.graph)", "*.graph"),
				new FileChooser.ExtensionFilter("All Files", "?.*")
		);
		String homePath = System.getProperty("user.home");
		fc.setInitialDirectory(new File(homePath));
		File file = fc.showOpenDialog(null);
		try {
			Graph g = Graph.load(file.getAbsolutePath());
			this.visualizerView.setGraph(g);
			Stage primStage = (Stage) getScene().getWindow();
			primStage.setTitle(String.format("GVST - %s", g.getSetting().getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	};

	private final Callback<Void, Void> saveGraph = (unused) -> {
		FileChooser fc = new FileChooser();
		fc.setTitle("Save graph");
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Graph Files (*.graph)", "*.graph"),
				new FileChooser.ExtensionFilter("All Files", "?.*")
		);
		String homePath = System.getProperty("user.home");
		fc.setInitialDirectory(new File(homePath));
		File f = fc.showSaveDialog(null);
		if (f != null) {
			try {
				FileWriter fw = new FileWriter(f.getAbsolutePath());
				String data = Graph.convertToFileString(graph, "Graph A");
				fw.write(data);
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	};
}
